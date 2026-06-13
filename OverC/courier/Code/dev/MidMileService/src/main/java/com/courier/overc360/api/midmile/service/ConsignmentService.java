package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignment.*;
import com.courier.overc360.api.midmile.primary.model.finance.AsrPriceList;
import com.courier.overc360.api.midmile.primary.model.finance.CodPriceList;
import com.courier.overc360.api.midmile.primary.model.finance.RtoPriceList;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.UpdateImageReference;
import com.courier.overc360.api.midmile.primary.model.itemdetails.AddItemDetails;
import com.courier.overc360.api.midmile.primary.model.itemdetails.ItemDetails;
import com.courier.overc360.api.midmile.primary.model.itemdetails.UpdateItemDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.AddPieceDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.PieceDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.UpdatePieceDetails;
import com.courier.overc360.api.midmile.primary.repository.*;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.consignment.*;
import com.courier.overc360.api.midmile.replica.model.dto.*;
import com.courier.overc360.api.midmile.primary.model.finance.Rate;
import com.courier.overc360.api.midmile.replica.model.itemdetails.ReplicaItemDetails;
import com.courier.overc360.api.midmile.replica.model.piecedetails.ReplicaPieceDetails;
import com.courier.overc360.api.midmile.replica.repository.*;
import com.courier.overc360.api.midmile.replica.repository.specification.PreAlertManifestConsignmentSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ConsignmentService {


    @Autowired
    ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    ConsignmentStatusService consignmentStatusService;

    @Autowired
    NumberRangeService numberRangeService;

    @Autowired
    ReplicaConsignmentEntityRepository replicaConsignmentEntityRepository;

    @Autowired
    BondedManifestRepository bondedManifestRepository;

    @Autowired
    ReplicaBondedManifestRepository replicaBondedManifestRepository;

    @Autowired
    PieceDetailsService pieceDetailsService;

    @Autowired
    CommonService commonService;

    @Autowired
    ReplicaImageReferenceRepository replicaImageReferenceRepository;

    @Autowired
    ImageReferenceService imageReferenceService;

    @Autowired
    ImageReferenceRepository imageReferenceRepository;

    @Autowired
    PieceDetailsRepository pieceDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @Autowired
    ItemDetailsService itemDetailsService;

    @Autowired
    ReplicaOriginDetailsRepository replicaOriginDetailsRepository;

    @Autowired
    ReplicaDestinationDetailsRepository replicaDestinationDetailsRepository;

    @Autowired
    ReplicaPieceDetailsRepository replicaPieceDetailsRepository;

    @Autowired
    ConsoleRepository consoleRepository;

    @Autowired
    InventoryTableService inventoryTableService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    CodPriceListRepository codPriceListRepository;

    @Autowired
    AsrPriceListRepository asrPriceListRepository;

    @Autowired
    RtoPriceRepository rtoPriceRepository;

    @Autowired
    ProcessedConsignmentService processedConsignmentService;

    @Autowired
    DeliveryRepository deliveryRepository;


    /**
     * @param consignmentEntityList
     * @param loginUserID
     * @return
     * @throws Exception
     */
    @Transactional
    public List<ConsignmentEntity> createConsignmentEntity(List<AddConsignment> consignmentEntityList, String loginUserID) throws Exception {

        List<ConsignmentEntity> consignmentEntities = Collections.synchronizedList(new CopyOnWriteArrayList<>());
//        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        String masterAirwayBill = numberRangeService.getNextNumberRange("MAWB");
        // Cassandra save
//        commonService.createConsignment(consignmentEntityList, loginUserID, masterAirwayBill);
        List<CompletableFuture<Void>> futures = consignmentEntityList.stream()
                .map(consignmentEntity -> CompletableFuture.runAsync(() -> {
                    try {
                        ConsignmentEntity result = processConsignment(consignmentEntity, masterAirwayBill, loginUserID);
                        consignmentEntities.add(result);
                    } catch (Exception e) {
                        log.error("Error processing consignment: {}", consignmentEntity, e);
                        throw new BadRequestException("Consignment Create Failed " + e);
                    }
                }, executor))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
//        commonService.createConsignment(consignmentEntities, masterAirwayBill);
        // Send Notification
        notificationService.sendNotificationForConsignmentCreate(consignmentEntities.get(0).getCompanyId(),
                consignmentEntities.get(0).getLanguageId());
        return consignmentEntities;
    }


    /**
     * @param consignmentEntity
     * @param loginUserID
     * @return
     */
    @Transactional
    public ConsignmentEntity processConsignment(AddConsignment consignmentEntity, String masterAirwayBill, String loginUserID)
            throws Exception {
        ConsignmentEntity newConsignment = new ConsignmentEntity();
        Map<Integer, String> logs = new HashMap<>();
        String status = "SUCCESS";
        try {
//            consignmentEntity = createConsignmentNullValidation(consignmentEntity);                                                                                                                                 // NULL VALIDATION CHECK CODE
            logs.put(1, "Step 2: Consignment Validation Success");
            IKeyValuePair iKeyValuePair = replicaConsignmentEntityRepository.getDescription(consignmentEntity.getCompanyId());                                                                                      // GET COMPANY DESCRIPTION
            String companyId = consignmentEntity.getCompanyId();
            String languageId = iKeyValuePair.getLangId();
            String partnerId = consignmentEntity.getPartnerId();
            String partnerName = consignmentEntity.getPartnerName();
            String partnerHawBill = consignmentEntity.getPartnerHouseAirwayBill();
            String partnerMawBill = consignmentEntity.getPartnerMasterAirwayBill();
            String productName = consignmentEntity.getProductName();
            String hawbTypeId = consignmentEntity.getHawbTypeId();
            if (partnerHawBill != null) {
                ConsignmentEntity consignment = consignmentEntityRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerHouseAirwayBillAndDeletionIndicator(                                                   // DUPLICATE CHECK
                        iKeyValuePair.getLangId(), companyId, partnerId, partnerHawBill, 0L);
                if (consignment != null) {
                    throw new BadRequestException("Given Values Getting Duplicate PartnerHouseAirwayBill " + consignment.getPartnerHouseAirwayBill());
                }
            } else if (consignmentEntity.getPickupEntityId() != null) {
                boolean duplicatePickup = consignmentEntityRepository.existsByPickupEntityIdAndDeletionIndicator(consignmentEntity.getPickupEntityId(), 0L);
                if (duplicatePickup) {
                    throw new BadRequestException("Given Values Getting Duplicate for PickUpEntityId  - " + consignmentEntity.getPickupEntityId());
                }
            }
            // SetHouseAirwayBill
            String houseAirwayBill = Optional.ofNullable(generateUniqueHouseAirwayBill(productName))
                    .orElseThrow(() -> new BadRequestException("Number Range Object " + productName + " Doesn't exist in NumberRangeTable"));
            logs.put(2, "Step 2: Consignment Checking Duplicate and Generate Consignment Success PartnerHouseAirwayBill - " + partnerHawBill + " PickupEntityId - " + consignmentEntity.getPickupEntityId() + " Consignment " + productName);
            int pieceCount = Optional.ofNullable(consignmentEntity.getPieceDetails()).map(List::size).orElse(0);                                                                                                                          // PIECE_COUNT SET IN NO_OF_HAB
            int totalItemCount = 0;
            String currency = null;
            // Set LoadType Text
            if (consignmentEntity.getLoadTypeId() != null) {
                String getLoadType = replicaBondedManifestRepository.getLoadTypeText(languageId, companyId, consignmentEntity.getLoadTypeId());
                if (getLoadType != null) {
                    consignmentEntity.setLoadType(getLoadType);
                }
            }
            // Set ServiceType Text
            if (consignmentEntity.getServiceTypeId() != null) {
                String getServiceType = replicaBondedManifestRepository.getServiceTypeText(languageId, companyId, consignmentEntity.getServiceTypeId());
                if (getServiceType != null) {
                    consignmentEntity.setServiceTypeText(getServiceType);
                }
            }
            // GetItemCount
            for (AddPieceDetails pieceDetails : consignmentEntity.getPieceDetails()) {
                List<AddItemDetails> addItemDetails = pieceDetails.getItemDetails();
                int itemCount = Optional.ofNullable(addItemDetails).map(List::size).orElse(0);
                totalItemCount += itemCount;
                if (addItemDetails != null) {
                    currency = addItemDetails.stream().findFirst().map(AddItemDetails::getCurrency).orElse(null);
                }
            }
            // Set noOfPieceHawb based on the total item count
            if (totalItemCount == 0) {
                consignmentEntity.setNoOfPieceHawb("1");
            } else {
                consignmentEntity.setNoOfPieceHawb(String.valueOf(totalItemCount));
            }
            BeanUtils.copyProperties(consignmentEntity, newConsignment, CommonUtils.getNullPropertyNames(consignmentEntity));
            newConsignment.setLanguageId(iKeyValuePair.getLangId());
            newConsignment.setLanguageDescription(iKeyValuePair.getLangDesc() != null ? iKeyValuePair.getLangDesc() : null);
            newConsignment.setCompanyName(iKeyValuePair.getCompanyDesc() != null ? iKeyValuePair.getCompanyDesc() : null);
            newConsignment.setNoOfPackageHawb(pieceCount == 0 ? "1" : String.valueOf(pieceCount));
            newConsignment.setConsignmentCurrency(currency);
            newConsignment.setHouseAirwayBill(houseAirwayBill);
            newConsignment.setMasterAirwayBill(masterAirwayBill);
            // Set additional details
            setupDestinationDetails(consignmentEntity, newConsignment, loginUserID);
            setupOriginDetails(consignmentEntity, newConsignment, loginUserID);
            setupReturnDetails(consignmentEntity, newConsignment, loginUserID);
            //HAWB_TYPE
            if (hawbTypeId != null) {
                consignmentEntityRepository.statusEventText(companyId, languageId, hawbTypeId)
                        .ifPresent(statusText -> {
                            newConsignment.setHawbType("STATUS");
                            newConsignment.setHawbTypeId(hawbTypeId);
                            newConsignment.setHawbTypeDescription(statusText);
                            newConsignment.setHawbTimeStamp(new Date());
                        });
            } else {
                consignmentEntityRepository.statusEventText(companyId, languageId, "1")
                        .ifPresent(statusText -> {
                            newConsignment.setHawbType("STATUS");
                            newConsignment.setHawbTypeId("1");
                            newConsignment.setHawbTypeDescription(statusText);
                            newConsignment.setHawbTimeStamp(new Date());
                        });
            }
            // CON_IMAGE
            Set<ImageReference> imageReferenceSet = Optional.ofNullable(consignmentEntity.getReferenceImageList())
                    .orElse(Collections.emptySet()).stream()
                    .map(imageReference -> {
                        String downloadDocument = commonService.downLoadDocument(imageReference.getReferenceImageUrl(), "document", "image");
                        if (downloadDocument != null) {
                            return imageReferenceService.createImageReference(
                                    languageId, companyId, partnerId, partnerName, houseAirwayBill, masterAirwayBill,
                                    partnerHawBill, partnerMawBill, null, null, imageReference.getReferenceImageUrl(),
                                    "CON_ID", downloadDocument, loginUserID);
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toSet());
            newConsignment.setReferenceImageList(imageReferenceSet);

            ConsignmentCommonValues commonValues = new ConsignmentCommonValues();
            BeanUtils.copyProperties(newConsignment, commonValues);
            // Set HubCode
            Object[] hc = consignmentEntityRepository.getHubCode(
                    partnerId, newConsignment.getPartnerType(), newConsignment.getLanguageId(),
                    newConsignment.getCompanyId(), newConsignment.getProductId());
            if (hc != null && hc.length >= 2) {
                String hubCode = hc[0] != null ? (String) hc[0] : "0";
                String hubName = hc[1] != null ? (String) hc[1] : "0";
                newConsignment.setHubCode(hubCode);
                newConsignment.setHubName(hubName);
            }

            // Set Address and Destinations
            newConsignment.setAddress(Stream.of(newConsignment.getDestinationDetails().getAddressLine1(), newConsignment.getDestinationDetails().getAddressLine2(), newConsignment.getDestinationDetails().getPinCode())
                    .filter(part -> part != null && !part.trim().isEmpty())
                    .collect(Collectors.joining(", ")));
            newConsignment.setDestinations(newConsignment.getAddress());

            // SetCountry
            commonValues.setCountry(consignmentEntity.getOriginDetails().getCountry() != null ? consignmentEntity.getOriginDetails().getCountry() : null);
            // Piece Create
            List<PieceDetails> pieceDetails = pieceDetailsService.createPieceDetailsList(commonValues, consignmentEntity.getPieceDetails());
            logs.put(3, "Consignment Piece And Item Created Successfully");
            // TotalPieceVolume
            Double totalPieceVolume = pieceDetails.stream().map(PieceDetails::getVolume).filter(n -> n != null).mapToDouble(a -> a).sum();
            Double totalPieceWeight = pieceDetails.stream().map(PieceDetails::getWeight).filter(n -> n != null).mapToDouble(a -> a).sum();
            Double totalPieceLength = pieceDetails.stream().map(PieceDetails::getLength).filter(n -> n != null).mapToDouble(a -> a).sum();
            Double totalPieceWidth = pieceDetails.stream().map(PieceDetails::getWidth).filter(n -> n != null).mapToDouble(a -> a).sum();
            Double totalPieceHeight = pieceDetails.stream().map(PieceDetails::getHeight).filter(n -> n != null).mapToDouble(a -> a).sum();
            String firstWeightUnit = pieceDetails.stream().map(PieceDetails::getWeight_unit).filter(Objects::nonNull).findFirst().orElse("");

            newConsignment.setPieceDetails(pieceDetails);
            newConsignment.setVolume(totalPieceVolume);
            newConsignment.setWeight(totalPieceWeight);
            newConsignment.setLength(totalPieceLength);
            newConsignment.setWidth(totalPieceWidth);
            newConsignment.setHeight(totalPieceHeight);
            newConsignment.setWeightUnit(firstWeightUnit);
            newConsignment.setActualWeight(totalPieceWeight);
            newConsignment.setCreatedBy(loginUserID);
            newConsignment.setCreatedOn(new Date());
            newConsignment.setUpdatedBy(null);
            newConsignment.setUpdatedOn(null);
            newConsignment.setConsoleIndicator(0L);
            newConsignment.setManifestIndicator(0L);
            logs.put(4, "Consignment Process Successfully and Finance Process Start");

            /* ===========================================FINANCE_LOGIC_CODE=====================================================================*/
            // Rate_List
            List<Rate> rateList = rateRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndDeletionIndicator(companyId, languageId, partnerId, 0L);
                for(Rate rate : rateList) {
                    double ceilingValue = rate.getCeilingValue() != null ? rate.getCeilingValue() : 0.0;
                    double from = rate.getRangeFrom() != null ? rate.getRangeFrom() : 0.0;
                    double to = rate.getRangeTo() != null ? rate.getRangeTo() : 0.0;
                    double chargeWeight = calculateAdjustedChargeWeight(totalPieceWeight, ceilingValue);
                    if (rate.getRateParameterId().equalsIgnoreCase("4")) {
                        if (from <= chargeWeight && to >= chargeWeight) {

                            newConsignment.setChargeableWeight(chargeWeight);
                            newConsignment.setCeilingValue(ceilingValue);
                            newConsignment.setFrightCharge(rate.getRate());
                        }
                    }
                    if (rate.getRateParameterId().equalsIgnoreCase("2")) {
                        newConsignment.setChargeableWeight(chargeWeight);
                        newConsignment.setCeilingValue(ceilingValue);
//                    newConsignment.setFrightCharge(rate.getRate());
                    }
                    logs.put(5, "Consignment Finance Logic FrightCharge Set SuccessFully");
                }

            // COD_PRICE_LIST
            if (consignmentEntity.getPaymentType() != null && consignmentEntity.getPaymentType().equalsIgnoreCase("COD")) {
                List<CodPriceList> codPriceListList = codPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndDeletionIndicator(languageId, companyId, partnerId, 0L);
                codPriceListList.forEach(cod -> {
                    double ceilingValue = cod.getCeilingValue() != null ? cod.getCeilingValue() : 0.0;
                    double from = cod.getRangeFrom() != null ? cod.getRangeFrom() : 0.0;
                    double to = cod.getRangeTo() != null ? cod.getRangeTo() : 0.0;
                    double chargeWeight = totalPieceWeight + ceilingValue;
                    if (cod.getRateParameterId().equalsIgnoreCase("4")) {
                        if (from <= chargeWeight && to >= chargeWeight) {
                            newConsignment.setCodCharge(cod.getRate());
                        }
                    }
                    if (cod.getRateParameterId().equalsIgnoreCase("2")) {
                        newConsignment.setCodCharge(cod.getRate());
                    }
                    logs.put(6, "Consignment Finance Logic COD Charge Set SuccessFully");
                });
            }
            // ASR_LIST
            if (consignmentEntity.getMovementType() != null && consignmentEntity.getMovementType().equalsIgnoreCase("ASR") && consignmentEntity.getConsignmentType().equalsIgnoreCase("2")) {
                List<AsrPriceList> asrPriceListList = asrPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndDeletionIndicator(languageId, companyId, partnerId, 0L);
                asrPriceListList.forEach(asr -> {
                    double ceilingValue = asr.getCeilingValue() != null ? asr.getCeilingValue() : 0.0;
                    double from = asr.getRangeFrom() != null ? asr.getRangeFrom() : 0.0;
                    double to = asr.getRangeTo() != null ? asr.getRangeTo() : 0.0;
                    double chargeWeight = totalPieceWeight + ceilingValue;
                    if (asr.getRateParameterId().equalsIgnoreCase("4")) {
                        if (from <= chargeWeight && to >= chargeWeight) {
                            newConsignment.setAsrCharge(asr.getRate());
                        }
                    }
                    if (asr.getRateParameterId().equalsIgnoreCase("2")) {
                        newConsignment.setAsrCharge(asr.getRate());
                    }
                    logs.put(6, "Consignment Finance Logic ASR Charge Set SuccessFully");
                });
            }

            double chargeWeight = calculateAdjustedChargeWeight(totalPieceWeight, newConsignment.getCeilingValue());
            double totalFreightCharge = 0.0;
            boolean firstRate = true;
            for (Rate rate : rateList) {
                double from = rate.getRangeFrom() != null ? rate.getRangeFrom() : 0.0;
                double to = rate.getRangeTo() != null ? rate.getRangeTo() : 0.0;
//                if (from <= chargeWeight && to >= chargeWeight) {
                    if (from <= chargeWeight) {
                    if(firstRate) {
                        totalFreightCharge += rate.getRate();
                        double weightToCharge = Math.min(chargeWeight, to) - from + 1;
                        chargeWeight -= weightToCharge;
                        firstRate = false;
                    } else {
                        totalFreightCharge += chargeWeight * rate.getRate();
                        double weightToCharge = Math.min(chargeWeight, to) - from + 1;
                        chargeWeight -= weightToCharge;
                    }
                    if (chargeWeight <= 0) {
                        break;
                    }
                }
            }
            newConsignment.setFrightCharge(totalFreightCharge);

            // TotalLMD
            double totalLmd =
                    (newConsignment.getFrightCharge() != null ? newConsignment.getFrightCharge() : 0.0) +
                            (newConsignment.getCodCharge() != null ? newConsignment.getCodCharge() : 0.0) +
                            (newConsignment.getFulfilmentCharge() != null ? newConsignment.getFulfilmentCharge() : 0.0) +
                            (newConsignment.getRtoCharge() != null ? newConsignment.getRtoCharge() : 0.0) +
                            (newConsignment.getAsrCharge() != null ? newConsignment.getAsrCharge() : 0.0) +
                            (newConsignment.getMovementCharge() != null ? newConsignment.getMovementCharge() : 0.0) +
                            (newConsignment.getTruckCharge() != null ? newConsignment.getTruckCharge() : 0.0);

            newConsignment.setTotalLmdCharges(totalLmd);
        } catch (Exception e) {
            status = "FAILED";
            logs.put(logs.size() + 1, "Step " + (logs.size() + 1) + ": Failure - " + e.getMessage());
            throw e;
        } finally {
            processedConsignmentService.saveProcessedConsignmentWithLogs(newConsignment, status, logs);
        }
        ConsignmentEntity ce = consignmentEntityRepository.save(newConsignment);
        logs.put(7, "Consignment Saved Successfully");
        // Multiple PieceId Update in Pickup
        String pieceId = ce.getPieceDetails().stream().map(PieceDetails::getPieceId).filter(Objects::nonNull).collect(Collectors.joining(","));
        // PickupTable Update
        if (consignmentEntity.getPickupEntityId() != null && consignmentEntity.getPickupEntityId() != 0) {
            updatePickup(ce.getHouseAirwayBill(), pieceId, consignmentEntity.getPickupEntityId());
        }
        return ce;
    }
    // ConsignmentEntity ce = consignmentEntityRepository.save(newConsignment);
    //        logs.put(7, "Consignment Saved Successfully");
    //        // Multiple PieceId Update in Pickup
    //        String pieceId = ce.getPieceDetails().stream().map(PieceDetails::getPieceId).filter(Objects::nonNull).collect(Collectors.joining(","));
    //        // PickupTable Update
    //        if (consignmentEntity.getPickupEntityId() != null && consignmentEntity.getPickupEntityId() != 0) {
    //            updatePickup(ce.getHouseAirwayBill(), pieceId, consignmentEntity.getPickupEntityId());
    //        }
    //        return ce;
    //    }

    // Synchronized for GenerateHouseAirwayBill
    public synchronized String generateUniqueHouseAirwayBill(String numRanObj) {
        return numberRangeService.getNextNumberRange(numRanObj);
    }

    // Synchronized for PickupUpdate
    public synchronized void updatePickup(String houseAB, String pieceId, Long entityId) {
        consignmentEntityRepository.updatePickup(houseAB, pieceId, entityId);
        log.info("PickupId Table Update Successfully HouseAirwayBill: {}, PieceId: {}, PickupId: {}",
                houseAB, pieceId, entityId);
    }

    // GetRoundOfValue
    public double calculateAdjustedChargeWeight(double totalPieceWeight, double ceilingValue) {
        return Math.ceil(totalPieceWeight / ceilingValue) * ceilingValue;
    }


    /*===================================================================================================================================================*/
    // Create Destination Details
    private void setupDestinationDetails(AddConsignment consignmentEntity, ConsignmentEntity newConsignment, String loginUserID) {
        DestinationDetails dd = consignmentEntity.getDestinationDetails();

        if (dd != null) {
            BeanUtils.copyProperties(dd, newConsignment.getDestinationDetails(), CommonUtils.getNullPropertyNames(dd));

            IKeyValuePair ikey = replicaConsignmentEntityRepository.getDescription(
                    dd.getCity(), dd.getCountry(), dd.getDistrict(), dd.getState());

            String districtName = (ikey != null) ? ikey.getDistrictName() : "";
            String cityName = (ikey != null) ? ikey.getCityName() : "";
            String provinceName = (ikey != null) ? ikey.getProvinceName() : "";
            String countryName = (ikey != null) ? ikey.getCountryName() : "";

            newConsignment.setAddDestinationDetails(Stream.of(
                            dd.getAddressLine1(), dd.getAddressLine2(), dd.getPinCode(),
                            districtName, cityName, provinceName, countryName)
                    .filter(part -> part != null && !part.trim().isEmpty())
                    .collect(Collectors.joining(", ")));
            newConsignment.getDestinationDetails().setCreatedBy(loginUserID);
            newConsignment.getDestinationDetails().setCreatedOn(new Date());
        } else {
            DestinationDetails details = new DestinationDetails();
            details.setCreatedBy(loginUserID);
            details.setCreatedOn(new Date());
            consignmentEntity.setDestinationDetails(details);
        }
    }

    //setupOriginDetails
    private void setupOriginDetails(AddConsignment consignmentEntity, ConsignmentEntity newConsignment, String loginUserID) {
        OriginDetails od = consignmentEntity.getOriginDetails();

        if (od != null) {
            BeanUtils.copyProperties(od, newConsignment.getOriginDetails(), CommonUtils.getNullPropertyNames(od));
            IKeyValuePair ikey = replicaConsignmentEntityRepository.getDescription(
                    od.getCity(), od.getCountry(), od.getDistrict(), od.getState());

            String districtName = (ikey != null) ? ikey.getDistrictName() : "";
            String cityName = (ikey != null) ? ikey.getCityName() : "";
            String provinceName = (ikey != null) ? ikey.getProvinceName() : "";
            String countryName = (ikey != null) ? ikey.getCountryName() : "";

            newConsignment.setAddOriginDetails(Stream.of(
                            od.getAddressLine1(), od.getAddressLine2(), od.getPinCode(),
                            districtName, cityName, provinceName, countryName)
                    .filter(part -> part != null && !part.trim().isEmpty())
                    .collect(Collectors.joining(", ")));

            newConsignment.getOriginDetails().setCreatedBy(loginUserID);
            newConsignment.getOriginDetails().setCreatedOn(new Date());
        } else {
            OriginDetails details = new OriginDetails();
            details.setCreatedBy(loginUserID);
            details.setCreatedOn(new Date());
            consignmentEntity.setOriginDetails(details);
        }
    }

    //setup ReturnDetails
    private void setupReturnDetails(AddConsignment consignmentEntity, ConsignmentEntity newConsignment, String loginUserID) {
        ReturnDetails returnDetails = consignmentEntity.getReturnDetails();

        if (returnDetails != null) {
            BeanUtils.copyProperties(returnDetails, newConsignment.getReturnDetails(), CommonUtils.getNullPropertyNames(returnDetails));
            newConsignment.getReturnDetails().setCreatedBy(loginUserID);
            newConsignment.getReturnDetails().setCreatedOn(new Date());
        } else {
            ReturnDetails details = new ReturnDetails();
            details.setCreatedBy(loginUserID);
            details.setCreatedOn(new Date());
            newConsignment.setReturnDetails(details);
        }
    }

    /**
     * Update ConsignmentEntity
     *
     * @param consignmentList
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
    public List<ConsignmentEntity> updateConsignmentEntity(List<UpdateConsignment> consignmentList, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {

        List<ConsignmentEntity> updatedConsignmentList = new ArrayList<>();
        for (UpdateConsignment consignment : consignmentList) {

            log.info("Consignment Update Input " + consignment);
            // Get existing Consignment
            ConsignmentEntity existingConsignmentEntity =
                    consignmentEntityRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
                            consignment.getCompanyId(), consignment.getLanguageId(), consignment.getPartnerId(), consignment.getMasterAirwayBill(), consignment.getHouseAirwayBill(), 0L);

            if (existingConsignmentEntity == null) {
                throw new BadRequestException("Given Values Doesn't exist CompanyId " + consignment.getCompanyId() + " LanguageId " + consignment.getLanguageId() +
                        " PartnerId " + consignment.getPartnerId() + " MasterAirwayBillNo " + consignment.getMasterAirwayBill() + " HouseAirwayBillNo " + consignment.getHouseAirwayBill());
            }

            //Null validation code
            consignment = updateConsignmentNullValidation(consignment);
            String iKeyValuePair = replicaConsignmentEntityRepository.getStatusEventDescription(consignment.getHawbTypeId());
            // Update Consignment fields
            BeanUtils.copyProperties(consignment, existingConsignmentEntity, CommonUtils.getNullPropertyNames(consignment));

            //Set hubName from hub table based on hub code
            String hubName = null;
            if (consignment.getHubCode() != null) {
                hubName = replicaConsignmentEntityRepository.getHubName(consignment.getHubCode());
            }
            existingConsignmentEntity.setHubName(hubName);
            existingConsignmentEntity.setDeletionIndicator(0L);
            existingConsignmentEntity.setUpdatedBy(loginUserID);
            existingConsignmentEntity.setUpdatedOn(new Date());

            // Destination Details
            if (consignment.getDestinationDetails() != null && existingConsignmentEntity.getDestinationDetails() != null) {
                UpdateDestinationDetails dd = consignment.getDestinationDetails();
                BeanUtils.copyProperties(dd, existingConsignmentEntity.getDestinationDetails(), CommonUtils.getNullPropertyNames(dd));

                IKeyValuePair ikey = replicaConsignmentEntityRepository.getDescription(
                        dd.getCity(), dd.getCountry(), dd.getDistrict(), dd.getState());

                String districtName = (ikey != null) ? ikey.getDistrictName() : "";
                String cityName = (ikey != null) ? ikey.getCityName() : "";
                String provinceName = (ikey != null) ? ikey.getProvinceName() : "";
                String countryName = (ikey != null) ? ikey.getCountryName() : "";

                existingConsignmentEntity.setAddDestinationDetails(Stream.of(
                                dd.getAddressLine1(), dd.getAddressLine2(), dd.getPinCode(),
                                districtName, cityName, provinceName, countryName)
                        .filter(part -> part != null && !part.trim().isEmpty())
                        .collect(Collectors.joining(", ")));

                existingConsignmentEntity.getDestinationDetails().setCreatedBy(loginUserID);
                existingConsignmentEntity.getDestinationDetails().setCreatedOn(new Date());
            }

            // ReturnDetails
            if (consignment.getOriginDetails() != null && existingConsignmentEntity.getOriginDetails() != null) {
                UpdateOriginationDetails od = consignment.getOriginDetails();

                BeanUtils.copyProperties(od, existingConsignmentEntity.getOriginDetails(), CommonUtils.getNullPropertyNames(od));
                IKeyValuePair ikey = replicaConsignmentEntityRepository.getDescription(
                        od.getCity(), od.getCountry(), od.getDistrict(), od.getState());

                String districtName = (ikey != null) ? ikey.getDistrictName() : "";
                String cityName = (ikey != null) ? ikey.getCityName() : "";
                String provinceName = (ikey != null) ? ikey.getProvinceName() : "";
                String countryName = (ikey != null) ? ikey.getCountryName() : "";

                existingConsignmentEntity.setAddOriginDetails(Stream.of(
                                od.getAddressLine1(), od.getAddressLine2(), od.getPinCode(),
                                districtName, cityName, provinceName, countryName)
                        .filter(part -> part != null && !part.trim().isEmpty())
                        .collect(Collectors.joining(", ")));
                existingConsignmentEntity.getOriginDetails().setCreatedBy(loginUserID);
                existingConsignmentEntity.getOriginDetails().setCreatedOn(new Date());
            }

            // Origin
            if (consignment.getReturnDetails() != null && existingConsignmentEntity.getReturnDetails() != null) {
                BeanUtils.copyProperties(consignment.getReturnDetails(), existingConsignmentEntity.getReturnDetails(), CommonUtils.getNullPropertyNames(consignment.getReturnDetails()));
                UpdateReturnDetails returnDetails = consignment.getReturnDetails();
                BeanUtils.copyProperties(returnDetails, existingConsignmentEntity.getReturnDetails(), CommonUtils.getNullPropertyNames(returnDetails));
                existingConsignmentEntity.getReturnDetails().setCreatedBy(loginUserID);
                existingConsignmentEntity.getReturnDetails().setCreatedOn(new Date());
            }

            // Update ReferenceImage
            Set<ImageReference> referenceImageLists = new HashSet<>();
            if (consignment.getReferenceImageList() != null && !consignment.getReferenceImageList().isEmpty()) {
                for (UpdateImageReference image : consignment.getReferenceImageList()) {
                    String downloadDocument = commonService.downLoadDocument(image.getReferenceImageUrl(), "document", "image");
                    ImageReference imageReferenceRecord = imageReferenceRepository.findByImageRefIdAndDeletionIndicator(image.getImageRefId(), 0L);
                    if (imageReferenceRecord == null) {
                        log.info("ImageReference doesn't exist" + image.getImageRefId());
                        continue;
                    }
                    imageReferenceRecord.setReferenceImageUrl(image.getReferenceImageUrl());
                    imageReferenceRecord.setReferenceField2(downloadDocument);
                    imageReferenceRecord.setDeletionIndicator(0L);
                    imageReferenceRecord.setUpdatedBy(loginUserID);
                    imageReferenceRecord.setUpdatedOn(new Date());
                    referenceImageLists.add(imageReferenceRepository.save(imageReferenceRecord));

                }
                existingConsignmentEntity.setReferenceImageList(referenceImageLists);
            }

            // Update PieceDetails
            if (consignment.getPieceDetails() != null && !consignment.getPieceDetails().isEmpty()) {
                List<PieceDetails> updatedPieceDetailsList = new ArrayList<>();
                for (UpdatePieceDetails pieceDetails : consignment.getPieceDetails()) {
                    Optional<PieceDetails> existingPieceOpt = existingConsignmentEntity.getPieceDetails().stream()
                            .filter(dbPiece -> Objects.equals(dbPiece.getPieceId(), pieceDetails.getPieceId()))
                            .findFirst();

                    if (existingPieceOpt.isPresent()) {
                        PieceDetails existingPiece = existingPieceOpt.get();
                        BeanUtils.copyProperties(pieceDetails, existingPiece, CommonUtils.getNullPropertyNames(pieceDetails));

                        // Update Piece ReferenceImage
                        Set<ImageReference> pieceImage = new HashSet<>();
                        if (pieceDetails.getReferenceImageList() != null && !pieceDetails.getReferenceImageList().isEmpty()) {
                            for (UpdateImageReference image : pieceDetails.getReferenceImageList()) {
                                String downloadDocument = commonService.downLoadDocument(image.getReferenceImageUrl(), "document", "image");
                                ImageReference imageReferenceRecord = imageReferenceRepository.findByImageRefIdAndDeletionIndicator(image.getImageRefId(), 0L);
                                if (imageReferenceRecord == null) {
                                    log.info("ImageReference doesn't exist" + image.getImageRefId());
                                    continue;
                                }
                                imageReferenceRecord.setReferenceImageUrl(image.getReferenceImageUrl());
                                imageReferenceRecord.setReferenceField2(downloadDocument);
                                imageReferenceRecord.setDeletionIndicator(0L);
                                imageReferenceRecord.setUpdatedBy(loginUserID);
                                imageReferenceRecord.setUpdatedOn(new Date());
                                pieceImage.add(imageReferenceRepository.save(imageReferenceRecord));
                            }
                        }
                        existingPiece.setReferenceImageList(pieceImage);

                        // Update ItemDetails
                        List<ItemDetails> updatedItemDetailsList = new ArrayList<>();
                        if (pieceDetails.getItemDetails() != null && !pieceDetails.getItemDetails().isEmpty()) {
                            for (UpdateItemDetails itemDetails : pieceDetails.getItemDetails()) {
                                Optional<ItemDetails> existingItemOpt = existingPiece.getItemDetails().stream()
                                        .filter(dbItem -> Objects.equals(dbItem.getPieceItemId(), itemDetails.getPieceItemId()))
                                        .findFirst();

                                if (existingItemOpt.isPresent()) {
                                    ItemDetails existingItem = existingItemOpt.get();
                                    BeanUtils.copyProperties(itemDetails, existingItem, CommonUtils.getNullPropertyNames(itemDetails));
                                    existingItem.setUpdatedBy(loginUserID);
                                    existingItem.setUpdatedOn(new Date());

                                    // Update Item ReferenceImage
                                    Set<ImageReference> itemImage = new HashSet<>();
                                    if (itemDetails.getReferenceImageList() != null && !itemDetails.getReferenceImageList().isEmpty()) {
                                        for (UpdateImageReference image : pieceDetails.getReferenceImageList()) {
                                            String downloadDocument = commonService.downLoadDocument(image.getReferenceImageUrl(), "document", "image");
                                            ImageReference imageReferenceRecord = imageReferenceRepository.findByImageRefIdAndDeletionIndicator(image.getImageRefId(), 0L);
                                            if (imageReferenceRecord == null) {
                                                log.info("ImageReference doesn't exist" + image.getImageRefId());
                                                continue;
                                            }
                                            imageReferenceRecord.setReferenceImageUrl(image.getReferenceImageUrl());
                                            imageReferenceRecord.setReferenceField2(downloadDocument);
                                            imageReferenceRecord.setDeletionIndicator(0L);
                                            imageReferenceRecord.setUpdatedBy(loginUserID);
                                            imageReferenceRecord.setUpdatedOn(new Date());
                                            itemImage.add(imageReferenceRepository.save(imageReferenceRecord));
                                        }
                                    }
                                    existingItem.setReferenceImageList(itemImage);
                                    updatedItemDetailsList.add(itemDetailsRepository.save(existingItem));
                                }
                            }
                        }
                        existingPiece.setItemDetails(updatedItemDetailsList);
                        existingPiece.setDeletionIndicator(0L);
                        existingPiece.setUpdatedBy(loginUserID);
                        existingPiece.setUpdatedOn(new Date());
                        updatedPieceDetailsList.add(pieceDetailsRepository.save(existingPiece));
                    }
                }
                existingConsignmentEntity.setPieceDetails(updatedPieceDetailsList);
            }

            // FINANCE
            // RTO_PRICE_LIST
            if (existingConsignmentEntity.getHawbTypeId().equalsIgnoreCase("24")) {
                List<RtoPriceList> rtoPriceListList = rtoPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndDeletionIndicator(existingConsignmentEntity.getLanguageId(), existingConsignmentEntity.getCompanyId(), existingConsignmentEntity.getPartnerId(), 0L);
                rtoPriceListList.forEach(rto -> {
                    double ceilingValue = rto.getCeilingValue() != null ? rto.getCeilingValue() : 0.0;
                    double from = rto.getRangeFrom() != null ? rto.getRangeFrom() : 0.0;
                    double to = rto.getRangeTo() != null ? rto.getRangeTo() : 0.0;
                    double chargeWeight = existingConsignmentEntity.getActualWeight() + ceilingValue;
                    if (rto.getRateParameterId().equalsIgnoreCase("4")) {
                        if (from <= chargeWeight && to >= chargeWeight) {
                            existingConsignmentEntity.setRtoCharge(rto.getRate());
                        }
                    }
                    if (rto.getRateParameterId().equalsIgnoreCase("2")) {
                        if (existingConsignmentEntity.getRtoCharge() == 0) {
                            existingConsignmentEntity.setRtoCharge(rtoPriceListList.get(0).getRate());
                        }
                    }
                });

            }
            // Total_LMD_Finance
            double totalLmd =
                    (existingConsignmentEntity.getFrightCharge() != null ? existingConsignmentEntity.getFrightCharge() : 0.0) +
                            (existingConsignmentEntity.getCodCharge() != null ? existingConsignmentEntity.getCodCharge() : 0.0) +
                            (existingConsignmentEntity.getFulfilmentCharge() != null ? existingConsignmentEntity.getFulfilmentCharge() : 0.0) +
                            (existingConsignmentEntity.getRtoCharge() != null ? existingConsignmentEntity.getRtoCharge() : 0.0) +
                            (existingConsignmentEntity.getAsrCharge() != null ? existingConsignmentEntity.getAsrCharge() : 0.0) +
                            (existingConsignmentEntity.getMovementCharge() != null ? existingConsignmentEntity.getMovementCharge() : 0.0) +
                            (existingConsignmentEntity.getTruckCharge() != null ? existingConsignmentEntity.getTruckCharge() : 0.0);
            existingConsignmentEntity.setTotalLmdCharges(totalLmd);

            // Save updated consignment entity
            ConsignmentEntity saveConsignment = consignmentEntityRepository.save(existingConsignmentEntity);
            // Insert_Consignment_Status
            saveConsignment.getPieceDetails().stream().forEach(piece -> {
                if (saveConsignment.getHawbTypeId().equalsIgnoreCase("20")) {
                    Long agingCount = replicaConsignmentEntityRepository.agingCount(saveConsignment.getPartnerId());
                    consignmentStatusService.insertConsignmentStatusRecord(saveConsignment.getLanguageId(), saveConsignment.getLanguageDescription(), saveConsignment.getCompanyId(),
                            saveConsignment.getCompanyName(), piece.getPieceId(), saveConsignment.getMasterAirwayBill(), saveConsignment.getHouseAirwayBill(), saveConsignment.getHawbType(), saveConsignment.getHawbTypeId(),
                            saveConsignment.getHawbTypeDescription(), loginUserID, saveConsignment.getPartnerHouseAirwayBill(), saveConsignment.getPartnerMasterAirwayBill(), saveConsignment.getHubCode(),
                            saveConsignment.getHubName(), agingCount);

                    inventoryTableService.InventoryTableInsert(saveConsignment, loginUserID, agingCount);
                } else {
                    if (saveConsignment.getHawbTypeId().equals("16")) {
                        consignmentEntityRepository.updateDeliveryConsignment(saveConsignment.getHawbTypeDescription(), saveConsignment.getHouseAirwayBill());
                        String status = replicaConsignmentEntityRepository.getStatusEventDescription("15");
                        consignmentStatusService.insertConsignmentStatusRecord(saveConsignment.getLanguageId(), saveConsignment.getLanguageDescription(),
                                saveConsignment.getCompanyId(), saveConsignment.getCompanyName(), piece.getPieceId(),
                                saveConsignment.getMasterAirwayBill(), saveConsignment.getHouseAirwayBill(),
                                saveConsignment.getHawbType(), "15",
                                status, saveConsignment.getHawbTimeStamp(),
                                saveConsignment.getHawbType(), "15", status, null,
                                loginUserID, saveConsignment.getPartnerHouseAirwayBill(),
                                saveConsignment.getPartnerMasterAirwayBill(), saveConsignment.getConsignmentBagId(), saveConsignment.getHubCode(),
                                saveConsignment.getHubName());
                    }
                    consignmentStatusService.insertConsignmentStatusRecord(saveConsignment.getLanguageId(), saveConsignment.getLanguageDescription(),
                            saveConsignment.getCompanyId(), saveConsignment.getCompanyName(), piece.getPieceId(),
                            saveConsignment.getMasterAirwayBill(), saveConsignment.getHouseAirwayBill(),
                            saveConsignment.getHawbType(), saveConsignment.getHawbTypeId(),
                            saveConsignment.getHawbTypeDescription(), saveConsignment.getHawbTimeStamp(),
                            saveConsignment.getHawbType(), saveConsignment.getHawbTypeId(), saveConsignment.getHawbTypeDescription(), null,
                            loginUserID, saveConsignment.getPartnerHouseAirwayBill(),
                            saveConsignment.getPartnerMasterAirwayBill(), saveConsignment.getConsignmentBagId(), saveConsignment.getHubCode(),
                            saveConsignment.getHubName());
                }
            });
            updatedConsignmentList.add(saveConsignment);
        }
        return updatedConsignmentList;
    }


    //MultipleConsignment Delete

    /**
     * @param consignmentDeletes
     * @param loginUserID
     */
    public void deleteConsignmentEntity(List<ConsignmentDelete> consignmentDeletes, String loginUserID) {
        consignmentDeletes.stream().forEach(con -> {
            String pieceId = con.getPieceId();
            String pieceItemId = con.getPieceItemId();
            String companyId = con.getCompanyId();
            String languageId = con.getLanguageId();
            String partnerId = con.getPartnerId();
            String imageRefId = con.getImageRefId();
            String houseAirwayBill = con.getHouseAirwayBill();
            String masterAirwayBill = con.getMasterAirwayBill();

            if (pieceId == null && pieceItemId == null && imageRefId == null) {
                ConsignmentEntity dbConsignmentEntity = consignmentEntityRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
                        companyId, languageId, partnerId, masterAirwayBill, houseAirwayBill, 0L);

                if (dbConsignmentEntity == null) {
                    throw new BadRequestException(" Given values doesn't exits CompanyId - " + companyId + " LanguageId " + languageId + " PartnerId " + partnerId +
                            " MasterAirwayBill " + masterAirwayBill + " HouseAirwayBill " + houseAirwayBill);
                } else {
                    dbConsignmentEntity.setDeletionIndicator(1L);
                    dbConsignmentEntity.setUpdatedBy(loginUserID);
                    dbConsignmentEntity.setUpdatedOn(new Date());
                    //MultipleImageDelete
                    imageReferenceService.deleteImageReference(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, loginUserID);
                    //MultiplePiece
                    pieceDetailsService.deletePieceDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, loginUserID);
                    consignmentEntityRepository.save(dbConsignmentEntity);
                }
            }
            if (pieceId != null && pieceItemId == null) {
                pieceDetailsService.deletePieceDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, loginUserID);
            }
            if (pieceItemId != null && pieceId != null) {
                itemDetailsService.deleteItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, loginUserID);
            }
            if (imageRefId != null) {
                imageReferenceService.deleteImageReference(imageRefId, loginUserID);
            }
        });
    }


    /**
     * Find Consignment
     *
     * @param findConsignment
     * @return
     * @throws Exception
     */
//    public List<ReplicaConsignmentEntity> findConsignmentEntityAsync(FindConsignment findConsignment) throws ParseException {
//        List<CompletableFuture<List<ReplicaConsignmentEntity>>> futures = new ArrayList<>();
//        int limit = 500;
//        int offset = 0;
//
//        if (findConsignment.getFromDate() != null && findConsignment.getToDate() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearchV2(findConsignment.getFromDate(), findConsignment.getToDate());
//            findConsignment.setFromDate(dates[0]);
//            findConsignment.setToDate(dates[1]);
//            log.info("FROM DATE --> {}", findConsignment.getFromDate());
//            log.info("TO DATE --> {}", findConsignment.getToDate());
//        }
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        while (true) {
//            int finalOffset = offset;
//
//            CompletableFuture<List<ReplicaConsignmentEntity>> future = CompletableFuture.supplyAsync(() -> {
//                return replicaConsignmentEntityRepository.fetchConsignmentsWithQry(
//                        findConsignment.getLanguageId(),
//                        findConsignment.getCompanyId(),
//                        findConsignment.getPartnerId(),
//                        findConsignment.getMasterAirwayBill(),
//                        findConsignment.getHouseAirwayBill(),
//                        findConsignment.getFromDate(),
//                        findConsignment.getToDate(),
//                        limit, finalOffset);
//            }, executor);
//
//            futures.add(future);
//            offset += limit;
//
//            if (future.join().size() < limit) {
//                break;
//            }
//        }
//        List<ReplicaConsignmentEntity> result = futures.stream()
//                .map(CompletableFuture::join)
//                .flatMap(List::stream)
//                .collect(Collectors.toList());
//
//        executor.shutdown();
//        return result;
//    }
    public List<ReplicaConsignmentEntity> findConsignmentEntityAsync(FindConsignment findConsignment) throws ParseException {
        List<CompletableFuture<List<ReplicaConsignmentEntity>>> futures = new ArrayList<>();
        int limit = 1000;
        int offset = 0;

        long startTime = System.currentTimeMillis();
        // Adjust date range if needed
        if (findConsignment.getFromDate() != null && findConsignment.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearchV2(findConsignment.getFromDate(), findConsignment.getToDate());
            findConsignment.setFromDate(dates[0]);
            findConsignment.setToDate(dates[1]);
            log.info("FROM DATE --> {}", findConsignment.getFromDate());
            log.info("TO DATE --> {}", findConsignment.getToDate());
        }
        ExecutorService executor = Executors.newFixedThreadPool(10);
        while (true) {
            int finalOffset = offset;

            CompletableFuture<List<ReplicaConsignmentEntity>> future = CompletableFuture.supplyAsync(() -> {
                return replicaConsignmentEntityRepository.fetchConsignmentsWithQry(
                        findConsignment.getLanguageId(),
                        findConsignment.getCompanyId(),
                        findConsignment.getPartnerId(),
                        findConsignment.getMasterAirwayBill(),
                        findConsignment.getHouseAirwayBill(),
                        findConsignment.getFromDate(),
                        findConsignment.getToDate(),
                        limit, finalOffset);
            }, executor);

            futures.add(future);
            offset += limit;

            if (future.join().size() < limit) {
                break;
            }
        }
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allFutures.join();
        List<ReplicaConsignmentEntity> result = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        executor.shutdown();
        long endTime = System.currentTimeMillis();
        log.info("Total time taken for fetching consignments: {} ms", (endTime - startTime));
        return result;
    }

    /**
     * @param addConsignment
     * @return
     */
    public AddConsignment createConsignmentNullValidation(AddConsignment addConsignment) {
        log.info("Consignment null validaiton input: " + addConsignment);
        List<String> nullValidationCheck = new ArrayList<>();
        if (addConsignment != null) {
            if (addConsignment.getOriginDetails() != null) {
                if (addConsignment.getOriginDetails().getName() != null && addConsignment.getOriginDetails().getPhone() != null &&
                        addConsignment.getOriginDetails().getAddressLine1() != null && addConsignment.getOriginDetails().getAddressLine2() != null &&
                        addConsignment.getOriginDetails().getCity() != null && addConsignment.getOriginDetails().getCountry() != null) {
                    nullValidationCheck.add("true");
                } else {
                    nullValidationCheck.add("false");
                }
            }
            if (addConsignment.getDestinationDetails() != null) {
                if (addConsignment.getDestinationDetails().getName() != null && addConsignment.getDestinationDetails().getPhone() != null &&
                        addConsignment.getDestinationDetails().getAddressLine1() != null && addConsignment.getDestinationDetails().getAddressLine2() != null &&
                        addConsignment.getDestinationDetails().getCity() != null && addConsignment.getDestinationDetails().getCountry() != null) {
                    nullValidationCheck.add("true");
                } else {
                    nullValidationCheck.add("false");
                }
            }
            if (addConsignment.getPieceDetails() != null && !addConsignment.getPieceDetails().isEmpty()) {
                for (AddPieceDetails pieceDetails : addConsignment.getPieceDetails()) {
                    if (pieceDetails.getPartnerHouseAirwayBill() != null && pieceDetails.getDescription() != null &&
                            pieceDetails.getDeclaredValue() != null && pieceDetails.getWeight() != null && pieceDetails.getHsCode() != null) {
                        nullValidationCheck.add("true");
                    } else {
                        nullValidationCheck.add("false");
                    }
                }
            }
            int nullValidationCheckSize = nullValidationCheck.size();
            long nullValidation = nullValidationCheck.stream().filter(n -> n.equalsIgnoreCase("true")).count();
            boolean pass = nullValidationCheckSize == nullValidation;
            if (pass) {
                addConsignment.setPreAlertValidationIndicator(0L);
            }
            if (!pass) {
                addConsignment.setPreAlertValidationIndicator(1L);
            }
        }
        log.info("Consignment null validaiton output: " + addConsignment);
        return addConsignment;
    }


    /**
     * @param updateConsignment
     * @return
     */
    public UpdateConsignment updateConsignmentNullValidation(UpdateConsignment updateConsignment) {
        log.info("Consignment null validaiton input: " + updateConsignment);
        List<String> nullValidationCheck = new ArrayList<>();
        if (updateConsignment != null) {
            if (updateConsignment.getOriginDetails() != null) {
                if (updateConsignment.getOriginDetails().getName() != null && updateConsignment.getOriginDetails().getPhone() != null &&
                        updateConsignment.getOriginDetails().getAddressLine1() != null && updateConsignment.getOriginDetails().getAddressLine2() != null &&
                        updateConsignment.getOriginDetails().getCity() != null && updateConsignment.getOriginDetails().getCountry() != null) {
                    nullValidationCheck.add("true");
                } else {
                    nullValidationCheck.add("false");
                }
            }
            if (updateConsignment.getDestinationDetails() != null) {
                if (updateConsignment.getDestinationDetails().getName() != null && updateConsignment.getDestinationDetails().getPhone() != null &&
                        updateConsignment.getDestinationDetails().getAddressLine1() != null && updateConsignment.getDestinationDetails().getAddressLine2() != null &&
                        updateConsignment.getDestinationDetails().getCity() != null && updateConsignment.getDestinationDetails().getCountry() != null) {
                    nullValidationCheck.add("true");
                } else {
                    nullValidationCheck.add("false");
                }
            }
            if (updateConsignment.getPieceDetails() != null && !updateConsignment.getPieceDetails().isEmpty()) {
                for (UpdatePieceDetails pieceDetails : updateConsignment.getPieceDetails()) {
                    if (pieceDetails.getPartnerHouseAirwayBill() != null && pieceDetails.getDescription() != null &&
                            pieceDetails.getDeclaredValue() != null && pieceDetails.getWeight() != null && pieceDetails.getHsCode() != null) {
                        nullValidationCheck.add("true");
                    } else {
                        nullValidationCheck.add("false");
                    }
                }
            }
            int nullValidationCheckSize = nullValidationCheck.size();
            long nullValidation = nullValidationCheck.stream().filter(n -> n.equalsIgnoreCase("true")).count();
            boolean pass = nullValidationCheckSize == nullValidation;
            if (pass) {
                updateConsignment.setPreAlertValidationIndicator(0L);
            }
            if (!pass) {
                updateConsignment.setPreAlertValidationIndicator(1L);
            }
        }
        log.info("Consignment null validaiton output: " + updateConsignment);
        return updateConsignment;
    }

    /**
     * @param findPreAlertManifest
     * @return
     */
    public List<PreAlertManifestConsignment> findPreAlertManifest(FindPreAlertManifest findPreAlertManifest) {
        if (findPreAlertManifest.getManifestIndicator() == null) {
            findPreAlertManifest.setManifestIndicator(Collections.singletonList(0L));
        }
        if (findPreAlertManifest.getConsoleIndicator() == null) {
            findPreAlertManifest.setConsoleIndicator(Collections.singletonList(0L));
        }
        log.info("findPreAlertManifest: " + findPreAlertManifest);
        PreAlertManifestConsignmentSpecification specification = new PreAlertManifestConsignmentSpecification(findPreAlertManifest);
        List<ReplicaConsignmentEntity> results = replicaConsignmentEntityRepository.findAll(specification);
        List<PreAlertManifestConsignment> consignmentList = new ArrayList<>();
        if (results != null && !results.isEmpty()) {
            results.forEach(n -> {
                PreAlertManifestConsignment dbReplicaAddConsignment = new PreAlertManifestConsignment();
                BeanUtils.copyProperties(n, dbReplicaAddConsignment, CommonUtils.getNullPropertyNames(n));
                if (n.getOriginDetails() != null) {
                    BeanUtils.copyProperties(n.getOriginDetails(), dbReplicaAddConsignment.getOriginDetails());
                }
                if (n.getDestinationDetails() != null) {
                    BeanUtils.copyProperties(n.getDestinationDetails(), dbReplicaAddConsignment.getDestinationDetails());
                }
                if (n.getReturnDetails() != null) {
                    BeanUtils.copyProperties(n.getReturnDetails(), dbReplicaAddConsignment.getReturnDetails());
                }
                List<ReplicaPieceDetails> replicaAddPieceDetailsList = pieceDetailsService.getReplicaPieceDetailsForPreAlertManifest(n.getLanguageId(), n.getCompanyId(), n.getConsignmentId());
                List<PreAlertManifestPieceDetails> pieceDetailsList = new ArrayList<>();
                if (replicaAddPieceDetailsList != null && !replicaAddPieceDetailsList.isEmpty()) {
                    replicaAddPieceDetailsList.forEach(a -> {
                        PreAlertManifestPieceDetails replicaAddPieceDetails = new PreAlertManifestPieceDetails();
                        BeanUtils.copyProperties(a, replicaAddPieceDetails, CommonUtils.getNullPropertyNames(a));
                        List<ReplicaItemDetails> replicaItemDetailsList = itemDetailsService.replicaGetItemDetails(a.getLanguageId(), a.getCompanyId(), a.getPieceId());
                        if (replicaItemDetailsList != null && !replicaItemDetailsList.isEmpty()) {
                            replicaAddPieceDetails.setItemDetails(replicaItemDetailsList);
                        }
                        pieceDetailsList.add(replicaAddPieceDetails);
                    });
                    dbReplicaAddConsignment.setPieceDetails(pieceDetailsList);
                }
                consignmentList.add(dbReplicaAddConsignment);
            });
        }
        return consignmentList;
    }


    /**
     * FindConsignmentInvoice
     *
     * @param findConsignmentInvoice
     * @return
     */
    public List<ConsignmentInvoice> findConsignmentInvoice(FindConsignmentInvoice findConsignmentInvoice) {
        List<ConsignmentInvoice> results = replicaConsignmentEntityRepository.getConsignmentInvoice(findConsignmentInvoice.getHouseAirwayBill(), findConsignmentInvoice.getPartnerHouseAirwayBill(), findConsignmentInvoice.getPartnerMasterAirwayBill(), findConsignmentInvoice.getCompanyId());
        log.info("found Consignments -->" + results);
        return results;
    }

    public List<InvoiceForm> ConsignmentInvoicePdfGenerate(FindConsignmentInvoice findConsignmentInvoice) {
        List<ConsignmentInvoice> results = replicaConsignmentEntityRepository.getConsignmentInvoiceHeader(findConsignmentInvoice.getHouseAirwayBill(),
                findConsignmentInvoice.getPartnerHouseAirwayBill(), findConsignmentInvoice.getPartnerMasterAirwayBill(), findConsignmentInvoice.getCompanyId());
        List<InvoiceForm> invoiceFormList = new ArrayList<>();
        if (results != null && !results.isEmpty()) {
            results.forEach(n -> {
                InvoiceForm dbInvoiceHeader = new InvoiceForm();
                BeanUtils.copyProperties(n, dbInvoiceHeader, CommonUtils.getNullPropertyNames(n));
                List<ConsignmentInvoice> lineResults = replicaConsignmentEntityRepository.getConsignmentInvoiceLine(n.getMasterAirwayBill(), n.getHouseAirwayBill());
                if (lineResults != null && !lineResults.isEmpty()) {
                    dbInvoiceHeader.setInvoiceFormLines(lineResults);
                }
                invoiceFormList.add(dbInvoiceHeader);
            });
        }
        log.info("found Consignments -->" + results.size());
        return invoiceFormList;
    }


    //===================================================================Null validation columns=================================================================================//

    /**
     * null validation colum find
     *
     * @param findConsignment
     * @return
     */
    public List<IConsignment> findIConsignment(FindIConsignment findConsignment) {

        if (findConsignment.getConsignmentId() != null && findConsignment.getConsignmentId().isEmpty()) {
            findConsignment.setConsignmentId(null);
        }
        if (findConsignment.getLanguageId() != null && findConsignment.getLanguageId().isEmpty()) {
            findConsignment.setLanguageId(null);
        }
        if (findConsignment.getCompanyId() != null && findConsignment.getCompanyId().isEmpty()) {
            findConsignment.setCompanyId(null);
        }
        if (findConsignment.getPartnerId() != null && findConsignment.getPartnerId().isEmpty()) {
            findConsignment.setPartnerId(null);
        }
        if (findConsignment.getMasterAirwayBill() != null && findConsignment.getMasterAirwayBill().isEmpty()) {
            findConsignment.setMasterAirwayBill(null);
        }
        if (findConsignment.getHouseAirwayBill() != null && findConsignment.getHouseAirwayBill().isEmpty()) {
            findConsignment.setHouseAirwayBill(null);
        }
        if (findConsignment.getStatusId() != null && findConsignment.getStatusId().isEmpty()) {
            findConsignment.setStatusId(null);
        }
        if (findConsignment.getShipperId() != null && findConsignment.getShipperId().isEmpty()) {
            findConsignment.setShipperId(null);
        }
        if (findConsignment.getPartnerHouseAirwayBill() != null && findConsignment.getPartnerHouseAirwayBill().isEmpty()) {
            findConsignment.setPartnerHouseAirwayBill(null);
        }
        if (findConsignment.getPartnerMasterAirwayBill() != null && findConsignment.getPartnerMasterAirwayBill().isEmpty()) {
            findConsignment.setPartnerMasterAirwayBill(null);
        }
        log.info("Search Input - consignment(Null validation): " + findConsignment);
        List<IConsignment> consignmentList = new ArrayList<>();
        List<ConsignmentImpl> iconsignmentList = replicaConsignmentEntityRepository.getConsignmentImpl(
                findConsignment.getConsignmentId(),
                findConsignment.getLanguageId(),
                findConsignment.getCompanyId(),
                findConsignment.getPartnerId(),
                findConsignment.getMasterAirwayBill(),
                findConsignment.getHouseAirwayBill(),
                findConsignment.getStatusId(),
                findConsignment.getShipperId(),
                findConsignment.getPartnerHouseAirwayBill(),
                findConsignment.getPartnerMasterAirwayBill());
        log.info("Consignment List: " + iconsignmentList.size());
        if (iconsignmentList != null && !iconsignmentList.isEmpty()) {
            iconsignmentList.forEach(n -> {
                IConsignment dbConsignment = new IConsignment();
                BeanUtils.copyProperties(n, dbConsignment, CommonUtils.getNullPropertyNames(n));
                OriginDetailsImpl originDetails = replicaOriginDetailsRepository.getOriginDetailsImpl(n.getConsignmentId());
                DestinationDetailsImpl destinationDetails = replicaDestinationDetailsRepository.getDestinationDetailsImpl(n.getConsignmentId());
                List<PieceDetailsImpl> pieceDetails = replicaPieceDetailsRepository.getPieceDetailsImpl(n.getConsignmentId());
                List<ReplicaPieceDetails> replicaPieceDetailsList = new ArrayList<>();
                if (originDetails != null) {
                    dbConsignment.setOriginDetails(originDetails);
                }
                if (destinationDetails != null) {
                    dbConsignment.setDestinationDetails(destinationDetails);
                }
                if (pieceDetails != null && !pieceDetails.isEmpty()) {
                    pieceDetails.stream().forEach(a -> {
                        ReplicaPieceDetails replicaPieceDetails = new ReplicaPieceDetails();
                        BeanUtils.copyProperties(a, replicaPieceDetails, CommonUtils.getNullPropertyNames(a));
                        replicaPieceDetailsList.add(replicaPieceDetails);
                    });
                    dbConsignment.setPieceDetails(replicaPieceDetailsList);
                }
                consignmentList.add(dbConsignment);
            });
        }
        return consignmentList;
    }


    /**
     * Find Consignment MobileApp
     *
     * @param findConsignmentList
     * @return
     */

    public List<ReplicaConsignmentEntity> findConsignmentMobileApp(List<FindConsignmentMobileApp> findConsignmentList) {
        List<ReplicaConsignmentEntity> consignment = new ArrayList<>();
        if (findConsignmentList != null && !findConsignmentList.isEmpty()) {
            for (FindConsignmentMobileApp findConsignment : findConsignmentList) {
                if (findConsignment.getShippingLabelNo() == null) {
                    throw new BadRequestException("ShippingLabelNo cannot be null");
                }
                log.info("given Params to fetch Consignments for Mobile App --> {}", findConsignment);
                List<ReplicaConsignmentEntity> fetchedConsignments = null;
                if (findConsignment.getHawbTypeId() != null && !findConsignment.getHawbTypeId().isEmpty()) {
                    fetchedConsignments = replicaConsignmentEntityRepository.findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHawbTypeIdAndDeletionIndicator(
                            findConsignment.getLanguageId(), findConsignment.getCompanyId(), findConsignment.getShippingLabelNo(), findConsignment.getHawbTypeId(), 0L);
                    if (fetchedConsignments.isEmpty()) {
                        fetchedConsignments = replicaConsignmentEntityRepository.findByLanguageIdAndCompanyIdAndHouseAirwayBillAndHawbTypeIdAndDeletionIndicator(
                                findConsignment.getLanguageId(), findConsignment.getCompanyId(), findConsignment.getShippingLabelNo(), findConsignment.getHawbTypeId(), 0L);
                    }
                    if (fetchedConsignments.isEmpty()) {
                        String hawb = replicaPieceDetailsRepository.getHawbWithPieceId(findConsignment.getLanguageId(),
                                findConsignment.getCompanyId(), findConsignment.getShippingLabelNo());
                        if (hawb != null) {
                            fetchedConsignments = replicaConsignmentEntityRepository.findByLanguageIdAndCompanyIdAndHouseAirwayBillAndHawbTypeIdAndDeletionIndicator(
                                    findConsignment.getLanguageId(), findConsignment.getCompanyId(), hawb, findConsignment.getHawbTypeId(), 0L);
                        }
                    }
                } else {
                    fetchedConsignments = replicaConsignmentEntityRepository.findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndDeletionIndicator(
                            findConsignment.getLanguageId(), findConsignment.getCompanyId(), findConsignment.getShippingLabelNo(), 0L);
                    if (fetchedConsignments.isEmpty()) {
                        fetchedConsignments = replicaConsignmentEntityRepository.findByLanguageIdAndCompanyIdAndHouseAirwayBillAndDeletionIndicator(
                                findConsignment.getLanguageId(), findConsignment.getCompanyId(), findConsignment.getShippingLabelNo(), 0L);
                    }
                    if (fetchedConsignments.isEmpty()) {
                        String hawb = replicaPieceDetailsRepository.getHawbWithPieceId(findConsignment.getLanguageId(),
                                findConsignment.getCompanyId(), findConsignment.getShippingLabelNo());
                        if (hawb != null) {
                            fetchedConsignments = replicaConsignmentEntityRepository.findByLanguageIdAndCompanyIdAndHouseAirwayBillAndDeletionIndicator(
                                    findConsignment.getLanguageId(), findConsignment.getCompanyId(), hawb, 0L);
                        }
                    }
                }
                if (fetchedConsignments.isEmpty()) {
                    throw new BadRequestException("No Consignment Data found for given params : shippingLabelNo - " + findConsignment.getShippingLabelNo());
                }
                for (ReplicaConsignmentEntity consignmentEntity : fetchedConsignments) {
                    List<ReplicaPieceDetails> pieceDetailsList = consignmentEntity.getPieceDetails();
                    if (pieceDetailsList != null && !pieceDetailsList.isEmpty()) {
                        String pieceId = pieceDetailsList.get(0).getPieceId();
                        consignmentEntity.setPieceId(pieceId);
                    }
                    consignment.add(consignmentEntity);
                }
            }
        }
        return consignment;
    }


    /**
     * @param languageId
     * @param companyId
     * @return findInscanConsignments
     */
    public List<FindInscanConsignment> findInscanConsignments(String languageId, String companyId) {

        List<FindInscanConsignment> findInscanConsignments = new ArrayList<>();
        FindInscanConsignment findInscan = new FindInscanConsignment();
        List<ReplicaConsignmentEntity> dbCheck = replicaConsignmentEntityRepository.getInscanData(languageId, companyId);
        log.info("inscan data count --> {}", dbCheck.size());
        dbCheck.forEach(id -> {
            findInscan.setHouseAirwayBill(id.getHouseAirwayBill());
            findInscan.setPartnerName(id.getPartnerName());
            findInscan.setPieceId(id.getPieceId());
            findInscan.setNoOfPieceHawb(id.getNoOfPieceHawb());
            findInscanConsignments.add(findInscan);
        });

        return findInscanConsignments;
    }


    /**
     * @param findConsignmentScan
     * @param loginUserID
     * @return
     */
    public List<ConsignmentEntity> updateInscanStatus(List<FindConsignmentScan> findConsignmentScan, String loginUserID) {
        List<ConsignmentEntity> consignmentEntityList = new ArrayList<>();
        log.info("UpdateInscanStatus input --------->" + findConsignmentScan);
        ConsignmentEntity consignment = null;
        for (FindConsignmentScan findScan : findConsignmentScan) {
            Long consignmentEntity = consignmentEntityRepository.getHouseAirWayBill(findScan.getShippingLabelNo(), findScan.getCompanyId(), findScan.getLanguageId());
            Long piece = pieceDetailsRepository.getPiece(findScan.getShippingLabelNo(), findScan.getCompanyId(), findScan.getLanguageId());

            if (consignmentEntity != null) {
                log.info("Consignment Value" + consignmentEntity);
                consignment = consignmentEntityRepository.findByConsignmentIdAndDeletionIndicator(consignmentEntity, 0L);
                consignment.setUpdatedBy(loginUserID);
                consignment.setUpdatedOn(new Date());
                if (findScan.getStatusId() != null) {
                    String status = replicaConsignmentEntityRepository.getStatusEventDescription(findScan.getStatusId());
                    consignment.setHawbTypeId(findScan.getStatusId());
                    consignment.setHawbTypeDescription(status);
                }
            } else if (piece != null) {
                log.info("Piece Value" + piece);
                consignment = consignmentEntityRepository.findByConsignmentIdAndDeletionIndicator(piece, 0L);
                if (findScan.getStatusId() != null) {
                    String status = replicaConsignmentEntityRepository.getStatusEventDescription(findScan.getStatusId());
                    consignment.setHawbTypeId(findScan.getStatusId());
                    consignment.setHawbTypeDescription(status);
                }
                consignment.setUpdatedBy(loginUserID);
                consignment.setUpdatedOn(new Date());
//                consignmentEntityRepository.save(consignment);
            }
            if (findScan.getStorageDescription() != null) {
                consignment.setStorageDescription(findScan.getStorageDescription());
            }
            if (findScan.getHubCode() != null) {
                String hubCode = consoleRepository.getHubName(consignment.getCompanyId(), consignment.getLanguageId(), findScan.getHubCode());
                if (hubCode != null) {
                    consignment.setHubName(hubCode);
                }
            } else {
                String assignedHub = replicaConsignmentEntityRepository.assignedName(consignment.getLanguageId(), consignment.getCompanyId(), loginUserID);
                consignment.setHubCode(assignedHub != null ? assignedHub : "");
            }
            //Save Consignment
            consignmentEntityRepository.save(consignment);
            if (consignment != null && consignment.getPartnerId() != null) {
                // Execute the custom update query to set `STORAGE_LOCATION` based on the STORAGE_TYPE_ID
                consignmentEntityRepository.updateRefFieldWithStorageType(consignment.getConsignmentId());

                //Insert_Consignment_Status
                ConsignmentEntity finalConsignment = consignment;
                consignment.getPieceDetails().forEach(con -> {
                    if (finalConsignment.getHawbTypeId().equalsIgnoreCase("20")) {
                        Long agingCount = replicaConsignmentEntityRepository.agingCount(con.getPartnerId());
                        consignmentStatusService.insertConsignmentStatusRecord(con.getLanguageId(), con.getLanguageDescription(),
                                con.getCompanyId(), con.getCompanyName(), con.getPieceId(), con.getMasterAirwayBill(),
                                con.getHouseAirwayBill(), finalConsignment.getHawbType(), finalConsignment.getHawbTypeId(), finalConsignment.getHawbTypeDescription(),
                                loginUserID, con.getPartnerHouseAirwayBill(), con.getPartnerMasterAirwayBill(), finalConsignment.getHubCode(), finalConsignment.getHubName(), agingCount);
                        inventoryTableService.InventoryTableInsert(finalConsignment, loginUserID, agingCount);
                    } else {
                        consignmentStatusService.insertConsignmentStatusRecord(con.getLanguageId(), con.getLanguageDescription(),
                                con.getCompanyId(), con.getCompanyName(), con.getPieceId(), con.getMasterAirwayBill(),
                                con.getHouseAirwayBill(), finalConsignment.getHawbType(), finalConsignment.getHawbTypeId(), finalConsignment.getHawbTypeDescription(),
                                loginUserID, con.getPartnerHouseAirwayBill(), con.getPartnerMasterAirwayBill(), finalConsignment.getHubCode(), finalConsignment.getHubName(), null);
                    }
                });
            }
            consignmentEntityList.add(consignment);
//            if (consignment != null) {
//                ConsignmentEntity consignmentEntity1 = consignmentEntityRepository.getData(consignment.getConsignmentId());
//                consignmentEntityList.add(consignmentEntity1);
//            }
        }
        return consignmentEntityList;

    }

    //public List<ConsignmentEntity> updateInscanStatus(List<FindConsignmentScan> findConsignmentScan, String loginUserID) {
    //        List<ConsignmentEntity> consignmentEntityList = new ArrayList<>();
    //        ConsignmentEntity consignment = null;
    //        for (FindConsignmentScan findScan : findConsignmentScan) {
    //            Long consignmentEntity = consignmentEntityRepository.getHouseAirWayBill(findScan.getShippingLabelNo(), findScan.getCompanyId(), findScan.getLanguageId());
    //            Long piece = pieceDetailsRepository.getPiece(findScan.getShippingLabelNo(), findScan.getCompanyId(), findScan.getLanguageId());
    //
    //            if (consignmentEntity != null) {
    //                consignment = consignmentEntityRepository.getData(consignmentEntity);
    //                consignment.setUpdatedBy(loginUserID);
    //                consignment.setUpdatedOn(new Date());
    //                consignmentEntityRepository.save(consignment);
    //            } else if (piece != null) {
    //                consignment = consignmentEntityRepository.getData(piece);
    //                consignment.setUpdatedBy(loginUserID);
    //                consignment.setUpdatedOn(new Date());
    //                consignmentEntityRepository.save(consignment);
    //            }
    //            if (findScan.getStatusId() != null) {
    //                String status = replicaConsignmentEntityRepository.getStatusEventDescription(findScan.getStatusId());
    //                consignment.setHawbTypeId(findScan.getStatusId());
    //                consignment.setHawbTypeDescription(status);
    //            }
    //            if (findScan.getHubCode() != null) {
    //                String hubCode = consoleRepository.getHubName(consignment.getCompanyId(), consignment.getLanguageId(), findScan.getHubCode());
    //                if (hubCode != null) {
    //                    consignment.setHubName(hubCode);
    //                }
    //            }
    //            if (consignment != null && consignment.getPartnerId() != null) {
    //                // Execute the custom update query to set `STORAGE_LOCATION` based on the STORAGE_TYPE_ID
    //                consignmentEntityRepository.updateRefFieldWithStorageType(consignment.getConsignmentId());
    //            }
    //
    //            if (consignment != null) {
    //                ConsignmentEntity consignmentEntity1 = consignmentEntityRepository.getData(consignment.getConsignmentId());
    //                consignmentEntityList.add(consignmentEntity1);
    //            }
    //        }
    //        return consignmentEntityList;
    //
    //    }


    /**
     * Update InvoiceUrl
     *
     * @param updateInvoiceList
     */
    public List<UpdateInvoice> updateInvoiceUrl(List<UpdateInvoice> updateInvoiceList, String loginUserID) {

        UpdateInvoice newUpdateInvoice = new UpdateInvoice();
        List<UpdateInvoice> updateInvoiceList1 = new ArrayList<>();

        for (UpdateInvoice updateInvoice : updateInvoiceList) {
            consignmentEntityRepository.updateInvoiceUrl(updateInvoice.getHouseAirwayBill(), updateInvoice.getInvoiceUrl());
        }
        newUpdateInvoice.setUpdatedBy(loginUserID);
        newUpdateInvoice.setUpdatedOn(new Date());
        updateInvoiceList1.add(newUpdateInvoice);

        return updateInvoiceList1;
    }


    /**
     * @param findConsignments
     * @return
     */
    public List<FindConsignmentMobileResponse> findConsignmentReceivingMobileApp(List<FindConsignmentMobileApp> findConsignments) {
        List<FindConsignmentMobileResponse> findConsignmentMobileResponseList = new ArrayList<>();
        log.info("mobile app req --> {}", findConsignments.get(0));
        FindConsignmentMobileResponse findReceiving = new FindConsignmentMobileResponse();
        for (FindConsignmentMobileApp receiving : findConsignments) {
            log.info("mobile app req --> {}", receiving);
            List<ReplicaConsignmentEntity> dbConsignment = replicaConsignmentEntityRepository.getReceivingConsignment(receiving.getCompanyId(), receiving.getLanguageId(), receiving.getHawbTypeId(), receiving.getShippingLabelNo());
            log.info("receiving data count --> {}", dbConsignment.size());
            dbConsignment.forEach(id -> {
                ReplicaDestinationDetails replicaDestinationDetails = replicaDestinationDetailsRepository.findDestId(id.getConsignmentId());
                ReplicaOriginDetails replicaOriginDetails = replicaOriginDetailsRepository.findOriginId(id.getConsignmentId());
                findReceiving.setHouseAirwayBill(id.getHouseAirwayBill());
                findReceiving.setPartnerName(id.getPartnerName());
                findReceiving.setPieceId(id.getPieceId());
                findReceiving.setNoOfPieceHawb(id.getNoOfPieceHawb());
                findReceiving.setOriginAddress(replicaOriginDetails.getAddressLine1());
                findReceiving.setDestinationAddress(replicaDestinationDetails.getAddressLine1());
                findConsignmentMobileResponseList.add(findReceiving);
            });
        }
        return findConsignmentMobileResponseList;
    }

    public ConsignmentEntity updateReceivingStatus(String scanId, String statusId, String storageTypeId, String loginUserID) {

        ConsignmentEntity getHawb = consignmentEntityRepository.getHAWBValue(scanId);
        ConsignmentEntity getPiece = consignmentEntityRepository.getPiece(scanId);

        ConsignmentEntity con = null;
        if (getHawb != null) {
            log.info("HAWB --{}", getHawb.getStatusId());
            getHawb.setHawbTypeId(statusId);
            String status = replicaConsignmentEntityRepository.getStatusEventDescription(statusId);
            getHawb.setHawbTypeDescription(status);
            getHawb.setUpdatedBy(loginUserID);
            getHawb.setUpdatedOn(new Date());
            con = consignmentEntityRepository.save(getHawb);
        } else if (getPiece != null) {
            getPiece.setHawbTypeId(statusId);
            String status = replicaConsignmentEntityRepository.getStatusEventDescription(statusId);
            getHawb.setHawbTypeDescription(status);
            getPiece.setUpdatedBy(loginUserID);
            getPiece.setUpdatedOn(new Date());
            con = consignmentEntityRepository.save(getPiece);
        } else {
            throw new BadRequestException("ScanId Must be Match the ConsignmentId or PieceId");
        }

        // Execute the custom update query to set `STORAGE_LOCATION` based on the STORAGE_TYPE_ID
//        consignmentEntityRepository.updateRefFieldWithStorageType();
        return con;
    }


    /**
     * @param scanId
     * @param statusId
     * @param loginUserID
     * @return
     */

    public ConsignmentEntity updateOutScanStatus(String scanId, String statusId, String loginUserID) {
        ConsignmentEntity getHawb = consignmentEntityRepository.getHAWBValue(scanId);
        ConsignmentEntity getPiece = consignmentEntityRepository.getPiece(scanId);
        ConsignmentEntity con = null;
        if (getHawb != null && statusId.equals("15")) {
            log.info("HAWB --{}", getHawb.getStatusId());
            consignmentStatusService.insertConsignmentStatusRecord(getHawb.getLanguageId(), getHawb.getLanguageDescription(),
                    getHawb.getCompanyId(), getHawb.getCompanyName(), getHawb.getPieceId(), getHawb.getMasterAirwayBill(),
                    getHawb.getHouseAirwayBill(), getHawb.getHawbType(), getHawb.getHawbTypeId(), getHawb.getHawbTypeDescription(),
                    getHawb.getHawbTimeStamp(), getHawb.getHawbType(), getHawb.getHawbTypeId(), getHawb.getHawbTypeDescription(),
                    getHawb.getHawbTimeStamp(), loginUserID, getHawb.getPartnerHouseAirwayBill(), getHawb.getPartnerMasterAirwayBill(),
                    getHawb.getConsignmentBagId(), getHawb.getHubCode(), getHawb.getHubName());
            getHawb.setHawbTypeId("16");
            String status = replicaConsignmentEntityRepository.getStatusEventDescription("16");
            getHawb.setHawbTypeDescription(status);
            getHawb.setUpdatedBy(loginUserID);
            getHawb.setUpdatedOn(new Date());
            con = consignmentEntityRepository.save(getHawb);
            consignmentStatusService.insertConsignmentStatusRecord(con.getLanguageId(), con.getLanguageDescription(),
                    con.getCompanyId(), con.getCompanyName(), con.getPieceId(), con.getMasterAirwayBill(),
                    con.getHouseAirwayBill(), con.getHawbType(), con.getHawbTypeId(), con.getHawbTypeDescription(),
                    con.getHawbTimeStamp(), con.getHawbType(), con.getHawbTypeId(), con.getHawbTypeDescription(),
                    con.getHawbTimeStamp(), loginUserID, con.getPartnerHouseAirwayBill(), con.getPartnerMasterAirwayBill(),
                    con.getConsignmentBagId(), con.getHubCode(), con.getHubName());
        } else if (getPiece != null && statusId.equals("15")) {
            log.info("PIECE ID --{}", getPiece.getStatusId());
            consignmentStatusService.insertConsignmentStatusRecord(getPiece.getLanguageId(), getPiece.getLanguageDescription(),
                    getPiece.getCompanyId(), getPiece.getCompanyName(), getPiece.getPieceId(), getPiece.getMasterAirwayBill(),
                    getPiece.getHouseAirwayBill(), getPiece.getHawbType(), getPiece.getHawbTypeId(), getPiece.getHawbTypeDescription(),
                    getPiece.getHawbTimeStamp(), getPiece.getHawbType(), getPiece.getHawbTypeId(), getPiece.getHawbTypeDescription(),
                    getPiece.getHawbTimeStamp(), loginUserID, getPiece.getPartnerHouseAirwayBill(), getPiece.getPartnerMasterAirwayBill(),
                    getPiece.getConsignmentBagId(), getPiece.getHubCode(), getPiece.getHubName());
            getPiece.setHawbTypeId("16");
            String status = replicaConsignmentEntityRepository.getStatusEventDescription("16");
            getPiece.setHawbTypeDescription(status);
            getPiece.setUpdatedBy(loginUserID);
            getPiece.setUpdatedOn(new Date());
            con = consignmentEntityRepository.save(getPiece);
            consignmentStatusService.insertConsignmentStatusRecord(con.getLanguageId(), con.getLanguageDescription(),
                    con.getCompanyId(), con.getCompanyName(), con.getPieceId(), con.getMasterAirwayBill(),
                    con.getHouseAirwayBill(), con.getHawbType(), con.getHawbTypeId(), con.getHawbTypeDescription(),
                    con.getHawbTimeStamp(), con.getHawbType(), con.getHawbTypeId(), con.getHawbTypeDescription(),
                    con.getHawbTimeStamp(), loginUserID, con.getPartnerHouseAirwayBill(), con.getPartnerMasterAirwayBill(),
                    con.getConsignmentBagId(), con.getHubCode(), con.getHubName());
        } else {
            throw new BadRequestException("ScanId Must be Match consignment Id Or PieceId ");
        }
        return con;
    }


    /**
     * @param findPieceReq
     * @return
     */
    public List<FindPieceRes> findPieceDetails(FindPieceReq findPieceReq) {
        List<FindPieceRes> findPieceResList = new ArrayList<>();

//        if (findPieceReq.getHouseAirwayBill() != null && findPieceReq.getHouseAirwayBill().isEmpty()) {
//            findPieceReq.setHouseAirwayBill(null);
//        }
//        if (findPieceReq.getPickUpId() != null && findPieceReq.getPickUpId().isEmpty()) {
//            findPieceReq.setPickUpId(null);
//        }
//        if(findPieceReq.getStatusId() != null && findPieceReq.getStatusId().isEmpty()){
//            findPieceReq.setStatusId(null);
//        }
        List<ReplicaConsignmentEntity> dbConsignment = replicaConsignmentEntityRepository.getPieceDetails(findPieceReq.getLanguageId(), findPieceReq.getCompanyId(),
                findPieceReq.getHouseAirwayBill(), findPieceReq.getStatusId(), findPieceReq.getPickupId());
        log.info("Consignment piece data count --> {}", dbConsignment.size());
        if (dbConsignment.size() > 0) {
            dbConsignment.forEach(id -> {
                id.getPieceDetails().forEach(p -> {
                    FindPieceRes findPieceRes = new FindPieceRes();
                    findPieceRes.setHouseAirwayBill(id.getHouseAirwayBill());
                    log.info("PIECE_ID --> {}", p.getPieceId());
                    findPieceRes.setPieceId(p.getPieceId());
                    findPieceRes.setStatusId(id.getHawbTypeId());
                    findPieceRes.setStatusDescription(id.getHawbTypeDescription());
                    findPieceRes.setPickupId(id.getPickupId());
                    findPieceResList.add(findPieceRes);
                });
            });
            return findPieceResList;
        } else {
            throw new BadRequestException("The given values : languageId - " + findPieceReq.getLanguageId() + " companyId - " + findPieceReq.getCompanyId() +
                    " statusId - " + findPieceReq.getStatusId() + " houseAirwayBill - " + findPieceReq.getHouseAirwayBill() + " pickupId - " + findPieceReq.getPickupId() + " doesn't exists");
        }
    }

    /**
     * @param findHAWB
     * @return
     */
    public List<FindHouseAirwayBill> findHouseAirwayBill(FindHAWB findHAWB) throws ParseException {

        if (findHAWB.getFromDate() != null && findHAWB.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearchV2(findHAWB.getFromDate(),
                    findHAWB.getToDate());
            findHAWB.setFromDate(dates[0]);
            findHAWB.setToDate(dates[1]);
            log.info("FROM DATE --> {}", findHAWB.getFromDate());
            log.info("TO DATE --> {}", findHAWB.getToDate());
        }

        return replicaConsignmentEntityRepository.getHouseAirwayBillList(findHAWB.getLanguageId(),
                findHAWB.getCompanyId(), findHAWB.getStatusId(), findHAWB.getPartnerId(), findHAWB.getFromDate(), findHAWB.getToDate());
    }

//    /**
//     * Grouping Consignment Records based on DestinationDetails -> city
//     *
//     * @param statusId
//     * @return
//     */
//    public Map<String, List<ReplicaConsignmentEntity>> groupConsignmentByCityWithStatus(String statusId) {
//
//        List<ReplicaConsignmentEntity> dbConsignmentEntities = replicaConsignmentEntityRepository.getConsignmentWithStatus();
//
//        Map<String, List<ReplicaConsignmentEntity>> groupedByCity = dbConsignmentEntities.stream().filter(consignment ->
//                consignment.getDestinationDetails() != null && consignment.getStatusId().equals(statusId))
//                .collect(Collectors.groupingBy(consignment -> consignment.getDestinationDetails().getCity()));
//
//        return groupedByCity;
//    }


    public List<ReplicaConsignmentEntity> findConsignmentEntity(FindConsignment findConsignment) {
        // Validate inputs
        int limit = 500;  // Define your batch size
        int offset = 0;
        String companyIds = findConsignment.getCompanyId() != null ? String.join(",", findConsignment.getCompanyId()) : null;
        String languageIds = findConsignment.getLanguageId() != null ? String.join(",", findConsignment.getLanguageId()) : null;
        String partnerIds = findConsignment.getPartnerId() != null ? String.join(",", findConsignment.getPartnerId()) : null;
        String masterAirwayBills = findConsignment.getMasterAirwayBill() != null ? String.join(",", findConsignment.getMasterAirwayBill()) : null;
        String houseAirwayBills = findConsignment.getHouseAirwayBill() != null ? String.join(",", findConsignment.getHouseAirwayBill()) : null;

        Date fromDate = findConsignment.getFromDate();
        Date toDate = findConsignment.getToDate();

        // Add logging for debugging
        System.out.println("Language IDs: " + languageIds);
        System.out.println("Company IDs: " + companyIds);
        System.out.println("Partner IDs: " + partnerIds);
        System.out.println("Master Airway Bills: " + masterAirwayBills);
        System.out.println("House Airway Bills: " + houseAirwayBills);
        System.out.println("From Date: " + fromDate);
        System.out.println("To Date: " + toDate);

        // Call the repository method
        return replicaConsignmentEntityRepository.fetchConsignmentsWithQryProc(
                languageIds,
                companyIds,
                partnerIds,
                masterAirwayBills,
                houseAirwayBills,
                fromDate,
                toDate,
                limit,
                offset
        );
    }

//    public List<ConsignmentSummaryDTO> findConsignment() {
//        List<Object[]> results = consignmentEntityRepository.findConsignmentAndWithDetailsRaw();
//        Map<Long, ConsignmentSummaryDTO> consignmentMap = new HashMap<>();
//
//        for (Object[] row : results) {
//            Long consignmentId = ((BigInteger) row[0]).longValue();
//
//            ConsignmentSummaryDTO consignment = consignmentMap.computeIfAbsent(consignmentId, id -> {
//                ConsignmentSummaryDTO dto = new ConsignmentSummaryDTO();
//                dto.setConsignmentId(((BigInteger) row[0]).longValue());
//                dto.setLanguageId((String) row[1]);
//                dto.setLanguageDescription((String) row[2]);
//                dto.setCompanyId((String) row[3]);
//                dto.setCompanyName((String) row[4]);
//                dto.setPartnerId((String) row[5]);
//                dto.setPartnerType((String) row[6]);
//                dto.setPartnerName((String) row[7]);
//                dto.setMasterAirwayBill((String) row[8]);
//                dto.setHouseAirwayBill((String) row[9]);
//                dto.setPieceDetails(new ArrayList<>());
//                return dto;
//            });
//
//            // Add PieceDetails if present
//            if (row[10] != null) {  // Check if pieceDetails data exists
//                PieceDetailsDTO piece = new PieceDetailsDTO();
//                piece.setPieceDetailsId(((BigInteger) row[10]).longValue());
//                piece.setLanguageId((String) row[1]);
//                piece.setCompanyId((String) row[3]);
//                consignment.getPieceDetails().add(piece);
//            }
//        }
//
//        return new ArrayList<>(consignmentMap.values());
//    }


//    public ConsignmentEntity update(ConsignmentEntity cons) {
//
//        Double totalPieceWeight = cons.getPieceDetails().stream().map(PieceDetails::getWeight).filter(n -> n != null).mapToDouble(a -> a).sum();
//
//        // FINANCE_LOGIC_CODE
//        List<Rate> rateList = rateRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndDeletionIndicator(cons.getCompanyId(), cons.getLanguageId(), partnerId, 0L);
//
//        // Rate_List
//        rateList.forEach(rate -> {
//            double ceilingValue = rate.getCeilingValue();
//            double from = rate.getRangeFrom();
//            double to = rate.getRangeTo();
//            double chargeWeight = totalPieceWeight + ceilingValue;
//            if(from <= chargeWeight && to >= chargeWeight) {
//                newConsignment.setChargeableWeight(chargeWeight);
//                newConsignment.setCeilingValue(ceilingValue);
//                newConsignment.setFrightCharge(rate.getRate());
//            }
//        });
//        // COD_PRICE_LIST
//        List<CodPriceList> codPriceListList = codPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndDeletionIndicator(languageId, companyId, partnerId, 0L);
//        codPriceListList.forEach(cod -> {
//            double ceilingValue = cod.getCeilingValue();
//            double from = cod.getRangeFrom();
//            double to = cod.getRangeTo();
//            double chargeWeight = totalPieceWeight + ceilingValue;
//            if(from <= chargeWeight && to >= chargeWeight) {
//                newConsignment.setCodCharge(chargeWeight);
//            }
//        });
//
//    }

    /**
     * Find Consignment OutScan MobApp
     *
     * @param findConsignmentOutScanMobApp
     * @return
     */
    public List<FindConsignmentOutScanResponse> findConsignmentOutScanResponses(FindConsignmentOutScanMobApp findConsignmentOutScanMobApp) {
        try {
            List<FindConsignmentOutScanResponse> findConsignmentOutScanResponseList = new ArrayList<>();

            List<ReplicaConsignmentEntity> consignmentEntities = replicaConsignmentEntityRepository.getAllConsignmentData(
                    findConsignmentOutScanMobApp.getLanguageId(), findConsignmentOutScanMobApp.getCompanyId(),
                    findConsignmentOutScanMobApp.getHubCode(), findConsignmentOutScanMobApp.getStatusId()
            );

            for (ReplicaConsignmentEntity consignment : consignmentEntities) {

                Long consignmentNo = consignment.getConsignmentId();
                List<ReplicaPieceDetails> pieceDetails = replicaPieceDetailsRepository.getAllPieceDetails(consignment.getLanguageId(),
                        consignment.getCompanyId(), consignmentNo);

                for (ReplicaPieceDetails pieceDetails1 : pieceDetails) {
                    FindConsignmentOutScanResponse findConsignmentOutScanResponse = new FindConsignmentOutScanResponse();

                    findConsignmentOutScanResponse.setStorageDescription(consignment.getStorageDescription());
                    findConsignmentOutScanResponse.setZoneText(consignment.getZoneText());

                    findConsignmentOutScanResponse.setPieceId(pieceDetails1.getPieceId());
                    findConsignmentOutScanResponse.setConsignmentNo(consignment.getHouseAirwayBill());
                    findConsignmentOutScanResponse.setPartnerId(consignment.getPartnerId());
                    findConsignmentOutScanResponse.setMasterAirwayBill(consignment.getMasterAirwayBill());

                    findConsignmentOutScanResponseList.add(findConsignmentOutScanResponse);
                }
            }

            if (findConsignmentOutScanResponseList.size() == 0) {
                throw new BadRequestException("No Data Found");
            } else {
                return findConsignmentOutScanResponseList;
            }

        } catch (Exception e) {
            throw new RuntimeException("There is a Error Finding Consignment OutScanResponse" + e);
        }
    }


    // Find Finance
    public List<Finance> findFinanceFromConsignment(FindConsignment findConsignment) throws ParseException {

        if (findConsignment.getFromDate() != null && findConsignment.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearchV2(findConsignment.getFromDate(), findConsignment.getToDate());
            findConsignment.setFromDate(dates[0]);
            findConsignment.setToDate(dates[1]);
            log.info("FROM DATE --> {}", findConsignment.getFromDate());
            log.info("TO DATE --> {}", findConsignment.getToDate());
        }
        return replicaConsignmentEntityRepository.findFiance(findConsignment.getLanguageId(),
                findConsignment.getCompanyId(), findConsignment.getPartnerId(), findConsignment.getMasterAirwayBill(), findConsignment.getHouseAirwayBill(),
                findConsignment.getFromDate(), findConsignment.getToDate());
    }

    // Find Finance
    public List<ConsignmentData> findConsignmentDetails(FindConsignment findConsignment) throws ParseException {

        if (findConsignment.getFromDate() != null && findConsignment.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearchV2(findConsignment.getFromDate(), findConsignment.getToDate());
            findConsignment.setFromDate(dates[0]);
            findConsignment.setToDate(dates[1]);
            log.info("FROM DATE --> {}", findConsignment.getFromDate());
            log.info("TO DATE --> {}", findConsignment.getToDate());
        }
        return replicaConsignmentEntityRepository.getConsignment(findConsignment.getLanguageId(),
                findConsignment.getCompanyId(), findConsignment.getPartnerId(), findConsignment.getMasterAirwayBill(), findConsignment.getHouseAirwayBill(),
                findConsignment.getStatusId(), findConsignment.getFromDate(), findConsignment.getToDate());
//        return replicaConsignmentEntityRepository.getConsignment(findConsignment.getLanguageId(), findConsignment.getCompanyId(), findConsignment.getPartnerId(), findConsignment.getMasterAirwayBill(), findConsignment.getHouseAirwayBill(),
//                findConsignment.getFromDate(), findConsignment.getToDate());
    }

    public List<ConsignmentEntity> findConsignmentDetails() {
        return consignmentEntityRepository.getConsignmentList();
    }

    /**
     * Delivery Update Mobile update api for courierId update in deliveryTable if not create from consignment
     * and update
     *
     * @param deliveryMobUpdateInputs
     * @return
     */
    public List<Delivery> deliveryMobUpdate(List<DeliveryMobUpdateInput> deliveryMobUpdateInputs, String loginUserID) {

        try {
            List<ConsignmentEntity> consignmentEntities = new ArrayList<>();
            List<Delivery> deliveries = new ArrayList<>();

            for (DeliveryMobUpdateInput input : deliveryMobUpdateInputs) {
                Delivery dbDelivery = deliveryRepository.getMobUpdateDelivery(input.getHouseAirwayBill(),
                        input.getLanguageId(), input.getCompanyId());

                if (dbDelivery != null) {
                    deliveryRepository.updateCourierId(input.getRiderId(), input.getHouseAirwayBill(),
                            input.getLanguageId(), input.getCompanyId());
                    Delivery delivery = deliveryRepository.getMobUpdateDelivery(input.getHouseAirwayBill(),
                            input.getLanguageId(), input.getCompanyId());

                    deliveries.add(delivery);
                } else {
                    ConsignmentEntity dbConsignment = consignmentEntityRepository.getConsignment(input.getHouseAirwayBill(),
                            input.getLanguageId(), input.getCompanyId());
                    dbConsignment.setReferenceField5(input.getRiderId());
                    consignmentEntities.add(dbConsignment);
                }
            }
            if (consignmentEntities.size() > 0) {
                Delivery[] deliveryFromConsignment = commonService.createDeliveryFromConsignment(consignmentEntities, loginUserID);
                // Convert array to list
                List<Delivery> deliveryList = new ArrayList<>(Arrays.asList(deliveryFromConsignment));

                return deliveryList;
            }

            return deliveries;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Customer portal consignment status count
     *
     * @param input
     * @return
     * @throws ParseException
     */
    public List<CustomerConsignmentStatusCount> statusCustomerConsignmentCount(StatusCountInput input) throws ParseException {

        List<CustomerConsignmentStatusCount> customerConsignmentStatusCountList = new ArrayList<>();

        if (input.getFromDate() != null && input.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(input.getFromDate(), input.getToDate());
            input.setFromDate(dates[0]);
            input.setToDate(dates[1]);
        }

        CustomerConsignmentStatusCount customerConsignmentStatusCount;
        Long totalCount = 0L;

        // SoftData upload
        customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
        customerConsignmentStatusCount.setStatusText("Softdata Upload");
        customerConsignmentStatusCount.setStatusId("NA");
        customerConsignmentStatusCount.setStatusCount(0L);
        customerConsignmentStatusCountList.add(customerConsignmentStatusCount);

        // Pickup Await
        Object[] pa = replicaConsignmentEntityRepository.getPickupAwait(input.getFromDate(), input.getToDate());
        if (pa != null && pa.length > 0 && pa[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Pickup Await");
            customerConsignmentStatusCount.setStatusId("2");
            customerConsignmentStatusCount.setStatusCount(((Number) pa[0]).longValue());
            totalCount += ((Number) pa[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Pickup Await");
            customerConsignmentStatusCount.setStatusId("2");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        // Pickup Schedule
        Object[] ps = replicaConsignmentEntityRepository.getPickupSchedule(input.getFromDate(), input.getToDate());
        if (ps != null && ps.length > 0 && ps[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Pickup Schedule");
            customerConsignmentStatusCount.setStatusId("49");
            customerConsignmentStatusCount.setStatusCount(((Number) ps[0]).longValue());
            totalCount += ((Number) ps[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Pickup Schedule");
            customerConsignmentStatusCount.setStatusId("49");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        // Pickup Completed
        Object[] pc = replicaConsignmentEntityRepository.getPickupCompleted(input.getFromDate(), input.getToDate());
        if (pc != null && pc.length > 0 && pc[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Pickup Completed");
            customerConsignmentStatusCount.setStatusId("3");
            customerConsignmentStatusCount.setStatusCount(((Number) pc[0]).longValue());
            totalCount += ((Number) pc[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Pickup Completed");
            customerConsignmentStatusCount.setStatusId("3");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        // Held-up
        customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
        customerConsignmentStatusCount.setStatusText("Heldup");
        customerConsignmentStatusCount.setStatusId("NA");
        customerConsignmentStatusCount.setStatusCount(0L);
        customerConsignmentStatusCountList.add(customerConsignmentStatusCount);

        // InTransit
        Object[] in = replicaConsignmentEntityRepository.getInTransit(input.getFromDate(), input.getToDate());
        if (in != null && in.length > 0 && in[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("InTransit");
            customerConsignmentStatusCount.setStatusId("10");
            customerConsignmentStatusCount.setStatusCount(((Number) in[0]).longValue());
            totalCount += ((Number) in[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("InTransit");
            customerConsignmentStatusCount.setStatusId("10");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        // Out Of Delivery
        Object[] od = replicaConsignmentEntityRepository.getOutforDelivery(input.getFromDate(), input.getToDate());
        if (od != null && od.length > 0 && od[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Out For Delivery");
            customerConsignmentStatusCount.setStatusId("50");
            customerConsignmentStatusCount.setStatusCount(((Number) od[0]).longValue());
            totalCount += ((Number) od[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Out For Delivery");
            customerConsignmentStatusCount.setStatusId("50");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        // Delivered
        Object[] dd = replicaConsignmentEntityRepository.getDelivered(input.getFromDate(), input.getToDate());
        if (dd != null && dd.length > 0 && dd[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Delivered");
            customerConsignmentStatusCount.setStatusId("18");
            customerConsignmentStatusCount.setStatusCount(((Number) dd[0]).longValue());
            totalCount += ((Number) dd[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Delivered");
            customerConsignmentStatusCount.setStatusId("18");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        // UnDelivered
        Object[] ud = replicaConsignmentEntityRepository.getUnDelivered(input.getFromDate(), input.getToDate());
        if (ud != null && ud.length > 0 && ud[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("UnDelivered");
            customerConsignmentStatusCount.setStatusId("54, 55, 42");
            customerConsignmentStatusCount.setStatusCount(((Number) ud[0]).longValue());
            totalCount += ((Number) ud[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("UnDelivered");
            customerConsignmentStatusCount.setStatusId("54, 55, 42");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        //RTO
        Object[] rto = replicaConsignmentEntityRepository.getRto(input.getFromDate(), input.getToDate());
        if (rto != null && rto.length > 0 && rto[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("RTO");
            customerConsignmentStatusCount.setStatusId("22, 23, 24, 41");
            customerConsignmentStatusCount.setStatusCount(((Number) rto[0]).longValue());
            totalCount += ((Number) rto[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("RTO");
            customerConsignmentStatusCount.setStatusId("22, 23, 24, 41");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        // Cancelled
        Object[] dc = replicaConsignmentEntityRepository.getCancelled(input.getFromDate(), input.getToDate());
        if (dc != null && dc.length > 0 && dc[0] != null) {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Cancelled");
            customerConsignmentStatusCount.setStatusId("54");
            customerConsignmentStatusCount.setStatusCount(((Number) dc[0]).longValue());
            totalCount += ((Number) dc[0]).longValue();
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        } else {
            customerConsignmentStatusCount = new CustomerConsignmentStatusCount();
            customerConsignmentStatusCount.setStatusText("Cancelled");
            customerConsignmentStatusCount.setStatusId("54");
            customerConsignmentStatusCount.setStatusCount(0L);
            customerConsignmentStatusCountList.add(customerConsignmentStatusCount);
        }

        // All
        CustomerConsignmentStatusCount totalStatusCount = new CustomerConsignmentStatusCount();
        totalStatusCount.setStatusText("All");
        totalStatusCount.setStatusId("2, 49, 3, 10, 50, 18, 55, 54, 42, 22, 23, 24, 41, 54");
        totalStatusCount.setStatusCount(totalCount);
        customerConsignmentStatusCountList.add(totalStatusCount);

        return customerConsignmentStatusCountList;
    }

}