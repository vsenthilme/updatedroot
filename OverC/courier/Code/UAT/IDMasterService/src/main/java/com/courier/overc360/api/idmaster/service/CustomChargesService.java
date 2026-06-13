package com.courier.overc360.api.idmaster.service;


import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.customcharges.AddCustomCharge;
import com.courier.overc360.api.idmaster.primary.model.customcharges.CustomCharges;
import com.courier.overc360.api.idmaster.primary.model.customcharges.UpdateCustomCharge;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.menu.Menu;
import com.courier.overc360.api.idmaster.primary.repository.CustomChargesRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.customcharges.FindCustomCharge;
import com.courier.overc360.api.idmaster.replica.model.customcharges.ReplicaCustomCharges;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCustomChargeRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCustomChargeSpecification;
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
public class CustomChargesService {

    @Autowired
    private CustomChargesRepository customChargesRepository;

    @Autowired
    private ReplicaCustomChargeRepository replicaCustomChargeRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;
    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;



    public List<CustomCharges> createCustomCharge(List<AddCustomCharge> customChargesList, String loginUserID) throws IOException, CsvException {

            List<CustomCharges> customCharges = new ArrayList<>();
            for(AddCustomCharge customCharges1 : customChargesList) {
                CustomCharges newCustomCharge = createCustom(customCharges1, loginUserID);
                customCharges.add(newCustomCharge);
            }
            return customCharges;
    }

    public CustomCharges createCustom(AddCustomCharge customCharges1, String loginUserID) throws IOException, CsvException {
        try{
            boolean dbCustom = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    customCharges1.getCompanyId(), customCharges1.getLanguageId(), 0L);
            if (!dbCustom) {
                throw new BadRequestException("CompanyId - " + customCharges1.getCompanyId()
                        + " and LanguageId - " + customCharges1.getLanguageId() + " doesn't exists");
            }

            boolean duplicateCustomPresent = replicaCustomChargeRepository.existsByLanguageIdAndCompanyIdAndDeletionIndicator(
                    customCharges1.getLanguageId(), customCharges1.getCompanyId(), 0L);
            if (duplicateCustomPresent) {
                throw new BadRequestException("Record is Getting Duplicated with the given values : languageId - " + customCharges1.getLanguageId());
            }
            CustomCharges customCharges = new CustomCharges();
//            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(customCharges1.getLanguageId(), customCharges1.getCompanyId());
            BeanUtils.copyProperties(customCharges1,customCharges, CommonUtils.getNullPropertyNames(customCharges1));
            String statusDesc = replicaStatusRepository.getStatusDescription(customCharges1.getStatusId());
            if (statusDesc != null) {
                customCharges.setStatusDescription(statusDesc);
            }
            customCharges.setCreatedBy(loginUserID);
            customCharges.setCreatedOn(new Date());
            customCharges.setUpdatedBy(loginUserID);
            customCharges.setUpdatedOn(new Date());
           return customChargesRepository.save(customCharges);
        }  catch (Exception e) {
            // Error Log
            createCustomChargesLog(customCharges1, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void createCustomChargesLog(AddCustomCharge customChargesList, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();

            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(customChargesList.getLanguageId());
            errorLog.setCompanyId(customChargesList.getCompanyId());
            errorLog.setMethod("Exception thrown in createSortMaster");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);

            errorLogService.writeLog(errorLogList);
    }

    @Transactional
    public List<CustomCharges> updateCustomCharge(List<UpdateCustomCharge> customCharges, String loginUserID) throws IOException, CsvException {
     try {
         List<CustomCharges> customCharges1 = new ArrayList<>();
         for(UpdateCustomCharge customCharges2 : customCharges){
             CustomCharges customCharges3 = getCustomCharges(customCharges2.getLanguageId(),customCharges2.getCompanyId());
             BeanUtils.copyProperties(customCharges2,customCharges3,CommonUtils.getNullPropertyNames(customCharges2));
             customCharges3.setUpdatedBy(loginUserID);
             customCharges3.setUpdatedOn(new Date());
             CustomCharges customCharges4 = customChargesRepository.save(customCharges3);
             customCharges1.add(customCharges4);
         }
         return customCharges1;
     } catch (Exception e) {
         // Error Log
         updateCustomChargeLog1(customCharges, e.toString());
         e.printStackTrace();
         throw new RuntimeException(e);
     }

    }

    private void updateCustomChargeLog1(List<UpdateCustomCharge> customCharges, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateCustomCharge customCharges1 : customCharges) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(customCharges1.getLanguageId());
            errorLog.setCompanyId(customCharges1.getCompanyId());
            errorLog.setMethod("Exception thrown in updateConsignmentStatus");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    public CustomCharges getCustomCharges(String languageId, String companyId) throws IOException, CsvException {
        Optional<CustomCharges> customCharges = customChargesRepository.findByLanguageIdAndCompanyIdAndDeletionIndicator(
                languageId, companyId, 0L);
        if (customCharges.isEmpty()) {
            String errMsg = "The given values - languageId: " + languageId + ", companyId: " + companyId + " doesn't exists";
            // Error Log
            updateCustomChargeLog(languageId, companyId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return customCharges.get();
    }

    private void updateCustomChargeLog(String languageId, String companyId, String errMsg) throws IOException, CsvException {
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

    public List<ReplicaCustomCharges> getAllCustomCharge() {

        List<ReplicaCustomCharges> customCharges = replicaCustomChargeRepository.findAll();
        customCharges = customCharges.stream().filter(c -> c.getDeletionIndicator() == 0).collect(Collectors.toList());
        return customCharges;

    }

    public void deleteCustomCharge(List<CustomCharges> customCharges, String loginUserID) throws IOException, CsvException {
        try {
            if(customCharges != null || !customCharges.isEmpty()){
                for(CustomCharges customCharges1 : customCharges){
                    CustomCharges customCharges2 = getCustomCharges(customCharges1.getLanguageId(),customCharges1.getCompanyId());

                    if(customCharges2 != null){
                        customCharges2.setDeletionIndicator(1L);
                        customCharges2.setUpdatedBy(loginUserID);
                        customCharges2.setUpdatedOn(new Date());
                        customChargesRepository.save(customCharges2);
                    }
                }
            }
        } catch (Exception e) {
            // Error Log
            deleteCustomChargeLog(customCharges, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void deleteCustomChargeLog(List<CustomCharges> customCharges, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        for (CustomCharges customCharges1 : customCharges) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(customCharges1.getLanguageId());
            errorLog.setCompanyId(customCharges1.getCompanyId());
            errorLog.setMethod("Exception thrown in updatePreAlert");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);

    }

    public CustomCharges createNewCustomCharge(AddCustomCharge addCustomCharge, String loginUserID) throws IOException, CsvException {
        try {
            boolean valuePresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addCustomCharge.getCompanyId(), addCustomCharge.getLanguageId(), 0L);
            if (!valuePresent) {
                throw new BadRequestException("LanguageId - " + addCustomCharge.getLanguageId()
                        + ", companyId - " + addCustomCharge.getCompanyId()
                        + " doesn't exists");
            }

//            boolean duplicateEvent = replicaCustomChargeRepository.existsByLanguageIdAndCompanyIdAndDeletionIndicator(
//                    addCustomCharge.getLanguageId(), addCustomCharge.getCompanyId(), 0L);
//            if (duplicateEvent) {
//                throw new BadRequestException("Record is getting Duplicated with the given values : EventCode - " + addEvent.getEventCode());
//            }


            CustomCharges customCharges = new CustomCharges();
            BeanUtils.copyProperties(addCustomCharge, customCharges, CommonUtils.getNullPropertyNames(addCustomCharge));
            customCharges.setCreatedBy(loginUserID);
            customCharges.setCreatedOn(new Date());
            customCharges.setUpdatedBy(loginUserID);
            customCharges.setUpdatedOn(new Date());
            return customChargesRepository.save(customCharges);

        } catch (Exception e) {
            // Error Log
            createCustomChargeStatusLog(addCustomCharge, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    private void createCustomChargeStatusLog(AddCustomCharge addCustomCharge, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addCustomCharge.getLanguageId());
        errorLog.setCompanyId(addCustomCharge.getCompanyId());
        errorLog.setMethod("Exception thrown in createSortMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    public CustomCharges updateOneCustomCharge(String languageId, String companyId, UpdateCustomCharge updateCustomCharge, String loginUserID) throws IOException, CsvException {
        try {
            CustomCharges dbCustom = getCustomCharge(languageId, companyId);
            BeanUtils.copyProperties(updateCustomCharge, dbCustom, CommonUtils.getNullPropertyNames(updateCustomCharge));
            dbCustom.setUpdatedBy(loginUserID);
            dbCustom.setUpdatedOn(new Date());
            return customChargesRepository.save(dbCustom);
        }
        catch (Exception e) {
            // Error Log
            updateCustomChargeStatusLog(languageId,companyId,e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void updateCustomChargeStatusLog(String languageId, String companyId, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setMethod("Exception thrown in updateCustomCharge");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private CustomCharges getCustomCharge(String languageId, String companyId) throws IOException, CsvException {
        Optional<CustomCharges> customCharges = customChargesRepository.findByLanguageIdAndCompanyIdAndDeletionIndicator(languageId, companyId,0L);
        if (customCharges.isEmpty()) {
            String errMsg = "The given values - languageId: " + languageId + ", companyId: " + companyId + ", sortingId: " + " doesn't exists";
            // Error Log
            getCustomChargeStatusLog(languageId, companyId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return customCharges.get();
    }

    private void getCustomChargeStatusLog(String languageId, String companyId, String error) throws IOException, CsvException {
        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setMethod("Exception thrown in updateCustomCharge");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    public List<ReplicaCustomCharges> findCustomCharge(FindCustomCharge findCustomCharge) {
        ReplicaCustomChargeSpecification spec = new ReplicaCustomChargeSpecification(findCustomCharge);
        List<ReplicaCustomCharges> results = replicaCustomChargeRepository.findAll(spec);
        log.info("found custom charge list --> {}", results);
        return results;
    }
}
