package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.AddUnconsolidation;
import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.Unconsolidation;
import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.UnconsolidationDeleteInput;
import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.UpdateUnconsolidation;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.midmile.primary.repository.UnconsolidationRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.console.unconsolidation.FindUnconsolidation;
import com.courier.overc360.api.midmile.replica.model.console.unconsolidation.ReplicaUnconsolidation;
import com.courier.overc360.api.midmile.replica.repository.ReplicaUnconsolidationRepository;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class UnconsolidationService {

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private UnconsolidationRepository unconsolidationRepository;

    @Autowired
    private ReplicaUnconsolidationRepository replicaUnconsolidationRepository;

    private final ExecutorService executorService;

    public UnconsolidationService(ReplicaUnconsolidationRepository replicaUnconsolidationRepository) {
        this.replicaUnconsolidationRepository = replicaUnconsolidationRepository;
        this.executorService = Executors.newFixedThreadPool(10); // Adjust the pool size as needed
    }


    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @param pieceId
     * @return
     */
    private Unconsolidation getUnconsolidation(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                                               String partnerHouseAirwayBill, String pieceId) {
        Optional<Unconsolidation> dbUnconsolidation = unconsolidationRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndDeletionIndicator(
                languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, pieceId, 0L);
        if (dbUnconsolidation.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", masterAirwayBill - " + partnerMasterAirwayBill
                    + ", houseAirwayBill - " + partnerHouseAirwayBill + " and pieceId " + pieceId + " doesn't exists";
            // Error Log
            createUnconsolidationLog(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, pieceId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbUnconsolidation.get();
    }

    /**
     * Create Unconsolidation
     *
     * @param addUnconsolidation
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Unconsolidation createUnconsolidation(AddUnconsolidation addUnconsolidation, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean duplicateUnconsolidation = replicaUnconsolidationRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndDeletionIndicator(
                    addUnconsolidation.getLanguageId(), addUnconsolidation.getCompanyId(),
                    addUnconsolidation.getPartnerId(), addUnconsolidation.getPartnerMasterAirwayBill(),
                    addUnconsolidation.getPartnerHouseAirwayBill(), addUnconsolidation.getPieceId(), 0L);
            if (duplicateUnconsolidation) {
                throw new BadRequestException("Record is getting Duplicated with the given values : partnerHouseAirwayBill - " + addUnconsolidation.getPartnerHouseAirwayBill());
            }

            IKeyValuePair lAndCDesc = replicaUnconsolidationRepository.getLAndCDescription(addUnconsolidation.getLanguageId(), addUnconsolidation.getCompanyId());

            Unconsolidation newUnconsolidation = new Unconsolidation();
            BeanUtils.copyProperties(addUnconsolidation, newUnconsolidation, CommonUtils.getNullPropertyNames(addUnconsolidation));

            if (lAndCDesc != null) {
                newUnconsolidation.setLanguageDescription(lAndCDesc.getLangDesc());
                newUnconsolidation.setCompanyName(lAndCDesc.getCompanyDesc());
            }

            newUnconsolidation.setDeletionIndicator(0L);
            newUnconsolidation.setCreatedBy(loginUserID);
            newUnconsolidation.setCreatedOn(new Date());
            newUnconsolidation.setUpdatedBy(null);
            newUnconsolidation.setUpdatedOn(null);
            Unconsolidation createdUnconsolidation = unconsolidationRepository.save(newUnconsolidation);
            return createdUnconsolidation;
        } catch (Exception e) {
            // Error Log
            createUnconsolidationLog1(addUnconsolidation, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @param addUnconsolidation
     * @param loginUserID
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public void generateUnconsolidation(AddUnconsolidation addUnconsolidation, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean duplicateUnconsolidation = replicaUnconsolidationRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndDeletionIndicator(
                    addUnconsolidation.getLanguageId(), addUnconsolidation.getCompanyId(),
                    addUnconsolidation.getPartnerId(), addUnconsolidation.getPartnerMasterAirwayBill(),
                    addUnconsolidation.getPartnerHouseAirwayBill(), addUnconsolidation.getPieceId(), 0L);
            if (duplicateUnconsolidation) {
                throw new BadRequestException("Record is getting Duplicated with the given values : partnerHouseAirwayBill - " + addUnconsolidation.getPartnerHouseAirwayBill());
            }

            Unconsolidation newUnconsolidation = new Unconsolidation();
            BeanUtils.copyProperties(addUnconsolidation, newUnconsolidation, CommonUtils.getNullPropertyNames(addUnconsolidation));

            newUnconsolidation.setDeletionIndicator(0L);
            newUnconsolidation.setCreatedBy(loginUserID);
            newUnconsolidation.setCreatedOn(new Date());
            newUnconsolidation.setUpdatedBy(null);
            newUnconsolidation.setUpdatedOn(null);
            unconsolidationRepository.save(newUnconsolidation);

            // Update unconsolidated flag in Consignment table
            unconsolidationRepository.updateUnconsolidatedFlagInConsignmentTbl(addUnconsolidation.getLanguageId(), addUnconsolidation.getCompanyId(),
                    addUnconsolidation.getPartnerId(), addUnconsolidation.getPartnerHouseAirwayBill(), addUnconsolidation.getPartnerMasterAirwayBill());
        } catch (Exception e) {
            // Error Log
            createUnconsolidationLog1(addUnconsolidation, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Unconsolidation
     *
     * @param updateUnconsolidation
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Unconsolidation updateUnconsolidation(UpdateUnconsolidation updateUnconsolidation, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Unconsolidation dbUnconsolidation = getUnconsolidation(updateUnconsolidation.getLanguageId(), updateUnconsolidation.getCompanyId(),
                    updateUnconsolidation.getPartnerId(), updateUnconsolidation.getPartnerMasterAirwayBill(),
                    updateUnconsolidation.getPartnerHouseAirwayBill(), updateUnconsolidation.getPieceId());
            BeanUtils.copyProperties(updateUnconsolidation, dbUnconsolidation, CommonUtils.getNullPropertyNames(updateUnconsolidation));
            dbUnconsolidation.setUpdatedBy(loginUserID);
            dbUnconsolidation.setUpdatedOn(new Date());
            Unconsolidation updatedUnconsolidation = unconsolidationRepository.save(dbUnconsolidation);
            return updatedUnconsolidation;
        } catch (Exception e) {
            // Error Log
            createUnconsolidationLog2(updateUnconsolidation, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Unconsolidations
     *
     * @param unconsolidationDeleteInputList
     * @param loginUserID
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public void deleteUnconsolidation(List<UnconsolidationDeleteInput> unconsolidationDeleteInputList, String loginUserID) throws IOException, CsvException {
        try {
            if (unconsolidationDeleteInputList != null) {
                for (UnconsolidationDeleteInput deleteInput : unconsolidationDeleteInputList) {
                    Unconsolidation dbUnconsolidation = getUnconsolidation(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                            deleteInput.getPartnerId(), deleteInput.getPartnerMasterAirwayBill(),
                            deleteInput.getPartnerHouseAirwayBill(), deleteInput.getPieceId());

                    log.info("Unconsolidation deleteInput --> {}", deleteInput);

                    dbUnconsolidation.setDeletionIndicator(1L);
                    dbUnconsolidation.setUpdatedBy(loginUserID);
                    dbUnconsolidation.setUpdatedOn(new Date());
                    unconsolidationRepository.save(dbUnconsolidation);
                }
            }
        } catch (Exception e) {
            // Error Log
            createUnconsolidationLog3(unconsolidationDeleteInputList, e.toString());
            throw new RuntimeException(e);
        }
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    /**
     * Get All Unconsolidation Details
     *
     * @return
     */
    public List<ReplicaUnconsolidation> getAllUnconsolidations() {
        List<ReplicaUnconsolidation> unconsolidationList = replicaUnconsolidationRepository.getAllNonDeletedUnconsolidations();
        return unconsolidationList;
    }

    /**
     * Get Unconsolidation
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @param pieceId
     * @return
     */
    public ReplicaUnconsolidation getUnconsolidationReplica(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                                                            String partnerHouseAirwayBill, String pieceId) {
        Optional<ReplicaUnconsolidation> dbUnconsolidation = replicaUnconsolidationRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndDeletionIndicator(
                languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, pieceId, 0L);
        if (dbUnconsolidation.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", masterAirwayBill - " + partnerMasterAirwayBill
                    + ", houseAirwayBill - " + partnerHouseAirwayBill + " and pieceId " + pieceId + " doesn't exists";
            // Error Log
            createUnconsolidationLog(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, pieceId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbUnconsolidation.get();
    }

//    public List<ReplicaUnconsolidation> findUnconsolidations(FindUnconsolidation findUnconsolidation) {
//
//        log.info("given params to find Unconsolidations --> {}", findUnconsolidation);
//        UnconsolidationSpecification spec = new UnconsolidationSpecification(findUnconsolidation);
//        List<ReplicaUnconsolidation> results = replicaUnconsolidationRepository.findAll(spec);
//        log.info("No of Unconsolidations --> {}", results.size());
//        return results;
//    }

    /**
     * Find Unconsolidated Shipments with Qry
     *
     * @param findUnconsolidation
     * @return
     */
    public List<ReplicaUnconsolidation> findUnconsolidatedShipments(FindUnconsolidation findUnconsolidation) throws Exception {

        log.info("given params to fetch Unconsolidated Shipments with Qry --> {}", findUnconsolidation);
        return findUnconsolidatedAsync(findUnconsolidation).get();
    }

    private CompletableFuture<List<ReplicaUnconsolidation>> findUnconsolidatedAsync(FindUnconsolidation findUnconsolidation) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return replicaUnconsolidationRepository.findUnconsolidatedShipmentsWithQry(
                        findUnconsolidation.getLanguageId(),
                        findUnconsolidation.getCompanyId(),
                        findUnconsolidation.getPartnerId(),
                        findUnconsolidation.getPartnerMasterAirwayBill(),
                        findUnconsolidation.getPartnerHouseAirwayBill(),
                        findUnconsolidation.getUnconsolidatedFlag());
            } catch (Exception e) {
                // Handle exception, possibly log it or rethrow
                throw new RuntimeException("Failed to fetch Unconsolidated Shipments with Qry ", e);
            }
        }, executorService);
    }

//    public List<ReplicaUnconsolidation> findUnconsolidatedShipments(FindUnconsolidation findUnconsolidation) throws Exception {
//
//        log.info("given params to fetch Unconsolidated Shipments with Qry --> {}", findUnconsolidation);
//        return replicaUnconsolidationRepository.findUnconsolidatedShipmentsWithQry(
//                findUnconsolidation.getLanguageId(), findUnconsolidation.getCompanyId(), findUnconsolidation.getPartnerId(),
//                findUnconsolidation.getPartnerMasterAirwayBill(), findUnconsolidation.getPartnerHouseAirwayBill(), findUnconsolidation.getUnconsolidatedFlag());
//    }


    //==========================================Unconsolidation_ErrorLog===============================================
    private void createUnconsolidationLog(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
                                          String partnerHouseAirwayBill, String pieceId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerMasterAirwayBill);
        errorLog.setMethod("Exception thrown in getUnconsolidation");
        errorLog.setReferenceField1(partnerHouseAirwayBill);
        errorLog.setReferenceField2(partnerId);
        errorLog.setReferenceField3(pieceId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createUnconsolidationLog1(AddUnconsolidation addUnconsolidation, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addUnconsolidation.getLanguageId());
        errorLog.setCompanyId(addUnconsolidation.getCompanyId());
        errorLog.setRefDocNumber(addUnconsolidation.getPartnerMasterAirwayBill());
        errorLog.setMethod("Exception thrown in createUnconsolidation");
        errorLog.setReferenceField1(addUnconsolidation.getPartnerId());
        errorLog.setReferenceField2(addUnconsolidation.getPartnerHouseAirwayBill());
        errorLog.setReferenceField3(addUnconsolidation.getPieceId());
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createUnconsolidationLog2(UpdateUnconsolidation updateUnconsolidation, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(updateUnconsolidation.getLanguageId());
        errorLog.setCompanyId(updateUnconsolidation.getCompanyId());
        errorLog.setRefDocNumber(updateUnconsolidation.getPartnerMasterAirwayBill());
        errorLog.setMethod("Exception thrown in updateUnconsolidation");
        errorLog.setReferenceField1(updateUnconsolidation.getPartnerId());
        errorLog.setReferenceField2(updateUnconsolidation.getPartnerHouseAirwayBill());
        errorLog.setReferenceField3(updateUnconsolidation.getPieceId());
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createUnconsolidationLog3(List<UnconsolidationDeleteInput> deleteInputList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UnconsolidationDeleteInput deleteInput : deleteInputList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(deleteInput.getLanguageId());
            errorLog.setCompanyId(deleteInput.getCompanyId());
            errorLog.setRefDocNumber(deleteInput.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in deleteUnconsolidation");
            errorLog.setReferenceField1(deleteInput.getPartnerId());
            errorLog.setReferenceField2(deleteInput.getPartnerHouseAirwayBill());
            errorLog.setReferenceField3(deleteInput.getPieceId());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

}
