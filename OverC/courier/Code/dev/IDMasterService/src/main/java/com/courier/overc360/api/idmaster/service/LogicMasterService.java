package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.logicmaster.AddLogicMaster;
import com.courier.overc360.api.idmaster.primary.model.logicmaster.LogicMaster;
import com.courier.overc360.api.idmaster.primary.model.logicmaster.UpdateLogicMaster;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.LogicMasterRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.logicmaster.FindLogicMaster;
import com.courier.overc360.api.idmaster.replica.model.logicmaster.ReplicaLogicMaster;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaLogicMasterRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaLogicMasterSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LogicMasterService {

    @Autowired
    private ReplicaLogicMasterRepository replicaLogicMasterRepository;

    @Autowired
    private LogicMasterRepository logicMasterRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private NumberRangeService numberRangeService;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/


    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param consoleCountId
     * @return
     */
    public LogicMaster getLogicMaster(String companyId, String languageId, String consoleCountId) {
        Optional<LogicMaster> dblogicMaster = logicMasterRepository.findByCompanyIdAndLanguageIdAndConsoleCountIdAndDeletionIndicator
                (companyId, languageId, consoleCountId, 0L);
        if (dblogicMaster.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + " and consoleCountId - " + consoleCountId + " doesn't exists";
            // Error Log
            createLogicMasterLog1(companyId, languageId, consoleCountId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dblogicMaster.get();
    }

    /**
     * Create
     *
     * @param addLogicMaster
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public LogicMaster createLogicMaster(AddLogicMaster addLogicMaster, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompany = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addLogicMaster.getCompanyId(), addLogicMaster.getLanguageId(), 0L);
            if (!dbCompany) {
                throw new BadRequestException("The given values : CompanyId - " + addLogicMaster.getCompanyId()
                        + " and LanguageId - " + addLogicMaster.getLanguageId() + "  doesn't exists");
            }
            boolean duplicateLogicMaster = replicaLogicMasterRepository.existsByLanguageIdAndCompanyIdAndConsoleCountIdAndDeletionIndicator(
                    addLogicMaster.getLanguageId(), addLogicMaster.getCompanyId(), addLogicMaster.getConsoleCountId(), 0L);
            if (duplicateLogicMaster) {
                throw new BadRequestException("Record is getting duplicated with the given values : consoleCountId - " + addLogicMaster.getConsoleCountId());
            }

            log.info("new LogicMaster --> {}", addLogicMaster);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addLogicMaster.getLanguageId(), addLogicMaster.getCompanyId());
            LogicMaster newLogicMaster = new LogicMaster();
            BeanUtils.copyProperties(addLogicMaster, newLogicMaster, CommonUtils.getNullPropertyNames(addLogicMaster));
            if (iKeyValuePair != null) {
                newLogicMaster.setLanguageDescription(iKeyValuePair.getLangDesc());
                newLogicMaster.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addLogicMaster.getStatusId());
            if (statusDesc != null) {
                newLogicMaster.setStatusDescription(statusDesc);
            }
            //Save without spacing
//            newLogicMaster.setConsoleCountId(newLogicMaster.getConsoleCountId().replaceAll("\\s+", ""));

            if ((addLogicMaster.getConsoleCountId() != null &&
                    (addLogicMaster.getReferenceField10() != null && addLogicMaster.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addLogicMaster.getConsoleCountId() == null || addLogicMaster.getConsoleCountId().isBlank()) {
                String NUM_RAN_OBJ = "LOGICMASTER";
                String CONSOLE_COUNT_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for CONSOLE_COUNT_ID : {}", CONSOLE_COUNT_ID);
                newLogicMaster.setConsoleCountId(CONSOLE_COUNT_ID);
            }

            newLogicMaster.setDeletionIndicator(0L);
            newLogicMaster.setCreatedBy(loginUserID);
            newLogicMaster.setUpdatedBy(loginUserID);
            newLogicMaster.setCreatedOn(new Date());
            newLogicMaster.setUpdatedOn(new Date());
            return logicMasterRepository.save(newLogicMaster);

        } catch (Exception e) {
            // Error Log
            createLogicMasterLog2(addLogicMaster, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param consoleCountId
     * @param updateLogicMaster
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public LogicMaster updateLogicMaster(String companyId, String languageId, String consoleCountId,
                                         UpdateLogicMaster updateLogicMaster, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            LogicMaster dbLogicMaster = getLogicMaster(companyId, languageId, consoleCountId);
            BeanUtils.copyProperties(updateLogicMaster, dbLogicMaster, CommonUtils.getNullPropertyNames(updateLogicMaster));

            if(updateLogicMaster.getNoOfShipments() == null) {
                dbLogicMaster.setNoOfShipments(null);
            }
            if(updateLogicMaster.getConsignmentValue() == null) {
                dbLogicMaster.setConsignmentValue(null);
            }

            if (updateLogicMaster.getStatusId() != null && !updateLogicMaster.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateLogicMaster.getStatusId());
                if (statusDesc != null) {
                    dbLogicMaster.setStatusDescription(statusDesc);
                }
            }
            dbLogicMaster.setUpdatedBy(loginUserID);
            dbLogicMaster.setUpdatedOn(new Date());
            return logicMasterRepository.save(dbLogicMaster);
        } catch (Exception e) {
            // Error Log
            createLogicMasterLog(companyId, languageId, consoleCountId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param consoleCountId
     * @param loginUserID
     */
    public void deleteLogicMaster(String companyId, String languageId, String consoleCountId, String loginUserID) {
        LogicMaster dblogicMaster = getLogicMaster(companyId, languageId, consoleCountId);
        if (dblogicMaster != null) {
            dblogicMaster.setDeletionIndicator(1L);
            dblogicMaster.setUpdatedBy(loginUserID);
            dblogicMaster.setUpdatedOn(new Date());
            logicMasterRepository.save(dblogicMaster);
        } else {
            // Error Log
            createLogicMasterLog1(companyId, languageId, consoleCountId, "Error in deleting ConsoleCountId - " + consoleCountId);
            throw new EntityNotFoundException("Error in deleting ConsoleCountId - " + consoleCountId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get all
     *
     * @return
     */
    public List<ReplicaLogicMaster> getAll() {
        List<ReplicaLogicMaster> logicMasterList = replicaLogicMasterRepository.findAll();
        logicMasterList = logicMasterList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return logicMasterList;
    }

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param consoleCountId
     * @return
     */
    public ReplicaLogicMaster getReplicaLogicMaster(String companyId, String languageId, String consoleCountId) {
        Optional<ReplicaLogicMaster> dblogicMaster = replicaLogicMasterRepository.findByCompanyIdAndLanguageIdAndConsoleCountIdAndDeletionIndicator
                (companyId, languageId, consoleCountId, 0L);
        if (dblogicMaster.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + " and consoleCountId - " + consoleCountId + " doesn't exists";
            // Error Log
            createLogicMasterLog1(companyId, languageId, consoleCountId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dblogicMaster.get();
    }

    /**
     * Find
     *
     * @param findLogicMaster
     * @return
     */
    public List<ReplicaLogicMaster> findLogicMaster(FindLogicMaster findLogicMaster) {
        log.info("given Values to fetch LogicMasters --> {}", findLogicMaster);
        ReplicaLogicMasterSpecification spec = new ReplicaLogicMasterSpecification(findLogicMaster);
        List<ReplicaLogicMaster> results = replicaLogicMasterRepository.findAll(spec);
//        log.info("found LogicMaster --> {}", results);
        return results;
    }

    //=========================================Logic_MasterLog====================================================

    private void createLogicMasterLog(String companyId, String languageId, String consoleCountId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(consoleCountId);
        errorLog.setMethod("Exception thrown in updateLogicMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createLogicMasterLog1(String companyId, String languageId, String consoleCountId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(consoleCountId);
        errorLog.setMethod("Exception thrown in getLogicMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createLogicMasterLog2(AddLogicMaster addLogicMaster, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addLogicMaster.getLanguageId());
        errorLog.setCompanyId(addLogicMaster.getCompanyId());
        errorLog.setRefDocNumber(addLogicMaster.getConsoleCountId());
        errorLog.setMethod("Exception thrown in createLogicMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}

