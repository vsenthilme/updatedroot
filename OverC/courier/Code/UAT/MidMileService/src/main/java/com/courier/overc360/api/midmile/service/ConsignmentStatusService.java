package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.consignmentstatus.AddConsignmentStatus;
import com.courier.overc360.api.midmile.primary.model.consignmentstatus.ConsignmentStatus;
import com.courier.overc360.api.midmile.primary.model.consignmentstatus.UpdateConsignmentStatus;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.model.itemdetails.AddItemDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.AddPieceDetails;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentStatusRepository;
import com.courier.overc360.api.midmile.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.consignmentstatus.FindConsignmentNew;
import com.courier.overc360.api.midmile.replica.model.consignmentstatus.FindConsignmentStatus;
import com.courier.overc360.api.midmile.replica.model.consignmentstatus.ReplicaConsignmentStatus;
import com.courier.overc360.api.midmile.replica.repository.ReplicaConsignmentStatusRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPieceDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaConsignmentStatusSpecification;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsignmentStatusService {

    @Autowired
    private ConsignmentStatusRepository consignmentStatusRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaConsignmentStatusRepository replicaConsignmentStatusRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    private ReplicaPieceDetailsRepository replicaPieceDetailsRepository;


    // Auto Gen
    private static final AtomicLong counter = new AtomicLong(1);
    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    /**
     * Get ConsignmentStatus
     *
     * @param languageId
     * @param companyId
     * @param houseAirwayBill
     * @param pieceId
     * @return
     */
    public ConsignmentStatus getConsignmentStatus(String languageId, String companyId, String houseAirwayBill, String pieceId) {

        Optional<ConsignmentStatus> dbConsignmentStatus = consignmentStatusRepository.findByLanguageIdAndCompanyIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                languageId, companyId, houseAirwayBill, pieceId, 0L);
        if (dbConsignmentStatus.isEmpty()) {
            String errMsg = "The given values - languageId: " + languageId + ", companyId: " + companyId + ", houseAirwayBill: " + houseAirwayBill +
                    ", pieceId: " + pieceId + " doesn't exists";
            // Error Log
            getConsignmentStatusLog(languageId, companyId, houseAirwayBill, pieceId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsignmentStatus.get();
    }

    @Transactional
    public void insertConsignmentStatusRecord(String languageId, String languageDesc, String companyId, String companyName,
                                              String pieceId, String masterAirwayBill, String houseAirwayBill,
                                              String hawbType, String hawbTypeId, String hawbTypeDescription, Date hawbTimeStamp,
                                              String pieceType, String pieceTypeId, String pieceTypeDescription, Date pieceTimeStamp,
                                              String loginUserID, String partnerHouseAirwayBill, String partnerMasterAirwayBill) {
        try {
            if (languageId != null && companyId != null && pieceId != null && houseAirwayBill != null) {
                ConsignmentStatus newConsignmentStatus = new ConsignmentStatus();

                Long statusId = consignmentStatusRepository.statusId();

                if (statusId == null || statusId == 0) {
                    statusId = 1L;
                } else {
                    statusId++;
                }
                newConsignmentStatus.setConsignmentStatusId(statusId);
                newConsignmentStatus.setLanguageId(languageId);
                newConsignmentStatus.setLanguageDescription(languageDesc);
                newConsignmentStatus.setCompanyId(companyId);
                newConsignmentStatus.setCompanyName(companyName);

                newConsignmentStatus.setPieceId(pieceId);
                newConsignmentStatus.setPartnerHouseAirwayBill(partnerHouseAirwayBill);
                newConsignmentStatus.setPartnerMasterAirwayBill(partnerMasterAirwayBill);
                newConsignmentStatus.setMasterAirwayBill(masterAirwayBill);
                newConsignmentStatus.setHouseAirwayBill(houseAirwayBill);

                newConsignmentStatus.setHawbType(hawbType);
                newConsignmentStatus.setHawbTypeId(hawbTypeId);
                newConsignmentStatus.setHawbTypeDescription(hawbTypeDescription);
                newConsignmentStatus.setHawbTimeStamp(new Date());

                newConsignmentStatus.setPieceType(pieceType);
                newConsignmentStatus.setPieceTypeId(pieceTypeId);
                newConsignmentStatus.setPieceTypeDescription(pieceTypeDescription);
                newConsignmentStatus.setPieceTimeStamp(new Date());

                newConsignmentStatus.setDeletionIndicator(0L);
                newConsignmentStatus.setCreatedBy(loginUserID);
                newConsignmentStatus.setCreatedOn(new Date());
                newConsignmentStatus.setUpdatedBy(loginUserID);
                newConsignmentStatus.setUpdatedOn(new Date());
                consignmentStatusRepository.save(newConsignmentStatus);
                log.info("Consignment Status Table save {}", houseAirwayBill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param languageId
     * @param languageDesc
     * @param companyId
     * @param companyName
     * @param pieceId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param hawbType
     * @param hawbTypeId
     * @param hawbTypeDescription
     * @param hawbTimeStamp
     * @param pieceType
     * @param pieceTypeId
     * @param pieceTypeDescription
     * @param pieceTimeStamp
     * @param loginUserID
     * @param partnerHouseAirwayBill
     * @param partnerMasterAirwayBill
     * @param bagId
     * @param hubCode
     * @param hubName
     */
    @Transactional
    public void insertConsignmentStatusRecord(String languageId, String languageDesc, String companyId, String companyName,
                                              String pieceId, String masterAirwayBill, String houseAirwayBill,
                                              String hawbType, String hawbTypeId, String hawbTypeDescription, Date hawbTimeStamp,
                                              String pieceType, String pieceTypeId, String pieceTypeDescription, Date pieceTimeStamp,
                                              String loginUserID, String partnerHouseAirwayBill, String partnerMasterAirwayBill, Long bagId,
                                              String hubCode, String hubName) {
        try {
            log.info("Consignment_Status Insert Record LanguageId - " + languageId + " CompanyId - " + companyId + " PieceId - " + pieceId + " HouseAirwayBill " + houseAirwayBill);
            if (languageId != null && companyId != null && pieceId != null && houseAirwayBill != null) {
                ConsignmentStatus newConsignmentStatus = new ConsignmentStatus();

//                long uniqueId = counter.incrementAndGet();
//                newConsignmentStatus.setConsignmentStatusId(uniqueId);
                Long maxStatus = consignmentStatusRepository.getMaxConStatusId();                                                       // CON_STATUS -- MAX + 1
                newConsignmentStatus.setConsignmentStatusId(maxStatus != null ? maxStatus : 1L);
                newConsignmentStatus.setLanguageId(languageId);
                newConsignmentStatus.setLanguageDescription(languageDesc);
                newConsignmentStatus.setCompanyId(companyId);
                newConsignmentStatus.setCompanyName(companyName);

                newConsignmentStatus.setPieceId(pieceId);
                newConsignmentStatus.setPartnerHouseAirwayBill(partnerHouseAirwayBill);
                newConsignmentStatus.setPartnerMasterAirwayBill(partnerMasterAirwayBill);
                newConsignmentStatus.setMasterAirwayBill(masterAirwayBill);
                newConsignmentStatus.setHouseAirwayBill(houseAirwayBill);

                newConsignmentStatus.setHawbType(hawbType);
                newConsignmentStatus.setHawbTypeId(hawbTypeId);
                newConsignmentStatus.setHawbTypeDescription(hawbTypeDescription);
                newConsignmentStatus.setHawbTimeStamp(new Date());

                newConsignmentStatus.setPieceType(pieceType);
                newConsignmentStatus.setPieceTypeId(pieceTypeId);
                newConsignmentStatus.setPieceTypeDescription(pieceTypeDescription);
                newConsignmentStatus.setPieceTimeStamp(new Date());
                newConsignmentStatus.setBagId(String.valueOf(bagId));
                newConsignmentStatus.setHubCode(hubCode);
                newConsignmentStatus.setHubName(hubName);

                newConsignmentStatus.setDeletionIndicator(0L);
                newConsignmentStatus.setCreatedBy(loginUserID);
                newConsignmentStatus.setCreatedOn(new Date());
                newConsignmentStatus.setUpdatedBy(loginUserID);
                newConsignmentStatus.setUpdatedOn(new Date());
                consignmentStatusRepository.save(newConsignmentStatus);
                log.info("Consignment Status Updated Successfully <-----> HouseAirwayBill " + houseAirwayBill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Insert Records based on Aging Count
    public void insertConsignmentStatusRecord(String languageId, String languageDesc, String companyId, String companyName, String pieceId,
                                              String masterAirwayBill, String houseAirwayBill, String hawbType, String hawbTypeId, String hawbTypeDescription,
                                              String loginUserID, String partnerHouseAirwayBill, String partnerMasterAirwayBill, String hubCode, String hubName, Long agingCount) {
        try {
            log.info("Consignment_Status Insert Record LanguageId - " + languageId + " CompanyId - " + companyId + " PieceId - " + pieceId + " HouseAirwayBill " + houseAirwayBill);
            if (languageId != null && companyId != null && pieceId != null && houseAirwayBill != null) {

//                Long statusCount = consignmentStatusRepository.getStatusCount(hawbTypeId, houseAirwayBill);
//                log.info("StatusCount -------> "+ statusCount + "AgingCount <------------>" + agingCount);
//                if (agingCount >= statusCount) {
                    ConsignmentStatus newConsignmentStatus = new ConsignmentStatus();
                    Long maxStatus = consignmentStatusRepository.getMaxConStatusId();                                                       // CON_STATUS -- MAX + 1
                    newConsignmentStatus.setConsignmentStatusId(maxStatus != null ? maxStatus : 1L);
                    newConsignmentStatus.setLanguageId(languageId);
                    newConsignmentStatus.setLanguageDescription(languageDesc);
                    newConsignmentStatus.setCompanyId(companyId);
                    newConsignmentStatus.setCompanyName(companyName);
                    newConsignmentStatus.setPieceId(pieceId);
                    newConsignmentStatus.setPartnerHouseAirwayBill(partnerHouseAirwayBill);
                    newConsignmentStatus.setPartnerMasterAirwayBill(partnerMasterAirwayBill);
                    newConsignmentStatus.setMasterAirwayBill(masterAirwayBill);
                    newConsignmentStatus.setHouseAirwayBill(houseAirwayBill);
                    newConsignmentStatus.setHawbType(hawbType);
                    newConsignmentStatus.setHawbTypeId(hawbTypeId);
                    newConsignmentStatus.setHawbTypeDescription(hawbTypeDescription);
                    newConsignmentStatus.setHawbTimeStamp(new Date());
                    newConsignmentStatus.setPieceType(hawbType);
                    newConsignmentStatus.setPieceTypeId(hawbTypeId);
                    newConsignmentStatus.setPieceTypeDescription(hawbTypeDescription);
                    newConsignmentStatus.setPieceTimeStamp(new Date());
                    newConsignmentStatus.setHubCode(hubCode);
                    newConsignmentStatus.setHubName(hubName);
                    newConsignmentStatus.setDeletionIndicator(0L);
                    newConsignmentStatus.setCreatedBy(loginUserID);
                    newConsignmentStatus.setCreatedOn(new Date());
                    newConsignmentStatus.setUpdatedBy(loginUserID);
                    newConsignmentStatus.setUpdatedOn(new Date());
                    consignmentStatusRepository.save(newConsignmentStatus);
                    log.info("Consignment Status Updated Successfully <-----> HouseAirwayBill " + houseAirwayBill);
                }
//            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Create ConsignmentStatus
     *
     * @param addConsignmentStatus
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ConsignmentStatus createConsignmentStatus(AddConsignmentStatus addConsignmentStatus, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<ConsignmentStatus> dbConsignmentStatus = consignmentStatusRepository.findByLanguageIdAndCompanyIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                    addConsignmentStatus.getLanguageId(), addConsignmentStatus.getCompanyId(), addConsignmentStatus.getHouseAirwayBill(),
                    addConsignmentStatus.getPieceId(), 0L);

            // Check if duplicate record exists
            if (dbConsignmentStatus.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values : houseAirwayBill - " + addConsignmentStatus.getHouseAirwayBill());
            }
            log.info("new Consignment status --> {}", addConsignmentStatus);

            ConsignmentStatus newConsignmentStatus = new ConsignmentStatus();
            BeanUtils.copyProperties(addConsignmentStatus, newConsignmentStatus, CommonUtils.getNullPropertyNames(addConsignmentStatus));
            Long maxStatus = consignmentStatusRepository.getMaxConStatusId();                                                       // CON_STATUS -- MAX + 1
            newConsignmentStatus.setConsignmentStatusId(maxStatus != null ? maxStatus : 1L);
            newConsignmentStatus.setDeletionIndicator(0L);
            newConsignmentStatus.setCreatedBy(loginUserID);
            newConsignmentStatus.setCreatedOn(new Date());
            newConsignmentStatus.setUpdatedBy(loginUserID);
            newConsignmentStatus.setUpdatedOn(new Date());
            return consignmentStatusRepository.save(newConsignmentStatus);
        } catch (Exception e) {
            // Error Log
            createConsignmentStatusLog(addConsignmentStatus, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * Update ConsignmentStatus
     *
     * @param languageId
     * @param companyId
     * @param houseAirwayBill
     * @param pieceId
     * @return
     */
    @Transactional
    public ConsignmentStatus updateConsignmentStatus(String languageId, String companyId, String houseAirwayBill, String pieceId,
                                                     UpdateConsignmentStatus updateConsignmentStatus, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        try {
            ConsignmentStatus dbConsignmentStatus = getConsignmentStatus(languageId, companyId, houseAirwayBill, pieceId);
            BeanUtils.copyProperties(updateConsignmentStatus, dbConsignmentStatus, CommonUtils.getNullPropertyNames(updateConsignmentStatus));
            dbConsignmentStatus.setUpdatedBy(loginUserID);
            dbConsignmentStatus.setUpdatedOn(new Date());
            return consignmentStatusRepository.save(dbConsignmentStatus);
        } catch (Exception e) {
            // Error Log
            updateConsignmentStatusLog(languageId, companyId, houseAirwayBill, pieceId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * Delete ConsignmentDetails
     *
     * @param languageId
     * @param companyId
     * @param houseAirwayBill
     * @param pieceId
     * @param loginUserID
     */
    public void deleteConsignmentDetails(String languageId, String companyId, String houseAirwayBill, String pieceId, String loginUserID) {

        ConsignmentStatus dbConsignmentStatus = getConsignmentStatus(languageId, companyId, houseAirwayBill, pieceId);
        if (dbConsignmentStatus != null) {
            dbConsignmentStatus.setDeletionIndicator(1L);
            dbConsignmentStatus.setUpdatedBy(loginUserID);
            dbConsignmentStatus.setUpdatedOn(new Date());
            consignmentStatusRepository.save(dbConsignmentStatus);
        } else {
            // Error Log
            deleteConsignmentLog(languageId, companyId, houseAirwayBill, pieceId, "Error in deleting houseAirwayBill type - " + houseAirwayBill);
            throw new BadRequestException("Error in deleting houseAirwayBill type - " + houseAirwayBill);
        }
    }
    /*=================================================REPLICA=============================================================*/

    /**
     * Get all ConsignmentStatus
     *
     * @return
     */
    public List<ReplicaConsignmentStatus> getAllConsignmentStatus() {
        List<ReplicaConsignmentStatus> consignmentStatusList = replicaConsignmentStatusRepository.findAll();
        consignmentStatusList = consignmentStatusList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return consignmentStatusList;
    }

    /**
     * Get ConsignmentStatus
     *
     * @param languageId
     * @param companyId
     * @param houseAirwayBill
     * @param pieceId
     * @return
     */
    public ReplicaConsignmentStatus getReplicaConsignmentStatus(String languageId, String companyId, String houseAirwayBill, String pieceId) {

        Optional<ReplicaConsignmentStatus> dbConsignmentStatus =
                replicaConsignmentStatusRepository.findByLanguageIdAndCompanyIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                        languageId, companyId, houseAirwayBill, pieceId, 0L);
        if (dbConsignmentStatus.isEmpty()) {
            String errMsg = "The given values - languageId: " + languageId + ", companyId: " + companyId + ", houseAirwayBill: " + houseAirwayBill +
                    ", pieceId: " + pieceId + " doesn't exists";
            // Error Log
            getConsignmentStatusLog(languageId, companyId, houseAirwayBill, pieceId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsignmentStatus.get();
    }

    /**
     * Find ConsignmentStatus
     *
     * @param findConsignmentStatus
     * @return
     */
    public List<ReplicaConsignmentStatus> findConsignmentStatus(FindConsignmentStatus findConsignmentStatus) throws ParseException {

        log.info("given params for find --> {}", findConsignmentStatus);
        ReplicaConsignmentStatusSpecification spec = new ReplicaConsignmentStatusSpecification(findConsignmentStatus);
        List<ReplicaConsignmentStatus> results = replicaConsignmentStatusRepository.findAll(spec);
        log.info("found ConsignmentStatus --> {}", results);
        return results;
    }

    //==========================================ConsignmentStatus_ErrorLog================================================
    // getConsignmentStatus errorLog
    private void getConsignmentStatusLog(String languageId, String companyId, String houseAirwayBill,
                                         String pieceId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(houseAirwayBill);
        errorLog.setMethod("Exception thrown in getConsignmentStatus");
        errorLog.setReferenceField1(pieceId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    //createConsignmentStatus errorLog
    private void createConsignmentStatusLog(AddConsignmentStatus addConsignmentStatus, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addConsignmentStatus.getLanguageId());
        errorLog.setCompanyId(addConsignmentStatus.getCompanyId());
        errorLog.setRefDocNumber(addConsignmentStatus.getHouseAirwayBill());
        errorLog.setMethod("Exception thrown in createConsignmentStatus");
        errorLog.setReferenceField1(addConsignmentStatus.getPieceId());
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    //updateConsignmentStatus errorLog
    private void updateConsignmentStatusLog(String languageId, String companyId, String houseAirwayBill,
                                            String pieceId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(houseAirwayBill);
        errorLog.setMethod("Exception thrown in updateConsignmentStatus");
        errorLog.setReferenceField1(pieceId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    //deleteConsignment errorLog
    private void deleteConsignmentLog(String languageId, String companyId, String houseAirwayBill, String pieceId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(houseAirwayBill);
        errorLog.setMethod("Exception thrown in deleteConsignmentStatus");
        errorLog.setReferenceField1(pieceId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }


    /**
     * @param findConsignmentNew
     * @param loginUserID
     * @return
     */
    public List<ConsignmentStatus> addConsignmentStatus(List<FindConsignmentNew> findConsignmentNew, String loginUserID) {
        List<ConsignmentStatus> consignmentStatusList = new ArrayList<>();

        findConsignmentNew.forEach(consignmentStatus -> {
            log.info("Consignment_Status Mobile App Input <--------------------------------->" + consignmentStatus);
            ConsignmentStatus newConsignmentStatus = new ConsignmentStatus();

//            long uniqueId1 = counter.incrementAndGet();
//            newConsignmentStatus.setConsignmentStatusId(uniqueId1);
            Long maxStatus = consignmentStatusRepository.getMaxConStatusId();                                                       // CON_STATUS -- MAX + 1
            newConsignmentStatus.setConsignmentStatusId(maxStatus != null ? maxStatus : 1L);
            newConsignmentStatus.setLanguageId(consignmentStatus.getLanguageId());
            newConsignmentStatus.setCompanyId(consignmentStatus.getCompanyId());
            newConsignmentStatus.setHouseAirwayBill(consignmentStatus.getHouseAirwayBill());
            newConsignmentStatus.setHawbType("STATUS");
            newConsignmentStatus.setHawbTypeId("3");
            String statusText = consignmentEntityRepository.statusText("3");
            if (statusText != null && !statusText.isEmpty()) {
                newConsignmentStatus.setPieceTypeDescription(statusText);
                newConsignmentStatus.setHawbTypeDescription(statusText);
            }

            Long consignmentId = replicaPieceDetailsRepository.findConsignment(consignmentStatus.getLanguageId(), consignmentStatus.getCompanyId(),
                    consignmentStatus.getHouseAirwayBill(), consignmentStatus.getPieceId());

            consignmentEntityRepository.updateStatus("3", statusText, consignmentId);
            newConsignmentStatus.setHawbTimeStamp(new Date());
            newConsignmentStatus.setPieceId(consignmentStatus.getPieceId());
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
            log.info("Consignment_Status Mobile App Response <--------------------------------->" + newConsignmentStatus);
        });

//        });

        return consignmentStatusList;
    }
}
