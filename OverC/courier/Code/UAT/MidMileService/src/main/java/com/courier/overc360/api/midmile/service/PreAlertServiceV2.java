package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.repository.PreAlertRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.dto.DestinationDetailsImpl;
import com.courier.overc360.api.midmile.replica.repository.*;

import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.tomcat.jni.Thread;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class PreAlertServiceV2 {

    @Autowired
    ReplicaPieceDetailsRepository replicaPieceDetailsRepository;

    @Autowired
    ReplicaConsignmentEntityRepository replicaConsignmentEntityRepository;

    @Autowired
    PreAlertRepository preAlertRepository;

    @Autowired
    ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    ReplicaBondedManifestRepository replicaBondedManifestRepository;

    @Autowired
    ReplicaCcrRepository replicaCcrRepository;

    @Autowired
    ReplicaPreAlertRepository replicaPreAlertRepository;

    @Autowired
    ConsignmentStatusService consignmentStatusService;

    @Autowired
    ReplicaDestinationDetailsRepository replicaDestinationDetailsRepository;

    @Autowired
     NotificationService notificationService;
    /*================================================================================================================================================================*/

    //Decimal Format
    DecimalFormat decimalFormat = new DecimalFormat("#.###");


    // Create PreAlert
//    public List<PreAlert> createPreAlertService(List<PreAlert> preAlerts, String loginUserID) {
//        List<PreAlert> preAlertList = new ArrayList<>();
//
//        // Batch processing of preAlerts using streams
//        preAlerts.stream().forEach(dbPreAlert -> {
//            IKeyValuePair iKeyValuePair = replicaConsignmentEntityRepository.getDescription(dbPreAlert.getCompanyId());
//            Optional<PreAlert> preAlertOptional = preAlertRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
//                    dbPreAlert.getCompanyId(), iKeyValuePair.getLangId(), dbPreAlert.getPartnerId(),
//                    dbPreAlert.getPartnerMasterAirwayBill(), dbPreAlert.getPartnerHouseAirwayBill(), 0L);
//
//            if (preAlertOptional.isEmpty()) {
//                processNewPreAlert(dbPreAlert, iKeyValuePair, loginUserID, preAlertList);
//            } else {
//                log.info("Duplicate PreAlert Record: CompanyId - {}, PartnerId - {}, PartnerHouseAirwayBill - {}, PartnerMasterAirwayBill - {}",
//                        dbPreAlert.getCompanyId(), dbPreAlert.getPartnerId(), dbPreAlert.getPartnerHouseAirwayBill(), dbPreAlert.getPartnerMasterAirwayBill());
//            }
//        });
//
//        return preAlertList;
//    }

    // Create PreAlert
    public List<PreAlert> createPreAlertService(List<PreAlert> preAlerts, String loginUserID) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<PreAlert> preAlertList = Collections.synchronizedList(new ArrayList<>());

        // Create a thread pool with a fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Batch processing of preAlerts using streams and CompletableFuture
        preAlerts.stream().forEach(dbPreAlert -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                IKeyValuePair iKeyValuePair = replicaConsignmentEntityRepository.getDescription(dbPreAlert.getCompanyId());
                Optional<PreAlert> preAlertOptional = preAlertRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
                        dbPreAlert.getCompanyId(), iKeyValuePair.getLangId(), dbPreAlert.getPartnerId(),
                        dbPreAlert.getPartnerMasterAirwayBill(), dbPreAlert.getPartnerHouseAirwayBill(), 0L);

                if (preAlertOptional.isEmpty()) {
                    processNewPreAlert(dbPreAlert, iKeyValuePair, loginUserID, preAlertList);
                } else {
                    log.info("Duplicate PreAlert Record: CompanyId - {}, PartnerId - {}, PartnerHouseAirwayBill - {}, PartnerMasterAirwayBill - {}",
                            dbPreAlert.getCompanyId(), dbPreAlert.getPartnerId(), dbPreAlert.getPartnerHouseAirwayBill(), dbPreAlert.getPartnerMasterAirwayBill());
                }
            }, executor);
            futures.add(future);
        });

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();

        // Send Notification for PreAlert
//        notificationService.sendNotificationForPreAlertCreate(preAlertList.get(0).getCompanyId(), preAlertList.get(0).getLanguageId());
        return preAlertList;
    }

    // Process NewPreAlert
    private void processNewPreAlert(PreAlert dbPreAlert, IKeyValuePair iKeyValuePair, String loginUserID, List<PreAlert> preAlertList) {

        ConsignmentEntity consignment = consignmentEntityRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerHouseAirwayBillAndDeletionIndicator(
                iKeyValuePair.getLangId(), dbPreAlert.getCompanyId(), dbPreAlert.getPartnerId(), dbPreAlert.getPartnerHouseAirwayBill(), 0L);

        if (consignment == null) {
            throw new BadRequestException("Consignment doesn't exist for PartnerHouseAirwayBill - " + dbPreAlert.getPartnerHouseAirwayBill() +
                    "CompanyId - " + dbPreAlert.getCompanyId() + " LanguageId - " + dbPreAlert.getLanguageId() + " PartnerId - " + dbPreAlert.getPartnerId());
        }
        if(consignment.getConsignmentId() != null) {
            DestinationDetailsImpl destinationDetails = replicaDestinationDetailsRepository.getDestinationDetailsImpl(consignment.getConsignmentId());
            if(destinationDetails != null && destinationDetails.getPhone() !=  null) {
                dbPreAlert.setConsigneePhoneNo(destinationDetails.getPhone());
            }
        }

        IKeyValuePair currencyValuePair = Optional.ofNullable(replicaBondedManifestRepository.getToCurrencyValue(dbPreAlert.getCompanyId(), dbPreAlert.getCurrency()))
                .orElseThrow(() -> new BadRequestException("Currency value not found for the provided Exchange Rate Master: " + dbPreAlert.getCurrency()));
        Optional<IKeyValuePair> ikeyIata = Optional.ofNullable(replicaCcrRepository.getIataKd(dbPreAlert.getOriginCode(), dbPreAlert.getOrigin(),
                iKeyValuePair.getLangId(), dbPreAlert.getCompanyId()));
        if (currencyValuePair.getCurrencyValue() == null) {
            throw new BadRequestException("Currency value not found for the provided Exchange Rate Master: " + dbPreAlert.getCurrency());
        }

        dbPreAlert.setExchangeRate(Double.valueOf(currencyValuePair.getCurrencyValue()));
        dbPreAlert.setConsignmentLocalId(currencyValuePair.getCurrencyId());
        ikeyIata.ifPresent(iata -> dbPreAlert.setIata(iata.getIataKd()));
        setDefaultValues(dbPreAlert);
        calculateValues(dbPreAlert);
        Optional<String> statusText = consignmentEntityRepository.statusEventText(dbPreAlert.getCompanyId(), iKeyValuePair.getLangId(), "44");
        statusText.ifPresent(text -> {
            dbPreAlert.setHawbTypeDescription(text);
            dbPreAlert.setHawbTimeStamp(new Date());
        });

        Optional<IKeyValuePair> partnerNm = replicaPreAlertRepository.getPartnerName(iKeyValuePair.getLangId(), dbPreAlert.getCompanyId(),
                dbPreAlert.getPartnerId(), dbPreAlert.getPartnerHouseAirwayBill());

        PreAlert newPreAlert = new PreAlert();
        BeanUtils.copyProperties(dbPreAlert, newPreAlert, CommonUtils.getNullPropertyNames(dbPreAlert));
        newPreAlert.setHouseAirwayBill(consignment.getHouseAirwayBill());
        newPreAlert.setMasterAirwayBill(consignment.getMasterAirwayBill());
        newPreAlert.setLanguageId(iKeyValuePair.getLangId());
        newPreAlert.setLanguageDescription(iKeyValuePair.getLangDesc());
        newPreAlert.setCompanyName(iKeyValuePair.getCompanyDesc());
        newPreAlert.setCreatedBy(loginUserID);
        if (partnerNm.isPresent()) {
            IKeyValuePair ikey = partnerNm.get();
            newPreAlert.setPartnerName(ikey.getPartnerName() != null ? ikey.getPartnerName() : null);
            newPreAlert.setAddDestinationDetails(ikey.getAddDest() != null ? ikey.getAddDest() : null);
            newPreAlert.setAddOriginDetails(ikey.getAddOrigin() != null ? ikey.getAddOrigin() : null);
        }
        newPreAlert.setHubCode("2");
        newPreAlert.setHubName("Airport");
        newPreAlert.setCreatedOn(new Date());

        PreAlert savedPreAlert = preAlertRepository.save(newPreAlert);
        if (savedPreAlert != null) {
            updateConsignmentAndPieces(savedPreAlert, loginUserID);
            preAlertList.add(savedPreAlert);
        }
    }

    // Default Values
    private void setDefaultValues(PreAlert dbPreAlert) {
        //HAWB_TYPE
        dbPreAlert.setHawbType("EVENT");
        dbPreAlert.setHawbTypeId("3");
        if (dbPreAlert.getCustomsInsurance() == null) {
            dbPreAlert.setCustomsInsurance(1D);
        }
//        if (dbPreAlert.getDuty() == null) {
//            dbPreAlert.setDuty(5D);
//        }
    }


    // CalculatedValues
    private void calculateValues(PreAlert dbPreAlert) {
        if (dbPreAlert.getExchangeRate() != null && dbPreAlert.getConsignmentValue() != null) {

            double declaredValue = dbPreAlert.getConsignmentValue();
            double exchangeRate = dbPreAlert.getExchangeRate();

            double consignmentValue = 0.0;
            if (dbPreAlert.getConsignmentValueLocal() == null || dbPreAlert.getConsignmentValueLocal() == 0.0) {
                consignmentValue = declaredValue * exchangeRate;

                String formatConsignmentValue = decimalFormat.format(consignmentValue);
                dbPreAlert.setConsignmentValueLocal(Double.valueOf(formatConsignmentValue));
            } else {
                consignmentValue = dbPreAlert.getConsignmentValueLocal();
            }
//            if (dbPreAlert.getIata() != null) {
//                dbPreAlert.setAddIata(dbPreAlert.getIata() + consignmentValue);
//            }
            if(consignmentValue > 100) {
                double totalDuty = dbPreAlert.getConsignmentValueLocal() * 0.05;
                dbPreAlert.setCalculatedTotalDuty(Double.valueOf(decimalFormat.format(totalDuty)));
            }

//            if (dbPreAlert.getAddIata() != null) {
//                double addInsure = dbPreAlert.getAddIata() * 0.01;
//                dbPreAlert.setAddInsurance(Double.valueOf(decimalFormat.format(addInsure)));
//
//                if (dbPreAlert.getAddInsurance() != null) {
//                    double customsValue = dbPreAlert.getAddIata() + dbPreAlert.getAddInsurance();
//                    dbPreAlert.setCustomsValue(Double.valueOf(decimalFormat.format(customsValue)));
//
//                    if (customsValue > 100) {
//                        dbPreAlert.setDuty(5D);
//                        double totalDuty = customsValue * 0.05;
//                        dbPreAlert.setCalculatedTotalDuty(Double.valueOf(decimalFormat.format(totalDuty)));
//                    }
//                }
//            }
        }
    }


    // Update_Consignment_Status
    private void updateConsignmentAndPieces(PreAlert savedPreAlert, String loginUserID) {
        log.info("ConsignmentValues " + savedPreAlert);
        consignmentEntityRepository.updateConsignment(
                savedPreAlert.getCompanyId(), savedPreAlert.getLanguageId(), savedPreAlert.getPartnerId(),
                savedPreAlert.getPartnerHouseAirwayBill(), savedPreAlert.getPartnerMasterAirwayBill(), savedPreAlert.getConsignmentValue(),
                savedPreAlert.getExchangeRate(), savedPreAlert.getIata(), savedPreAlert.getConsignmentValueLocal(),
                savedPreAlert.getAddIata(), savedPreAlert.getAddInsurance(), savedPreAlert.getCustomsValue(), savedPreAlert.getCalculatedTotalDuty(),
                savedPreAlert.getCustomsInsurance());

        consignmentEntityRepository.updatePieceId(
                savedPreAlert.getCompanyId(), savedPreAlert.getLanguageId(), savedPreAlert.getPartnerId(),
                savedPreAlert.getPartnerHouseAirwayBill(), savedPreAlert.getPartnerMasterAirwayBill());

        List<String> pieceIds = replicaPieceDetailsRepository.getPieceId(
                savedPreAlert.getLanguageId(), savedPreAlert.getCompanyId(), savedPreAlert.getPartnerId(), savedPreAlert.getPartnerHouseAirwayBill());

        if (pieceIds != null && !pieceIds.isEmpty()) {
            pieceIds.forEach(pieceId -> {
                consignmentStatusService.insertConsignmentStatusRecord(
                        savedPreAlert.getLanguageId(), savedPreAlert.getLanguageDescription(), savedPreAlert.getCompanyId(),
                        savedPreAlert.getCompanyName(), pieceId, savedPreAlert.getMasterAirwayBill(), savedPreAlert.getHouseAirwayBill(),
                        savedPreAlert.getHawbType(), savedPreAlert.getHawbTypeId(), savedPreAlert.getHawbTypeDescription(),
                        savedPreAlert.getHawbTimeStamp(), savedPreAlert.getHawbType(), savedPreAlert.getHawbTypeId(),
                        savedPreAlert.getHawbTypeDescription(), savedPreAlert.getHawbTimeStamp(), loginUserID,
                        savedPreAlert.getPartnerHouseAirwayBill(), savedPreAlert.getPartnerMasterAirwayBill(), null,
                        savedPreAlert.getHubCode(), savedPreAlert.getHubName());
            });
        }
    }


}
