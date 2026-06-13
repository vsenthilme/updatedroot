package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.delivery.Delivery;
import com.courier.overc360.api.midmile.primary.model.delivery.UpdateDeliveryStatus;
import com.courier.overc360.api.midmile.primary.model.maps.GeoInfo;
import com.courier.overc360.api.midmile.primary.model.pickup.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.repository.DeliveryRepository;
import com.courier.overc360.api.midmile.primary.repository.PickupEntityRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.delivery.*;
import com.courier.overc360.api.midmile.replica.model.finance.ReplicaCodPriceList;
import com.courier.overc360.api.midmile.replica.repository.ReplicaDeliveryRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPickupDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaRateRepository;
import com.courier.overc360.api.midmile.replica.repository.finance.ReplicaCodPriceListRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaDeliverySpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class DeliveryService {

    @Autowired
    private ReplicaDeliveryRepository replicaDeliveryRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaPickupDetailsRepository replicaPickupDetailsRepository;

    @Autowired
    private RescheduleDeliveryService rescheduleDeliveryService;

    @Autowired
    private NdrService ndrService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GeoInfoService geoInfoService;

    @Autowired
    private DrsService drsService;

    @Autowired
    private DebriefService debriefService;

    @Autowired
    private PickupEntityRepository pickupEntityRepository;

    @Autowired
    ReplicaRateRepository replicaRateRepository;

    @Autowired
    ReplicaCodPriceListRepository replicaCodPriceListRepository;


    @Autowired
    ConsignmentStatusService consignmentStatusService;

    // Synchronized for GenerateNumberRange
    public synchronized String generateUniqueNumberRange(String numRanObj) {
        return numberRangeService.getNextNumberRange(numRanObj);
    }

    // GetRoundOfValue
    public double calculateAdjustedChargeWeight(double totalPieceWeight, double ceilingValue) {
        return Math.ceil(totalPieceWeight / ceilingValue) * ceilingValue;
    }
    //=======================================================================================================================


    // Consignment To Delivery Create
    public List<Delivery> createConsignmentToDelivery(List<ConsignmentEntity> consignmentEntityList, String loginUserID) {
        List<Delivery> deliveryList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        consignmentEntityList.stream().forEach(consignmentEntity -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    Long pieceCount = consignmentEntity.getPieceDetails().stream().count();
                    consignmentEntity.getPieceDetails().stream().forEach(piece -> {
                        Delivery delivery = new Delivery();
                        BeanUtils.copyProperties(consignmentEntity, delivery, CommonUtils.getNullPropertyNames(consignmentEntity));

                        if (consignmentEntity.getReferenceField5() != null) {
                            delivery.setCourierId(consignmentEntity.getReferenceField5());
                        }
                        if (consignmentEntity.getDestinationDetails().getAddressLine1() != null) {
                            delivery.setAddressLine1(consignmentEntity.getDestinationDetails().getAddressLine1());
                        }
                        if (consignmentEntity.getDestinationDetails().getAddressLine2() != null) {
                            delivery.setAddressLine2(consignmentEntity.getDestinationDetails().getAddressLine2());
                        }
                        if (consignmentEntity.getDestinationDetails().getPinCode() != null) {
                            delivery.setPinCode(consignmentEntity.getDestinationDetails().getPinCode());
                        }
                        delivery.setPhone(consignmentEntity.getDestinationDetails().getPhone());
                        if (consignmentEntity.getCodAmount() != null && !consignmentEntity.getCodAmount().isEmpty()) {
                            delivery.setCodAmount(Double.valueOf(consignmentEntity.getCodAmount()));
                        }
                        delivery.setPieceId(piece.getPieceId());
                        delivery.setPieceCount(String.valueOf(pieceCount));
                        deliveryList.add(delivery);
                    });
                } catch (Exception e) {
                    throw new RuntimeException("Consignment to Delivery create failed " + e.getMessage());
                }
            }, executor);
            futures.add(future);
        });
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        return createDelivery(deliveryList, loginUserID);
    }

    /**
     * @param deliveryList
     * @param loginUserID
     * @return
     */
    public List<Delivery> createDelivery(List<Delivery> deliveryList, String loginUserID) {
        List<Delivery> newDeliveries = new ArrayList<>();
        try {
            String deliveryId = generateUniqueNumberRange("DELIVERYID");
            Map<String, String> courierManifestMap = new HashMap<>(); // Map to store courierId -> manifestNumber

            deliveryList.stream().forEach(delivery -> {
                if (delivery.getHouseAirwayBill() != null) {
                    replicaDeliveryRepository.findByLanguageIdAndCompanyIdAndPieceIdAndHouseAirwayBillAndDeletionIndicator(
                                    delivery.getLanguageId(), delivery.getCompanyId(), delivery.getPieceId(),
                                    delivery.getHouseAirwayBill(), 0L)
                            .ifPresent(duplicate -> {
                                throw new BadRequestException("Record is getting duplicated with the given values : HouseAirwayBill - " + delivery.getHouseAirwayBill() +
                                        "CompanyId - " + delivery.getCompanyId() + " LanguageId - " + delivery.getLanguageId() + " PieceId - " + delivery.getPieceId());
                            });
                }
                Delivery newDelivery = new Delivery();
                BeanUtils.copyProperties(delivery, newDelivery, CommonUtils.getNullPropertyNames(delivery));
                newDelivery.setStatusId("48");
                newDelivery.setDeliveryId(deliveryId);
                String desc = replicaPickupDetailsRepository.getStatusDescription("48");
                if (desc != null && !desc.isEmpty()) {
                    newDelivery.setStatusDescription(desc);
                    newDelivery.setStatusTimestamp(new Date());
                }
                // Set Service
                if (delivery.getHouseAirwayBill() != null) {
                    IKeyValuePair conf = replicaDeliveryRepository.getCons(delivery.getHouseAirwayBill());
                    if (conf != null && conf.getServiceTypeText() != null && !conf.getServiceTypeText().isEmpty()) {
                        newDelivery.setServiceTypeName(conf.getServiceTypeText());
                    }
                    if (conf != null && conf.getWeight() != null) {
                        newDelivery.setTotalShipmentWeight(conf.getWeight());
                    }
                    if (conf != null && conf.getPartnerHouseAirwayBill() != null) {
                        newDelivery.setPartnerHouseAirwayBill(conf.getPartnerHouseAirwayBill());
                    }
                }

                // Generate or retrieve manifestNumber based on courierId
                String courierId = delivery.getCourierId();
                String manifestNumber;
                if (courierManifestMap.containsKey(courierId)) {
                    manifestNumber = courierManifestMap.get(courierId);
                    newDelivery.setManifestNumber(manifestNumber);
                } else {
                    manifestNumber = generateUniqueNumberRange("MANIFESTID");
                    courierManifestMap.put(courierId, manifestNumber); // Store in map for reuse
                    newDelivery.setManifestNumber(manifestNumber);
                }

                String addDest = newDelivery.getAddressLine1() + "," + newDelivery.getAddressLine2() + "," + newDelivery.getDistrict() + "," +
                        newDelivery.getCity() + "," + newDelivery.getState() + "," + newDelivery.getCountry() + "," + newDelivery.getPinCode();
                newDelivery.setDestinationAddress(addDest);

                GeoInfo getGeo = geoInfoService.geoCode(addDest);

                newDelivery.setLatitude(getGeo != null && getGeo.getLatitude() != null ? getGeo.getLatitude() : null);
                newDelivery.setLongitude(getGeo != null && getGeo.getLongitude() != null ? getGeo.getLongitude() : null);

//                String addDest1 = newDelivery.getName() + "," + newDelivery.getAddressLine1() + "," + newDelivery.getAddressLine2() + "," + newDelivery.getDistrict() + "," +
//                        newDelivery.getCity() + "," + newDelivery.getState() + "," + newDelivery.getCountry() + "," + newDelivery.getPinCode() + "," + newDelivery.getPhone();
//                newDelivery.setDestinationAddress1(addDest1);
                newDelivery.setDestinationAddress1(Stream.of(newDelivery.getName(), newDelivery.getAddressLine1(), newDelivery.getAddressLine2(), newDelivery.getDistrict(),
                                newDelivery.getCity(), newDelivery.getState(), newDelivery.getCountry(), newDelivery.getPinCode(), newDelivery.getPhone())
                        .filter(part -> part != null && !part.trim().isEmpty())
                        .collect(Collectors.joining(", ")));

                // Set Address and Destinations
                newDelivery.setAddress(Stream.of(newDelivery.getAddressLine1(), newDelivery.getAddressLine2(), newDelivery.getPinCode())
                        .filter(part -> part != null && !part.trim().isEmpty())
                        .collect(Collectors.joining(", ")));
                newDelivery.setDestinations(newDelivery.getAddress());

                double codAmount = newDelivery.getCodAmount() != null ? newDelivery.getCodAmount() : 0.0;
                double invoiceAmount = newDelivery.getInvoiceAmount() != null ? newDelivery.getInvoiceAmount() : 0.0;
                newDelivery.setTotalAmount(codAmount + invoiceAmount);
                newDelivery.setDeletionIndicator(0L);
                newDelivery.setCreatedBy(loginUserID);
                newDelivery.setCreatedOn(new Date());
                newDelivery.setUpdatedBy(loginUserID);
                newDelivery.setUpdatedOn(new Date());
                Delivery cp = deliveryRepository.save(newDelivery);
                if (delivery.getCourierId() != null) {
                    // 56 Status
                    String statusDesc = replicaPickupDetailsRepository.getStatusDescription("56");
                    consignmentStatusService.insertConsignmentStatusRecord(cp.getLanguageId(), cp.getLanguageDescription(), cp.getCompanyId(), cp.getCompanyName(),
                            cp.getPieceId(), cp.getHouseAirwayBill(), "STATUS", "56", statusDesc,
                            cp.getStatusTimestamp(), "STATUS", "56", statusDesc, cp.getStatusTimestamp(), loginUserID);

                    // 39 Status
                    String statusText = replicaPickupDetailsRepository.getStatusDescription("39");
                    consignmentStatusService.insertConsignmentStatusRecord(cp.getLanguageId(), cp.getLanguageDescription(), cp.getCompanyId(), cp.getCompanyName(),
                            cp.getPieceId(), cp.getHouseAirwayBill(), "STATUS", "39", statusText,
                            cp.getStatusTimestamp(), "STATUS", "39", statusText, cp.getStatusTimestamp(), loginUserID);
                    // 48 Status
                    consignmentStatusService.insertConsignmentStatusRecord(cp.getLanguageId(), cp.getLanguageDescription(), cp.getCompanyId(), cp.getCompanyName(),
                            cp.getPieceId(), cp.getHouseAirwayBill(), "STATUS", cp.getStatusId(), cp.getStatusDescription(),
                            cp.getStatusTimestamp(), "STATUS", cp.getStatusId(), cp.getStatusDescription(), cp.getStatusTimestamp(), loginUserID);

                    Double payment = cp.getPaymentCollected() != null ? Double.parseDouble(cp.getPaymentCollected()) : 0.0;
                    deliveryRepository.updateDeliveryConsignment(cp.getStatusId(), cp.getStatusDescription(), cp.getPartnerId(), cp.getHouseAirwayBill(), payment);
                }
                // Update Consignment
                if (delivery.getCourierId() == null) {
                    String statusDesc = replicaPickupDetailsRepository.getStatusDescription("56");
                    consignmentStatusService.insertConsignmentStatusRecord(cp.getLanguageId(), cp.getLanguageDescription(), cp.getCompanyId(), cp.getCompanyName(),
                            cp.getPieceId(), cp.getHouseAirwayBill(), "STATUS", "56", statusDesc,
                            cp.getStatusTimestamp(), "STATUS", "56", statusDesc, cp.getStatusTimestamp(), loginUserID);
                    Double payment = cp.getPaymentCollected() != null ? Double.parseDouble(cp.getPaymentCollected()) : 0.0;
                    deliveryRepository.updateDeliveryConsignment("56", statusDesc, cp.getPartnerId(), cp.getHouseAirwayBill(), payment);
                }
                newDeliveries.add(cp);

            });
            notificationService.sendNotificationForDeliveryCreate(newDeliveries.get(0).getCompanyId(), newDeliveries.get(0).getLanguageId(), newDeliveries.get(0).getDeliveryId());

            return newDeliveries;
        } catch (BadRequestException ex) {
            throw ex;
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }

    /**
     * @param updateDelivery
     * @param loginUserID
     * @return
     */
    public List<Delivery> updateDelivery(List<Delivery> updateDelivery, String loginUserID) {
        List<Delivery> deliveryList = new ArrayList<>();
        try {
            for (Delivery delivery : updateDelivery) {
                Optional<Delivery> dbDelivery = deliveryRepository.findByLanguageIdAndCompanyIdAndPieceIdAndHouseAirwayBillAndDeletionIndicator(
                        delivery.getLanguageId(), delivery.getCompanyId(), delivery.getPieceId(), delivery.getHouseAirwayBill(), 0L);

                if (dbDelivery.isPresent()) {
                    Delivery dbDeliveryData = dbDelivery.get();
//                    Delivery updateDeliveryData = new Delivery();
                    BeanUtils.copyProperties(delivery, dbDeliveryData, CommonUtils.getNullPropertyNames(delivery));
                    // Check weather Re-ScheduleStart & End Time is provided
                    if (dbDeliveryData.getReScheduledSlotStart() != null || dbDeliveryData.getReScheduledSlotEnd() != null) {
                        rescheduleDeliveryService.createRescheduleDelivery(dbDeliveryData);
                    }

                    // Check weather NdrId and Text is provided
                    if (dbDeliveryData.getNdrId() != null || dbDeliveryData.getNdrText() != null) {
                        ndrService.addNdr(dbDeliveryData);
                    }

                    if (dbDeliveryData.getCourierId() != null) {
                        // Create Debrief record
                        debriefService.createDeliveryDebrief(dbDeliveryData, loginUserID);
                        // Create Drs record
                        drsService.createDeliveryDrs(dbDeliveryData, loginUserID);
                    }

                    String status = replicaDeliveryRepository.getStatusDescription(delivery.getStatusId());
                    dbDeliveryData.setStatusDescription(status);
                    dbDeliveryData.setUpdatedBy(loginUserID);
                    dbDeliveryData.setUpdatedOn(new Date());
                    Delivery ud = deliveryRepository.save(dbDeliveryData);
                    if (ud.getStatusId().equalsIgnoreCase("16")) {
                        String statusText = replicaDeliveryRepository.getStatusDescription("15");
                        consignmentStatusService.insertConsignmentStatusRecord(ud.getLanguageId(), ud.getLanguageDescription(), ud.getCompanyId(), ud.getCompanyName(),
                                ud.getPieceId(), ud.getHouseAirwayBill(), "STATUS", ud.getStatusId(), ud.getStatusDescription(),
                                ud.getStatusTimestamp(), "STATUS", "15", statusText, ud.getStatusTimestamp(), loginUserID);
                        consignmentStatusService.insertConsignmentStatusRecord(ud.getLanguageId(), ud.getLanguageDescription(), ud.getCompanyId(), ud.getCompanyName(),
                                ud.getPieceId(), ud.getHouseAirwayBill(), "STATUS", ud.getStatusId(), ud.getStatusDescription(),
                                ud.getStatusTimestamp(), "STATUS", ud.getStatusId(), ud.getStatusDescription(), ud.getStatusTimestamp(), loginUserID);
                    } else {
                        consignmentStatusService.insertConsignmentStatusRecord(ud.getLanguageId(), ud.getLanguageDescription(), ud.getCompanyId(), ud.getCompanyName(),
                                ud.getPieceId(), ud.getHouseAirwayBill(), "STATUS", ud.getStatusId(), ud.getStatusDescription(),
                                ud.getStatusTimestamp(), "STATUS", ud.getStatusId(), ud.getStatusDescription(), ud.getStatusTimestamp(), loginUserID);
                    }

                    if (ud.getStatusId().equalsIgnoreCase("18")) {
                        // Finance Logic Rate
                        double chargeWeight = replicaDeliveryRepository.getWeight(ud.getHouseAirwayBill());
                        List<ReplicaRate> rateList = replicaRateRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndDeletionIndicator(ud.getCompanyId(), ud.getLanguageId(), ud.getPartnerId(), 0L);
                        double totalFreightCharge = 0.0;
                        for (ReplicaRate rate : rateList) {
                            double from = rate.getRangeFrom() != null ? rate.getRangeFrom() : 0.0;
                            double to = rate.getRangeTo() != null ? rate.getRangeTo() : Double.MAX_VALUE;

                            if (chargeWeight > 0) {
                                // Calculate the weight that can be charged in this range
                                double weightToCharge = Math.min(chargeWeight, to - from + 1);

                                // Apply charges based on rate type
                                if (rate.getRateParameterId().equalsIgnoreCase("1")) {
                                    totalFreightCharge += rate.getRate();
                                } else if (rate.getRateParameterId().equalsIgnoreCase("4")) {
                                    totalFreightCharge += weightToCharge * rate.getRate();
                                }

                                chargeWeight -= weightToCharge;

                                // If all weight is processed, exit the loop
                                if (chargeWeight <= 0) {
                                    break;
                                }
                            }
                        }
                        // COD
                        double totalCodCharge = 0.0;
                        List<ReplicaCodPriceList> codPriceListList = replicaCodPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndDeletionIndicator(ud.getLanguageId(), ud.getCompanyId(), ud.getPartnerId(), 0L);
                        double codAmount = Double.parseDouble(replicaDeliveryRepository.getCodAmount(ud.getHouseAirwayBill()));

                        for (ReplicaCodPriceList cod : codPriceListList) {
                            double from = cod.getRangeFrom() != null ? cod.getRangeFrom() : 0.0;
                            double to = cod.getRangeTo() != null ? cod.getRangeTo() : Double.MAX_VALUE;

                            if (codAmount > 0) {
                                // Calculate the weight that can be charged in this range
//                                double codCharge = Math.min(codAmount, to - from + 1);

                                // Apply charges based on rate type
                                if (cod.getRateParameterId().equalsIgnoreCase("1")) {
                                    totalCodCharge += cod.getRate();
                                } else if (cod.getRateParameterId().equalsIgnoreCase("3")) {
                                    totalCodCharge = codAmount * (cod.getRate() / 100);
                                }
                                // If all weight is processed, exit the loop
                                if (totalCodCharge <= 0) {
                                    break;
                                }
                            }
                        }

                        Double payment = ud.getPaymentCollected() != null ? Double.parseDouble(ud.getPaymentCollected()) : 0.0;
                        deliveryRepository.updateDeliveryConsignment(ud.getStatusId(), ud.getStatusDescription(), ud.getPartnerId(), ud.getHouseAirwayBill(), payment);
                        deliveryRepository.updateCharge(totalFreightCharge, totalCodCharge, ud.getHouseAirwayBill());
                    }
                    deliveryList.add(ud);
                }
            }
            notificationService.sendNotificationForDeliveryCreate(deliveryList.get(0).getCompanyId(), deliveryList.get(0).getLanguageId(), deliveryList.get(0).getDeliveryId());
            return deliveryList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * @param deleteDelivery
     * @param loginUserID
     */
    public void deleteDelivery(List<Delivery> deleteDelivery, String loginUserID) {
        if (deleteDelivery != null || !deleteDelivery.isEmpty()) {

            for (Delivery deleteInput : deleteDelivery) {
                Delivery dbDelivery = getDelivery(deleteInput.getCompanyId(),
                        deleteInput.getLanguageId(), deleteInput.getPieceId(), deleteInput.getHouseAirwayBill());
                if (dbDelivery != null) {
                    dbDelivery.setDeletionIndicator(1L);
                    dbDelivery.setUpdatedBy(loginUserID);
                    dbDelivery.setUpdatedOn(new Date());
                    deliveryRepository.save(dbDelivery);
                }
            }
        } else {
            throw new EntityNotFoundException("Error in deleting costCenter");
        }

    }

    /**
     * @param companyId
     * @param languageId
     * @param pieceId
     * @param houseAirwayBill
     * @return
     */
    public Delivery getDelivery(String companyId, String languageId, String pieceId, String houseAirwayBill) {
        Optional<Delivery> dbDelivery = deliveryRepository.findByLanguageIdAndCompanyIdAndPieceIdAndHouseAirwayBillAndDeletionIndicator(
                languageId, companyId, pieceId, houseAirwayBill, 0L);
        if (dbDelivery.isEmpty()) {
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " pieceId - " + pieceId + " houseAirwayBill - " + houseAirwayBill + " doesn't exists");
        }
        return dbDelivery.get();
    }

    /**
     * @return
     */
    public List<ReplicaDelivery> getAllDelivery() {
        List<ReplicaDelivery> deliveryList = replicaDeliveryRepository.findAll();
        deliveryList = deliveryList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return deliveryList;
    }

    /**
     * @param input
     * @return
     */
    public List<ReplicaDelivery> findDeliveryList(FindDelivery input) throws ParseException {

        if (input.getFromDate() != null && input.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(input.getFromDate(), input.getToDate());
            input.setFromDate(dates[0]);
            input.setToDate(dates[1]);
        }

        ReplicaDeliverySpecification spec = new ReplicaDeliverySpecification(input);
        List<ReplicaDelivery> results = replicaDeliveryRepository.findAll(spec);
        log.info("found delivery --> {}", results);
        return results;
    }


    /**
     * @param deliveryMobileAppReq
     * @return
     */
    public List<FindDeliveryAssigned> findDeliveryAssignedList(DeliveryMobileAppReq deliveryMobileAppReq) {
        List<FindDeliveryAssigned> deliveryAssignedList = new ArrayList<>();

        List<ReplicaDelivery> dbDelivery = replicaDeliveryRepository.getDeliveryAssignedData(deliveryMobileAppReq.getLanguageId(), deliveryMobileAppReq.getCompanyId(),
                deliveryMobileAppReq.getPieceId(), deliveryMobileAppReq.getHouseAirwayBill(), deliveryMobileAppReq.getDeliveryId(), deliveryMobileAppReq.getCourierId());
        log.info("assigned data count --> {}", dbDelivery.size());
        if (dbDelivery.size() > 0) {
            dbDelivery.forEach(id -> {
                FindDeliveryAssigned findDeliveryAssigned = new FindDeliveryAssigned();
                findDeliveryAssigned.setActualSequenceNo(id.getActualSequenceNo());
                findDeliveryAssigned.setCustomerName(id.getPartnerName());
                findDeliveryAssigned.setAddress(id.getAddress());
                findDeliveryAssigned.setPhone(id.getPhone());
                findDeliveryAssigned.setPieceCount(id.getPieceCount());
                findDeliveryAssigned.setCurrency("KWD");
                findDeliveryAssigned.setHouseAirwayBill(id.getHouseAirwayBill());
                findDeliveryAssigned.setDeliveryDateTime(id.getDeliveryTimeSlotStart());
                findDeliveryAssigned.setDeliveryId(id.getDeliveryId());
                findDeliveryAssigned.setStatusId(id.getStatusId());
                findDeliveryAssigned.setCreatedBy(id.getCreatedBy());
                findDeliveryAssigned.setCreatedOn(id.getCreatedOn());
                deliveryAssignedList.add(findDeliveryAssigned);
            });
            return deliveryAssignedList;
        } else {
            throw new BadRequestException("No Data found");
        }
    }

    /**
     * @param deliveryMobileAppReq
     * @return
     */
    public List<FindDeliveryAssignedTask> findDeliveryAcceptedList(DeliveryMobileAppReq deliveryMobileAppReq) {
        List<FindDeliveryAssignedTask> deliveryAssignedTaskList = new ArrayList<>();
        List<ReplicaDelivery> dbDelivery = replicaDeliveryRepository.getDeliveryAssignedData(deliveryMobileAppReq.getLanguageId(), deliveryMobileAppReq.getCompanyId(),
                deliveryMobileAppReq.getPieceId(), deliveryMobileAppReq.getHouseAirwayBill(), deliveryMobileAppReq.getDeliveryId(), deliveryMobileAppReq.getCourierId());
        log.info("accepted task data count --> {}", dbDelivery.size());
        if (dbDelivery.size() > 0) {
            for (ReplicaDelivery id : dbDelivery) {
                FindDeliveryAssignedTask findDeliveryAssignedTask = new FindDeliveryAssignedTask();
                findDeliveryAssignedTask.setHouseAirwayBill(id.getHouseAirwayBill());
                findDeliveryAssignedTask.setCustomerName(id.getPartnerName());
                findDeliveryAssignedTask.setAddressLine1(id.getAddress());
                findDeliveryAssignedTask.setPhone(id.getPhone());
                findDeliveryAssignedTask.setDeliveryTimeSlotStart(id.getDeliveryTimeSlotStart());
                findDeliveryAssignedTask.setIncoTerms(id.getIncoTerms());
                findDeliveryAssignedTask.setPaymentType(id.getPaymentType());
                findDeliveryAssignedTask.setCodAmount(id.getCodAmount());
                findDeliveryAssignedTask.setReceiverName(id.getReceiverName());
                findDeliveryAssignedTask.setReceiverRelationShip(id.getReceiverRelationShip());
                findDeliveryAssignedTask.setReceiverPhone(id.getReceiverPhone());
                findDeliveryAssignedTask.setPaymentCollected(id.getPaymentCollected());
                findDeliveryAssignedTask.setStatusId(id.getStatusId());
                findDeliveryAssignedTask.setPaymentMode(id.getPaymentMode());
                findDeliveryAssignedTask.setInvoiceAmount(id.getInvoiceAmount());
                findDeliveryAssignedTask.setTotalAmount(id.getTotalAmount());
                findDeliveryAssignedTask.setInvoiceName(id.getInvoiceName());
                findDeliveryAssignedTask.setInvoiceUrl(id.getInvoiceUrl());
                findDeliveryAssignedTask.setPaymentLink(id.getPaymentLink());
                findDeliveryAssignedTask.setLatitude(id.getLatitude());
                findDeliveryAssignedTask.setLongitude(id.getLongitude());
                findDeliveryAssignedTask.setCreatedBy(id.getCreatedBy());
                findDeliveryAssignedTask.setCreatedOn(id.getCreatedOn());
                findDeliveryAssignedTask.setPieceId(id.getPieceId());
                findDeliveryAssignedTask.setPieceCount(id.getPieceCount());
                findDeliveryAssignedTask.setDeliveryId(id.getDeliveryId());
                deliveryAssignedTaskList.add(findDeliveryAssignedTask);
            }
            return deliveryAssignedTaskList;
        } else {
//            throw new BadRequestException("The given values : languageId - " + deliveryMobileAppReq.getLanguageId() + " companyId - " + deliveryMobileAppReq.getCompanyId() +
//                    " pieceId - " + deliveryMobileAppReq.getPieceId() + " houseAirwayBill - " + deliveryMobileAppReq.getHouseAirwayBill() + " deliveryId - " +
//                    deliveryMobileAppReq.getDeliveryId() + " doesn't exists");
            throw new BadRequestException("No Data Found");
        }
    }

    /**
     * @param loginUserID
     * @param updateDeliveryStatuses
     * @return
     */
    public List<Delivery> updateDeliveryStatus(List<UpdateDeliveryStatus> updateDeliveryStatuses, String loginUserID) {
        List<Delivery> deliveryList = new ArrayList<>();

        try {
            for (UpdateDeliveryStatus updateDeliveryStatus : updateDeliveryStatuses) {
                log.info("Delivery Update Status Input Values   <------->" + updateDeliveryStatus);
                String status = replicaDeliveryRepository.getStatusDescription(updateDeliveryStatus.getStatusId());
//                Delivery dbDelivery = getDelivery(updateDeliveryStatus.getLanguageId(), updateDeliveryStatus.getCompanyId(), updateDeliveryStatus.getPieceId(),
//                        updateDeliveryStatus.getHouseAirwayBill(), updateDeliveryStatus.getDeliveryId());
                List<Delivery> dbDelivery = deliveryRepository.updateDeliveryStatus(updateDeliveryStatus.getLanguageId(), updateDeliveryStatus.getCompanyId(),
                        updateDeliveryStatus.getPieceId(), updateDeliveryStatus.getHouseAirwayBill(), updateDeliveryStatus.getDeliveryId());

                dbDelivery.forEach(delivery -> {
                    BeanUtils.copyProperties(updateDeliveryStatus, delivery, CommonUtils.getNullPropertyNames(updateDeliveryStatus));
                    delivery.setUpdatedBy(loginUserID);
                    delivery.setUpdatedOn(new Date());
                    delivery.setStatusId(updateDeliveryStatus.getStatusId());
                    delivery.setStatusDescription(status);
                    delivery.setStatusTimestamp(new Date());
                    Double cashCollected = 0.0;
                    Double onlineCollected = 0.0;
                    Double checkCollected = 0.0;

                    // Calculate total amounts for cash and online
                    if (updateDeliveryStatus.getPaymentCollectedList() != null) {
                        for (PaymentCollected paymentCollected : updateDeliveryStatus.getPaymentCollectedList()) {
                            if ("cash".equalsIgnoreCase(paymentCollected.getMethod())) {
                                cashCollected += paymentCollected.getCollectAmount();
                            } else if ("online".equalsIgnoreCase(paymentCollected.getMethod())) {
                                onlineCollected += paymentCollected.getCollectAmount();
                            } else if ("cheque".equalsIgnoreCase(paymentCollected.getMethod())) {
                                checkCollected += paymentCollected.getCollectAmount();
                            }
                        }
                    }
                    delivery.setCashCollected(cashCollected);
                    delivery.setOnlineCollected(onlineCollected);
                    delivery.setChequeCollected(checkCollected);
                    delivery.setCollectedAmount(cashCollected + onlineCollected + checkCollected);

                    // Delivery_Count
                    if (Arrays.asList("55", "18").contains(updateDeliveryStatus.getStatusId())) {
                        String deliveryCountStr = pickupEntityRepository.getDeliveryCount(delivery.getPartnerId(), delivery.getLanguageId(), delivery.getCompanyId());
                        int count = (deliveryCountStr != null) ? Integer.parseInt(deliveryCountStr) : 0;

                        int deliveryCount = (delivery.getDeliveryAttemptCount() != null) ? (int) (delivery.getDeliveryAttemptCount() + 1) : 1;
                        if (count < deliveryCount) {
                            throw new RuntimeException(" Delivery attempt count exceeded the allowed limit. ");
                        }
                        delivery.setDeliveryAttemptCount((long) deliveryCount);
                    }

                    String nprText = deliveryRepository.getNdrText(delivery.getLanguageId(), delivery.getCompanyId(), delivery.getNdrId());
                    if (nprText != null && !nprText.isEmpty()) {
                        delivery.setNdrText(nprText);
                    }
                    if (delivery.getStatusId().equalsIgnoreCase("20")) {
                        Long deliveryCount = replicaDeliveryRepository.deliveryCount(delivery.getPartnerId());
                        if (deliveryCount != null && delivery.getDeliveryAttemptCount() != null) {
                            if (deliveryCount < delivery.getDeliveryAttemptCount()) {
                                throw new BadRequestException("The no of Inventory scans are exceeded");
                            }
                        }
                    }
                    Delivery saved = deliveryRepository.save(delivery);
                    log.info("Delivery Saved Value ------------" + saved);
                    consignmentStatusService.insertConsignmentStatusRecord(saved.getLanguageId(), saved.getLanguageDescription(), saved.getCompanyId(), saved.getCompanyName(),
                            saved.getPieceId(), saved.getHouseAirwayBill(), "STATUS", saved.getStatusId(), saved.getStatusDescription(),
                            null, "STATUS", saved.getStatusId(), saved.getStatusDescription(), null, loginUserID);
                    Double payment = saved.getPaymentCollected() != null ? Double.parseDouble(saved.getPaymentCollected()) : 0.0;
                    deliveryRepository.updateDeliveryConsignment(saved.getStatusId(), saved.getStatusDescription(), saved.getPartnerId(), saved.getHouseAirwayBill(), payment);
                    deliveryList.add(saved);
                });
            }
            return deliveryList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }

    }

//    public Delivery getDelivery(String languageId, String companyId, String pieceId, String houseAirwayBill, String deliveryId) {
//        Delivery dbDelivery = deliveryRepository.updateDeliveryStatus(
//                languageId, companyId, pieceId, houseAirwayBill, deliveryId);
//        if (dbDelivery == null) {
//            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
//                    " pieceId - " + pieceId + " houseAirwayBill - " + houseAirwayBill + " deliveryId - " +
//                    deliveryId + " doesn't exists");
//        }
//        return dbDelivery;
//    }


    /**
     * Find DeliveryManifest Mobile App
     *
     * @param deliveryManifestMobAppInput
     * @return
     */
    public List<DeliveryManifestMobileAppRes> findDeliveryManifestMobApp(DeliveryManifestMobAppInput deliveryManifestMobAppInput) {
        try {

            if (deliveryManifestMobAppInput.getFromDate() != null && deliveryManifestMobAppInput.getToDate() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearch(deliveryManifestMobAppInput.getFromDate(), deliveryManifestMobAppInput.getToDate());
                deliveryManifestMobAppInput.setFromDate(dates[0]);
                deliveryManifestMobAppInput.setToDate(dates[1]);
            }

            List<DeliveryManifestMobileAppRes> deliveryManifestMobileAppResList = new ArrayList<>();

            List<ReplicaDelivery> dbDelivery = replicaDeliveryRepository.getDeliveryManifestData(deliveryManifestMobAppInput.getHubCode(),
                    deliveryManifestMobAppInput.getFromDate(), deliveryManifestMobAppInput.getToDate(), deliveryManifestMobAppInput.getStatusId());

            for (ReplicaDelivery delivery : dbDelivery) {
                DeliveryManifestMobileAppRes response = new DeliveryManifestMobileAppRes();
                response.setManifestNumber(delivery.getManifestNumber());
                response.setDate(delivery.getCreatedOn());
                response.setRider(delivery.getCourierId());
                Long noOfShipment = replicaDeliveryRepository.getManifestCount(response.getManifestNumber());
                response.setNoOfShipment(noOfShipment);
                deliveryManifestMobileAppResList.add(response);
            }


            return deliveryManifestMobileAppResList;
        } catch (Exception e) {
            throw new BadRequestException("Find OutScan Delivery Error " + e.getMessage());
        }
    }

    /**
     * Find OutStan Delivery HAWB
     *
     * @param findOutScanInput
     * @return
     */
    public List<FindOutScanMobileApp> findDeliveryHawbMobApp(FindOutScanInput findOutScanInput) {

        List<FindOutScanMobileApp> findOutScanMobileAppList = new ArrayList<>();

        List<ReplicaDelivery> dbDelivery = replicaDeliveryRepository.getDeliveryData(findOutScanInput.getManifestNumber());

        for (ReplicaDelivery delivery : dbDelivery) {
            FindOutScanMobileApp response = new FindOutScanMobileApp();
            response.setConsignmentNo(delivery.getHouseAirwayBill());
            response.setPieceId(delivery.getPieceId());
            response.setNoOfPiece(delivery.getPieceCount());
            response.setPartnerId(delivery.getPartnerId());
            findOutScanMobileAppList.add(response);
        }

        return findOutScanMobileAppList;
    }

    /**
     * Update Status of Delivery, Consignment
     * & ConsignmentStatus table
     *
     * @param findConsignmentOutScanInput
     */
    public List<FindConsignmentOutScanInput> updateDeliveryConsignmentStatus(List<FindConsignmentOutScanInput> findConsignmentOutScanInput, String loginUserID) {

        try {

            for (FindConsignmentOutScanInput findScan : findConsignmentOutScanInput) {
                // Update Status of Delivery table record with given input ManifestNumber
                deliveryRepository.updateStatusDeliveryData(findScan.getLanguageId(),
                        findScan.getCompanyId(), findScan.getManifestNumber(),
                        findScan.getStatusId());

                // Update Status of Consignment table record with given input
                List<String> houseAirwayBill = deliveryRepository.getHouseAirwayBillFromManifest(findScan.getManifestNumber());

                for (String hawb : houseAirwayBill) {
                    deliveryRepository.updateStatusConsignmentData(hawb, findScan.getStatusId(),
                            findScan.getLanguageId(), findScan.getCompanyId());

                    String pieceId = deliveryRepository.getPieceId(hawb, findScan.getLanguageId(), findScan.getCompanyId());

                    String hawbType = deliveryRepository.getHawbType(hawb, findScan.getLanguageId(), findScan.getCompanyId());

                    String hawbTypeId = deliveryRepository.getHawbTypeId(hawb, findScan.getLanguageId(), findScan.getCompanyId());

                    String hawbTypeDescription = deliveryRepository.getHawbTypeDescription(hawb, findScan.getLanguageId(), findScan.getCompanyId());

                    String pieceType = deliveryRepository.getPieceType(hawb, findScan.getLanguageId(), findScan.getCompanyId());

                    String pieceTypeId = deliveryRepository.getPieceTypeId(hawb, findScan.getLanguageId(), findScan.getCompanyId());

                    String pieceTypeDescription = deliveryRepository.getPieceTypeDesc(hawb, findScan.getLanguageId(), findScan.getCompanyId());

                    // Inserting ConsignmentStatus record with given input
                    consignmentStatusService.insertConsignmentStatusRecordV2(findScan.getLanguageId(),
                            findScan.getCompanyId(), pieceId, hawb, hawbType, hawbTypeId, hawbTypeDescription, pieceType, pieceTypeId, pieceTypeDescription, loginUserID);
                }
            }

            return findConsignmentOutScanInput;
        } catch (Exception e) {
            throw new BadRequestException("Error Updating Delivery, Consignment & Inserting ConsignmentStatus tables " + e);
        }
    }


    // Delivery_Status_Count
    public List<DeliveryStatusCount> statusDeliveryCount(StatusCountInput input) throws ParseException {
        List<DeliveryStatusCount> deliveryList = new ArrayList<>();
        if (input.getFromDate() != null && input.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(input.getFromDate(), input.getToDate());
            input.setFromDate(dates[0]);
            input.setToDate(dates[1]);
        }

        DeliveryStatusCount deliveryStatusCount;
        Long totalCount = 0L;
        // UnAssigned
        Object[] ua = replicaDeliveryRepository.getUnAssigned(input.getFromDate(), input.getToDate());
        if (ua != null && ua.length > 0 && ua[0] != null) {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusText("UnAssigned");
            deliveryStatusCount.setStatusId(12L);
            deliveryStatusCount.setStatusCount(((Number) ua[0]).longValue());
//            totalCount += ((Number) ua[0]).longValue();
            deliveryList.add(deliveryStatusCount);
        } else {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusText("UnAssigned");
            deliveryStatusCount.setStatusId(12L);
            deliveryStatusCount.setStatusCount(0L);
            deliveryList.add(deliveryStatusCount);
        }

        // Assigned_Count
        Object[] as = replicaDeliveryRepository.getAssigned(input.getFromDate(), input.getToDate());
        if (as != null && as.length > 0 && as[0] != null) {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusCount(((Number) as[0]).longValue());
            deliveryStatusCount.setStatusId(48L);
            deliveryStatusCount.setStatusText("Assigned");
            totalCount += ((Number) as[0]).longValue();
            deliveryList.add(deliveryStatusCount);
        } else {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusText("Assigned");
            deliveryStatusCount.setStatusId(48L);
            deliveryStatusCount.setStatusCount(0L);
            deliveryList.add(deliveryStatusCount);
        }

        // Accepted
        Object[] ap = replicaDeliveryRepository.getAccepted(input.getFromDate(), input.getToDate());
        if (ap != null && ap.length > 0 && ap[0] != null) {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusCount(((Number) ap[0]).longValue());
            deliveryStatusCount.setStatusId(50L);
            deliveryStatusCount.setStatusText("Accepted");
            totalCount += ((Number) ap[0]).longValue();
            deliveryList.add(deliveryStatusCount);
        } else {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusText("Accepted");
            deliveryStatusCount.setStatusId(50L);
            deliveryStatusCount.setStatusCount(0L);
            deliveryList.add(deliveryStatusCount);
        }

        // Delivered
        Object[] dd = replicaDeliveryRepository.getDelivered(input.getFromDate(), input.getToDate());
        if (dd != null && dd.length > 0 && dd[0] != null) {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusId(18L);
            deliveryStatusCount.setStatusCount(((Number) dd[0]).longValue());
            deliveryStatusCount.setStatusText("Delivered");
            totalCount += ((Number) dd[0]).longValue();
            deliveryList.add(deliveryStatusCount);
        } else {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusText("Delivered");
            deliveryStatusCount.setStatusId(18L);
            deliveryStatusCount.setStatusCount(0L);
            deliveryList.add(deliveryStatusCount);
        }

        // UnDelivered
        Object[] ud = replicaDeliveryRepository.getUnDelivered(input.getFromDate(), input.getToDate());
        if (ud != null && ud.length > 0 && ud[0] != null) {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusId(55L);
            deliveryStatusCount.setStatusCount(((Number) ud[0]).longValue());
            deliveryStatusCount.setStatusText("UnDelivered");
            totalCount += ((Number) ud[0]).longValue();
            deliveryList.add(deliveryStatusCount);
        } else {
            deliveryStatusCount = new DeliveryStatusCount();
            deliveryStatusCount.setStatusText("UnDelivered");
            deliveryStatusCount.setStatusId(55L);
            deliveryStatusCount.setStatusCount(0L);
            deliveryList.add(deliveryStatusCount);
        }

        DeliveryStatusCount totalStatusCount = new DeliveryStatusCount();
        totalStatusCount.setStatusText("Total");
        totalStatusCount.setStatusId(0L);
        totalStatusCount.setStatusCount(totalCount);
        deliveryList.add(totalStatusCount);

        return deliveryList;
    }


}
