package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.statusevent.AddStatusEvent;
import com.courier.overc360.api.idmaster.primary.model.statusevent.StatusEvent;
import com.courier.overc360.api.idmaster.primary.model.statusevent.UpdateStatusEvent;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.StatusEventRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.statusevent.FindStatusEvent;
import com.courier.overc360.api.idmaster.replica.model.statusevent.ReplicaStatusEvent;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusEventRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaStatusEventSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatusEventService {

    @Autowired
    private StatusEventRepository statusEventRepository;

    @Autowired
    private ReplicaStatusEventRepository replicaStatusEventRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;


    /*======================================================PRIMARY=============================================================*/

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param typeId
     * @return
     */
    public StatusEvent getStatusEvent(String companyId, String languageId, String typeId){

        Optional<StatusEvent> dbStatusEvent = statusEventRepository.findByCompanyIdAndLanguageIdAndTypeIdAndDeletionIndicator
                (companyId,languageId,typeId,0l);
        if(dbStatusEvent.isEmpty()){
            //Error Log
            createStatusEventLog1(languageId, companyId, typeId, "TypeId - " + typeId + " and given values doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " typeId - " + typeId + " doesn't exists");
        }
        return dbStatusEvent.get();
    }

    /**
     * Create
     *
     * @param addStatusEvent
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public StatusEvent createStatusEvent(AddStatusEvent addStatusEvent, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<StatusEvent> duplicateStatusEvent = statusEventRepository.findByCompanyIdAndLanguageIdAndTypeIdAndDeletionIndicator
                    (addStatusEvent.getCompanyId(), addStatusEvent.getLanguageId(), addStatusEvent.getTypeId(), 0L);
            if (duplicateStatusEvent.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with typeId - " + addStatusEvent.getTypeId());
            }
            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addStatusEvent.getCompanyId(), addStatusEvent.getLanguageId(), 0L);
            if (dbCompany.isEmpty()) {
                throw new BadRequestException("CompanyId - " + addStatusEvent.getCompanyId() + " and LanguageId - " + addStatusEvent.getLanguageId() + " doesn't exists");
            } else {
                log.info("new StatusEvent --> " + addStatusEvent);
                StatusEvent newStatusEvent = new StatusEvent();
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addStatusEvent.getLanguageId(), addStatusEvent.getCompanyId());
                BeanUtils.copyProperties(addStatusEvent, newStatusEvent, CommonUtils.getNullPropertyNames(addStatusEvent));
                if ((addStatusEvent.getTypeId() != null &&
                        (addStatusEvent.getReferenceField10() != null && addStatusEvent.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addStatusEvent.getTypeId() == null || addStatusEvent.getTypeId().isBlank()) {
                    String NUM_RAN_OBJ = "STATUSEVENT";
                    String TYPE_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for STATUS/EVENT : " + TYPE_ID);
                    newStatusEvent.setTypeId(TYPE_ID);
                }
                if (iKeyValuePair != null) {
                    newStatusEvent.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newStatusEvent.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addStatusEvent.getStatusId());
                if (statusDesc != null) {
                    newStatusEvent.setStatusDescription(statusDesc);
                }
                newStatusEvent.setDeletionIndicator(0L);
                newStatusEvent.setCreatedBy(loginUserID);
                newStatusEvent.setCreatedOn(new Date());
                newStatusEvent.setUpdatedBy(loginUserID);
                newStatusEvent.setUpdatedOn(new Date());
                return statusEventRepository.save(newStatusEvent);
            }
        } catch (Exception e) {
            // Error Log
            createStatusEventLog2(addStatusEvent, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param typeId
     * @param updateStatusEvent
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public StatusEvent updateStatusEvent(String companyId, String languageId, String typeId,
                                         UpdateStatusEvent updateStatusEvent, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try{
            StatusEvent dbStatusEvent=getStatusEvent(companyId,languageId,typeId);
            BeanUtils.copyProperties(updateStatusEvent, dbStatusEvent,CommonUtils.getNullPropertyNames(updateStatusEvent));
            if (updateStatusEvent.getStatusId() != null && !updateStatusEvent.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateStatusEvent.getStatusId());
                if (statusDesc != null) {
                    dbStatusEvent.setStatusDescription(statusDesc);
                }
            }
            dbStatusEvent.setUpdatedBy(loginUserID);
            dbStatusEvent.setUpdatedOn(new Date());
            return statusEventRepository.save(dbStatusEvent);
        }catch (Exception e) {
            // Error Log
            createStatusEventLog(companyId, languageId, typeId, e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param typeId
     * @param loginUserID
     */
    public void deleteStatusEvent(String companyId, String languageId, String typeId, String loginUserID) {

        StatusEvent dbStatusEvent = getStatusEvent(companyId, languageId, typeId);
        if (dbStatusEvent != null) {
            dbStatusEvent.setDeletionIndicator(1L);
            dbStatusEvent.setUpdatedBy(loginUserID);
            dbStatusEvent.setUpdatedOn(new Date());
            statusEventRepository.save(dbStatusEvent);
        } else {
            // Error Log
            createStatusEventLog1(companyId, languageId, typeId,"Error in deleting typeId - " + typeId);
            throw new BadRequestException("Error in deleting TypeId - " + typeId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /***
     * Get all
     *
     * @return
     */
    public List<ReplicaStatusEvent> getAllStatusEvent() {
        List<ReplicaStatusEvent> statusEventList = replicaStatusEventRepository.findAll();
        statusEventList = statusEventList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return statusEventList;
    }

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param typeId
     * @return
     */
    public ReplicaStatusEvent getReplicaStatusEvent(String companyId, String languageId, String typeId){

        Optional<ReplicaStatusEvent> dbStatusEvent = replicaStatusEventRepository.findByCompanyIdAndLanguageIdAndTypeIdAndDeletionIndicator
                (companyId,languageId,typeId,0l);
        if (dbStatusEvent.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + "and typeId - " + typeId
                    + " doesn't exists";
            // Error Log
            createStatusEventLog1(companyId, languageId, typeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbStatusEvent.get();
    }

    /**
     * Find
     *
     * @param findStatusEvent
     * @return
     * @throws ParseException
     */
    public List<ReplicaStatusEvent> findStatusEvent(FindStatusEvent findStatusEvent) throws ParseException {
        ReplicaStatusEventSpecification spec = new ReplicaStatusEventSpecification(findStatusEvent);
        List<ReplicaStatusEvent> results = replicaStatusEventRepository.findAll(spec);
        log.info("found Replica StatusEvent --> " + results);
        return results;
    }

    //==============================================StatusEvent_ErrorLog==================================================
    private void createStatusEventLog( String companyId, String languageId,String typeId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(typeId);
        errorLog.setMethod("Exception thrown in updateStatusEvent");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createStatusEventLog1( String companyId,String languageId, String typeId,String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(typeId);
        errorLog.setMethod("Exception thrown in getStatusEvent");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createStatusEventLog2(AddStatusEvent addStatusEvent, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addStatusEvent.getLanguageId());
        errorLog.setCompanyId(addStatusEvent.getCompanyId());
        errorLog.setRefDocNumber(addStatusEvent.getTypeId());
        errorLog.setMethod("Exception thrown in createStatusEvent");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}







