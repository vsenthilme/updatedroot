package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.hub.AddHub;
import com.courier.overc360.api.idmaster.primary.model.hub.Hub;
import com.courier.overc360.api.idmaster.primary.model.hub.UpdateHub;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.HubRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.hub.FindHub;
import com.courier.overc360.api.idmaster.replica.model.hub.ReplicaHub;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaHubRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaProvinceRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaHubSpecification;
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

@Slf4j
@Service
public class HubService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaProvinceRepository replicaProvinceRepository;

    @Autowired
    private HubRepository hubRepository;

    @Autowired
    private ReplicaHubRepository replicaHubRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/


    /**
     * Get Hub
     *
     * @param languageId
     * @param companyId
     * @param hubCode
     * @return
     */
    public Hub getHub(String languageId, String companyId, String hubCode) {

        Optional<Hub> dbHub = hubRepository.findByLanguageIdAndCompanyIdAndHubCodeAndDeletionIndicator(
                languageId, companyId, hubCode, 0L);
        if (dbHub.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId
                    + ", companyId - " + companyId + " and hubCode - " + hubCode + " and doesn't exists";
            // Error Log
            createHubLog1(languageId, companyId, hubCode, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbHub.get();
    }

    /**
     * Create Hub
     *
     * @param addHub
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Hub createHub(AddHub addHub, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addHub.getCompanyId(), addHub.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addHub.getCompanyId() + " and LanguageId - " + addHub.getLanguageId() + " doesn't exists");
            }

            boolean duplicateHubPresent = replicaHubRepository.existsByLanguageIdAndCompanyIdAndHubCodeAndDeletionIndicator(
                    addHub.getLanguageId(), addHub.getCompanyId(), addHub.getHubCode(), 0L);
            if (duplicateHubPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : hubCode - " + addHub.getHubCode());
            }

            log.info("new Hub --> {}", addHub);
            Hub newHub = new Hub();
            IKeyValuePair iKeyValuePair = replicaHubRepository.getDescription(addHub.getLanguageId(), addHub.getCompanyId(),
                    addHub.getCountryId(), addHub.getCityId());
            BeanUtils.copyProperties(addHub, newHub, CommonUtils.getNullPropertyNames(addHub));
            if ((addHub.getHubCode() != null &&
                    (addHub.getReferenceField10() != null && addHub.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addHub.getHubCode() == null || addHub.getHubCode().isBlank()) {
                String NUM_RAN_OBJ = "HUB";
                String HUB_CODE = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for HUB_CODE : " + HUB_CODE);
                newHub.setHubCode(HUB_CODE);
            }
            if (iKeyValuePair != null) {
                newHub.setLanguageDescription(iKeyValuePair.getLangDesc());
                newHub.setCompanyName(iKeyValuePair.getCompanyDesc());
                newHub.setCountryName(iKeyValuePair.getCountryDesc());
                newHub.setCityName(iKeyValuePair.getCityDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addHub.getStatusId());
            if (statusDesc != null) {
                newHub.setStatusDescription(statusDesc);
            }
            if (addHub.getProvinceId() != null) {
                String provinceName = replicaProvinceRepository.getProvinceName(addHub.getLanguageId(), addHub.getCompanyId(),
                        addHub.getCountryId(), addHub.getProvinceId());
                if (provinceName != null) {
                    newHub.setProvinceName(provinceName);
                }
            }
            newHub.setDeletionIndicator(0L);
            newHub.setCreatedBy(loginUserID);
            newHub.setCreatedOn(new Date());
            newHub.setUpdatedBy(loginUserID);
            newHub.setUpdatedOn(new Date());
            return hubRepository.save(newHub);
        } catch (Exception e) {
            // Error Log
            createHubLog2(addHub, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Hub
     *
     * @param languageId
     * @param companyId
     * @param hubCode
     * @param updateHub
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Hub updateHub(String languageId, String companyId, String hubCode, UpdateHub updateHub, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Hub dbHub = getHub(languageId, companyId, hubCode);
            BeanUtils.copyProperties(updateHub, dbHub, CommonUtils.getNullPropertyNames(updateHub));
            if (updateHub.getStatusId() != null && !updateHub.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateHub.getStatusId());
                if (statusDesc != null) {
                    dbHub.setStatusDescription(statusDesc);
                }
            }
            dbHub.setUpdatedBy(loginUserID);
            dbHub.setUpdatedOn(new Date());
            return hubRepository.save(dbHub);
        } catch (Exception e) {
            // Error Log
            createHubLog(languageId, companyId, hubCode, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Hub
     *
     * @param languageId
     * @param companyId
     * @param hubCode
     * @param loginUserID
     */
    public void deleteHub(String languageId, String companyId, String hubCode, String loginUserID) {

        Hub dbHub = getHub(languageId, companyId, hubCode);
        if (dbHub != null) {
            dbHub.setDeletionIndicator(1L);
            dbHub.setUpdatedBy(loginUserID);
            dbHub.setUpdatedOn(new Date());
            hubRepository.save(dbHub);
        } else {
            // Error Log
            createHubLog1(languageId, companyId, hubCode, "Error in deleting Hub Id - " + hubCode);
            throw new BadRequestException("Error in deleting Hub Id - " + hubCode);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All Hub Details
     *
     * @return
     */
    public List<ReplicaHub> getAllHubs() {
        List<ReplicaHub> hubList = replicaHubRepository.findAll();
        hubList = hubList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return hubList;
    }

    /**
     * Get Hub
     *
     * @param languageId
     * @param companyId
     * @param hubCode
     * @return
     */
    public ReplicaHub replicaGetHub(String languageId, String companyId, String hubCode) {

        Optional<ReplicaHub> dbHub = replicaHubRepository.findByLanguageIdAndCompanyIdAndHubCodeAndDeletionIndicator(
                languageId, companyId, hubCode, 0L);
        if (dbHub.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId
                    + ", companyId - " + companyId + " and hubCode - " + hubCode + " and doesn't exists";
            // Error Log
            createHubLog1(languageId, companyId, hubCode, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbHub.get();
    }

    /**
     * Find Hub
     *
     * @param findHub
     * @return
     * @throws ParseException
     */
    public List<ReplicaHub> findHubs(FindHub findHub) throws ParseException {

        ReplicaHubSpecification spec = new ReplicaHubSpecification(findHub);
        List<ReplicaHub> results = replicaHubRepository.findAll(spec);
        log.info("found Hubs --> " + results);
        return results;
    }

    //============================================Hub_ErrorLog=========================================================
    private void createHubLog(String languageId, String companyId, String hubCode, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(hubCode);
        errorLog.setMethod("Exception thrown in updateHub");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createHubLog1(String languageId, String companyId, String hubCode, String error) {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(hubCode);
        errorLog.setMethod("Exception thrown in getHub");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createHubLog2(AddHub addHub, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addHub.getLanguageId());
        errorLog.setCompanyId(addHub.getCompanyId());
        errorLog.setRefDocNumber(addHub.getHubCode());
        errorLog.setMethod("Exception thrown in createHub");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
