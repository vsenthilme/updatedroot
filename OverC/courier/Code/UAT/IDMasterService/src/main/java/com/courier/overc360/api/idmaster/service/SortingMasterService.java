package com.courier.overc360.api.idmaster.service;


import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.sortingmaster.AddSortMaster;
import com.courier.overc360.api.idmaster.primary.model.sortingmaster.SortMasterDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.sortingmaster.SortingMaster;
import com.courier.overc360.api.idmaster.primary.model.sortingmaster.UpdateSortMaster;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.SortingMasterRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.sortingmaster.FindSortMaster;
import com.courier.overc360.api.idmaster.replica.model.sortingmaster.ReplicaSortingMaster;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaSortingMasterRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaSortMasterSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SortingMasterService {

    @Autowired
    ErrorLogRepository errorLogRepository;
    @Autowired
    private SortingMasterRepository sortingMasterRepository;

    @Autowired
    private ReplicaSortingMasterRepository replicaSortingMasterRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;
    @Autowired
    private ErrorLogService errorLogService;

    @Transactional
    public SortingMaster createSortMaster(AddSortMaster sortingMaster, String loginUserID) throws IOException, CsvException {

        try {

            boolean valuePresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    sortingMaster.getCompanyId(), sortingMaster.getLanguageId(), 0L);
            if (!valuePresent) {
                throw new BadRequestException("LanguageId - " + sortingMaster.getLanguageId()
                        + ", companyId - " + sortingMaster.getCompanyId()
                        + " doesn't exists");
            }
//            boolean valuePresent = replicaSortingMasterRepository.existsByLanguageIdAndCompanyIdAndSortingIdAndZoneTypeAndDeletionIndicator(
//                    sortingMaster.getLanguageId(), sortingMaster.getCompanyId(), sortingMaster.getSortingId(),
//                    sortingMaster.getZoneType(), 0L);
//            if (!valuePresent) {
//                throw new BadRequestException("SortingId - " + sortingMaster.getSortingId() + ", zoneType - " + sortingMaster.getZoneType()
//                        + ", companyId - " + sortingMaster.getCompanyId()
//                        + " and languageId - " + sortingMaster.getLanguageId() + " doesn't exists");
//            }


                SortingMaster sortingMaster1 = new SortingMaster();
                BeanUtils.copyProperties(sortingMaster, sortingMaster1, CommonUtils.getNullPropertyNames(sortingMaster));
                sortingMaster1.setCreatedBy(loginUserID);
                sortingMaster1.setCreatedOn(new Date());
                sortingMaster1.setUpdatedBy(loginUserID);
                sortingMaster1.setUpdatedOn(new Date());
               return sortingMasterRepository.save(sortingMaster1);

        } catch (Exception e) {
            // Error Log
            createSortMasterStatusLog(sortingMaster, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private void createSortMasterStatusLog(AddSortMaster sortingMaster, String error) throws IOException, CsvException {
            List<ErrorLog> errorLogList = new ArrayList<>();
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(sortingMaster.getLanguageId());
            errorLog.setCompanyId(sortingMaster.getCompanyId());
            errorLog.setMethod("Exception thrown in createSortMaster");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogService.writeLog(errorLogList);
    }






    public SortingMaster getSortMaster(String languageId, String companyId, String sortingId, String zoneType) throws IOException, CsvException {
        Optional<SortingMaster> sortingMaster = sortingMasterRepository.findByLanguageIdAndCompanyIdAndSortingIdAndZoneTypeAndDeletionIndicator(languageId, companyId, sortingId, zoneType, 0L);
        if (sortingMaster.isEmpty()) {
            String errMsg = "The given values - languageId: " + languageId + ", companyId: " + companyId + ", sortingId: " + sortingId + ", zoneType: " + zoneType + " doesn't exists";
            // Error Log
            getSortMasterStatusLog(languageId, companyId, sortingId, zoneType, errMsg);
            throw new BadRequestException(errMsg);
        }
        return sortingMaster.get();
    }

    private void getSortMasterStatusLog(String languageId, String companyId, String sortingId, String zoneType, String errMsg) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setMethod("Exception thrown in get sorting master");
        errorLog.setErrorMessage(errMsg);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    public List<ReplicaSortingMaster> getSortmasterList() {
        List<ReplicaSortingMaster> sortingMasters = replicaSortingMasterRepository.findAll();
        sortingMasters = sortingMasters.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return sortingMasters;

    }



    private void deleteSortMasterLog(String languageId, String companyId, String sortingId, String zoneType, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(languageId);
            errorLog.setCompanyId(companyId);
            errorLog.setMethod("Exception thrown in updatePreAlert");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
    }
    @Transactional
    public SortingMaster updateSortMaster(String sortingId, String languageId, String companyId, String zoneType, UpdateSortMaster sortingMaster, String loginUserID) throws IOException, CsvException {
        try {
              SortingMaster dbSort = getSortMaster(languageId, companyId, sortingId, zoneType);
                BeanUtils.copyProperties(sortingMaster, dbSort, CommonUtils.getNullPropertyNames(sortingMaster));
                dbSort.setUpdatedBy(loginUserID);
                dbSort.setUpdatedOn(new Date());
                return sortingMasterRepository.save(dbSort);
        }
        catch (Exception e) {
            // Error Log
            updateSortMasterStatusLog(languageId,companyId,sortingId,zoneType, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void updateSortMasterStatusLog(String languageId, String companyId, String sortingId, String zoneType, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
           ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(languageId);
            errorLog.setCompanyId(companyId);
            errorLog.setMethod("Exception thrown in updateConsignmentStatus");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
            errorLogService.writeLog(errorLogList);
    }

    public void deleteSortMaster(String sortingId, String languageId, String companyId, String zoneType, String loginUserID) throws IOException, CsvException {
        try {
            SortingMaster dbSort = getSortMaster(languageId, companyId, sortingId, zoneType);
            if(dbSort != null){
                dbSort.setDeletionIndicator(1L);
                dbSort.setUpdatedBy(loginUserID);
                dbSort.setUpdatedOn(new Date());
                sortingMasterRepository.save(dbSort);
            }

        } catch (Exception e) {
            // Error Log
            deleteSortMasterLog(languageId,companyId,zoneType,sortingId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ReplicaSortingMaster getReplicaSortingMaster(String languageId, String companyId, String sortingId, String zoneType) {
        Optional<ReplicaSortingMaster> dbSort = replicaSortingMasterRepository.findByLanguageIdAndCompanyIdAndSortingIdAndZoneTypeAndDeletionIndicator(
                languageId, companyId,sortingId,zoneType, 0L);
        if (dbSort.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId + ", sortingId - " + sortingId +
                    ", zoneType - " + zoneType + " doesn't exists.";
            // Error Log
            createSortMasterLog1(languageId, companyId, zoneType, sortingId ,errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbSort.get();
    }

    private void createSortMasterLog1(String languageId, String companyId, String zoneType, String sortingId, String error) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setMethod("Exception thrown in getCustomer");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    public List<ReplicaSortingMaster> findSortMaster(FindSortMaster findSortMaster) {
        ReplicaSortMasterSpecification spec = new ReplicaSortMasterSpecification(findSortMaster);
        List<ReplicaSortingMaster> results = replicaSortingMasterRepository.findAll(spec);
        log.info("found sort master list --> {}", results);
        return results;
    }

    public List<SortingMaster> createSortMasterBulk(List<AddSortMaster> addSortMasters, String loginUserID) throws IOException, CsvException {
       try {
           List<SortingMaster> sortingMasters = new ArrayList<>();
           for (AddSortMaster addSortMaster : addSortMasters){
               boolean valuePresent = replicaSortingMasterRepository.existsByLanguageIdAndCompanyIdAndSortingIdAndZoneTypeAndDeletionIndicator(
                       addSortMaster.getLanguageId(), addSortMaster.getCompanyId(), addSortMaster.getSortingId(),
                       addSortMaster.getZoneType(), 0L);

           if (!valuePresent) {
               throw new BadRequestException("SortingId - " + addSortMaster.getSortingId() + ", zoneType - " + addSortMaster.getZoneType()
                       + ", companyId - " + addSortMaster.getCompanyId()
                       + " and languageId - " + addSortMaster.getLanguageId() + " doesn't exists");
           }
               SortingMaster sortingMaster1 = new SortingMaster();
               BeanUtils.copyProperties(addSortMaster, sortingMaster1, CommonUtils.getNullPropertyNames(addSortMaster));
               sortingMaster1.setCreatedBy(loginUserID);
               sortingMaster1.setCreatedOn(new Date());
               sortingMaster1.setUpdatedBy(loginUserID);
               sortingMaster1.setUpdatedOn(new Date());
               SortingMaster sortingMaster = sortingMasterRepository.save(sortingMaster1);
               sortingMasters.add(sortingMaster);
       }
           return sortingMasters;
    } catch (Exception e) {
           // Error Log
           createSortMasterLog3(addSortMasters, e.toString());
           e.printStackTrace();
           throw new RuntimeException(e);
       }
}

    private void createSortMasterLog3(List<AddSortMaster> addSortMasters, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        for (AddSortMaster addSortMaster : addSortMasters) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addSortMaster.getLanguageId());
            errorLog.setCompanyId(addSortMaster.getCompanyId());
            errorLog.setMethod("Exception thrown in createCustomer");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    public List<SortingMaster> updateSortMasterBulk(List<UpdateSortMaster> updateSortMasters, String loginUserID) throws IOException, CsvException {

        try{
            List<SortingMaster> updateSortMaster = new ArrayList<>();

            for (UpdateSortMaster updateSortMaster1 : updateSortMasters) {
                SortingMaster sortingMaster = new SortingMaster();
                BeanUtils.copyProperties(sortingMaster, updateSortMaster1, CommonUtils.getNullPropertyNames(sortingMaster));
                sortingMaster.setUpdatedBy(loginUserID);
                sortingMaster.setUpdatedOn(new Date());
                SortingMaster sortingMaster1 = sortingMasterRepository.save(sortingMaster);
                updateSortMaster.add(sortingMaster1);
            }
            return updateSortMaster;
        } catch (Exception e) {
            // Error Log
            createSortMasterLog4(updateSortMasters, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void createSortMasterLog4(List<UpdateSortMaster> updateSortMasters, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateSortMaster updateSortMaster : updateSortMasters) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(updateSortMaster.getLanguageId());
            errorLog.setCompanyId(updateSortMaster.getCompanyId());
            errorLog.setMethod("Exception thrown in createCustomer");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);


    }

    public void deleteSortMasterBulk(List<SortMasterDeleteInput> sortMasterDeleteInputs, String loginUserID) throws IOException, CsvException {
        if (sortMasterDeleteInputs != null && !sortMasterDeleteInputs.isEmpty()) {
            for (SortMasterDeleteInput deleteInput : sortMasterDeleteInputs) {
                List<SortingMaster> dbSortingMaster = getSortMasterListForDelete(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                        deleteInput.getZoneType(), deleteInput.getSortingId());
                log.info("Deleting sort master --> {}", deleteInput.getSortingId());
                for (SortingMaster dbSort : dbSortingMaster) {
                    dbSort.setDeletionIndicator(1L);
                    dbSort.setUpdatedBy(loginUserID);
                    dbSort.setUpdatedOn(new Date());
                    sortingMasterRepository.save(dbSort);
                }
            }

        }

    }

    private List<SortingMaster> getSortMasterListForDelete(String languageId, String companyId, String zoneType, String sortingId) throws IOException, CsvException {
        List<SortingMaster> dbSortMaster = sortingMasterRepository.getSortMastersWithQry(languageId, companyId,
                zoneType, sortingId);
        if (dbSortMaster.isEmpty()) {
            String errMsg = "There are no sort master with given values";
            // Error Log
            createSortMasterLog5(languageId, companyId, sortingId, zoneType, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbSortMaster;
    }

    private void createSortMasterLog5(String languageId, String companyId, String sortingId, String zoneType, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setMethod("Exception thrown in getCustomerListForDelete");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}

