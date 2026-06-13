package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.iata.AddIata;
import com.courier.overc360.api.idmaster.primary.model.iata.Iata;
import com.courier.overc360.api.idmaster.primary.model.iata.UpdateIata;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.IataRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.iata.FindIata;
import com.courier.overc360.api.idmaster.replica.model.iata.ReplicaIata;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCurrencyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaIataRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaIataSpecification;
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
public class IataService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCurrencyRepository replicaCurrencyRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private IataRepository iataRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaIataRepository replicaIataRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;


    /**
     * Get Iata
     *
     * @param companyId
     * @param languageId
     * @param origin
     * @param originCode
     * @return
     */

    public Iata getIata(String companyId, String languageId, String origin, String originCode) {

        Optional<Iata> dbIata = iataRepository.findByLanguageIdAndCompanyIdAndOriginAndOriginCodeAndDeletionIndicator(
                languageId, companyId, origin, originCode, 0L);
        if (dbIata.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId
                    + ", origin - " + origin + " and originCode - " + originCode + " doesn't exists";
            createIataLog1(companyId, languageId, origin, originCode, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbIata.get();
    }

    /**
     * Create new Iata
     *
     * @param addIata
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */

    @Transactional
    public Iata createIata(AddIata addIata, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addIata.getCompanyId(), addIata.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addIata.getCompanyId() + " and LanguageId - " + addIata.getLanguageId() + " doesn't exists");
            }

            boolean duplicateIataPresent = replicaIataRepository.existsByLanguageIdAndCompanyIdAndOriginAndOriginCodeAndDeletionIndicator(
                    addIata.getLanguageId(), addIata.getCompanyId(), addIata.getOrigin(), addIata.getOriginCode(), 0L);
            if (duplicateIataPresent) {
                throw new BadRequestException(" Record is getting Duplicated with the given values : originCode - " + addIata.getOriginCode());
            }

            log.info("new Iata --> " + addIata);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addIata.getLanguageId(), addIata.getCompanyId());
            Iata newIata = new Iata();
            BeanUtils.copyProperties(addIata, newIata, CommonUtils.getNullPropertyNames(addIata));
            if ((addIata.getOriginCode() != null &&
                    (addIata.getReferenceField10() != null && addIata.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addIata.getOriginCode() == null || addIata.getOriginCode().isBlank()) {
                String NUM_RAN_OBJ = "ORIGINCODE";
                String ORIGINCODE = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for ORIGINCODE : " + ORIGINCODE);
                newIata.setOriginCode(ORIGINCODE);
            }
            if (iKeyValuePair != null) {
                newIata.setLanguageDescription(iKeyValuePair.getLangDesc());
                newIata.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(newIata.getStatusId());
            if (statusDesc != null) {
                newIata.setStatusDescription(statusDesc);
            }
            String currencyDesc = null;
            if (addIata.getCurrencyId() != null) {
                currencyDesc = replicaCurrencyRepository.getCurrencyDesc(addIata.getCurrencyId());
                if (currencyDesc == null) {
                    throw new BadRequestException("CurrencyId - " + addIata.getCurrencyId() + " doesn't exists");
                }
            }
            if (currencyDesc != null && !currencyDesc.isEmpty()) {
                newIata.setCurrencyDescription(currencyDesc);
            }
            newIata.setDeletionIndicator(0L);
            newIata.setCreatedBy(loginUserID);
            newIata.setCreatedOn(new Date());
            newIata.setUpdatedBy(loginUserID);
            newIata.setUpdatedOn(new Date());
            return iataRepository.save(newIata);
        } catch (Exception e) {
            createIataLog2(addIata, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Iata
     *
     * @param companyId
     * @param languageId
     * @param origin
     * @param originCode
     * @param updateIata
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Iata updateIata(String companyId, String languageId, String origin, String originCode, UpdateIata updateIata, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Iata dbIata = getIata(companyId, languageId, origin, originCode);
            BeanUtils.copyProperties(updateIata, dbIata, CommonUtils.getNullPropertyNames(updateIata));
            if (updateIata.getStatusId() != null && !updateIata.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateIata.getStatusId());
                if (statusDesc != null) {
                    dbIata.setStatusDescription(statusDesc);
                }
            }
            dbIata.setUpdatedBy(loginUserID);
            dbIata.setUpdatedOn(new Date());
            return iataRepository.save(dbIata);
        } catch (Exception e) {
            createIataLog(companyId, languageId, origin, originCode, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Iata
     *
     * @param companyId
     * @param languageId
     * @param origin
     * @param originCode
     * @param loginUserID
     */

    public void deleteIata(String companyId, String languageId, String origin, String originCode, String loginUserID) {

        Iata dbIata = getIata(companyId, languageId, origin, originCode);
        if (dbIata != null) {
            dbIata.setDeletionIndicator(1L);
            dbIata.setUpdatedBy(loginUserID);
            dbIata.setUpdatedOn(new Date());
            iataRepository.save(dbIata);
        } else {
            // Error Log
            createIataLog1(companyId, languageId, origin, originCode, "Error in deleting originCode: " + originCode);
            throw new BadRequestException("Error in deleting originCode: " + originCode);
        }
    }

    //===============================================Replica==================================================

    /**
     * Get All Iata Details
     *
     * @return
     */

    public List<ReplicaIata> getAllIata() {
        List<ReplicaIata> iataList = replicaIataRepository.findAll();
        iataList = iataList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return iataList;
    }

    /**
     * Get Iata
     *
     * @param companyId
     * @param languageId
     * @param origin
     * @param originCode
     * @return
     */

    public ReplicaIata replicaGetIata(String companyId, String languageId, String origin, String originCode) {

        Optional<ReplicaIata> dbIata = replicaIataRepository.findByLanguageIdAndCompanyIdAndOriginAndOriginCodeAndDeletionIndicator(
                languageId, companyId, origin, originCode, 0L);
        if (dbIata.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId
                    + ", origin - " + origin + " and originCode - " + originCode + " doesn't exists";
            createIataLog1(companyId, languageId, origin, originCode, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbIata.get();
    }

    /**
     * Find Iata
     *
     * @param findIata
     * @return
     * @throws ParseException
     */

    public List<ReplicaIata> findIata(FindIata findIata) throws ParseException {

        ReplicaIataSpecification spec = new ReplicaIataSpecification(findIata);
        List<ReplicaIata> results = replicaIataRepository.findAll(spec);
        log.info("found Iata --> " + results);
        return results;
    }

    //    //====================================IATA_ErrorLog=======================================================
    private void createIataLog(String companyId, String languageId, String origin, String originCode, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(originCode);
        errorLog.setReferenceField1(origin);
        errorLog.setErrorMessage(error);
        errorLog.setMethod("Exception thrown in updateIata");
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createIataLog1(String companyId, String languageId, String origin, String originCode, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(originCode);
        errorLog.setReferenceField1(origin);
        errorLog.setErrorMessage(error);
        errorLog.setMethod("Exception thrown in getIata");
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createIataLog2(AddIata addIata, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addIata.getLanguageId());
        errorLog.setCompanyId(addIata.getCompanyId());
        errorLog.setRefDocNumber(addIata.getOriginCode());
        errorLog.setReferenceField1(addIata.getOrigin());
        errorLog.setErrorMessage(error);
        errorLog.setMethod("Exception thrown in createIata");
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
