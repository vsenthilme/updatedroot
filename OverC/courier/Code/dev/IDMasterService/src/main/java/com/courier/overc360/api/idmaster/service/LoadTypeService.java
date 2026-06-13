package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.loadtype.AddLoadType;
import com.courier.overc360.api.idmaster.primary.model.loadtype.LoadType;
import com.courier.overc360.api.idmaster.primary.model.loadtype.UpdateLoadType;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.LoadTypeRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.loadtype.FindLoadType;
import com.courier.overc360.api.idmaster.replica.model.loadtype.ReplicaLoadType;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaLoadTypeRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaLoadTypeSpecification;
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

@Slf4j
@Service
public class LoadTypeService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private LoadTypeRepository loadTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReplicaLoadTypeRepository replicaLoadTypeRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    /*======================================================PRIMARY=============================================================*/

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param loadTypeId
     * @return
     */
    public LoadType getLoadType(String loadTypeId, String languageId, String companyId) {

        Optional<LoadType> dbLoadType = loadTypeRepository.findByLoadTypeIdAndLanguageIdAndCompanyIdAndDeletionIndicator
                (loadTypeId, languageId, companyId, 0l);
        if (dbLoadType.isEmpty()) {
            // Error Log
            createLoadTypeLog1(languageId, companyId, loadTypeId, "LoadTypeId - " + loadTypeId + " and given values doesn't exists");
            throw new BadRequestException("The given values : companyId - " + companyId + " languageId - " + languageId +
                    " LoadTypeId - " + loadTypeId + " doesn't exists");
        }
        return dbLoadType.get();
    }

    /**
     * Create LoadType
     *
     * @param addLoadType
     * @param loginUserID
     * @return
     */
    @Transactional
    public LoadType createLoadType(AddLoadType addLoadType, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addLoadType.getCompanyId(), addLoadType.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addLoadType.getCompanyId() + " and LanguageId - " + addLoadType.getLanguageId() + " doesn't exists");
            }

            boolean duplicateLoadType = replicaLoadTypeRepository.existsByLoadTypeIdAndLanguageIdAndCompanyIdAndDeletionIndicator(
                    addLoadType.getLoadTypeId(), addLoadType.getLanguageId(), addLoadType.getCompanyId(), 0L);
            if (duplicateLoadType) {
                throw new BadRequestException("Record is getting duplicated with loadTypeId - " + addLoadType.getLoadTypeId());
            }

//            String normalInput = normalizeString(addLoadType.getLoadTypeText());
//            List<String> loadTypeNames = replicaLoadTypeRepository.getLoadTypeNames();
//            for (String loadTypeNm : loadTypeNames) {
//                if (normalizeString(loadTypeNm).equalsIgnoreCase(normalInput)) {
//                    log.info("db loadType --> {}, new record's Input --> {}", loadTypeNm, normalInput);
//                    throw new BadRequestException("There is already a record with loadType - " + addLoadType.getLoadTypeText());
//                }
//            }

//            if (loadTypeNames.contains(addLoadType.getLoadTypeText())) {
//                throw new BadRequestException("There is already a record with loadType - " + addLoadType.getLoadTypeText());
//            }

            log.debug("new LoadType --> {}", addLoadType);
            LoadType newLoadType = new LoadType();
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addLoadType.getLanguageId(), addLoadType.getCompanyId());
            BeanUtils.copyProperties(addLoadType, newLoadType, CommonUtils.getNullPropertyNames(addLoadType));
            if ((addLoadType.getLoadTypeId() != null &&
                    (addLoadType.getReferenceField10() != null && addLoadType.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addLoadType.getLoadTypeId() == null || addLoadType.getLoadTypeId().isBlank()) {
                String NUM_RAN_OBJ = "LOADTYPE";
                String LOAD_TYPE_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for LOAD_TYPE_ID : {}", LOAD_TYPE_ID);
                newLoadType.setLoadTypeId(LOAD_TYPE_ID);
            }
            if (iKeyValuePair != null) {
                newLoadType.setLanguageDescription(iKeyValuePair.getLangDesc());
                newLoadType.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addLoadType.getStatusId());
            if (statusDesc != null) {
                newLoadType.setStatusDescription(statusDesc);
            }
            newLoadType.setDeletionIndicator(0L);
            newLoadType.setCreatedBy(loginUserID);
            newLoadType.setCreatedOn(new Date());
            newLoadType.setUpdatedBy(loginUserID);
            newLoadType.setUpdatedOn(new Date());
            return loadTypeRepository.save(newLoadType);
        } catch (Exception e) {
            // Error Log
            createLoadTypeLog2(addLoadType, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String normalizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.toLowerCase().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Update
     *
     * @param loadTypeId
     * @param languageId
     * @param companyId
     * @param updateLoadType
     * @param loginUserID
     * @return
     */
    public LoadType updateLoadType(String loadTypeId, String languageId, String companyId, UpdateLoadType updateLoadType, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            LoadType dbLoadType = getLoadType(loadTypeId, languageId, companyId);
            BeanUtils.copyProperties(updateLoadType, dbLoadType, CommonUtils.getNullPropertyNames(updateLoadType));
            if (updateLoadType.getStatusId() != null && !updateLoadType.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateLoadType.getStatusId());
                if (statusDesc != null) {
                    dbLoadType.setStatusDescription(statusDesc);
                }
            }
            dbLoadType.setUpdatedBy(loginUserID);
            dbLoadType.setUpdatedOn(new Date());
            return loadTypeRepository.save(dbLoadType);
        } catch (Exception e) {
            // Error Log
            createLoadTypeLog(languageId, companyId, loadTypeId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * delete
     *
     * @param loadTypeId
     * @param languageId
     * @param companyId
     * @param loginUserID
     */
    public void deleteLoadType(String loadTypeId, String languageId, String companyId, String loginUserID) {

        LoadType dbLoadType = getLoadType(loadTypeId, languageId, companyId);
        if (dbLoadType != null) {
            dbLoadType.setDeletionIndicator(1L);
            dbLoadType.setUpdatedBy(loginUserID);
            dbLoadType.setUpdatedOn(new Date());
            loadTypeRepository.save(dbLoadType);
        } else {
            // Error Log
            createLoadTypeLog1(languageId, companyId, loadTypeId, "Error in deleting LoadTypeId - " + loadTypeId);
            throw new EntityNotFoundException("Error in deleting LoadTypeId - " + loadTypeId);
        }
    }

    /*=================================================REPLICA=============================================================*/

    /**
     * Get all
     *
     * @return
     */
    public List<ReplicaLoadType> getAllLoadType() {
        List<ReplicaLoadType> loadTypeList = replicaLoadTypeRepository.findAll();
        loadTypeList = loadTypeList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return loadTypeList;
    }

    /**
     * get loadtype
     *
     * @param loadTypeId
     * @param languageId
     * @param companyId
     * @return
     */

    public ReplicaLoadType replicaGetLoadType(String loadTypeId, String languageId, String companyId) {

        Optional<ReplicaLoadType> dbLoadType = replicaLoadTypeRepository.findByLoadTypeIdAndLanguageIdAndCompanyIdAndDeletionIndicator
                (loadTypeId, languageId, companyId, 0l);
        if (dbLoadType.isEmpty()) {
            // Error Log
            createLoadTypeLog1(languageId, companyId, loadTypeId, "LoadTypeId - " + loadTypeId + " and given values doesn't exists");
            throw new BadRequestException("The given values : companyId - " + companyId + " languageId - " + languageId +
                    " LoadTypeId - " + loadTypeId + " doesn't exists");
        }
        return dbLoadType.get();
    }

    public List<ReplicaLoadType> findLoadType(FindLoadType findLoadType) {

        ReplicaLoadTypeSpecification spec = new ReplicaLoadTypeSpecification(findLoadType);
        List<ReplicaLoadType> res = replicaLoadTypeRepository.findAll(spec);
        log.info("results:  " + res);
        return res;
    }

    //=========================================LoadType_ErrorLog====================================================
    private void createLoadTypeLog(String languageId, String companyId, String loadTypeId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(loadTypeId);
        errorLog.setMethod("Exception thrown in updateLoadType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createLoadTypeLog1(String languageId, String companyId, String loadTypeId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(loadTypeId);
        errorLog.setMethod("Exception thrown in getLoadType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createLoadTypeLog2(AddLoadType addLoadType, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addLoadType.getLanguageId());
        errorLog.setCompanyId(addLoadType.getCompanyId());
        errorLog.setRefDocNumber(addLoadType.getLoadTypeId());
        errorLog.setMethod("Exception thrown in createLoadType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
