package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.storagetypemaster.AddStorageTypeMaster;
import com.courier.overc360.api.idmaster.primary.model.storagetypemaster.StorageTypeMaster;
import com.courier.overc360.api.idmaster.primary.model.storagetypemaster.UpdateStorageTypeMaster;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.StorageTypeMasterRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.storagetypemaster.FindStorageTypeMaster;
import com.courier.overc360.api.idmaster.replica.model.storagetypemaster.ReplicaStorageTypeMaster;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStorageTypeMasterRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaStorageTypeMasterSpecification;
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
public class StorageTypeMasterService {

    @Autowired
    private StorageTypeMasterRepository storageTypeMasterRepository;

    @Autowired
    private ReplicaStorageTypeMasterRepository replicaStorageTypeMasterRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ErrorLogRepository errorLogRepository;




    /*======================================================PRIMARY=============================================================*/

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param storageTypeId
     * @return
     */
    public StorageTypeMaster getStorageTypeMaster(String companyId, String languageId, String storageTypeId) {

        Optional<StorageTypeMaster> dbStorageTypeMaster = storageTypeMasterRepository.findByCompanyIdAndLanguageIdAndStorageTypeIdAndDeletionIndicator(
                companyId, languageId, storageTypeId, 0l);
        if (dbStorageTypeMaster.isEmpty()) {
            createStorageTypeMasterLog1(languageId, companyId, storageTypeId, " StorageTypeId - " + storageTypeId + " and given values doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " storageTypeId - " + storageTypeId + " doesn't exists");
        }
        return dbStorageTypeMaster.get();
    }

    /**
     * Create
     *
     * @param addStorageTypeMaster
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public StorageTypeMaster createStorageTypeMaster(AddStorageTypeMaster addStorageTypeMaster, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            replicaStorageTypeMasterRepository.findByCompanyIdAndLanguageIdAndStorageTypeIdAndDeletionIndicator(
                            addStorageTypeMaster.getCompanyId(), addStorageTypeMaster.getLanguageId(), addStorageTypeMaster.getStorageTypeId(), 0l)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : storageTypeId - " + addStorageTypeMaster.getStorageTypeId());
                    });
            companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                            addStorageTypeMaster.getCompanyId(), addStorageTypeMaster.getLanguageId(), 0l)
                    .orElseThrow(() -> new BadRequestException("The given values : CompanyId - " + addStorageTypeMaster.getCompanyId()
                            + " and LanguageId - " + addStorageTypeMaster.getLanguageId() + "  doesn't exists"));
            log.info("new StorageTypeMaster --> " + addStorageTypeMaster);
            StorageTypeMaster newStorageTypeMaster = new StorageTypeMaster();
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addStorageTypeMaster.getLanguageId(), addStorageTypeMaster.getCompanyId());
            BeanUtils.copyProperties(addStorageTypeMaster, newStorageTypeMaster, CommonUtils.getNullPropertyNames(addStorageTypeMaster));
            if (iKeyValuePair != null) {
                newStorageTypeMaster.setCompanyName(iKeyValuePair.getCompanyDesc());
                newStorageTypeMaster.setLanguageDescription(iKeyValuePair.getLangDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addStorageTypeMaster.getStatusId());
            if (statusDesc != null) {
                newStorageTypeMaster.setStatusDescription(statusDesc);
            }
            //Save without spacing
            newStorageTypeMaster.setStorageTypeId(newStorageTypeMaster.getStorageTypeId().replaceAll("\\s", "\\"));

            // StorageTypeId auto-gen
            String storageTypeId = numberRangeService.getNextNumberRange("STORAGETYPEID");
            newStorageTypeMaster.setStorageTypeId(storageTypeId);

            newStorageTypeMaster.setDeletionIndicator(0l);
            newStorageTypeMaster.setCreatedBy(loginUserID);
            newStorageTypeMaster.setCreatedOn(new Date());
            newStorageTypeMaster.setUpdatedBy(loginUserID);
            newStorageTypeMaster.setUpdatedOn(new Date());
            return storageTypeMasterRepository.save(newStorageTypeMaster);
        } catch (Exception e) {
            // Error Log
            createStorageTypeMasterLog2(addStorageTypeMaster, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param storageTypeId
     * @param updateStorageTypeMaster
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */

    @Transactional
    public StorageTypeMaster updateStorageTypeMaster(String companyId, String languageId, String storageTypeId,
                                                     UpdateStorageTypeMaster updateStorageTypeMaster, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            StorageTypeMaster dbStorageTypeMaster = getStorageTypeMaster(companyId, languageId, storageTypeId);
            BeanUtils.copyProperties(updateStorageTypeMaster, dbStorageTypeMaster, CommonUtils.getNullPropertyNames(updateStorageTypeMaster));
            if (updateStorageTypeMaster.getStatusId() != null && !updateStorageTypeMaster.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateStorageTypeMaster.getStatusId());
                if (statusDesc != null) {
                    dbStorageTypeMaster.setStatusDescription(statusDesc);
                }
            }
            dbStorageTypeMaster.setUpdatedOn(new Date());
            dbStorageTypeMaster.setUpdatedBy(loginUserID);
            return storageTypeMasterRepository.save(dbStorageTypeMaster);
        } catch (Exception e) {
            // Error Log
            createStorageTypeMasterLog(companyId, languageId, storageTypeId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param storageTypeId
     * @param loginUserID
     */


    public void deleteStorageTypeMaster(String companyId, String languageId, String storageTypeId, String loginUserID) {

        StorageTypeMaster dbStorageTypeMaster = getStorageTypeMaster(companyId, languageId, storageTypeId);
        if (dbStorageTypeMaster != null) {
            dbStorageTypeMaster.setDeletionIndicator(1L);
            dbStorageTypeMaster.setUpdatedBy(loginUserID);
            dbStorageTypeMaster.setUpdatedOn(new Date());
            storageTypeMasterRepository.save(dbStorageTypeMaster);

        } else {
            // Error Log
            createStorageTypeMasterLog1(companyId, languageId, storageTypeId, "Error in deleting StorageTypeId - " + storageTypeId);
            throw new EntityNotFoundException("Error in deleting StorageTypeId - " + storageTypeId);
        }
    }

    /*======================================================REPLICA=====================================================*/


    public List<ReplicaStorageTypeMaster> getAll() {
        List<ReplicaStorageTypeMaster> storageTypeMasterList = replicaStorageTypeMasterRepository.findAll();
        storageTypeMasterList = storageTypeMasterList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return storageTypeMasterList;
    }


    public ReplicaStorageTypeMaster getReplicaStorageTypeMaster(String companyId, String languageId, String storageTypeId) {
        Optional<ReplicaStorageTypeMaster> dbStorageTypeMaster = replicaStorageTypeMasterRepository.findByCompanyIdAndLanguageIdAndStorageTypeIdAndDeletionIndicator
                (companyId, languageId, storageTypeId, 0l);
        if (dbStorageTypeMaster.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + " and storageTypeId - " + storageTypeId + " doesn't exists";
            // Error Log
            createStorageTypeMasterLog1(companyId, languageId, storageTypeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbStorageTypeMaster.get();
    }

    public List<ReplicaStorageTypeMaster> findStorageTypeMaster(FindStorageTypeMaster findStorageTypeMaster) {
        ReplicaStorageTypeMasterSpecification spec = new ReplicaStorageTypeMasterSpecification(findStorageTypeMaster);
        List<ReplicaStorageTypeMaster> results = replicaStorageTypeMasterRepository.findAll(spec);
        log.info("found StorageTypeMaster --> {}", results);
        return results;
    }

    //=========================================Storage_Type_Master====================================================

    private void createStorageTypeMasterLog(String companyId, String languageId, String storageTypeId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(storageTypeId);
        errorLog.setMethod("Exception thrown in updateStorageTypeMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createStorageTypeMasterLog1(String companyId, String languageId, String storageTypeId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(storageTypeId);
        errorLog.setMethod("Exception thrown in getStorageTypeMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createStorageTypeMasterLog2(AddStorageTypeMaster addStorageTypeMaster, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addStorageTypeMaster.getLanguageId());
        errorLog.setCompanyId(addStorageTypeMaster.getCompanyId());
        errorLog.setRefDocNumber(addStorageTypeMaster.getStorageTypeId());
        errorLog.setMethod("Exception thrown in createStorageTypeMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
