package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.Consolidation;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignmentstatus.ConsignmentStatus;
import com.courier.overc360.api.midmile.primary.model.maps.GeoInfo;
import com.courier.overc360.api.midmile.primary.model.pickup.*;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentStatusRepository;
import com.courier.overc360.api.midmile.primary.repository.PickupEntityRepository;
import com.courier.overc360.api.midmile.primary.repository.RiderAssignmentImageReferenceRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.delivery.DeliveryStatusCount;
import com.courier.overc360.api.midmile.replica.model.delivery.StatusCountInput;
import com.courier.overc360.api.midmile.replica.model.pickup.*;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPickupDestinationDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPickupDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPickupEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class PickupService {

    @Autowired
    PickupEntityRepository pickupEntityRepository;

    @Autowired
    ConsignmentStatusRepository consignmentStatusRepository;

    @Autowired
    ReplicaPickupDetailsRepository replicaPickupDetailsRepository;

    @Autowired
    ReplicaPickupDestinationDetailsRepository replicaPickupDestinationDetailsRepository;

    @Autowired
    ReplicaPickupEntityRepository replicaPickupEntityRepository;

    @Autowired
    NumberRangeService numberRangeService;

    @Autowired
    RiderAssignmentImageReferenceRepository riderAssignmentImageReferenceRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    NprService nprService;

    @Autowired
    ReschedulePickupService reschedulePickupService;

    @Autowired
    GeoInfoService geoInfoService;

    @Autowired
    ConsignmentStatusService consignmentStatusService;

    @Autowired
    NotificationService notificationService;

    // Synchronized for GenerateNumberRange
    public synchronized String generateUniqueNumberRange(String numRanObj) {
        return numberRangeService.getNextNumberRange(numRanObj);
    }

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/
    // Get Pickup
    private PickupEntity getPickup(String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId) {

        Optional<PickupEntity> pickup = pickupEntityRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
                languageId, companyId, partnerId, houseAirwayBill, pickupId, 0L);
        if (pickup.isEmpty()) {
            throw new BadRequestException("Pickup with given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", houseAirwayBill - " + houseAirwayBill
                    + " and pickupId - " + pickupId + " doesn't exists");
        }
        return pickup.get();
    }


    // Consignment To Pickup Create
    public List<PickupEntity> createPickupConsignment(List<ConsignmentEntity> consignmentEntityList, String loginUserID) {

        List<AddPickup> pickupEntities = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        consignmentEntityList.stream().forEach(consignmentEntity -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    AddPickup newPickup = new AddPickup();
                    BeanUtils.copyProperties(consignmentEntity, newPickup, CommonUtils.getNullPropertyNames(consignmentEntity));

                    if (consignmentEntity.getDestinationDetails() != null) {
                        AddPickupDestinationDetails newDest = new AddPickupDestinationDetails();
                        BeanUtils.copyProperties(consignmentEntity.getDestinationDetails(), newDest);
                        newPickup.setDestinationDetails(newDest);
                    }
                    if (consignmentEntity.getOriginDetails() != null) {
                        AddPickupDetails newOrigin = new AddPickupDetails();
                        BeanUtils.copyProperties(consignmentEntity.getOriginDetails(), newOrigin);
                        newPickup.setPickupDetails(newOrigin);
                    }
                    // Set PieceId
                    String pieceId = consignmentEntity.getPieceDetails().stream().map(PieceDetails::getPieceId).filter(Objects::nonNull).collect(Collectors.joining(","));
                    // PieceCount
                    newPickup.setPieceCount(String.valueOf(consignmentEntity.getPieceDetails().stream().count()));
                    newPickup.setPieceId(pieceId);
                    if (consignmentEntity.getCodAmount() != null && !consignmentEntity.getCodAmount().isEmpty()) {
                        newPickup.setCodAmount(Double.valueOf(consignmentEntity.getCodAmount()));
                    }
                    pickupEntities.add(newPickup);

                } catch (Exception e) {
                    throw new RuntimeException("Consignment to pickup create failed " + e.getMessage());
                }
            }, executor);
            futures.add(future);
        });
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        // Send Notification
        notificationService.sendNotificationForPickupCreate(pickupEntities.get(0).getCompanyId(), pickupEntities.get(0).getLanguageId(), pickupEntities.get(0).getPickupId());
        return createPickupList(pickupEntities, loginUserID);
    }


    /**
     * Create Pickup
     *
     * @param addPickups
     * @param loginUserID
     * @return
     */
    public List<PickupEntity> createPickupList(List<AddPickup> addPickups, String loginUserID) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        ConcurrentHashMap<String, String> partnerPickUpIdMap = new ConcurrentHashMap<>();
        List<PickupEntity> pickupEntityList = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executor = Executors.newFixedThreadPool(10);
        String pickupId = generateUniqueNumberRange("PICKUPID");
        addPickups.stream().forEach(pickup -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    // Fetching description for company
                    IKeyValuePair iKeyValuePair = replicaPickupEntityRepository.getDescription(pickup.getCompanyId());
                    if (iKeyValuePair == null) {
                        throw new BadRequestException("Company description not found for ID: " + pickup.getCompanyId());
                    }

                    // Check for duplicates if HouseAirwayBill is provided
                    if (pickup.getHouseAirwayBill() != null && !pickup.getHouseAirwayBill().isEmpty()) {
                        boolean duplicate = pickupEntityRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndDeletionIndicator(
                                iKeyValuePair.getLangId(), pickup.getCompanyId(), pickup.getPartnerId(),
                                pickup.getHouseAirwayBill(), 0L);
                        if (duplicate) {
                            throw new BadRequestException("Duplicate record found for HouseAirwayBill: " + pickup.getHouseAirwayBill());
                        }
                    }

                    // Create new PickupEntity and set properties
                    PickupEntity newPickup = new PickupEntity();
                    BeanUtils.copyProperties(pickup, newPickup, CommonUtils.getNullPropertyNames(pickup));

                    if (pickup.getHouseAirwayBill() != null && !pickup.getHouseAirwayBill().isEmpty()) {
                        newPickup.setConsignmentCreation("1");
                    } else {
                        newPickup.setConsignmentCreation("0");
                    }
                    newPickup.setStatusId("2");
                    String desc = replicaPickupDetailsRepository.getStatusDescription("2");
                    if (desc != null && !desc.isEmpty()) {
                        newPickup.setStatusDescription(desc);
                    }
                    newPickup.setLanguageId(iKeyValuePair.getLangId());
                    newPickup.setPartnerType("Customer");
                    newPickup.setEtaDateTime(null);
                    newPickup.setCreatedBy(loginUserID);
                    newPickup.setCreatedOn(new Date());
                    newPickup.setUpdatedBy(null);
                    newPickup.setUpdatedOn(null);

                    // Destination Create
                    setupDestinationDetails(pickup, newPickup, loginUserID);
                    // PickupDetails Create
                    setupPickUpDetails(pickup, newPickup, loginUserID);
                    // Set Address and Destinations
                    newPickup.setAddress(Stream.of(newPickup.getPickupDetails().getAddressLine1(), newPickup.getPickupDetails().getAddressLine2(), newPickup.getPickupDetails().getPinCode())
                            .filter(part -> part != null && !part.trim().isEmpty())
                            .collect(Collectors.joining(", ")));
                    newPickup.setDestinations(newPickup.getAddress());

                    // CON_IMAGE
                    Set<RiderAssignmentImageReference> imageReferenceSet = Optional.ofNullable(pickup.getImageReferenceList())
                            .orElse(Collections.emptySet()).stream()
                            .map(imageReference -> {
                                try {
                                    String downloadDocument = commonService.downLoadDocument(imageReference.getReferenceImageUrl(), "document", "image");
                                    if (downloadDocument != null) {
                                        return createImageReference(
                                                pickup.getLanguageId(), pickup.getCompanyId(), pickup.getPartnerId(), pickup.getPartnerName(), pickup.getHouseAirwayBill(), null,
                                                null, null, null, null, imageReference.getReferenceImageUrl(),
                                                "PICKUP", downloadDocument, loginUserID);
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to download image: " + imageReference.getReferenceImageUrl(), e);
                                }
                                return null;
                            }).filter(Objects::nonNull).collect(Collectors.toSet());
                    if (imageReferenceSet != null) {
                        BeanUtils.copyProperties(imageReferenceSet, newPickup.getImageReferenceList());
                    }
//                    pickup.setImageReferenceList(imageReferenceSet);

//                    String uniquePickupKey = createUniquePickupKey(pickup);
//                    partnerPickUpIdMap.computeIfAbsent(uniquePickupKey, key -> {
//                        try {
//                            return generateUniqueNumberRange("PICKUPID");
//                        } catch (Exception e) {
//                            log.info("Failed to generate PICKUPID : " + key, e);
//                            throw new RuntimeException("Failed to generate PICKUPID");
//                        }
//                    });
                    newPickup.setPickupId(pickupId);
                    PickupEntity cp = pickupEntityRepository.save(newPickup);

                    // Update consignment with pickup ID if HouseAirwayBill is provided
                    if (pickup.getHouseAirwayBill() != null) {
                        pickupEntityRepository.updatePickupIdInConsignment(cp.getPickupId(), cp.getStatusId(), cp.getStatusDescription(),
                                cp.getPickupEntityId(), pickup.getPartnerId(), pickup.getHouseAirwayBill());
                        consignmentStatusService.insertConsignmentStatusRecord(cp.getLanguageId(), cp.getLanguageDescription(), cp.getCompanyId(), cp.getCompanyName(),
                                cp.getPieceId(), cp.getHouseAirwayBill(), "STATUS", cp.getStatusId(), cp.getStatusDescription(),
                                cp.getStatusTimeStamp(), "STATUS", cp.getStatusId(), cp.getStatusDescription(), cp.getStatusTimeStamp(), loginUserID);

                    }
                    pickupEntityList.add(cp);

                } catch (BadRequestException e) {
                    log.warn("BadRequestException: " + e.getMessage(), e);
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to process pickup " + e);
                }
            }, executor);
            futures.add(future);
        });
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        return pickupEntityList;
    }

//    public List<PickupEntity> createPickupList(List<AddPickup> addPickups, String loginUserID) {
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
//        Set<String> partnerIdSet = new HashSet<>();
//
//        List<PickupEntity> pickupEntityList = Collections.synchronizedList(new ArrayList<>());
//
//        // Create a thread pool with a fixed number of threads
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//
//        addPickups.parallelStream().forEach(pickup -> {
//            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                // Fetching the description for a company
//                IKeyValuePair iKeyValuePair = replicaPickupEntityRepository.getDescription(pickup.getCompanyId());
//                if (pickup.getHouseAirwayBill() != null && !pickup.getHouseAirwayBill().isEmpty()) {
//                    boolean duplicate = pickupEntityRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndDeletionIndicator(
//                            iKeyValuePair.getLangId(), pickup.getCompanyId(), pickup.getPartnerId(),
//                            pickup.getHouseAirwayBill(), 0L);
//                    if (duplicate) {
//                        throw new BadRequestException("Record is getting Duplicated with given values");
//                    }
//                }
//                // Create new PickupEntity and related entities
//                PickupEntity newPickup = new PickupEntity();
//                BeanUtils.copyProperties(pickup, newPickup, CommonUtils.getNullPropertyNames(pickup));
//
////               String pickupId = numberRangeService.getNextNumberRange("PICKUPID");
//                // If HouseAirwayBill null or not null set value
//                if (pickup.getHouseAirwayBill() != null) {
//                    newPickup.setConsignmentCreation("0");
//                } else {
//                    newPickup.setConsignmentCreation("1");
//                }
//
//                // Set Hardcoded value Status
//                newPickup.setStatusId("2");
//                String desc = replicaPickupDetailsRepository.getStatusDescription("2");
//                if (desc != null && !desc.isEmpty()) {
//                    newPickup.setStatusDescription(desc);
//                }
//                newPickup.setLanguageId(iKeyValuePair.getLangId());
//                newPickup.setPartnerType("Customer");
//
//
////                newPickup.setPickUpId(pickupId);
//                newPickup.setEtaDateTime(null);
//                newPickup.setCreatedBy(loginUserID);
//                newPickup.setCreatedOn(new Date());
//                newPickup.setUpdatedBy(null);
//                newPickup.setUpdatedOn(null);
//
//                // Destination Create
//                setupDestinationDetails(pickup, newPickup, loginUserID);
//                // PickupDetails Create
//                setupPickUpDetails(pickup, newPickup, loginUserID);
//
//                newPickup.setAddress(Stream.of(newPickup.getPickupDetails().getAddressLine1(), newPickup.getPickupDetails().getAddressLine2(), newPickup.getPickupDetails().getPinCode())
//                        .filter(part -> part != null && !part.trim().isEmpty())  // Skip null or empty parts
//                        .collect(Collectors.joining(", ")));
//
//                newPickup.setDestinations(Stream.of(newPickup.getPickupDetails().getAddressLine1(), newPickup.getPickupDetails().getAddressLine2(), newPickup.getPickupDetails().getPinCode())
//                        .filter(part -> part != null && !part.trim().isEmpty())  // Skip null or empty parts
//                        .collect(Collectors.joining(", ")));
//
//                // CON_IMAGE
//                Set<RiderAssignmentImageReference> imageReferenceSet = Optional.ofNullable(pickup.getImageReferenceList())
//                        .orElse(Collections.emptySet()).stream()
//                        .map(imageReference -> {
//                            String downloadDocument = commonService.downLoadDocument(imageReference.getReferenceImageUrl(), "document", "image");
//                            if (downloadDocument != null) {
//                                return createImageReference(
//                                        pickup.getLanguageId(), pickup.getCompanyId(), pickup.getPartnerId(), pickup.getPartnerName(), pickup.getHouseAirwayBill(), null,
//                                        null, null, null, null, imageReference.getReferenceImageUrl(),
//                                        "PICKUP", downloadDocument, loginUserID);
//                            }
//                            return null;
//                        }).filter(Objects::nonNull).collect(Collectors.toSet());
//                pickup.setImageReferenceList(imageReferenceSet);
//
//                String pickupId = generatePickupId(newPickup.getPartnerId());
//
//                String pickupId = null;
//
//                if (newPickup.getPartnerId() != null && !partnerIdSet.contains(newPickup.getPartnerId())) {
//                    pickupId = numberRangeService.getNextNumberRange("PICKUPID");
//                    partnerIdSet.add(newPickup.getPartnerId());
//                }
//                newPickup.setPickUpId(pickupId);
//                log.info("pickup --{}", pickup.getPartnerId());
//                PickupEntity pickupEntity = pickupEntityRepository.save(newPickup);
//
//
//                // Set single pickup id for partner id
////                String pickupId = numberRangeService.getNextNumberRange("PICKUPID");
////                pickupEntityRepository.updtaePickupId(pickupId, pickup.getPartnerId());
//                if (pickup.getHouseAirwayBill() != null) {
//                    pickupEntityRepository.updtaePickupIdInConsignment(pickupEntity.getPickupId(), pickup.getPartnerId(), pickup.getHouseAirwayBill());
//                }
//                pickupEntityList.add(pickupEntity);
//            }, executor);
//            futures.add(future);
//        });
//
//        // Wait for all the futures to complete
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//
//        // Shut down the executor service
//        executor.shutdown();
//        return pickupEntityList;
//    }


    /**
     * @param addPickup
     * @param pickup
     * @param loginUserID
     */
    public void setupDestinationDetails(AddPickup addPickup, PickupEntity pickup, String loginUserID) {

        AddPickupDestinationDetails pd = addPickup.getDestinationDetails();
        if (pd != null) {
            BeanUtils.copyProperties(pd, pickup.getDestinationDetails(), CommonUtils.getNullPropertyNames(pd));
            pickup.getDestinationDetails().setDestinationDetailId(null);
            IKeyValuePair ikey = replicaPickupDetailsRepository.getDescription(
                    pd.getCity(), pd.getCountry(), pd.getDistrict(), pd.getState());

            String districtName = (ikey != null) ? ikey.getDistrictName() : "";
            String cityName = (ikey != null) ? ikey.getCityName() : "";
            String provinceName = (ikey != null) ? ikey.getProvinceName() : "";
            String countryName = (ikey != null) ? ikey.getCountryName() : "";

            pickup.getDestinationDetails().setDestinationAddress(Stream.of(
                            pd.getAddressLine1(), pd.getAddressLine2(), pd.getPinCode(),
                            districtName, cityName, provinceName, countryName)
                    .filter(part -> part != null && !part.trim().isEmpty())
                    .collect(Collectors.joining(", ")));

            pickup.getDestinationDetails().setCreatedBy(loginUserID);
            pickup.getDestinationDetails().setCreatedOn(new Date());
        } else {
            PickupDestinationDetails pdd = new PickupDestinationDetails();
            pdd.setCreatedBy(loginUserID);
            pdd.setCreatedOn(new Date());
            pickup.setDestinationDetails(pdd);

        }
    }


    /**
     * @param updatePickupList
     * @param loginUserID
     * @return
     */
    public List<PickupEntity> updatePickup(List<AddPickup> updatePickupList, String loginUserID) {
        List<PickupEntity> updatedPickupList = new ArrayList<>();

        updatePickupList.forEach(up -> {
            PickupEntity dbPickup = pickupEntityRepository.findByPickupEntityIdAndDeletionIndicator(up.getPickupEntityId(), 0L);

//            String[] ignoreProperties = CommonUtils.getNullPropertyNames(up);
//
//            List<String> ignoreList = new ArrayList<>(Arrays.asList(ignoreProperties));
//            ignoreList.add("imageReferenceList"); // Add RiderAssignmentImageReference field to ignore list
//            BeanUtils.copyProperties(up, dbPickup, ignoreList.toArray(new String[0]));

            String status = replicaPickupDetailsRepository.getStatusDescription(up.getStatusId());
            BeanUtils.copyProperties(up, dbPickup, CommonUtils.getNullPropertyNames(up));
            if (status != null) {
                dbPickup.setStatusDescription(status);
                String nprText = pickupEntityRepository.getNprText(dbPickup.getLanguageId(), dbPickup.getCompanyId(), dbPickup.getNprId());
                log.info("NPR TEXT -->{}", nprText);
                if (nprText != null && !nprText.isEmpty()) {
                    dbPickup.setNprText(nprText);
                    dbPickup.setRescheduleReason(nprText);
                }
                pickupEntityRepository.updateConsignment(up.getStatusId(), status, up.getPickupId());
                pickupEntityRepository.updatePieceTable(up.getStatusId(), status, up.getHouseAirwayBill());
                consignmentStatusService.insertConsignmentStatusRecord(up.getLanguageId(), up.getLanguageDescription(), up.getCompanyId(), up.getCompanyName(),
                        up.getPieceId(), up.getHouseAirwayBill(), "STATUS", up.getStatusId(), status,
                        up.getStatusTimeStamp(), "STATUS", up.getStatusId(), status, up.getStatusTimeStamp(), loginUserID);
                log.info("Consignment Status Updated Successfully <----->  PickupId - " + up.getPickupId() + " AND HouseAirwayBill " + up.getHouseAirwayBill());
            }

            // Destination Details
            if (up.getDestinationDetails() != null) {
                AddPickupDestinationDetails dd = up.getDestinationDetails();
                BeanUtils.copyProperties(dd, dbPickup.getDestinationDetails(), CommonUtils.getNullPropertyNames(dd));
                IKeyValuePair ikey = replicaPickupDetailsRepository.getDescription(
                        dd.getCity(), dd.getCountry(), dd.getDistrict(), dd.getState());

                if (ikey != null) {
                    String addDest = dd.getAddressLine1() + "," + dd.getAddressLine1() + "," + ikey.getDistrictName() + "," +
                            ikey.getCityName() + "," + ikey.getProvinceName() + "," + ikey.getCountryName() + "," + dd.getPinCode();
                    dbPickup.getDestinationDetails().setDestinationAddress(addDest);
                }
                dbPickup.getDestinationDetails().setCreatedBy(loginUserID);
                dbPickup.getDestinationDetails().setCreatedOn(new Date());
            }

            // PickupDetails
            if (up.getPickupDetails() != null) {
                AddPickupDetails pd = up.getPickupDetails();
                BeanUtils.copyProperties(pd, dbPickup.getPickupDetails(), CommonUtils.getNullPropertyNames(pd));
                IKeyValuePair ikey = replicaPickupDetailsRepository.getDescription(
                        pd.getCity(), pd.getCountry(), pd.getDistrict(), pd.getState());
                if (ikey != null) {
                    String addDest = pd.getName() + "," + pd.getAddressLine1() + "," + pd.getAddressLine2() + "," + ikey.getDistrictName() + "," +
                            ikey.getCityName() + "," + ikey.getProvinceName() + "," + ikey.getCountryName() + "," + pd.getPinCode() + "," + pd.getPhone();
                    dbPickup.getPickupDetails().setPickUpAddress(addDest);
                }
                if (ikey != null) {
                    String addDest = pd.getAddressLine1() + "," + pd.getAddressLine2() + "," + ikey.getDistrictName() + "," +
                            ikey.getCityName() + "," + ikey.getProvinceName() + "," + ikey.getCountryName() + "," + pd.getPinCode();
                    dbPickup.getPickupDetails().setPickUpAddress1(addDest);
                    GeoInfo getGeo = geoInfoService.geoCode(addDest);

                    dbPickup.setLatitude(getGeo != null && getGeo.getLatitude() != null ? getGeo.getLatitude() : null);
                    dbPickup.setLongitude(getGeo != null && getGeo.getLongitude() != null ? getGeo.getLongitude() : null);
                }
            }

            //  Image Update
            if (up.getImageReferenceList() != null) {
                Set<RiderAssignmentImageReference> imageList = new HashSet<>();
                // Update the existing images
                for (AddRiderImageReference newImageRef : up.getImageReferenceList()) {
                    boolean imageUpdated = false;

                    // Check if the image ID matches any existing image reference
                    if (dbPickup.getImageReferenceList() != null) {
                        for (RiderAssignmentImageReference existingImageRef : dbPickup.getImageReferenceList()) {
                            if (newImageRef.getImageId() != null && Objects.equals(existingImageRef.getImageId(), newImageRef.getImageId())) {
//                            BeanUtils.copyProperties(newImageRef, existingImageRef, CommonUtils.getNullPropertyNames(newImageRef));
                                existingImageRef.setReferenceImageUrl(existingImageRef.getReferenceImageUrl());
                                existingImageRef.setUpdatedBy(loginUserID);
                                existingImageRef.setUpdatedOn(new Date());
                                imageList.add(existingImageRef);
                                imageUpdated = true;
                                break;
                            }
                        }
                    }

                    // If no matching image was found, download the image and add it to the existing images
                    if (!imageUpdated) {
                        String downloadDocument = commonService.downLoadDocument(newImageRef.getReferenceImageUrl(), "document", "image");
                        if (downloadDocument != null) {
                            RiderAssignmentImageReference newImage = createImageReference(
                                    up.getLanguageId(), up.getCompanyId(), up.getPartnerId(),
                                    up.getPartnerName(), up.getHouseAirwayBill(), null,
                                    null, null, null, null, newImageRef.getReferenceImageUrl(), "PICKUP", downloadDocument, loginUserID);
                            imageList.add(newImage);
                        }
                    }
                }
                dbPickup.setImageReferenceList(imageList);
            }
            if (Arrays.asList("53", "52", "17", "3").contains(up.getStatusId())) {
                String pickupCountStr = pickupEntityRepository.getPickupCount(up.getPartnerId(), up.getLanguageId(), up.getCompanyId());
                int count = (pickupCountStr != null) ? Integer.parseInt(pickupCountStr) : 0;

                long pickupCount = dbPickup.getPickupAttemptCount() != null ? dbPickup.getPickupAttemptCount() + 1 : 1;
//                if (count >= pickupCount) {
//                    throw new RuntimeException(" Pickup attempt count exceeded the allowed limit. ");
//                }
                dbPickup.setPickupAttemptCount(pickupCount);
            }
            dbPickup.setUpdatedBy(loginUserID);
            dbPickup.setUpdatedOn(new Date());

            Long pickupCount = replicaPickupEntityRepository.pickupCount(dbPickup.getPartnerId());
            if(dbPickup.getPickupAttemptCount() != null && pickupCount != null) {
                if (dbPickup.getPickupAttemptCount() > pickupCount) {
                    throw new BadRequestException("The no of Inventory scans are exceeded");
                }
            }
            // Save the updated pickup entity
            PickupEntity updatedPickup = pickupEntityRepository.save(dbPickup);
            updatedPickupList.add(updatedPickup);

            // Additional logic based on reschedule or NPR fields
            if (updatedPickup.getReschedule() != null || updatedPickup.getRescheduleReason() != null) {
                reschedulePickupService.addReschedulePickup(updatedPickup);
            } else if (updatedPickup.getNprId() != null || updatedPickup.getNprText() != null) {
                nprService.addNpr(updatedPickup);
            }
        });
        //Send Notification
        notificationService.sendNotificationForPickupUpdate(updatedPickupList.get(0).getLanguageId(), updatePickupList.get(0).getCompanyId(), updatedPickupList.get(0).getPickupId());
        return updatedPickupList;
    }

    /**
     * @param addPickup
     * @param pickup
     * @param loginUserID
     */
    public void setupPickUpDetails(AddPickup addPickup, PickupEntity pickup, String loginUserID) {

        AddPickupDetails pd = addPickup.getPickupDetails();
        if (pd != null) {
            BeanUtils.copyProperties(pd, pickup.getPickupDetails(), CommonUtils.getNullPropertyNames(pd));
            IKeyValuePair ikey = replicaPickupDetailsRepository.getDescription(
                    pd.getCity(), pd.getCountry(), pd.getDistrict(), pd.getState());


            if (ikey != null) {
                String addDest = pd.getAddressLine1() + "," + pd.getAddressLine2() + "," + ikey.getDistrictName() + "," +
                        ikey.getCityName() + "," + ikey.getProvinceName() + "," + ikey.getCountryName() + "," + pd.getPinCode();
                pickup.getPickupDetails().setPickUpAddress1(addDest);
                GeoInfo getGeo = geoInfoService.geoCode(addDest);

                pickup.setLatitude(getGeo != null && getGeo.getLatitude() != null ? getGeo.getLatitude() : null);
                pickup.setLongitude(getGeo != null && getGeo.getLongitude() != null ? getGeo.getLongitude() : null);
            }

            pickup.getPickupDetails().setCreatedBy(loginUserID);
            pickup.getPickupDetails().setCreatedOn(new Date());
        } else {
            PickupDetails pdd = new PickupDetails();
            pdd.setCreatedBy(loginUserID);
            pdd.setCreatedOn(new Date());
            pickup.setPickupDetails(pdd);

        }
    }

    /**
     * Delete Pickup
     *
     * @param pickupDeleteInputList
     * @param loginUserID
     */
    public void deletePickup(List<PickupDeleteInput> pickupDeleteInputList, String loginUserID) {

        if (pickupDeleteInputList != null && !pickupDeleteInputList.isEmpty()) {
            log.info("given values to delete PickupEntity --> {}", pickupDeleteInputList);
            pickupDeleteInputList.parallelStream().forEach(deleteInput -> {
                pickupEntityRepository.updatePickupId(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                        deleteInput.getPartnerId(), deleteInput.getHouseAirwayBill(), deleteInput.getPickupId(), loginUserID);
//                PickupEntity dbPickup = getPickup(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
//                        deleteInput.getPartnerId(), deleteInput.getHouseAirwayBill(), deleteInput.getPickupId());
//                dbPickup.setDeletionIndicator(1L);
//                dbPickup.setUpdatedBy(loginUserID);
//                dbPickup.setUpdatedOn(new Date());
//                pickupEntityRepository.save(dbPickup);
            });
        }
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    /**
     * Get ALl Pickup Details
     *
     * @return
     */
    public List<ReplicaPickupEntity> getAllPickup() {
        return replicaPickupEntityRepository.getAllNonDeletedPickup();
    }
//    public List<ReplicaRiderAssignmentEntity> getAllRiderAssignments() {
//        List<ReplicaRiderAssignmentEntity> riderAssignmentEntities = replicaRiderAssignmentEntityRepository.findAll()
//                .stream()
//                .filter(i -> i.getDeletionIndicator() == 0)
//                .collect(Collectors.toList());
//        return riderAssignmentEntities;
//    }

    /**
     * Get Pickup - Replica
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param houseAirwayBill
     * @param pickupId
     * @return
     */
    public ReplicaPickupEntity getPickupReplica(String languageId, String companyId, String partnerId, String
            houseAirwayBill, String pickupId) {

        Optional<ReplicaPickupEntity> pickupOpt = replicaPickupEntityRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
                languageId, companyId, partnerId, houseAirwayBill, pickupId, 0L);
        if (pickupOpt.isEmpty()) {
            throw new BadRequestException("Pickup with given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", houseAirwayBill - " + houseAirwayBill
                    + " and pickupId " + pickupId + " doesn't exists");
        }
        return pickupOpt.get();
    }

    /**
     * Find Pickup
     *
     * @param findPickup
     * @return
     * @throws Exception
     */
//    public Stream<ReplicaRiderAssignmentEntity> findRiderAssignments(FindRiderAssignment findRiderAssignment) throws ParseException {
//
//        if (findRiderAssignment.getFromCreatedOn() != null && findRiderAssignment.getToCreatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(findRiderAssignment.getFromCreatedOn(), findRiderAssignment.getToCreatedOn());
//            findRiderAssignment.setFromCreatedOn(dates[0]);
//            findRiderAssignment.setToCreatedOn(dates[1]);
//        }
//        log.info("given Values to fetch RiderAssignments --> {}", findRiderAssignment);
//        RiderAssignmentSpecification spec = new RiderAssignmentSpecification(findRiderAssignment);
//        return replicaRiderAssignmentEntityRepository.findAll(spec).stream();
//    }
    public List<ReplicaPickupEntity> findPickup(FindPickup findPickup) throws Exception {

        if (findPickup.getFromCreatedOn() != null && findPickup.getToCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(findPickup.getFromCreatedOn(), findPickup.getToCreatedOn());
            findPickup.setFromCreatedOn(dates[0]);
            findPickup.setToCreatedOn(dates[1]);
        }
        log.info("given Values to fetch Pickup with Qry --> {}", findPickup);
        return replicaPickupEntityRepository.findPickupWithOptionalParams(findPickup.getLanguageId(), findPickup.getCompanyId(),
                findPickup.getPartnerId(), findPickup.getHouseAirwayBill(), findPickup.getPickupId(),
                findPickup.getFromCreatedOn(), findPickup.getToCreatedOn(), findPickup.getConsignmentCreation(),
                findPickup.getStatusId(), findPickup.getCourierId(), findPickup.getCreatedBy(), findPickup.getPickupEntityId(), findPickup.getAssignedBy());
    }


    /**
     * @param pickupMobileAppReq
     * @return
     */
    public List<FindPickupAssigned> findPickupAssignedList(PickupMobileAppReq pickupMobileAppReq) {

        List<FindPickupAssigned> pickupAssignedList = new ArrayList<>();
        List<ReplicaPickupEntity> dbPickup = replicaPickupEntityRepository.getPickupAssignedData(pickupMobileAppReq.getLanguageId(), pickupMobileAppReq.getCompanyId(), pickupMobileAppReq.getPartnerId(),
                pickupMobileAppReq.getHouseAirwayBill(), pickupMobileAppReq.getPickupId(), pickupMobileAppReq.getCourierId());
        log.info("assigned data count --> {}", dbPickup.size());
        if (dbPickup.size() > 0) {
            dbPickup.forEach(id -> {
                FindPickupAssigned findPickupAssigned = new FindPickupAssigned();
                ReplicaPickupDetails pickupId = replicaPickupDetailsRepository.findPickupId(id.getPickupEntityId());
                findPickupAssigned.setActualSequenceNo(id.getActualSequenceNo());
                findPickupAssigned.setCustomerName(pickupId.getName() != null ? pickupId.getName() : null);
                findPickupAssigned.setAddress(pickupId.getAddressLine1() != null ? pickupId.getAddressLine1() : null);
                findPickupAssigned.setPhone(pickupId.getPhone() != null ? pickupId.getPhone() : null);
                findPickupAssigned.setPieceCount(id.getPieceCount());
                findPickupAssigned.setCurrency("KWD");
                findPickupAssigned.setHouseAirwayBill(id.getHouseAirwayBill());
                findPickupAssigned.setPickupDateTime(id.getPickupTimeSlotStart());
                findPickupAssigned.setPickupId(id.getPickupId());
                findPickupAssigned.setStatusId(id.getStatusId());
                findPickupAssigned.setStatusDescription(id.getStatusDescription());
                findPickupAssigned.setConsignmentCreation(id.getConsignmentCreation());
                findPickupAssigned.setRemarks(id.getRemarks());
                findPickupAssigned.setCodAmount(id.getCodAmount());
                findPickupAssigned.setPickupEntityId(id.getPickupEntityId());
                findPickupAssigned.setCreatedBy(id.getCreatedBy());
                findPickupAssigned.setCreatedOn(id.getCreatedOn());
                pickupAssignedList.add(findPickupAssigned);
            });
            return pickupAssignedList;
        } else {
            throw new BadRequestException("No Data Found");
        }

    }


    /**
     * @param pickupMobileAppReq
     * @return
     */
    public List<FindPickupAssignedTask> findPickupAcceptedList(PickupMobileAppReq pickupMobileAppReq) {

        List<FindPickupAssignedTask> pickupAssignedTaskList = new ArrayList<>();

        List<ReplicaPickupEntity> dbPickUp = replicaPickupEntityRepository.getPickupAssignedData(pickupMobileAppReq.getLanguageId(),
                pickupMobileAppReq.getCompanyId(), pickupMobileAppReq.getPartnerId(), pickupMobileAppReq.getHouseAirwayBill(),
                pickupMobileAppReq.getPickupId(), pickupMobileAppReq.getCourierId());
        log.info("accepted task data count --> {}", dbPickUp.size());
        if (dbPickUp.size() > 0) {
            dbPickUp.forEach(id -> {
                FindPickupAssignedTask findPickupAssignedTask = new FindPickupAssignedTask();
                ReplicaPickupDetails replicaPickupDetails = replicaPickupDetailsRepository.findPickupId(id.getPickupEntityId());
                String destAddress = replicaPickupDestinationDetailsRepository.findPickupId(id.getPickupEntityId());
                findPickupAssignedTask.setHouseAirwayBill(id.getHouseAirwayBill());
                findPickupAssignedTask.setCustomerName(replicaPickupDetails.getName() != null ? replicaPickupDetails.getName() : null);
                findPickupAssignedTask.setPickupAddress(replicaPickupDetails.getAddressLine1() != null ? replicaPickupDetails.getAddressLine1() : null);
                findPickupAssignedTask.setPhone(replicaPickupDetails.getPhone() != null ? replicaPickupDetails.getPhone() : null);
                findPickupAssignedTask.setDestinationAddress(destAddress != null ? destAddress : null);
                findPickupAssignedTask.setPickupTimeSlotStart(id.getPickupTimeSlotStart());
                findPickupAssignedTask.setPieceId(id.getPieceId());
                findPickupAssignedTask.setPieceCount(id.getPieceCount());
                findPickupAssignedTask.setPaymentType(id.getPaymentType());
                findPickupAssignedTask.setCodAmount(id.getCodAmount());
                findPickupAssignedTask.setPickupFailedReason(id.getPickupFailedReason());
                findPickupAssignedTask.setPickupId(id.getPickupId());
                findPickupAssignedTask.setImageReferenceList(id.getImageReferenceList());
                findPickupAssignedTask.setStatusId(id.getStatusId());
                findPickupAssignedTask.setStatusDescription(id.getStatusDescription());
                findPickupAssignedTask.setConsignmentCreation(id.getConsignmentCreation());
                findPickupAssignedTask.setPickupEntityId(id.getPickupEntityId());
                findPickupAssignedTask.setRemarks(id.getRemarks());
                findPickupAssignedTask.setLatitude(id.getLatitude());
                findPickupAssignedTask.setLongitude(id.getLongitude());
                findPickupAssignedTask.setCreatedBy(id.getCreatedBy());
                findPickupAssignedTask.setCreatedOn(id.getCreatedOn());
                pickupAssignedTaskList.add(findPickupAssignedTask);
            });
            return pickupAssignedTaskList;
        } else {
            throw new BadRequestException("No Data Found");
        }
    }


    /**
     * @param updatePickupStatus
     * @param loginUserID
     * @return
     */
    public List<PickupEntity> updatePickupStatus(List<UpdatePickupStatus> updatePickupStatus, String loginUserID) {
        List<PickupEntity> updatedPickupList = new ArrayList<>();

        for (UpdatePickupStatus updatePickup : updatePickupStatus) {

            String status = replicaPickupEntityRepository.getStatusDescription(updatePickup.getStatusId());

            log.info("Update PickupStatus ------------------------->" + updatePickup);
            List<PickupEntity> dbPickup = pickupEntityRepository.updatePickupStatus(
                    updatePickup.getLanguageId(), updatePickup.getCompanyId(), updatePickup.getPartnerId(), updatePickup.getHouseAirwayBill(),
                    updatePickup.getPickupId(), updatePickup.getPickupEntityId());
            dbPickup.forEach(pu -> {
                BeanUtils.copyProperties(updatePickup, pu, CommonUtils.getNullPropertyNames(updatePickup));
                if (status != null) {
                    pu.setStatusDescription(status);
                    pickupEntityRepository.updateConsignment(pu.getStatusId(), status, pu.getPickupId());
                    pickupEntityRepository.updatePieceTable(pu.getStatusId(), status, pu.getHouseAirwayBill());
                }
                pu.setUpdatedBy(loginUserID);
                pu.setUpdatedOn(new Date());
                pu.setImageReferenceList(updatePickup.getImageReferenceList());

                String nprText = pickupEntityRepository.getNprText(pu.getLanguageId(), pu.getCompanyId(), pu.getNprId());
                if (nprText != null && !nprText.isEmpty()) {
                    pu.setNprText(nprText);
                    pu.setRescheduleReason(nprText);
                }
                if (Arrays.asList("53", "52", "17", "3").contains(pu.getStatusId())) {
                    String pickupCountStr = pickupEntityRepository.getPickupCount(pu.getPartnerId(), pu.getLanguageId(), pu.getCompanyId());
                    int count = (pickupCountStr != null) ? Integer.parseInt(pickupCountStr) : 0;

                    int pickupCount = (pu.getPickupAttemptCount() != null) ? (int) (pu.getPickupAttemptCount() + 1) : 1;
//                    if (count <= pickupCount) {
//                        throw new RuntimeException(" Pickup attempt count exceeded the allowed limit. ");
//                    }
                    pu.setPickupAttemptCount((long) pickupCount);
                }
//                Long pickupCount = replicaPickupEntityRepository.pickupCount(pu.getPartnerId());
//                if(pu.getPickupAttemptCount() != null && pickupCount != null) {
//                    if (pu.getPickupAttemptCount() > pickupCount) {
//                        throw new BadRequestException("The no of Inventory scans are exceeded");
//                    }
//                }
                PickupEntity pickup = pickupEntityRepository.save(pu);
                consignmentStatusService.insertConsignmentStatusRecord(pickup.getLanguageId(), pickup.getLanguageDescription(), pickup.getCompanyId(), pickup.getCompanyName(),
                        pickup.getPieceId(), pu.getHouseAirwayBill(), "STATUS", pickup.getStatusId(), pickup.getStatusDescription(),
                        pickup.getStatusTimeStamp(), "STATUS", pickup.getStatusId(), pickup.getStatusDescription(), pickup.getStatusTimeStamp(), loginUserID);
                updatedPickupList.add(pickup);

                // Additional logic based on reschedule or NPR fields
                if (pickup.getReschedule() != null || pickup.getRescheduleReason() != null) {
                    reschedulePickupService.addReschedulePickup(pickup);
                } else if (pickup.getNprId() != null || pickup.getNprText() != null) {
                    nprService.addNpr(pickup);
                }
            });

        }
        return updatedPickupList;
    }


    /**
     * @param pickupUpdateByPieces
     * @param loginUserID
     * @return
     */
    public List<PickupEntity> updatePickupByPieceId(List<PickupUpdateByPiece> pickupUpdateByPieces, String
            loginUserID) {
        List<PickupEntity> updatedPickupList = new ArrayList<>();

        for (PickupUpdateByPiece pu : pickupUpdateByPieces) {
            PickupEntity dbPickup = pickupEntityRepository.updateStatusByPieceId(
                    pu.getLanguageId(), pu.getCompanyId(), pu.getPieceId(), pu.getHouseAirwayBill(), pu.getPickupId());
            BeanUtils.copyProperties(pu, dbPickup, CommonUtils.getNullPropertyNames(pu));
            log.info("Update Pickup ------------ Values PickupId - > " + pu.getPickupId() + " AND Given Values " + dbPickup);
            // Status Set for Count
            if (Arrays.asList("53", "52", "17", "3").contains(dbPickup.getStatusId())) {
                String pickupCountStr = pickupEntityRepository.getPickupCount(dbPickup.getPartnerId(), dbPickup.getLanguageId(), dbPickup.getCompanyId());
                int count = (pickupCountStr != null) ? Integer.parseInt(pickupCountStr) : 0;
                int pickupCount = (dbPickup.getPickupAttemptCount() != null) ? (int) (dbPickup.getPickupAttemptCount() + 1) : 1;
                if (count >= pickupCount) {
                    throw new RuntimeException(" Pickup attempt count exceeded the allowed limit. ");
                }
                dbPickup.setPickupAttemptCount((long) pickupCount);
            }
            // Pickup_Attempt_Count
            Long pickupCount = replicaPickupEntityRepository.pickupCount(dbPickup.getPartnerId());
            if(dbPickup.getPickupAttemptCount() != null && pickupCount != null) {
                if (dbPickup.getPickupAttemptCount() > pickupCount) {
                    throw new BadRequestException("The no of Inventory scans are exceeded");
                }
            }
            dbPickup.setUpdatedBy(loginUserID);
            dbPickup.setUpdatedOn(new Date());
            dbPickup.setStatusId(pu.getStatusId());
            // Update Status
            String status = replicaPickupEntityRepository.getStatusDescription(pu.getStatusId());
            if (status != null && !status.isEmpty()) {
                dbPickup.setStatusDescription(status);
                pickupEntityRepository.updateConsignment(pu.getStatusId(), status, pu.getPickupId());
                pickupEntityRepository.updatePieceTable(pu.getStatusId(), status, pu.getHouseAirwayBill());
            }
            PickupEntity sp = pickupEntityRepository.save(dbPickup);
            consignmentStatusService.insertConsignmentStatusRecord(sp.getLanguageId(), sp.getLanguageDescription(), sp.getCompanyId(), sp.getCompanyName(),
                    sp.getPieceId(), sp.getHouseAirwayBill(), "STATUS", sp.getStatusId(), sp.getStatusDescription(),
                    sp.getStatusTimeStamp(), "STATUS", sp.getStatusId(), sp.getStatusDescription(), sp.getStatusTimeStamp(), loginUserID);
            updatedPickupList.add(sp);
        }
        return updatedPickupList;
    }


    @Transactional
    public RiderAssignmentImageReference createImageReference(String languageId, String companyId, String
            partnerId, String partnerName, String houseAirwayBill,
                                                              String masterAirwayBillNo, String partnerHouseAirwayBill, String partnerMasterAirwayBill,
                                                              String pieceId, String pieceItemId, String url, String tableName, String downloadUrl, String loginUserID) {
        try {
            RiderAssignmentImageReference newImageReference = new RiderAssignmentImageReference();
            String NUM_RAN_OBJ = "IMAGEREFERENCE";
            String IMAGE_REF_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
            log.info("next Value from NumberRange for IMAGE_REF_ID : " + IMAGE_REF_ID);
            newImageReference.setLanguageId(languageId);
            newImageReference.setCompanyId(companyId);
            newImageReference.setPartnerId(partnerId);
            newImageReference.setPartnerName(partnerName);
            newImageReference.setHouseAirwayBill(houseAirwayBill);
            newImageReference.setMasterAirwayBill(masterAirwayBillNo);
            newImageReference.setPartnerHouseAirwayBill(partnerHouseAirwayBill);
            newImageReference.setPartnerMasterAirwayBill(partnerMasterAirwayBill);
            newImageReference.setPieceId(pieceId);
            newImageReference.setPieceItemId(pieceItemId);
            newImageReference.setReferenceImageUrl(url);
            newImageReference.setImageRefId(IMAGE_REF_ID);
            newImageReference.setReferenceField1(tableName);
            newImageReference.setReferenceField2(downloadUrl);
            newImageReference.setDeletionIndicator(0L);
            newImageReference.setCreatedBy(loginUserID);
            newImageReference.setCreatedOn(new Date());
            newImageReference.setUpdatedBy(null);
            newImageReference.setUpdatedOn(null);
            return newImageReference;
        } catch (Exception e) {
//            createImageReferenceLog2(addImageReference, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    // Create unique key based on the specified fields
    private String createUniquePickupKey(AddPickup pickup) {
        return Stream.of(
                        pickup.getPickupDetails().getName(),
                        pickup.getPickupDetails().getCompanyName(),
                        pickup.getPickupDetails().getAddressLine1(),
                        pickup.getPickupDetails().getAddressLine2(),
                        pickup.getPickupDetails().getCity(),
                        pickup.getPickupDetails().getDistrict(),
                        pickup.getPickupDetails().getState(),
                        pickup.getPickupDetails().getCountry(),
                        pickup.getPickupDetails().getPinCode(),
                        pickup.getPickupDetails().getPhone(),
                        pickup.getPickupDetails().getAlternatePhone()
                ).filter(Objects::nonNull)
                .collect(Collectors.joining("-"));
    }

    public List<PickupEntity> createConsolidationPickup(FindConsolidation findConsolidation, String loginUserID) {
        List<PickupEntity> pickupEntityList = new ArrayList<>();
//        ConcurrentHashMap<String, String> partnerPickUpIdMap = new ConcurrentHashMap<>();
        log.info("Consolidation Pickup Input" + findConsolidation);

        List<Consolidation> dbConsignment = pickupEntityRepository.getConsignmentData(findConsolidation.getHouseAirwayBill());
        String pickupId = generateUniqueNumberRange("PICKUPID");
        dbConsignment.forEach(consignment -> {
            // Create new Pickup
            PickupEntity newPickup = new PickupEntity();
            BeanUtils.copyProperties(consignment, newPickup, CommonUtils.getNullPropertyNames(consignment));
            newPickup.setPickupId(pickupId);
            newPickup.setCreatedBy(loginUserID);
            newPickup.setCreatedOn(new Date());
            newPickup.setUpdatedBy(loginUserID);
            newPickup.setUpdatedOn(new Date());

//            String uniquePickupKey = createUniquePickupId(consignment);
//            partnerPickUpIdMap.computeIfAbsent(uniquePickupKey, key -> {
//                try {
//                    return generateUniqueNumberRange("PICKUPID");
//                } catch (Exception e) {
//                    log.info("Failed to generate PICKUPID : " + key, e);
//                    throw new RuntimeException("Failed to generate PICKUPID");
//                }
//            });
//            newPickup.setPickupId(partnerPickUpIdMap.get(uniquePickupKey));
            newPickup.setStatusId("3");
            String desc = replicaPickupDetailsRepository.getStatusDescription("3");
            if (desc != null && !desc.isEmpty()) {
                newPickup.setStatusDescription(desc);
            }

            // Add pickup details create
            PickupDetails pd = new PickupDetails();
            BeanUtils.copyProperties(consignment, pd, CommonUtils.getNullPropertyNames(consignment));
            pd.setCreatedBy(loginUserID);
            pd.setCreatedOn(new Date());
            newPickup.setPickupDetails(pd);

            // Add destination details create
            PickupDestinationDetails dd = new PickupDestinationDetails();
            BeanUtils.copyProperties(consignment, dd, CommonUtils.getNullPropertyNames(consignment));
            dd.setCreatedBy(loginUserID);
            dd.setCreatedOn(new Date());
            newPickup.setConsignmentCreation("1");
            newPickup.setDestinationDetails(dd);

            PickupEntity pickupEntity = pickupEntityRepository.save(newPickup);
            pickupEntityRepository.updateConsignmentWithPickup("3", pickupEntity.getStatusDescription(), pickupEntity.getHouseAirwayBill());
            pickupEntityList.add(pickupEntity);
            log.info("size --{}", pickupEntityList.size());
            consignmentStatusService.insertConsignmentStatusRecord(pickupEntity.getLanguageId(), pickupEntity.getLanguageDescription(), pickupEntity.getCompanyId(), pickupEntity.getCompanyName(),
                    pickupEntity.getPieceId(), pickupEntity.getHouseAirwayBill(), "STATUS", pickupEntity.getStatusId(), pickupEntity.getStatusDescription(),
                    pickupEntity.getStatusTimeStamp(), "STATUS", pickupEntity.getStatusId(), pickupEntity.getStatusDescription(), pickupEntity.getStatusTimeStamp(), loginUserID);
            pickupEntityRepository.updatePickedUpStatus("3", pickupEntity.getStatusDescription(), pickupEntity.getHouseAirwayBill());
        });
        return pickupEntityList;
    }

    private String createUniquePickupId(Consolidation consignment) {
        return Stream.of(
                        consignment.getDestName(),
                        consignment.getDestCompanyName(),
                        consignment.getDestAddressLine1(),
                        consignment.getDestAddressLine2(),
                        consignment.getDestCity(),
                        consignment.getDestDistrict(),
                        consignment.getDestState(),
                        consignment.getDestCountry(),
                        consignment.getDestPincode(),
                        consignment.getDestPhone(),
                        consignment.getDestAlternatePhone()
                ).filter(Objects::nonNull)
                .collect(Collectors.joining("-"));
    }


    /**
     * @param addConsignmentStatuses
     * @param loginUserID
     * @return
     */
    public List<ConsignmentStatus> addConsignmentStatus(List<AddConsignmentStatus> addConsignmentStatuses, String loginUserID) {
        List<ConsignmentStatus> consignmentStatusList = new ArrayList<>();
        ConcurrentHashMap<String, String> partnerPickUpIdMap = new ConcurrentHashMap<>();
        log.info("Consignment Update Input ----------->" + addConsignmentStatuses);
        addConsignmentStatuses.forEach(consignmentStatus -> {
            List<IKeyValuePair> pickup = replicaPickupEntityRepository.getPickupData(consignmentStatus.getCompanyId(), consignmentStatus.getLanguageId(),consignmentStatus.getHouseAirwayBill());
            if(pickup != null && !pickup.isEmpty()) {
                pickup.forEach(p->{
                    ConsignmentStatus newConsignmentStatus = new ConsignmentStatus();
                    Long maxStatus = consignmentStatusRepository.getMaxConStatusId();
                    newConsignmentStatus.setConsignmentStatusId(maxStatus != null ? maxStatus : 1L);
                    newConsignmentStatus.setLanguageId(p.getLangId());
                    newConsignmentStatus.setLanguageDescription(p.getLangDesc());
                    newConsignmentStatus.setCompanyId(p.getCompanyId());
                    newConsignmentStatus.setCompanyName(p.getCompanyDesc());
                    newConsignmentStatus.setPieceId(p.getPieceId());
                    newConsignmentStatus.setHouseAirwayBill(consignmentStatus.getHouseAirwayBill());
                    newConsignmentStatus.setHawbType("STATUS");
                    newConsignmentStatus.setHawbTypeId("3");
                    String statusText = replicaPickupEntityRepository.getStatusDescription("3");
                    if (statusText != null && !statusText.isEmpty()) {
                        newConsignmentStatus.setPieceTypeDescription(statusText);
                        newConsignmentStatus.setHawbTypeDescription(statusText);
                    }
                    newConsignmentStatus.setHawbTimeStamp(new Date());
                    newConsignmentStatus.setPieceType("STATUS");
                    newConsignmentStatus.setPieceTypeId("3");
                    newConsignmentStatus.setPieceTimeStamp(new Date());
                    newConsignmentStatus.setDeletionIndicator(0L);
                    newConsignmentStatus.setCreatedBy(loginUserID);
                    newConsignmentStatus.setCreatedOn(new Date());
                    newConsignmentStatus.setUpdatedBy(loginUserID);
                    newConsignmentStatus.setUpdatedOn(new Date());
                    consignmentStatusRepository.save(newConsignmentStatus);
                    consignmentStatusList.add(newConsignmentStatus);

                    pickupEntityRepository.updateStatus("3", statusText, consignmentStatus.getHouseAirwayBill());
                    log.info("PICKUP UPDATED TO --> {}", statusText);
                });
            } else {
                List<IKeyValuePair> consignment = replicaPickupEntityRepository.getConsignmentData(consignmentStatus.getCompanyId(),consignmentStatus.getLanguageId(),consignmentStatus.getHouseAirwayBill());
                String pickupId = generateUniqueNumberRange("PICKUPID");
                if(consignment != null && !consignment.isEmpty()) {
                    consignment.forEach(c -> {
                        String pieceId = replicaPickupEntityRepository.getPiece(c.getConsignmentId());
                        Consolidation dbConsignment = pickupEntityRepository.getConsignment(consignmentStatus.getHouseAirwayBill());

                        // Create new Pickup
                        PickupEntity newPickup = new PickupEntity();
                        BeanUtils.copyProperties(dbConsignment, newPickup, CommonUtils.getNullPropertyNames(dbConsignment));
                        newPickup.setCreatedBy(loginUserID);
                        newPickup.setCreatedOn(new Date());
                        newPickup.setUpdatedBy(loginUserID);
                        newPickup.setUpdatedOn(new Date());

//                        String uniquePickupKey = createUniquePickupId(dbConsignment);
//                        partnerPickUpIdMap.computeIfAbsent(uniquePickupKey, key -> {
//                            try {
//                                return generateUniqueNumberRange("PICKUPID");
//                            } catch (Exception e) {
//                                log.info("Failed to generate PICKUPID : " + key, e);
//                                throw new RuntimeException("Failed to generate PICKUPID");
//                            }
//                        });
                        newPickup.setPickupId(pickupId);
                        newPickup.setStatusId("3");
                        String desc = replicaPickupDetailsRepository.getStatusDescription("3");
                        if (desc != null && !desc.isEmpty()) {
                            newPickup.setStatusDescription(desc);
                        }
                        newPickup.setPieceId(pieceId);

                        // Add pickup details create
                        PickupDetails pd = new PickupDetails();

                        BeanUtils.copyProperties(consignment, pd, CommonUtils.getNullPropertyNames(consignment));
                        pd.setCreatedBy(loginUserID);
                        pd.setCreatedOn(new Date());
                        newPickup.setPickupDetails(pd);

                        // Add destination details create
                        PickupDestinationDetails dd = new PickupDestinationDetails();

                        BeanUtils.copyProperties(consignment, dd, CommonUtils.getNullPropertyNames(consignment));
                        dd.setCreatedBy(loginUserID);
                        dd.setCreatedOn(new Date());
                        newPickup.setConsignmentCreation("1");
                        newPickup.setDestinationDetails(dd);
                        PickupEntity pickupEntity = pickupEntityRepository.save(newPickup);
                        ConsignmentStatus status = consignmentStatusService.insertConsignmentStatusRecord(pickupEntity.getLanguageId(), pickupEntity.getLanguageDescription(), pickupEntity.getCompanyId(), pickupEntity.getCompanyName(),
                                pickupEntity.getPieceId(), pickupEntity.getHouseAirwayBill(), "STATUS", pickupEntity.getStatusId(), pickupEntity.getStatusDescription(),
                                pickupEntity.getStatusTimeStamp(), "STATUS", pickupEntity.getStatusId(), pickupEntity.getStatusDescription(), pickupEntity.getStatusTimeStamp(), loginUserID);
                        consignmentStatusList.add(status);
                    });
                }
            }
            List<IKeyValuePair> consignment = replicaPickupEntityRepository.getConsignmentData(consignmentStatus.getCompanyId(),consignmentStatus.getLanguageId(),consignmentStatus.getHouseAirwayBill());
            if(consignment != null) {
                consignment.forEach(c ->{
                    String pieceId = replicaPickupEntityRepository.getPiece(c.getConsignmentId());
                    String status = replicaPickupEntityRepository.getStatusDescription("12");
                    ConsignmentStatus status1 = consignmentStatusService.insertConsignmentStatusRecord(c.getLangId(), c.getLangDesc(), c.getCompanyId(),
                            c.getCompanyDesc(), pieceId, consignmentStatus.getHouseAirwayBill(), "STATUS", "12",
                            status, new Date(), "STATUS", "12", status, new Date(), loginUserID);
                    IKeyValuePair partnerHubMapping = replicaPickupEntityRepository.getHubCode(c.getPartnerId());
                    log.info("partner hub --> {}",partnerHubMapping.getHubCode());
                    if(partnerHubMapping != null) {
                        pickupEntityRepository.updateStatusAndHub(status, partnerHubMapping.getHubCode(), partnerHubMapping.getHubName(), consignmentStatus.getHouseAirwayBill());
                        log.info("CONSIGNMENT UPDATED TO --> {}", status);
                    }
                    consignmentStatusList.add(status1);
                });
            } else {
                throw new IllegalStateException("No data found");
            }
        });
        return consignmentStatusList;
    }

    // Pickup Status count
    public List<PickupStatusCount> statusPickupCount(StatusCountInput input) throws ParseException {
        List<PickupStatusCount> pickupStatusCountList = new ArrayList<>();
        if (input.getFromDate() != null && input.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(input.getFromDate(), input.getToDate());
            input.setFromDate(dates[0]);
            input.setToDate(dates[1]);
        }

        PickupStatusCount pickupStatusCount;
        Long totalCount = 0L;

        // UnAssigned
        Object[] ua = replicaPickupEntityRepository.getUnAssigned(input.getFromDate(), input.getToDate());
        if (ua != null && ua.length > 0 && ua[0] != null) {
            pickupStatusCount = new PickupStatusCount();
            pickupStatusCount.setStatusText("UnAssigned");
            pickupStatusCount.setStatusId(2L);
            pickupStatusCount.setStatusCount(((Number) ua[0]).longValue());
            totalCount += ((Number) ua[0]).longValue();
            pickupStatusCountList.add(pickupStatusCount);
        } else {
            pickupStatusCount = new PickupStatusCount();
            pickupStatusCount.setStatusText("UnAssigned");
            pickupStatusCount.setStatusId(2L);
            pickupStatusCount.setStatusCount(0L);
            pickupStatusCountList.add(pickupStatusCount);
        }

        // Assigned Count
        Object[] as = replicaPickupEntityRepository.getAssigned(input.getFromDate(), input.getToDate());
        if (as != null && as.length > 0 && as[0] != null) {
            pickupStatusCount = new PickupStatusCount();
            pickupStatusCount.setStatusCount(((Number) as[0]).longValue());
            pickupStatusCount.setStatusId(48L);
            pickupStatusCount.setStatusText("Assigned");
            totalCount += ((Number) as[0]).longValue();
            pickupStatusCountList.add(pickupStatusCount);
        } else {
            pickupStatusCount = new PickupStatusCount();
            pickupStatusCount.setStatusText("Assigned");
            pickupStatusCount.setStatusId(48L);
            pickupStatusCount.setStatusCount(0L);
            pickupStatusCountList.add(pickupStatusCount);
        }

        // InProgress
        Object[] ip = replicaPickupEntityRepository.getInProgress(input.getFromDate(), input.getToDate());
        if (ip != null && ip.length > 0 && ip[0] != null) {
            pickupStatusCount = new PickupStatusCount();
            pickupStatusCount.setStatusCount(((Number) ip[0]).longValue());
            pickupStatusCount.setStatusId(52L);
            pickupStatusCount.setStatusText("InProgress");
            totalCount += ((Number) ip[0]).longValue();
            pickupStatusCountList.add(pickupStatusCount);
        } else {
            pickupStatusCount = new PickupStatusCount();
            pickupStatusCount.setStatusCount(0L);
            pickupStatusCount.setStatusId(52L);
            pickupStatusCount.setStatusText("InProgress");
            pickupStatusCountList.add(pickupStatusCount);
        }

        // PickedUp
        Object[] pp = replicaPickupEntityRepository.getPicked(input.getFromDate(), input.getToDate());
        if (pp != null && pp.length > 0 && pp[0] != null) {
            pickupStatusCount = new PickupStatusCount();
            pickupStatusCount.setStatusId(3L);
            pickupStatusCount.setStatusCount(((Number) pp[0]).longValue());
            pickupStatusCount.setStatusText("PickedUp");
            totalCount += ((Number) pp[0]).longValue();
            pickupStatusCountList.add(pickupStatusCount);
        } else {
            pickupStatusCount = new PickupStatusCount();
            pickupStatusCount.setStatusId(3L);
            pickupStatusCount.setStatusCount(0L);
            pickupStatusCount.setStatusText("PickedUp");
            pickupStatusCountList.add(pickupStatusCount);
        }

        // Total
        PickupStatusCount totalStatusCount = new PickupStatusCount();
        totalStatusCount.setStatusText("Total");
        totalStatusCount.setStatusId(0L);
        totalStatusCount.setStatusCount(totalCount);
        pickupStatusCountList.add(totalStatusCount);

        return pickupStatusCountList;
    }

    /**
     * Customer Portal Pickup Status Count
     *
     * @param input
     * @return
     * @throws ParseException
     */
    public List<CustomerPickupStatusCount> statusCustomerPickupCount(StatusCountInput input) throws ParseException {
        List<CustomerPickupStatusCount> customerPickupStatusCountList = new ArrayList<>();
        if (input.getFromDate() != null && input.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(input.getFromDate(), input.getToDate());
            input.setFromDate(dates[0]);
            input.setToDate(dates[1]);
        }

        CustomerPickupStatusCount customerPickupStatusCount;
        Long totalCount = 0L;

        // Created
        Object[] cr = replicaPickupEntityRepository.getUnAssigned(input.getFromDate(), input.getToDate());
        if (cr != null && cr.length > 0 && cr[0] != null) {
            customerPickupStatusCount = new CustomerPickupStatusCount();
            customerPickupStatusCount.setStatusText("Created");
            customerPickupStatusCount.setStatusId("2");
            customerPickupStatusCount.setStatusCount(((Number) cr[0]).longValue());
            totalCount += ((Number) cr[0]).longValue();
            customerPickupStatusCountList.add(customerPickupStatusCount);
        } else {
            customerPickupStatusCount = new CustomerPickupStatusCount();
            customerPickupStatusCount.setStatusText("Created");
            customerPickupStatusCount.setStatusId("2");
            customerPickupStatusCount.setStatusCount(0L);
            customerPickupStatusCountList.add(customerPickupStatusCount);
        }

        // Attempted
        Object[] at = replicaPickupEntityRepository.getAttempted(input.getFromDate(), input.getToDate());
        if (at != null && at.length > 0 && at[0] != null) {
            customerPickupStatusCount = new CustomerPickupStatusCount();
            customerPickupStatusCount.setStatusText("Attempted");
            customerPickupStatusCount.setStatusId("49, 53");
            customerPickupStatusCount.setStatusCount(((Number) at[0]).longValue());
            totalCount += ((Number) at[0]).longValue();
            customerPickupStatusCountList.add(customerPickupStatusCount);
        } else {
            customerPickupStatusCount = new CustomerPickupStatusCount();
            customerPickupStatusCount.setStatusText("Attempted");
            customerPickupStatusCount.setStatusId("49, 53");
            customerPickupStatusCount.setStatusCount(0L);
            customerPickupStatusCountList.add(customerPickupStatusCount);
        }

        // Success
        Object[] ss = replicaPickupEntityRepository.getPicked(input.getFromDate(), input.getToDate());
        if (ss != null && ss.length > 0 && ss[0] != null) {
            customerPickupStatusCount = new CustomerPickupStatusCount();
            customerPickupStatusCount.setStatusText("Success");
            customerPickupStatusCount.setStatusId("3");
            customerPickupStatusCount.setStatusCount(((Number) ss[0]).longValue());
            totalCount += ((Number) ss[0]).longValue();
            customerPickupStatusCountList.add(customerPickupStatusCount);
        } else {
            customerPickupStatusCount = new CustomerPickupStatusCount();
            customerPickupStatusCount.setStatusText("Success");
            customerPickupStatusCount.setStatusId("3");
            customerPickupStatusCount.setStatusCount(0L);
        }

        // Cancelled
        Object[] cc = replicaPickupEntityRepository.getCancelled(input.getFromDate(), input.getToDate());
        if (cc != null && cc.length > 0 && cc[0] != null) {
            customerPickupStatusCount = new CustomerPickupStatusCount();
            customerPickupStatusCount.setStatusText("Cancelled");
            customerPickupStatusCount.setStatusId("52");
            customerPickupStatusCount.setStatusCount(((Number) cc[0]).longValue());
            totalCount += ((Number) cc[0]).longValue();
            customerPickupStatusCountList.add(customerPickupStatusCount);
        } else {
            customerPickupStatusCount = new CustomerPickupStatusCount();
            customerPickupStatusCount.setStatusText("Cancelled");
            customerPickupStatusCount.setStatusId("52");
            customerPickupStatusCount.setStatusCount(0L);
            customerPickupStatusCountList.add(customerPickupStatusCount);
        }

        // All
        CustomerPickupStatusCount totalStatusCount = new CustomerPickupStatusCount();
        totalStatusCount.setStatusText("All");
        totalStatusCount.setStatusId("2, 53, 49, 3, 52");
        totalStatusCount.setStatusCount(totalCount);
        customerPickupStatusCountList.add(totalStatusCount);

        return customerPickupStatusCountList;
    }

}
