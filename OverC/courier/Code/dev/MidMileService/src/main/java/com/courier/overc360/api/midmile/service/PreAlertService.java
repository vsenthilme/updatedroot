package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlertDeleteInput;
import com.courier.overc360.api.midmile.primary.model.prealert.UpdatePreAlert;
import com.courier.overc360.api.midmile.primary.repository.BondedManifestRepository;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.midmile.primary.repository.PreAlertRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.prealert.*;
import com.courier.overc360.api.midmile.replica.repository.ReplicaBondedManifestRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaCcrRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaConsignmentEntityRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPieceDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPreAlertRepository;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PreAlertService {

    @Autowired
    PreAlertRepository preAlertRepository;

    @Autowired
    ReplicaPreAlertRepository replicaPreAlertRepository;

    @Autowired
    ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    ReplicaConsignmentEntityRepository replicaConsignmentEntityRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ReplicaCcrRepository replicaCcrRepository;

    @Autowired
    private ReplicaPieceDetailsRepository replicaPieceDetailsRepository;

    @Autowired
    private ConsignmentStatusService consignmentStatusService;

    @Autowired
    private ReplicaBondedManifestRepository replicaBondedManifestRepository;

    @Autowired
    private BondedManifestRepository bondedManifestRepository;

    private final ExecutorService executorService;

    public PreAlertService(ReplicaPreAlertRepository replicaPreAlertRepository) {
        this.replicaPreAlertRepository = replicaPreAlertRepository;
        this.executorService = Executors.newFixedThreadPool(10); // Adjust the pool size as needed
    }


    //Decimal Format
    DecimalFormat decimalFormat = new DecimalFormat("#.###");


    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @return
     */
    private PreAlert getPreAlert(String companyId, String languageId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill) {
        Optional<PreAlert> dbPreAlert = preAlertRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator
                (companyId, languageId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, 0L);
        if (dbPreAlert.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", partnerMasterAirwayBill - " + partnerMasterAirwayBill
                    + " and partnerHouseAirwayBill - " + partnerHouseAirwayBill + " doesn't exists";
            // Error Log
            createPreAlertLog(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbPreAlert.get();
    }

    /**
     * Create
     *
     * @param preAlert
     * @param loginUserID
     * @return
     */
    public List<PreAlert> createPreAlertService(List<PreAlert> preAlert, String loginUserID) {

        List<PreAlert> preAlertList = new ArrayList<>();
        for (PreAlert dbPreAlert : preAlert) {

            // Fetching the description for a company
            IKeyValuePair iKeyValuePair = replicaConsignmentEntityRepository.getDescription(dbPreAlert.getCompanyId());

            Optional<PreAlert> preAlertOptional =
                    preAlertRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator
                            (dbPreAlert.getCompanyId(), iKeyValuePair.getLangId(), dbPreAlert.getPartnerId(),
                                    dbPreAlert.getPartnerMasterAirwayBill(), dbPreAlert.getPartnerHouseAirwayBill(), 0L);

            if (preAlertOptional.isEmpty()) {

                ConsignmentEntity consignment =
                        consignmentEntityRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerHouseAirwayBillAndDeletionIndicator(
                                iKeyValuePair.getLangId(), dbPreAlert.getCompanyId(), dbPreAlert.getPartnerId(), dbPreAlert.getPartnerHouseAirwayBill(), 0L);

                log.info("companyId" + dbPreAlert.getCompanyId() + "languageId" + iKeyValuePair.getLangId() + "partner" + dbPreAlert.getPartnerId());
                if (consignment == null) {
                    throw new BadRequestException("Consignment Doesn't exist PartnerHouseAirwayBill - " + dbPreAlert.getPartnerHouseAirwayBill() + " CompanyId - " + dbPreAlert.getCompanyId() +
                            " LanguageId - " + iKeyValuePair.getLangId() + " PartnerId - " + dbPreAlert.getPartnerId());
                }

                // Get Iatakd
                IKeyValuePair optionalIKeyValuePair =
                        replicaBondedManifestRepository.getToCurrencyValue(dbPreAlert.getCompanyId(), dbPreAlert.getCurrency());
                IKeyValuePair ikeyIata = replicaCcrRepository.getIataKd(dbPreAlert.getOriginCode(), dbPreAlert.getOrigin(), iKeyValuePair.getLangId(), dbPreAlert.getCompanyId());

                //ExchangeCurrencyRate
                if (optionalIKeyValuePair != null && optionalIKeyValuePair.getCurrencyValue() != null) {

                    dbPreAlert.setExchangeRate(Double.valueOf(optionalIKeyValuePair.getCurrencyValue()));
                    dbPreAlert.setConsignmentLocalId(optionalIKeyValuePair.getCurrencyId());
                    if (ikeyIata != null && ikeyIata.getIataKd() != null) {
                        dbPreAlert.setIata(ikeyIata.getIataKd());
                    }
                }

                //Hardcode CustomsCurrency
                if (dbPreAlert.getCustomsInsurance() == null) {
                    dbPreAlert.setCustomsInsurance(1D);
                }
                //HardCode Duty
                if (dbPreAlert.getDuty() == null) {
                    dbPreAlert.setDuty(5D);
                }

                Double declaredValue = 0.0;
                Double exchangeRate = 0.0;
                Double consignmentValue = 0.0;

                //Consignment Value
                if (dbPreAlert.getExchangeRate() != null && dbPreAlert.getConsignmentValueLocal() != null) {
                    declaredValue = Double.valueOf(dbPreAlert.getConsignmentValue());
                    exchangeRate = dbPreAlert.getExchangeRate();
                    consignmentValue = declaredValue * exchangeRate;

                    String formatConsignmentValue = decimalFormat.format(consignmentValue);
                    dbPreAlert.setConsignmentValueLocal(Double.valueOf(formatConsignmentValue));
                }
                if (dbPreAlert.getIata() != null) {
                    Double iata = dbPreAlert.getIata();
                    dbPreAlert.setAddIata(iata + consignmentValue);
                }
                if (dbPreAlert.getAddIata() != null && dbPreAlert.getCustomsInsurance() != null) {
                    Double addIata = dbPreAlert.getAddIata();

                    Double addInsure = addIata * 0.01;
                    //Decimal Format
                    String formatInsurance = decimalFormat.format(addInsure);
                    dbPreAlert.setAddInsurance(Double.valueOf(formatInsurance));

                    if (dbPreAlert.getAddInsurance() != null) {

                        Double addInsurance = dbPreAlert.getAddInsurance();
                        Double customs = addIata + addInsurance;
                        //Decimal Format
                        String formatCustomsValue = decimalFormat.format(customs);
                        dbPreAlert.setCustomsValue(Double.valueOf(formatCustomsValue));

                        if (dbPreAlert.getDuty() != null) {
                            Double customsValue = dbPreAlert.getCustomsValue();

                            Double totalDuty = customsValue + (customsValue * 0.05);
                            //Decimal Format
                            String formatTotalDuty = decimalFormat.format(totalDuty);
                            dbPreAlert.setCalculatedTotalDuty(Double.valueOf(formatTotalDuty));
                        }
                    }
                }

                //HAWB_TYPE
                dbPreAlert.setHawbType("EVENT");
                dbPreAlert.setHawbTypeId("3");
                Optional<String> statusText = consignmentEntityRepository.statusEventText(dbPreAlert.getCompanyId(), iKeyValuePair.getLangId(), "44");
                if (statusText.isPresent()) {
                    String ikey = statusText.get();
                    dbPreAlert.setHawbTypeDescription(ikey);
                    dbPreAlert.setHawbTimeStamp(new Date());
                }

                // Get Partner Name from Consignment table
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
                newPreAlert.setHubCode("2");
                newPreAlert.setHubName("Airport");
                if (partnerNm.isPresent()) {
                    IKeyValuePair ikey = partnerNm.get();
                    newPreAlert.setPartnerName(ikey.getPartnerName() != null ? ikey.getPartnerName() : null);
                    newPreAlert.setAddDestinationDetails(ikey.getAddDest() != null ? ikey.getAddDest() : null);
                    newPreAlert.setAddOriginDetails(ikey.getAddOrigin() != null ? ikey.getAddOrigin() : null);
                }
                newPreAlert.setUpdatedBy(null);
                newPreAlert.setCreatedOn(new Date());
                newPreAlert.setUpdatedOn(null);
                PreAlert savedPreAlert = preAlertRepository.save(newPreAlert);

                if (savedPreAlert != null) {
                    //Update Consignment-entity
                    consignmentEntityRepository.updateConsignment(savedPreAlert.getCompanyId(), savedPreAlert.getLanguageId(),
                            savedPreAlert.getPartnerId(), savedPreAlert.getPartnerHouseAirwayBill(), savedPreAlert.getPartnerMasterAirwayBill(),
                            savedPreAlert.getConsignmentValue(), savedPreAlert.getExchangeRate(), savedPreAlert.getIata(),
                            savedPreAlert.getConsignmentValueLocal(), savedPreAlert.getAddIata(), savedPreAlert.getAddInsurance(),
                            savedPreAlert.getCustomsValue(), savedPreAlert.getCalculatedTotalDuty(), savedPreAlert.getCustomsInsurance());
                    log.info("consignment updated");

                    consignmentEntityRepository.updatePieceId(savedPreAlert.getCompanyId(), savedPreAlert.getLanguageId(),
                            savedPreAlert.getPartnerId(), savedPreAlert.getPartnerHouseAirwayBill(),
                            savedPreAlert.getPartnerMasterAirwayBill());
                    log.info("Piece updated");

                    List<String> piece = replicaPieceDetailsRepository.getPieceId(savedPreAlert.getLanguageId(), savedPreAlert.getCompanyId(),
                            savedPreAlert.getPartnerId(), savedPreAlert.getPartnerHouseAirwayBill());
                    if (piece != null) {
                        for (String pieceId : piece) {
                            consignmentStatusService.insertConsignmentStatusRecord(savedPreAlert.getLanguageId(), savedPreAlert.getLanguageDescription(),
                                    savedPreAlert.getCompanyId(), savedPreAlert.getCompanyName(), pieceId, savedPreAlert.getMasterAirwayBill(),
                                    savedPreAlert.getHouseAirwayBill(), savedPreAlert.getHawbType(), savedPreAlert.getHawbTypeId(), savedPreAlert.getHawbTypeDescription(),
                                    savedPreAlert.getHawbTimeStamp(), savedPreAlert.getHawbType(), savedPreAlert.getHawbTypeId(), savedPreAlert.getHawbTypeDescription(),
                                    savedPreAlert.getHawbTimeStamp(), loginUserID, savedPreAlert.getPartnerHouseAirwayBill(), savedPreAlert.getPartnerMasterAirwayBill(),
                                    null, savedPreAlert.getHubCode(), savedPreAlert.getHubName());
                        }
                    }
                }
                preAlertList.add(savedPreAlert);
            } else {
                log.info("PreAlert Record is Getting Duplicate CompanyId - " + dbPreAlert.getCompanyId() + " PartnerId " + dbPreAlert.getPartnerId() +
                        " PartnerHouseAirwayBill " + dbPreAlert.getPartnerHouseAirwayBill() + " PartnerMasterAirwayBill " + dbPreAlert.getPartnerMasterAirwayBill());
            }
        }
        return preAlertList;
    }

    /**
     * Update
     *
     * @param updatePreAlertList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public List<PreAlert> updatePreAlert(List<UpdatePreAlert> updatePreAlertList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<PreAlert> updatedPreAlertList = new ArrayList<>();
            for (UpdatePreAlert updatePreAlert : updatePreAlertList) {
                PreAlert dbPreAlert = getPreAlert(
                        updatePreAlert.getCompanyId(), updatePreAlert.getLanguageId(),
                        updatePreAlert.getPartnerId(), updatePreAlert.getPartnerMasterAirwayBill(),
                        updatePreAlert.getPartnerHouseAirwayBill());

                BeanUtils.copyProperties(updatePreAlert, dbPreAlert, CommonUtils.getNullPropertyNames(updatePreAlert));
                dbPreAlert.setUpdatedBy(loginUserID);
                dbPreAlert.setUpdatedOn(new Date());
                updatedPreAlertList.add(preAlertRepository.save(dbPreAlert));
            }
            return updatedPreAlertList;
        } catch (
                Exception e) {
            // Error Log
            createPreAlertLog3(updatePreAlertList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param deleteInputList
     * @param loginUserID
     * @throws IOException
     * @throws CsvException
     */
    public void deletePreAlert(List<PreAlertDeleteInput> deleteInputList, String loginUserID) throws IOException, CsvException {
        try {
            if (deleteInputList != null || !deleteInputList.isEmpty()) {
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                ExecutorService executor = Executors.newFixedThreadPool(10);

                deleteInputList.parallelStream().forEach(pa -> {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        PreAlert dbPreAlert = getPreAlert(pa.getCompanyId(), pa.getLanguageId(),
                                pa.getPartnerId(), pa.getPartnerMasterAirwayBill(), pa.getPartnerHouseAirwayBill());

                        if (dbPreAlert != null) {
                            dbPreAlert.setDeletionIndicator(1L);
                            dbPreAlert.setUpdatedBy(loginUserID);
                            dbPreAlert.setUpdatedOn(new Date());

                            // Delete BondedManifest
                            bondedManifestRepository.deleteBondedManifest(pa.getCompanyId(), pa.getLanguageId(),
                                    pa.getPartnerId(), pa.getPartnerMasterAirwayBill(), pa.getPartnerHouseAirwayBill(), loginUserID);
                            log.info("Bonded Manifest Delete PartnerMAB - {} PartnerHAB - {}", pa.getPartnerMasterAirwayBill(), pa.getPartnerHouseAirwayBill());
                            preAlertRepository.save(dbPreAlert);
                        }
                    }, executor);
                    futures.add(future);
                });
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                executor.shutdown();
            }
        } catch (Exception e) {
            // Error Log
            createPreAlertLog4(deleteInputList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    public List<ReplicaPreAlert> getAllPreAlert() {
        List<ReplicaPreAlert> preAlertList = replicaPreAlertRepository.findAll();
        preAlertList = preAlertList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return preAlertList;
    }

    /**
     * get
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @return
     */
    public ReplicaPreAlert getPreAlertReplica(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                                              String partnerHouseAirwayBill) {
        Optional<ReplicaPreAlert> dbPreAlert =
                replicaPreAlertRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator
                        (companyId, languageId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, 0L);
        if (dbPreAlert.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", partnerMasterAirwayBill - " + partnerMasterAirwayBill
                    + " and partnerHouseAirwayBill - " + partnerHouseAirwayBill + " doesn't exists";
            // Error Log
            createPreAlertLog(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbPreAlert.get();
    }

    /**
     * Find
     *
     * @param findPreAlert
     * @return
     */
    public List<ReplicaPreAlertProjection> findPreAlert(FindPreAlert findPreAlert) throws ExecutionException, InterruptedException {
        return findPreAlertAsync(findPreAlert).get();
    }

    private CompletableFuture<List<ReplicaPreAlertProjection>> findPreAlertAsync(FindPreAlert findPreAlert) {
        try {
            if (findPreAlert.getFromDate() != null && findPreAlert.getToDate() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearchV2(findPreAlert.getFromDate(),
                        findPreAlert.getToDate());
                findPreAlert.setFromDate(dates[0]);
                findPreAlert.setToDate(dates[1]);
            }
        } catch (ParseException e) {
            throw new BadRequestException(e.toString());
        }
        return CompletableFuture.supplyAsync(() -> {
            try {
                return replicaPreAlertRepository.findPreAlertWithHawb(
                        findPreAlert.getCompanyId(),
                        findPreAlert.getLanguageId(),
                        findPreAlert.getPartnerMasterAirwayBill(),
                        findPreAlert.getPartnerHouseAirwayBill(),
                        findPreAlert.getPartnerId(),
                        findPreAlert.getHsCode(),
                        findPreAlert.getInvoice(),
                        findPreAlert.getDdpInvoiceNo(),
                        findPreAlert.getSubCustomerId(),
                        findPreAlert.getFromDate(),
                        findPreAlert.getToDate());
            } catch (Exception e) {
                // Handle exception, possibly log it or rethrow
                throw new RuntimeException("Failed to fetch PreAlerts with Qry ", e);
            }
        }, executorService);
    }


    //==========================================PreAlert_ErrorLog================================================
    private void createPreAlertLog(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                                   String partnerHouseAirwayBill, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerMasterAirwayBill);
        errorLog.setMethod("Exception thrown in getPreAlert");
        errorLog.setReferenceField1(partnerHouseAirwayBill);
        errorLog.setReferenceField2(partnerId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createPreAlertLog3(List<UpdatePreAlert> updatePreAlertList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdatePreAlert updatePreAlert : updatePreAlertList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(updatePreAlert.getLanguageId());
            errorLog.setCompanyId(updatePreAlert.getCompanyId());
            errorLog.setRefDocNumber(updatePreAlert.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in updatePreAlert");
            errorLog.setReferenceField1(updatePreAlert.getPartnerId());
            errorLog.setReferenceField2(updatePreAlert.getPartnerHouseAirwayBill());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }


    private void createPreAlertLog4(List<PreAlertDeleteInput> deleteInputList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (PreAlertDeleteInput deleteInput : deleteInputList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(deleteInput.getLanguageId());
            errorLog.setCompanyId(deleteInput.getCompanyId());
            errorLog.setRefDocNumber(deleteInput.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in deletePreAlert");
            errorLog.setReferenceField1(deleteInput.getPartnerId());
            errorLog.setReferenceField2(deleteInput.getPartnerHouseAirwayBill());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }


    /**
     *
     * @param findPreAlert
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<PreAlertResponse> findPreAlertNew(FindPreAlert findPreAlert) throws ExecutionException, InterruptedException {

        log.info(" PreAlert Input <---------------------> " + findPreAlert);
        if (findPreAlert.getFromDate() != null && findPreAlert.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(findPreAlert.getFromDate(),
                    findPreAlert.getToDate());
            findPreAlert.setFromDate(dates[0]);
            findPreAlert.setToDate(dates[1]);
        }
        return replicaPreAlertRepository.findPreAlertNew(findPreAlert.getCompanyId(),
                findPreAlert.getLanguageId(),
                findPreAlert.getPartnerMasterAirwayBill(),
                findPreAlert.getPartnerHouseAirwayBill(),
                findPreAlert.getPartnerId(),
                findPreAlert.getHsCode(),
                findPreAlert.getInvoice(),
                findPreAlert.getDdpInvoiceNo(),
                findPreAlert.getSubCustomerId(),
                findPreAlert.getFromDate(),
                findPreAlert.getToDate());
    }

}


