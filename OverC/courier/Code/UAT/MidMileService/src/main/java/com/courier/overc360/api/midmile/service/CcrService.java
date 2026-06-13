package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.ccr.*;
import com.courier.overc360.api.midmile.primary.model.console.Console;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.repository.*;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.ccr.FindCcr;
import com.courier.overc360.api.midmile.replica.model.ccr.ReplicaCcr;
import com.courier.overc360.api.midmile.replica.model.ccr.UpdateCCR;
import com.courier.overc360.api.midmile.replica.repository.ReplicaCcrRepository;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CcrService {

    @Autowired
    private CcrRepository ccrRepository;

    @Autowired
    private ReplicaCcrRepository replicaCcrRepository;

    @Autowired
    private BondedManifestRepository bondedManifestRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    ConsignmentStatusService consignmentStatusService;

    @Autowired
    ConsoleRepository consoleRepository;

    @Autowired
    ConsoleService consoleService;

    @Autowired
    PreAlertRepository preAlertRepository;

    @Autowired
    CustomsCostingService customsCostingService;

    @Autowired
    CustomsCostingRepository customsCostingRepository;

    //Decimal Format
    DecimalFormat decimalFormat = new DecimalFormat("#.###");
    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    /**
     * Get Ccr
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @param consoleId
     * @return
     */
    public Ccr getCcr(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                      String partnerHouseAirwayBill, String consoleId, String ccrId) {
        Optional<Ccr> dbCcr =
                ccrRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndCcrIdAndDeletionIndicator(
                        languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, 0L);
        if (dbCcr.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", masterAirwayBill - " + partnerMasterAirwayBill + ", houseAirwayBill - "
                    + partnerHouseAirwayBill + " , consoleId - " + consoleId + ", and ccrId - " + ccrId + " doesn't exists";
            // Error Log
            createCcrLog(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCcr.get();
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @param consoleId
     * @param ccrId
     * @param pieceId
     * @return
     */
    public Ccr getCcr(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                      String partnerHouseAirwayBill, String consoleId, String ccrId, String pieceId) {
        Optional<Ccr> dbCcr =
                ccrRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndCcrIdAndPieceIdAndDeletionIndicator(
                        languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, pieceId, 0L);
        if (dbCcr.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", masterAirwayBill - " + partnerMasterAirwayBill + ", houseAirwayBill - "
                    + partnerHouseAirwayBill + " , consoleId - " + consoleId + ", and ccrId - " + ccrId + ", and pieceId - " + pieceId + " doesn't exists";
            // Error Log
            createCcrLog1(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, pieceId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCcr.get();
    }


    // Create Console CCR
//    @Transactional
//    public List<Ccr> createConsoleCcr(List<Console> addCcrList, String loginUserID) throws IOException, CsvException {
//        ExecutorService executor = Executors.newFixedThreadPool(10);  // Use a fixed thread pool
//        List<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();
//        List<Ccr> createdCcrList = Collections.synchronizedList(new ArrayList<>());
//
//        try {
//            String CCR_ID = numberRangeService.getNextNumberRange("CCRID");
//            log.info("next Value from NumberRange for CCR_ID : {}", CCR_ID);
//
//            addCcrList.stream().forEach(addCcr -> {
//                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                    Optional<Ccr> duplicateConsole = ccrRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndConsoleIdAndDeletionIndicator(
//                            addCcr.getCompanyId(), addCcr.getLanguageId(), addCcr.getPartnerId(), addCcr.getPartnerMasterAirwayBill(), addCcr.getPartnerHouseAirwayBill(),
//                            addCcr.getPieceId(), addCcr.getConsoleId(), 0L);
//
//                    if (duplicateConsole.isEmpty()) {
//                        Ccr newCcr = new Ccr();
//                        BeanUtils.copyProperties(addCcr, newCcr, CommonUtils.getNullPropertyNames(addCcr));
//                        newCcr.setCcrId(CCR_ID);
//                        newCcr.setDeletionIndicator(0L);
//                        newCcr.setCreatedBy(loginUserID);
//                        newCcr.setCreatedOn(new Date());
//                        newCcr.setUpdatedBy(loginUserID);
//                        newCcr.setUpdatedOn(new Date());
//
//                        Ccr createdCcr = ccrRepository.save(newCcr);
//                        createdCcrList.add(createdCcr);
//                        try {
//                            int noOfRecordsUpdated = consoleRepository.updateCCRIdInConsoleTbl(addCcr.getConsoleId(),
//                                    CCR_ID, addCcr.getLanguageId(), addCcr.getCompanyId());
//                            log.info("Consoles -- consoleId-{} updated with ccrId-{} : count --> {}", addCcr.getConsoleId(), CCR_ID, noOfRecordsUpdated);
//                        } catch (Exception e) {
//                            log.error("Error in updating CCR_ID - {} for consoleId - {} : {}", CCR_ID, addCcr.getConsoleId(), e.getMessage());
//                        }
//                    }
//                }, executor);
//                futures.add(future);
//            });
//        } catch (Exception e) {
//            createCcrLog5(addCcrList, e.toString());
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        // Wait for all the futures to complete
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//        // Shut down the executor service
//        executor.shutdown();
//        return createdCcrList;
//    }


    /**
     * Create ConsoleCcr
     *
     * @param addCcrList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public List<Ccr> createConsoleCcr(List<Console> addCcrList, String loginUserID) throws IOException, CsvException {
        ExecutorService executor = Executors.newFixedThreadPool(10);  // Use a fixed thread pool
        List<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();
        List<Ccr> createdCcrList = Collections.synchronizedList(new ArrayList<>());

        try {
            addCcrList.stream().forEach(addCcr -> {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    Optional<Ccr> duplicateConsole = ccrRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndConsoleIdAndDeletionIndicator(
                            addCcr.getCompanyId(), addCcr.getLanguageId(), addCcr.getPartnerId(), addCcr.getPartnerMasterAirwayBill(), addCcr.getPartnerHouseAirwayBill(),
                            addCcr.getPieceId(), addCcr.getConsoleId(), 0L);

                    if (duplicateConsole.isEmpty()) {
                        Ccr newCcr = new Ccr();
                        BeanUtils.copyProperties(addCcr, newCcr, CommonUtils.getNullPropertyNames(addCcr));
                        newCcr.setDeletionIndicator(0L);
                        newCcr.setCreatedBy(loginUserID);
                        newCcr.setCreatedOn(new Date());
                        newCcr.setUpdatedBy(loginUserID);
                        newCcr.setUpdatedOn(new Date());

                        Ccr createdCcr = ccrRepository.save(newCcr);
                        createdCcrList.add(createdCcr);
                    }
                }, executor);
                futures.add(future);
            });
        } catch (Exception e) {
            createCcrLog5(addCcrList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // Wait for all the futures to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        // Shut down the executor service
        executor.shutdown();
        return createdCcrList;
    }


    /**
     * Create Ccr
     *
     * @param addCcrList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<Ccr> createCcr(List<AddCcr> addCcrList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<Ccr> createdCcrList = new ArrayList<>();

            for (AddCcr addCcr : addCcrList) {

                boolean duplicate = replicaCcrRepository.duplicateExists(
                        addCcr.getLanguageId(), addCcr.getCompanyId(),
                        addCcr.getPartnerId(), addCcr.getPartnerMasterAirwayBill(),
                        addCcr.getPartnerHouseAirwayBill(), addCcr.getConsoleId()) == 1;

                //Check IsExempted status and throw error
                if (addCcr.getIsExempted().equalsIgnoreCase("Yes")) {
                    if (addCcr.getExemptionFor() == null || addCcr.getExemptionBeneficiary() == null || addCcr.getExemptionReference() == null) {
                        throw new BadRequestException(" ExemptionFor - " + addCcr.getExemptionFor() + " , ExemptionBeneficiary - "
                                + addCcr.getExemptionBeneficiary() + " and ExemptionReference - " + addCcr.getExemptionReference() + " is mandatory ");
                    }
                } else if (addCcr.getIsExempted().equalsIgnoreCase("No")) {
                    if (addCcr.getConsigneeName() == null || addCcr.getInvoiceDate() == null || addCcr.getInvoiceType() == null ||
                            addCcr.getCurrency() == null || addCcr.getInvoiceSupplierName() == null || addCcr.getFreightCurrency() == null ||
                            addCcr.getFreightCharges() == null || addCcr.getCountryOfOrigin() == null) {

                        throw new BadRequestException(" ConsigneeName -  " + addCcr.getConsigneeName() + ",InvoiceDate - " + addCcr.getInvoiceDate() +
                                ", InvoiceType - " + addCcr.getInvoiceType() + ", Currency - " + addCcr.getCurrency() + ", InvoiceSupplierName - " + addCcr.getInvoiceSupplierName() +
                                ", FreightCurrency - " + addCcr.getFreightCurrency() + ", FreightCharges - " + addCcr.getFreightCharges() +
                                " and CountryOfOrigin - " + addCcr.getCountryOfOrigin() + " is mandatory ");
                    }
                }

                //Get Iatakd
                IKeyValuePair iataData = ccrRepository.getIataKd(addCcr.getCountryOfOrigin(),
                        addCcr.getLanguageId(), addCcr.getCompanyId());

                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with given values : houseAirwayBill - " + addCcr.getPartnerHouseAirwayBill());
                }

                Ccr newCcr = new Ccr();
                BeanUtils.copyProperties(addCcr, newCcr, CommonUtils.getNullPropertyNames(addCcr));

                String STATUS_ID = "2 - Ccr Created";
                String NUM_RAN_OBJ = "CCR_ID";
                String CCR_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for CCR_ID : " + CCR_ID);
                newCcr.setCcrId(CCR_ID);

                IKeyValuePair lAndCDesc = ccrRepository.getLAndCDescription(
                        addCcr.getLanguageId(), addCcr.getCompanyId());

                if (lAndCDesc != null) {
                    newCcr.setLanguageDescription(lAndCDesc.getLangDesc());
                    newCcr.setCompanyName(lAndCDesc.getCompanyDesc());
                }
                if (iataData != null) {
                    newCcr.setIata(iataData.getIataKd() != null ? iataData.getIataKd() : 0.0);
                }
                newCcr.setDeletionIndicator(0L);
                newCcr.setCreatedBy(loginUserID);
                newCcr.setCreatedOn(new Date());
                newCcr.setUpdatedBy(loginUserID);
                newCcr.setUpdatedOn(new Date());

                Ccr createdCcr = ccrRepository.save(newCcr);
                createdCcrList.add(createdCcr);
            }
            return createdCcrList;
        } catch (Exception e) {
            // Error Log
            createCcrLog2(addCcrList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Ccr
     *
     * @param updateCcrList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<Ccr> updateCcr(List<UpdateCcr> updateCcrList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<Ccr> updatedCcrList = new ArrayList<>();

            for (UpdateCcr updateCcr : updateCcrList) {

                Ccr dbCcr = getCcr(
                        updateCcr.getLanguageId(), updateCcr.getCompanyId(),
                        updateCcr.getPartnerId(), updateCcr.getPartnerMasterAirwayBill(),
                        updateCcr.getPartnerHouseAirwayBill(), updateCcr.getConsoleId(),
                        updateCcr.getCcrId(), updateCcr.getPieceId());


                BeanUtils.copyProperties(updateCcr, dbCcr, CommonUtils.getNullPropertyNames(updateCcr));
                dbCcr.setUpdatedBy(loginUserID);
                dbCcr.setUpdatedOn(new Date());


                Ccr updatedCcr = ccrRepository.save(dbCcr);
                updatedCcrList.add(updatedCcr);
            }
            return updatedCcrList;
        } catch (Exception e) {
            // Error Log
            createCcrLog3(updateCcrList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Ccr
     *
     * @param deleteInputList
     * @param loginUserID
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public void deleteCcr(List<CcrDeleteInput> deleteInputList, String loginUserID) throws IOException, CsvException {
        try {
            if (deleteInputList != null || !deleteInputList.isEmpty()) {
                for (CcrDeleteInput deleteInput : deleteInputList) {

                    Ccr dbCcr = getCcr(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                            deleteInput.getPartnerId(), deleteInput.getPartnerMasterAirwayBill(), deleteInput.getPartnerHouseAirwayBill(),
                            deleteInput.getConsoleId(), deleteInput.getCcrId(), deleteInput.getPieceId());

                    if (dbCcr != null) {
                        dbCcr.setDeletionIndicator(1L);
                        dbCcr.setUpdatedBy(loginUserID);
                        dbCcr.setUpdatedOn(new Date());


                        ccrRepository.save(dbCcr);
                    }
                }
            }
        } catch (Exception e) {
            // Error Log
            createCcrLog4(deleteInputList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    /**
     * Get All CcrDetails
     *
     * @return
     */
    public List<ReplicaCcr> getAllCcr() {
        List<ReplicaCcr> ccrList = replicaCcrRepository.findAll();
        ccrList = ccrList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return ccrList;
    }

    /**
     * Get Ccr - Replica
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @param ccrId
     * @param consoleId
     * @return
     */
    public ReplicaCcr getCcrReplica(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                                    String partnerHouseAirwayBill, String consoleId, String ccrId) {
        Optional<ReplicaCcr> dbCcr =
                replicaCcrRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndCcrIdAndDeletionIndicator(
                        languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, 0L);
        if (dbCcr.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", masterAirwayBill - " + partnerMasterAirwayBill
                    + ", houseAirwayBill - " + partnerHouseAirwayBill + " ,consoleId - " + consoleId + " , and ccrId - " + ccrId + " doesn't exists";
            // Error Log
            createCcrLog(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCcr.get();
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @param consoleId
     * @param ccrId
     * @param pieceId
     * @return
     */
    public ReplicaCcr getCcrReplica(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                                    String partnerHouseAirwayBill, String consoleId, String ccrId, String pieceId) {
        Optional<ReplicaCcr> dbCcr =
                replicaCcrRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndCcrIdAndPieceIdAndDeletionIndicator(
                        languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, pieceId, 0L);
        if (dbCcr.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", masterAirwayBill - " + partnerMasterAirwayBill
                    + ", houseAirwayBill - " + partnerHouseAirwayBill + " ,consoleId - " + consoleId +
                    " , and ccrId - " + ccrId + " , and pieceId - " + pieceId + " doesn't exists";
            // Error Log
            createCcrLog1(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, pieceId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCcr.get();
    }

    /**
     * Find Ccr
     *
     * @param findCcr
     * @return
     * @throws Exception
     */
//    public List<ReplicaCcr> findCcr(FindCcr findCcr) throws Exception {
//
//        CcrSpecification spec = new CcrSpecification(findCcr);
//        List<ReplicaCcr> results = replicaCcrRepository.findAll(spec);
//        log.info("found Ccr  --> {}", results);
//        return results;
//    }
    public List<ReplicaCcr> findCcr(FindCcr findCcr) throws Exception {

        log.info("given Params for find -- > {}", findCcr);
        List<ReplicaCcr> ccrList = replicaCcrRepository.findCCRsWithQry(
                findCcr.getLanguageId(), findCcr.getCompanyId(), findCcr.getPartnerId(), findCcr.getPartnerMasterAirwayBill(),
                findCcr.getPartnerHouseAirwayBill(), findCcr.getCcrId(), findCcr.getConsoleId());
//        log.info("found CCRs  --> {}", ccrList);
        return ccrList;
    }


    //==========================================Ccr_ErrorLog================================================
    private void createCcrLog(String languageId, String companyId, String partnerId, String masterAirwayBill,
                              String houseAirwayBill, String consoleId, String ccrId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(masterAirwayBill);
        errorLog.setMethod("Exception thrown in getCcr");
        errorLog.setReferenceField1(houseAirwayBill);
        errorLog.setReferenceField2(partnerId);
        errorLog.setReferenceField3(ccrId);
        errorLog.setReferenceField4(consoleId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCcrLog1(String languageId, String companyId, String partnerId, String masterAirwayBill,
                               String houseAirwayBill, String consoleId, String ccrId, String pieceId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(masterAirwayBill);
        errorLog.setMethod("Exception thrown in getCcr");
        errorLog.setReferenceField1(houseAirwayBill);
        errorLog.setReferenceField2(partnerId);
        errorLog.setReferenceField3(ccrId);
        errorLog.setReferenceField4(consoleId);
        errorLog.setReferenceField5(pieceId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }


    private void createCcrLog2(List<AddCcr> addCcrList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (AddCcr addCcr : addCcrList) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addCcr.getLanguageId());
            errorLog.setCompanyId(addCcr.getCompanyId());
            errorLog.setRefDocNumber(addCcr.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in createCcr");
            errorLog.setReferenceField1(addCcr.getPartnerId());
            errorLog.setReferenceField2(addCcr.getPartnerHouseAirwayBill());
            errorLog.setReferenceField4(addCcr.getConsoleId());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }


    private void createCcrLog3(List<UpdateCcr> updateCcrList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateCcr updateCcr : updateCcrList) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(updateCcr.getLanguageId());
            errorLog.setCompanyId(updateCcr.getCompanyId());
            errorLog.setRefDocNumber(updateCcr.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in updateCcr");
            errorLog.setReferenceField1(updateCcr.getPartnerId());
            errorLog.setReferenceField2(updateCcr.getPartnerHouseAirwayBill());
            errorLog.setReferenceField3(updateCcr.getCcrId());
            errorLog.setReferenceField5(updateCcr.getCustomsCcrNo());
            errorLog.setReferenceField6(updateCcr.getConsoleId());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createCcrLog4(List<CcrDeleteInput> deleteInputList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (CcrDeleteInput deleteInput : deleteInputList) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(deleteInput.getLanguageId());
            errorLog.setCompanyId(deleteInput.getCompanyId());
            errorLog.setRefDocNumber(deleteInput.getPartnerHouseAirwayBill());
            errorLog.setMethod("Exception thrown in deleteCcr");
            errorLog.setReferenceField1(deleteInput.getPartnerId());
            errorLog.setReferenceField2(deleteInput.getPartnerMasterAirwayBill());
            errorLog.setReferenceField3(deleteInput.getCcrId());
            errorLog.setReferenceField4(deleteInput.getCustomsCcrNo());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createCcrLog5(List<Console> addCcrList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (Console addCcr : addCcrList) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addCcr.getLanguageId());
            errorLog.setCompanyId(addCcr.getCompanyId());
            errorLog.setRefDocNumber(addCcr.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in createCcr");
            errorLog.setReferenceField1(addCcr.getPartnerId());
            errorLog.setReferenceField2(addCcr.getPartnerHouseAirwayBill());
            errorLog.setReferenceField4(addCcr.getConsoleId());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    //==========================================update CCR from Bayan PDF================================================

    /**
     * Get Ccr - Replica
     *
     * @param ccrId
     * @return
     */
    public List<Ccr> getCcr(String ccrId) {
        List<Ccr> dbCcrList = ccrRepository.findAllByCcrIdAndDeletionIndicator(ccrId, 0L);
        return dbCcrList;
    }

//    /**
//     * @param updateCcrList
//     * @return
//     * @throws IOException
//     * @throws CsvException
//     */
//    @Transactional
//    public String updateCCRFromBayan(List<UpdateCCR> updateCcrList) throws IOException, CsvException {
//        try {
//            log.info("Bayan Update CCR Initiated: " + updateCcrList.size());
//            List<Ccr> updatedCcrList = new ArrayList<>();
//            for (UpdateCCR updateCcr : updateCcrList) {
//                List<Ccr> dbCcrList = getCcr(updateCcr.getCcrId());
//                if (dbCcrList != null && !dbCcrList.isEmpty()) {
//                    for (Ccr dbCcr : dbCcrList) {
//                        String customsConsignmentValue = null;
//                        String dbConsignmentValue = null;
//                        boolean pass = false;
//                        if (updateCcr.getConsignmentValue() != null) {
//                            if (updateCcr.getConsignmentValue().contains(".")) {
//                                String ccv = updateCcr.getConsignmentValue();
//                                String ccv1 = null;
//                                if ((ccv.substring(ccv.indexOf("."), ccv.length()).length() > 4)) {
//                                    ccv1 = ccv.substring(0, ccv.indexOf(".") + 4);
//                                } else {
//                                    ccv1 = ccv;
//                                }
//                                DecimalFormat decimalFormat = new DecimalFormat("0.#####");
//                                customsConsignmentValue = decimalFormat.format(Double.valueOf(ccv1));
//                            } else {
//                                customsConsignmentValue = updateCcr.getConsignmentValue();
//                            }
//                        }
//                        if (dbCcr.getConsignmentValue() != null) {
//                            if (dbCcr.getConsignmentValue().contains(".")) {
//                                String dbcv = dbCcr.getConsignmentValue();
//                                String dbcv1 = null;
//                                if ((dbcv.substring(dbcv.indexOf("."), dbcv.length()).length() > 4)) {
//                                    dbcv1 = dbcv.substring(0, dbcv.indexOf(".") + 4);
//                                } else {
//                                    dbcv1 = dbcv;
//                                }
//                                DecimalFormat decimalFormat = new DecimalFormat("0.#####");
//                                dbConsignmentValue = decimalFormat.format(Double.valueOf(dbcv1));
//                            } else {
//                                dbConsignmentValue = dbCcr.getConsignmentValue();
//                            }
//                        }
//                        if (customsConsignmentValue != null && dbConsignmentValue != null) {
//                            pass = customsConsignmentValue.equalsIgnoreCase(dbConsignmentValue);
//                        }
//                        if (dbCcr.getCcrId().equalsIgnoreCase(updateCcr.getCcrId()) &&
//                                dbCcr.getHsCode().equalsIgnoreCase(updateCcr.getHsCode()) &&
//                                pass) {
//                            log.info("Updating CCR from Pdf: " + updateCcr);
//                            dbCcr.setCustomsCcrNo(updateCcr.getCustomsCcrNo());
//                            dbCcr.setPrimaryDo(updateCcr.getPrimaryDo());
//                            dbCcr.setCustomsKd(updateCcr.getCustomsKd());
//                            dbCcr.setTotalDuty(updateCcr.getTotalDuty());
//                            dbCcr.setUpdatedBy("Bayan");
//                            dbCcr.setUpdatedOn(new Date());
//
//                            Ccr updatedCcr = ccrRepository.save(dbCcr);
//                            updatedCcrList.add(updatedCcr);
//                        }
//
//                        List<Console> dbConsoleList = consoleService.getConsole(dbCcr.getConsoleId());
//
//                        for (Console console : dbConsoleList) {
//                            String consoleConsignmentValue = null;
//                            String dbConsoleConsignmentValue = null;
//                            boolean consolePass = false;
//                            if (updateCcr.getConsignmentValue() != null) {
//                                if (updateCcr.getConsignmentValue().contains(".")) {
//                                    String ccv = updateCcr.getConsignmentValue();
//                                    String ccv1 = null;
//                                    if ((ccv.substring(ccv.indexOf("."), ccv.length()).length() > 4)) {
//                                        ccv1 = ccv.substring(0, ccv.indexOf(".") + 4);
//                                    } else {
//                                        ccv1 = ccv;
//                                    }
//                                    DecimalFormat decimalFormat = new DecimalFormat("0.#####");
//                                    consoleConsignmentValue = decimalFormat.format(Double.valueOf(ccv1));
//                                } else {
//                                    consoleConsignmentValue = updateCcr.getConsignmentValue();
//                                }
//                            }
//                            if (console.getConsignmentValue() != null) {
//                                if (console.getConsignmentValue().contains(".")) {
//                                    String dbcv = console.getConsignmentValue();
//                                    String dbcv1 = null;
//                                    if ((dbcv.substring(dbcv.indexOf("."), dbcv.length()).length() > 4)) {
//                                        dbcv1 = dbcv.substring(0, dbcv.indexOf(".") + 4);
//                                    } else {
//                                        dbcv1 = dbcv;
//                                    }
//                                    DecimalFormat decimalFormat = new DecimalFormat("0.#####");
//                                    dbConsoleConsignmentValue = decimalFormat.format(Double.valueOf(dbcv1));
//                                } else {
//                                    dbConsoleConsignmentValue = console.getConsignmentValue();
//                                }
//                            }
//                            if (consoleConsignmentValue != null && dbConsoleConsignmentValue != null) {
//                                consolePass = consoleConsignmentValue.equalsIgnoreCase(dbConsoleConsignmentValue);
//                            }
//                            if (console.getHsCode().equalsIgnoreCase(updateCcr.getHsCode()) && consolePass) {
//                                log.info("Updating CONSOLE from Pdf: " + updateCcr);
//                                console.setCustomsCcrNo(updateCcr.getCustomsCcrNo());
//                                console.setPrimaryDo(updateCcr.getPrimaryDo());
//                                console.setCustomsKd(updateCcr.getCustomsKd());
//                                console.setTotalDuty(updateCcr.getTotalDuty());
//                                console.setUpdatedBy("Bayan");
//                                console.setUpdatedOn(new Date());
//                                consoleRepository.save(console);
//
//                                //Update PreAlert
//                                preAlertRepository.updatePreAlert(console.getCompanyId(), console.getLanguageId(),
//                                        console.getPartnerId(), console.getPartnerHouseAirwayBill(), console.getPartnerMasterAirwayBill(),
//                                        updateCcr.getTotalDuty());
//                            }
//                        }
//                    }
//                }
//            }
//            return "Update Success";
//        } catch (
//                Exception e) {
//            // Error Log
//            createCcrLog(updateCcrList, e.toString());
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//    }

//    /**
//     * @param updateCcrList
//     * @return
//     * @throws IOException
//     * @throws CsvException
//     */
//    @Transactional
//    public String updateCCRFromBayan(List<UpdateCCR> updateCcrList) throws IOException, CsvException {
//        try {
//            log.info("Bayan Update CCR Initiated: " + updateCcrList.size());
//            List<Ccr> updatedCcrList = new ArrayList<>();
//            for (UpdateCCR updateCcr : updateCcrList) {
//                List<Ccr> dbCcrList = getCcr(updateCcr.getCcrId());
//                if (dbCcrList != null && !dbCcrList.isEmpty()) {
//                    for (Ccr dbCcr : dbCcrList) {
//                        String customsConsignmentValue = null;
//                        String dbConsignmentValue = null;
//                        boolean pass = false;
//                        if (updateCcr.getConsignmentValue() != null) {
//                            if (updateCcr.getConsignmentValue().contains(".")) {
//                                String ccv = updateCcr.getConsignmentValue();
//                                String ccv1 = null;
//                                if ((ccv.substring(ccv.indexOf("."), ccv.length()).length() > 4)) {
//                                    ccv1 = ccv.substring(0, ccv.indexOf(".") + 4);
//                                } else {
//                                    ccv1 = ccv;
//                                }
//                                DecimalFormat decimalFormat = new DecimalFormat("0.#####");
//                                customsConsignmentValue = decimalFormat.format(Double.valueOf(ccv1));
//                            } else {
//                                customsConsignmentValue = updateCcr.getConsignmentValue();
//                            }
//                        }
//                        if (dbCcr.getConsignmentValue() != null) {
//                            if (dbCcr.getConsignmentValue().contains(".")) {
//                                String dbcv = dbCcr.getConsignmentValue();
//                                String dbcv1 = null;
//                                if ((dbcv.substring(dbcv.indexOf("."), dbcv.length()).length() > 4)) {
//                                    dbcv1 = dbcv.substring(0, dbcv.indexOf(".") + 4);
//                                } else {
//                                    dbcv1 = dbcv;
//                                }
//                                DecimalFormat decimalFormat = new DecimalFormat("0.#####");
//                                dbConsignmentValue = decimalFormat.format(Double.valueOf(dbcv1));
//                            } else {
//                                dbConsignmentValue = dbCcr.getConsignmentValue();
//                            }
//                        }
//                        if (customsConsignmentValue != null && dbConsignmentValue != null) {
//                            pass = customsConsignmentValue.equalsIgnoreCase(dbConsignmentValue);
//                        }
//                        if (dbCcr.getCcrId().equalsIgnoreCase(updateCcr.getCcrId()) &&
//                                dbCcr.getHsCode().equalsIgnoreCase(updateCcr.getHsCode()) &&
//                                pass) {
//                            log.info("Updating CCR from Pdf: " + updateCcr);
//                            dbCcr.setCustomsCcrNo(updateCcr.getCustomsCcrNo());
//                            dbCcr.setPrimaryDo(updateCcr.getPrimaryDo());
//                            dbCcr.setCustomsKd(updateCcr.getCustomsKd());
//                            dbCcr.setTotalDuty(updateCcr.getTotalDuty());
//                            dbCcr.setUpdatedBy("Bayan");
//                            dbCcr.setUpdatedOn(new Date());
//
//                            Ccr updatedCcr = ccrRepository.save(dbCcr);
//                            updatedCcrList.add(updatedCcr);
//                        }
//
//                        List<Console> dbConsoleList = consoleService.getConsole(dbCcr.getConsoleId());
//
//                        for (Console console : dbConsoleList) {
//                            String consoleConsignmentValue = null;
//                            String dbConsoleConsignmentValue = null;
//                            boolean consolePass = false;
//                            if (updateCcr.getConsignmentValue() != null) {
//                                if (updateCcr.getConsignmentValue().contains(".")) {
//                                    String ccv = updateCcr.getConsignmentValue();
//                                    String ccv1 = null;
//                                    if ((ccv.substring(ccv.indexOf("."), ccv.length()).length() > 4)) {
//                                        ccv1 = ccv.substring(0, ccv.indexOf(".") + 4);
//                                    } else {
//                                        ccv1 = ccv;
//                                    }
//                                    DecimalFormat decimalFormat = new DecimalFormat("0.#####");
//                                    consoleConsignmentValue = decimalFormat.format(Double.valueOf(ccv1));
//                                } else {
//                                    consoleConsignmentValue = updateCcr.getConsignmentValue();
//                                }
//                            }
//                            if (console.getConsignmentValue() != null) {
//                                if (console.getConsignmentValue().contains(".")) {
//                                    String dbcv = console.getConsignmentValue();
//                                    String dbcv1 = null;
//                                    if ((dbcv.substring(dbcv.indexOf("."), dbcv.length()).length() > 4)) {
//                                        dbcv1 = dbcv.substring(0, dbcv.indexOf(".") + 4);
//                                    } else {
//                                        dbcv1 = dbcv;
//                                    }
//                                    DecimalFormat decimalFormat = new DecimalFormat("0.#####");
//                                    dbConsoleConsignmentValue = decimalFormat.format(Double.valueOf(dbcv1));
//                                } else {
//                                    dbConsoleConsignmentValue = console.getConsignmentValue();
//                                }
//                            }
//                            if (consoleConsignmentValue != null && dbConsoleConsignmentValue != null) {
//                                consolePass = consoleConsignmentValue.equalsIgnoreCase(dbConsoleConsignmentValue);
//                            }
//                            if (console.getHsCode().equalsIgnoreCase(updateCcr.getHsCode()) && consolePass) {
//                                log.info("Updating CONSOLE from Pdf: " + updateCcr);
//                                console.setCustomsCcrNo(updateCcr.getCustomsCcrNo());
//                                console.setPrimaryDo(updateCcr.getPrimaryDo());
//                                console.setCustomsKd(updateCcr.getCustomsKd());
//                                console.setTotalDuty(updateCcr.getTotalDuty());
//                                console.setUpdatedBy("Bayan");
//                                console.setUpdatedOn(new Date());
//                                consoleRepository.save(console);
//
//                                //Update PreAlert
//                                preAlertRepository.updatePreAlert(console.getCompanyId(), console.getLanguageId(),
//                                        console.getPartnerId(), console.getPartnerHouseAirwayBill(), console.getPartnerMasterAirwayBill(),
//                                        updateCcr.getTotalDuty());
//                            }
//                        }
//                    }
//                }
//            }
//            return "Update Success";
//        } catch (
//                Exception e) {
//            // Error Log
//            createCcrLog(updateCcrList, e.toString());
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//    }

    /**
     * @param updateCcrList
     * @return
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public String updateCCRFromBayan(List<UpdateCCR> updateCcrList) throws IOException, CsvException {
        try {
            log.info("Bayan Update CCR Initiated: " + updateCcrList.size());
            List<Ccr> updatedCcrList = new ArrayList<>();
            for (UpdateCCR updateCcr : updateCcrList) {
                List<Ccr> dbCcrList = getCcr(updateCcr.getCcrId());
                if (dbCcrList != null && !dbCcrList.isEmpty()) {
                    for (Ccr dbCcr : dbCcrList) {
                        Double customsConsignmentValue = null;
                        Double dbConsignmentValue = null;
                        boolean pass = false;
                        if (updateCcr.getConsignmentValue() != null) {
                            customsConsignmentValue = Double.valueOf(decimalFormat.format(updateCcr.getConsignmentValue()));
                        }
                        if (dbCcr.getConsignmentValue() != null) {
                            dbConsignmentValue = Double.valueOf(decimalFormat.format(dbCcr.getConsignmentValue()));
                        }
                        if (customsConsignmentValue != null && dbConsignmentValue != null) {
                            pass = customsConsignmentValue.equals(dbConsignmentValue);
                        }
                        if (dbCcr.getCcrId().equalsIgnoreCase(updateCcr.getCcrId()) && dbCcr.getHsCode().equalsIgnoreCase(updateCcr.getHsCode()) && pass) {
//                            log.info("Updating CCR from Pdf: " + updateCcr);
                            dbCcr.setCustomsCcrNo(updateCcr.getCustomsCcrNo());
                            dbCcr.setPrimaryDo(updateCcr.getPrimaryDo());
                            dbCcr.setCustomsKd(updateCcr.getCustomsKd());
                            dbCcr.setTotalDuty(updateCcr.getTotalDuty());
                            dbCcr.setUpdatedBy(updateCcr.getLoginUserID());
                            dbCcr.setUpdatedOn(new Date());
                            Ccr updatedCcr = ccrRepository.save(dbCcr);
                            preAlertRepository.updatePreAlert(updatedCcr.getCompanyId(), updatedCcr.getLanguageId(),
                                    updatedCcr.getPartnerId(), updatedCcr.getPartnerHouseAirwayBill(), updatedCcr.getPartnerMasterAirwayBill(),
                                    updateCcr.getTotalDuty());
                            consoleRepository.updateConsole(updateCcr.getCustomsCcrNo(), updateCcr.getPrimaryDo(), updateCcr.getCustomsKd(),
                                    updateCcr.getTotalDuty(), updateCcr.getLoginUserID(), updatedCcr.getCompanyId(), updatedCcr.getLanguageId(), updatedCcr.getPartnerId(),
                                    updatedCcr.getPartnerHouseAirwayBill(), updatedCcr.getPartnerMasterAirwayBill());
                            log.info("Console_Update Successfully");
                            updatedCcrList.add(updatedCcr);
                        }
                    }
                }
                List<Console> dbConsoleList = consoleService.getConsole(updatedCcrList.get(0).getConsoleId());
                Double totalDuty = consoleRepository.getTotalDuty(dbConsoleList.get(0).getPartnerMasterAirwayBill());
                totalDuty = totalDuty != null ? totalDuty : 0D;
                // Create CustomCosting
                customsCostingService.createCustomCostingForBayanValue(dbConsoleList.get(0), updateCcr.getLoginUserID(), totalDuty);
                log.info("custom costing created from Bayan");
            }
            log.info("CCR updated from Bayan");
            return "Update Success";
        } catch (
                Exception e) {
            // Error Log
            createCcrLog(updateCcrList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    // @Transactional
    //    public String updateCCRFromBayan(List<UpdateCCR> updateCcrList) throws IOException, CsvException {
    //        try {
    //            log.info("Bayan Update CCR Initiated: " + updateCcrList.size());
    //            List<Ccr> updatedCcrList = new ArrayList<>();
    //            for (UpdateCCR updateCcr : updateCcrList) {
    //                List<Ccr> dbCcrList = getCcr(updateCcr.getCcrId());
    //                if (dbCcrList != null && !dbCcrList.isEmpty()) {
    //                    for (Ccr dbCcr : dbCcrList) {
    //                        Double customsConsignmentValue = null;
    //                        Double dbConsignmentValue = null;
    //                        boolean pass = false;
    //                        if (updateCcr.getConsignmentValue() != null) {
    //                            customsConsignmentValue = Double.valueOf(decimalFormat.format(updateCcr.getConsignmentValue()));
    //                        }
    //                        if (dbCcr.getConsignmentValue() != null) {
    //                            dbConsignmentValue = Double.valueOf(decimalFormat.format(dbCcr.getConsignmentValue()));
    //                        }
    //                        if (customsConsignmentValue != null && dbConsignmentValue != null) {
    //                            pass = customsConsignmentValue.equals(dbConsignmentValue);
    //                        }
    //                        if (dbCcr.getCcrId().equalsIgnoreCase(updateCcr.getCcrId()) && dbCcr.getHsCode().equalsIgnoreCase(updateCcr.getHsCode()) && pass) {
    ////                            log.info("Updating CCR from Pdf: " + updateCcr);
    //                            dbCcr.setCustomsCcrNo(updateCcr.getCustomsCcrNo());
    //                            dbCcr.setPrimaryDo(updateCcr.getPrimaryDo());
    //                            dbCcr.setCustomsKd(updateCcr.getCustomsKd());
    //                            dbCcr.setTotalDuty(updateCcr.getTotalDuty());
    //                            dbCcr.setUpdatedBy(updateCcr.getLoginUserID());
    //                            dbCcr.setUpdatedOn(new Date());
    //                            Ccr updatedCcr = ccrRepository.save(dbCcr);
    //                            preAlertRepository.updatePreAlert(updatedCcr.getCompanyId(), updatedCcr.getLanguageId(),
    //                                    updatedCcr.getPartnerId(), updatedCcr.getPartnerHouseAirwayBill(), updatedCcr.getPartnerMasterAirwayBill(),
    //                                    updateCcr.getTotalDuty());
    //                            consoleRepository.updateConsoleBayanValue();
    //                            updatedCcrList.add(updatedCcr);
    //                        }
    //
    //                        List<Console> dbConsoleList = consoleService.getConsole(dbCcr.getConsoleId());
    //
    //                        for (Console console : dbConsoleList) {
    //                            Double consoleConsignmentValue = null;
    //                            Double dbConsoleConsignmentValue = null;
    //                            boolean consolePass = false;
    //                            if (updateCcr.getConsignmentValue() != null) {
    //                                consoleConsignmentValue = Double.valueOf(decimalFormat.format(updateCcr.getConsignmentValue()));
    //                            }
    //                            if (console.getConsignmentValue() != null) {
    //                                dbConsoleConsignmentValue = Double.valueOf(decimalFormat.format(console.getConsignmentValue()));
    //                            }
    //                            if (consoleConsignmentValue != null && dbConsoleConsignmentValue != null) {
    //                                consolePass = consoleConsignmentValue.equals(dbConsoleConsignmentValue);
    //                            }
    //                            if (console.getHsCode().equalsIgnoreCase(updateCcr.getHsCode()) && consolePass) {
    ////                                log.info("Updating CONSOLE from Pdf: " + updateCcr);
    //                                console.setCustomsCcrNo(updateCcr.getCustomsCcrNo());
    //                                console.setPrimaryDo(updateCcr.getPrimaryDo());
    //                                console.setCustomsKd(updateCcr.getCustomsKd());
    //                                console.setTotalDuty(updateCcr.getTotalDuty());
    //                                console.setUpdatedBy(updateCcr.getLoginUserID());
    //                                console.setUpdatedOn(new Date());
    //                                consoleRepository.save(console);
    //                                log.info("Console updated from Bayan");
    //                                //Update PreAlert
    //                                preAlertRepository.updatePreAlert(console.getCompanyId(), console.getLanguageId(),
    //                                        console.getPartnerId(), console.getPartnerHouseAirwayBill(), console.getPartnerMasterAirwayBill(),
    //                                        updateCcr.getTotalDuty());
    //                                log.info("pre alert updated from Bayan");
    //                            }
    //                        }
    //
    //                        Double totalDuty = consoleRepository.getTotalDuty(dbConsoleList.get(0).getPartnerMasterAirwayBill());
    //                        totalDuty = totalDuty != null ? totalDuty : 0D;
    //
    //                        // Create CustomCosting
    //                        customsCostingService.createCustomCostingForBayanValue(dbConsoleList.get(0), updateCcr.getLoginUserID(), totalDuty);
    //                        log.info("custom costing created from Bayan");
    //                    }
    //                }
    //            }
    //            log.info("CCR updated from Bayan");
    //            return "Update Success";
    //        } catch (
    //                Exception e) {
    //            // Error Log
    //            createCcrLog(updateCcrList, e.toString());
    //            e.printStackTrace();
    //            throw new RuntimeException(e);
    //        }
    //
    //    }

    /**
     * @param bayanUpdateList
     * @return
     */
    public String updateBayan(List<BayanUpdate> bayanUpdateList) {
        try {
            bayanUpdateList.stream().forEach(bayan -> {
                log.info("BayanHv Value -------------{}", bayan.getBayanHv());

                if (bayan.getBayanHv() != null) {
                    // PreAlert_Update
                    preAlertRepository.updatePreAlert(bayan.getCompanyId(), bayan.getLanguageId(), bayan.getPartnerId(),
                            bayan.getPartnerHouseAirwayBill(), bayan.getPartnerMasterAirwayBill(), bayan.getBayanHv());
                    log.info("PreAlert Bayan Value Update Successfully");

                    // Console_Update
                    consoleRepository.updateConsoleBayanValue(bayan.getCompanyId(), bayan.getLanguageId(), bayan.getPartnerId(),
                            bayan.getPartnerHouseAirwayBill(), bayan.getPartnerMasterAirwayBill(), bayan.getBayanHv());
                    log.info("Console Update Bayan Value Successfully");

                    Double totalDuty = consoleRepository.getTotalDuty(bayan.getPartnerMasterAirwayBill());

                    //CustomCosting_Update CustomDuty
                    customsCostingRepository.updateAmount(bayan.getCompanyId(), bayan.getLanguageId(), bayan.getPartnerMasterAirwayBill(),
                            totalDuty, "CustomDuty");
                    log.info("CustomCosting Update Bayan value Successfully");
                } else {
                    throw new BadRequestException("Bayan Hv Value is Null <----------> " + bayan);
                }
            });
        } catch (BadRequestException e) {
            log.error("Bad request: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.info("Bayan Update failed error throw");
            throw new RuntimeException("Bayan Value Update Failed <------->" + e.getMessage());
        }
        return "Bayan Value Update Successfully";
    }


    /**
     * @param updateCcrList
     * @param error
     * @throws IOException
     * @throws CsvException
     */
    private void createCcrLog(List<UpdateCCR> updateCcrList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateCCR updateCcr : updateCcrList) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setRefDocNumber(updateCcr.getCcrId());
            errorLog.setMethod("Exception thrown in updateCcr from bayan PDF");
            errorLog.setReferenceField1(updateCcr.getCustomsCcrNo());
            errorLog.setReferenceField2(updateCcr.getPrimaryDo());
            errorLog.setReferenceField3(updateCcr.getHsCode());
            errorLog.setReferenceField5(String.valueOf(updateCcr.getConsignmentValue()));
            errorLog.setReferenceField6(updateCcr.getCustomsKd());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }


}