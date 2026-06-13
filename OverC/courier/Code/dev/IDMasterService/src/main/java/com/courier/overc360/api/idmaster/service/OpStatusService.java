package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.opstatus.AddOpStatus;
import com.courier.overc360.api.idmaster.primary.model.opstatus.OpStatus;
import com.courier.overc360.api.idmaster.primary.model.opstatus.UpdateOpStatus;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.OpStatusRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.opStatus.FindOpStatus;
import com.courier.overc360.api.idmaster.replica.model.opStatus.ReplicaOpStatus;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaOpStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaOpStatusSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OpStatusService {

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private OpStatusRepository opStatusRepository;

    @Autowired
    private ReplicaOpStatusRepository replicaOpStatusRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get OpStatus
     *
     * @param companyId
     * @param languageId
     * @param statusCode
     * @return
     */
    public OpStatus getOpStatus(String languageId, String companyId, String statusCode) {

        Optional<OpStatus> dbOpStatus = opStatusRepository.findByLanguageIdAndCompanyIdAndStatusCodeAndDeletionIndicator(
                languageId, companyId, statusCode, 0L);
        if (dbOpStatus.isEmpty()) {
            throw new BadRequestException("The given values : languageId - " +
                    languageId + " and companyId - " + companyId + " and statusCode - " + statusCode + " doesn't exist ");
        }
        return dbOpStatus.get();
    }

    /**
     * Create OpStatus
     *
     * @param addOpStatus
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public OpStatus createOpStatus(AddOpStatus addOpStatus, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<OpStatus> duplicateOpStatus = opStatusRepository.findByLanguageIdAndCompanyIdAndStatusCodeAndDeletionIndicator(
                    addOpStatus.getLanguageId(), addOpStatus.getCompanyId(), addOpStatus.getStatusCode(), 0L);
            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addOpStatus.getCompanyId(), addOpStatus.getLanguageId(), 0L);

            if (dbCompany.isEmpty()) {
                throw new BadRequestException("CompanyId - " + addOpStatus.getCompanyId()
                        + " and LanguageId - " + addOpStatus.getLanguageId() + " doesn't exists");
            } else if (duplicateOpStatus.isPresent()) {
                throw new BadRequestException("Record is Getting Duplicated with the given values : statusCode - " + addOpStatus.getStatusCode());
            } else {
                log.info("new OpStatus --> " + addOpStatus);
                OpStatus dbOpStatus = new OpStatus();
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addOpStatus.getLanguageId(), addOpStatus.getCompanyId());
                BeanUtils.copyProperties(addOpStatus, dbOpStatus, CommonUtils.getNullPropertyNames(addOpStatus));
                if ((addOpStatus.getStatusCode() != null &&
                        (addOpStatus.getReferenceField10() != null && addOpStatus.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addOpStatus.getStatusCode() == null || addOpStatus.getStatusCode().isBlank()) {
                    String NUM_RAN_OBJ = "OPSTATUS";
                    String STATUS_CODE = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for STATUS_CODE : " + STATUS_CODE);
                    dbOpStatus.setStatusCode(STATUS_CODE);
                }
                if (iKeyValuePair != null) {
                    dbOpStatus.setLanguageDescription(iKeyValuePair.getLangDesc());
                    dbOpStatus.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                dbOpStatus.setDeletionIndicator(0L);
                dbOpStatus.setCreatedBy(loginUserID);
                dbOpStatus.setUpdatedBy(loginUserID);
                dbOpStatus.setCreatedOn(new Date());
                dbOpStatus.setUpdatedOn(new Date());
                return opStatusRepository.save(dbOpStatus);
            }
        } catch (Exception e) {
            //Error log
            createOpStatusLog2(addOpStatus, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update OpStatus
     *
     * @param statusCode
     * @param languageId
     * @param companyId
     * @param loginUserID
     * @param updateOpStatus
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    @Transactional
    public OpStatus updateOpStatus(String languageId, String companyId, String statusCode, String loginUserID, UpdateOpStatus updateOpStatus)
            throws IllegalAccessException, InvocationTargetException, ParseException, IOException, CsvException {
        try {
            OpStatus dbOpStatus = getOpStatus(languageId, companyId, statusCode);
            BeanUtils.copyProperties(updateOpStatus, dbOpStatus, CommonUtils.getNullPropertyNames(updateOpStatus));
            dbOpStatus.setUpdatedBy(loginUserID);
            dbOpStatus.setUpdatedOn(new Date());
            return opStatusRepository.save(dbOpStatus);
        } catch (Exception e) {
            //Error log
            createOpStatusLog(languageId, companyId, statusCode, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete OpStatus
     *
     * @param languageId
     * @param companyId
     * @param statusCode
     * @param loginUserID
     */
    public void deleteOpStatus(String languageId, String companyId, String statusCode, String loginUserID) {
        OpStatus dbOpStatus = getOpStatus(languageId, companyId, statusCode);
        if (dbOpStatus != null) {
            dbOpStatus.setDeletionIndicator(1L);
            dbOpStatus.setUpdatedBy(loginUserID);
            dbOpStatus.setUpdatedOn(new Date());
            opStatusRepository.save(dbOpStatus);
        } else {
            //Error log
            createOpStatusLog1(languageId, companyId, statusCode, "Error in deleting statusCode - " + statusCode);
            throw new EntityNotFoundException("Error in deleting statusCode - " + statusCode);
        }
    }

    /*======================================================REPLICA=====================================================*/


    /**
     * Get All Status Details
     *
     * @return
     */
    public List<ReplicaOpStatus> getAllOpStatus() {
        List<ReplicaOpStatus> opStatusList = replicaOpStatusRepository.findAll();
        opStatusList = opStatusList.stream().filter(n -> n.getDeletionIndicator() == 0L).collect(Collectors.toList());
        return opStatusList;
    }

    /**
     * Get OpStatus
     *
     * @param companyId
     * @param languageId
     * @param statusCode
     * @return
     */
    public ReplicaOpStatus replicaGetOpStatus(String languageId, String companyId, String statusCode) {

        Optional<ReplicaOpStatus> dbOpStatus = replicaOpStatusRepository.findByLanguageIdAndCompanyIdAndStatusCodeAndDeletionIndicator(
                languageId, companyId, statusCode, 0L);
        if (dbOpStatus.isEmpty()) {
            throw new BadRequestException("The given values : languageId - " +
                    languageId + " and companyId - " + companyId + " and statusCode - " + statusCode + " doesn't exist ");
        }
        return dbOpStatus.get();
    }

    /**
     * Find OpStatus
     *
     * @param findOpStatus
     * @return
     */
    public List<ReplicaOpStatus> findOpStatus(FindOpStatus findOpStatus) {

        ReplicaOpStatusSpecification oSpec = new ReplicaOpStatusSpecification(findOpStatus);
        List<ReplicaOpStatus> results = replicaOpStatusRepository.findAll(oSpec);
        log.info("results: " + results);
        return results;
    }


    //=============================================OpStatus_ErrorLog=======================================================
    private void createOpStatusLog(String languageId, String companyId, String statusCode, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(statusCode);
        errorLog.setMethod("Exception thrown in updateOpStatus");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createOpStatusLog1(String languageId, String companyId, String statusCode, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(statusCode);
        errorLog.setMethod("Exception thrown in getOpStatus");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createOpStatusLog2(AddOpStatus addOpStatus, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addOpStatus.getLanguageId());
        errorLog.setCompanyId(addOpStatus.getCompanyId());
        errorLog.setRefDocNumber(addOpStatus.getStatusCode());
        errorLog.setMethod("Exception thrown in createOpStatus");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}


