package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.airportcode.AddAirportCode;
import com.courier.overc360.api.idmaster.primary.model.airportcode.AirportCode;
import com.courier.overc360.api.idmaster.primary.model.airportcode.UpdateAirportCode;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.AirportCodeRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.airportcode.FindAirportCode;
import com.courier.overc360.api.idmaster.replica.model.airportcode.ReplicaAirportCode;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaAirportCodeRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCountryRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaAirportCodeSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AirportCodeService {

    @Autowired
    private AirportCodeRepository airportCodeRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaCountryRepository replicaCountryRepository;

    @Autowired
    private ReplicaAirportCodeRepository replicaAirportCodeRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ErrorLogRepository errorLogRepository;
    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Airport Code
     *
     * @param companyId
     * @param languageId
     * @param airportCode
     * @return
     */
    public AirportCode getAirportCode(String companyId, String languageId, String airportCode) {
        Optional<AirportCode> dbAirportCode = airportCodeRepository.findByCompanyIdAndLanguageIdAndAirportCodeAndDeletionIndicator(
                companyId, languageId, airportCode, 0L);
        if (dbAirportCode.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and airportCode - " + airportCode + " doesn't exists";
            // Error Log
            createAirportCodeLog1(languageId, companyId, airportCode, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbAirportCode.get();
    }

    /**
     * Create AirportCode
     *
     * @param addAirportCode
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public AirportCode createAirportCode(AddAirportCode addAirportCode, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addAirportCode.getCompanyId(), addAirportCode.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addAirportCode.getCompanyId()
                        + " and LanguageId - " + addAirportCode.getLanguageId() + " doesn't exists");
            }

            boolean duplicateAirportCode = replicaAirportCodeRepository.existsByCompanyIdAndLanguageIdAndAirportCodeAndDeletionIndicator(
                    addAirportCode.getCompanyId(), addAirportCode.getLanguageId(), addAirportCode.getAirportCode(), 0L);
            if (duplicateAirportCode) {
                throw new BadRequestException("Record is getting Duplicated with the given values : airportCode - " + addAirportCode.getAirportCode());
            }

            log.info("new AirportCode --> {}", addAirportCode);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addAirportCode.getLanguageId(), addAirportCode.getCompanyId());
            AirportCode newAirportCode = new AirportCode();
            BeanUtils.copyProperties(addAirportCode, newAirportCode, CommonUtils.getNullPropertyNames(addAirportCode));
            if ((addAirportCode.getAirportCode() != null &&
                    (addAirportCode.getReferenceField10() != null && addAirportCode.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addAirportCode.getAirportCode() == null || addAirportCode.getAirportCode().isBlank()) {
                String NUM_RAN_OBJ = "AIRPORTCODE";
                String AIRPORT_CODE = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for AIRPORT_CODE : " + AIRPORT_CODE);
                newAirportCode.setAirportCode(AIRPORT_CODE);
            }
            if (iKeyValuePair != null) {
                newAirportCode.setLanguageDescription(iKeyValuePair.getLangDesc());
                newAirportCode.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            if (addAirportCode.getCountryId() != null && !addAirportCode.getCountryId().isEmpty()) {
                String countryDesc = replicaCountryRepository.getCountryDesc(addAirportCode.getCountryId(),
                        addAirportCode.getLanguageId(), addAirportCode.getCompanyId());
                if (countryDesc != null) {
                    newAirportCode.setCountryName(countryDesc);
                }
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addAirportCode.getStatusId());
            if (statusDesc != null) {
                newAirportCode.setStatusDescription(statusDesc);
            }
            newAirportCode.setDeletionIndicator(0L);
            newAirportCode.setCreatedBy(loginUserID);
            newAirportCode.setCreatedOn(new Date());
            newAirportCode.setUpdatedBy(loginUserID);
            newAirportCode.setUpdatedOn(new Date());
            return airportCodeRepository.save(newAirportCode);

        } catch (Exception e) {
            // Error Log
            createAirportCodeLog2(addAirportCode, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update AirportCode
     *
     * @param companyId
     * @param languageId
     * @param airportCode
     * @param loginUserID
     * @param updateAirportCode
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public AirportCode updateAirportCode(String companyId, String languageId, String airportCode, String loginUserID,
                                         UpdateAirportCode updateAirportCode)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            AirportCode dbAirportCode = getAirportCode(companyId, languageId, airportCode);
            BeanUtils.copyProperties(updateAirportCode, dbAirportCode, CommonUtils.getNullPropertyNames(updateAirportCode));
            if (updateAirportCode.getCountryId() != null && !updateAirportCode.getCountryId().isEmpty()) {
                String countryDesc = replicaCountryRepository.getCountryDesc(updateAirportCode.getCountryId(), languageId, companyId);
                if (countryDesc != null) {
                    dbAirportCode.setCountryName(countryDesc);
                }
            }
            if (updateAirportCode.getStatusId() != null && !updateAirportCode.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateAirportCode.getStatusId());
                if (statusDesc != null) {
                    dbAirportCode.setStatusDescription(statusDesc);
                }
            }
            dbAirportCode.setUpdatedBy(loginUserID);
            dbAirportCode.setUpdatedOn(new Date());
            return airportCodeRepository.save(dbAirportCode);
        } catch (Exception e) {
            // Error Log
            createAirportCodeLog(languageId, companyId, airportCode, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete AirportCode
     *
     * @param languageId
     * @param companyId
     * @param airportCode
     * @param loginUserID
     */
    public void deleteAirportCode(String languageId, String companyId, String airportCode, String loginUserID) {
        AirportCode dbAirportCode = getAirportCode(languageId, companyId, airportCode);
        if (dbAirportCode != null) {
            dbAirportCode.setDeletionIndicator(1L);
            dbAirportCode.setUpdatedBy(loginUserID);
            dbAirportCode.setUpdatedOn(new Date());
            airportCodeRepository.save(dbAirportCode);
        } else {
            createAirportCodeLog1(languageId, companyId, airportCode, "Error in deleting AirportCode - " + airportCode);
            throw new BadRequestException("Error in deleting AirportCode - " + airportCode);
        }
    }

    /*=================================================REPLICA=======================================================*/

    /**
     * Get all AirportCode Details
     *
     * @return
     */
    public List<ReplicaAirportCode> getAllAirportCodes() {
        List<ReplicaAirportCode> airportCodeList = replicaAirportCodeRepository.findAll();
        airportCodeList = airportCodeList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return airportCodeList;
    }

    /**
     * Get AirportCode
     *
     * @param languageId
     * @param companyId
     * @param airportCode
     * @return
     */
    public ReplicaAirportCode getReplicaAirportCode(String languageId, String companyId, String airportCode) {

        Optional<ReplicaAirportCode> dbAirportCode = replicaAirportCodeRepository.findByCompanyIdAndLanguageIdAndAirportCodeAndDeletionIndicator
                (languageId, companyId, airportCode, 0L);

        if (dbAirportCode.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and airportCode - " + airportCode + " doesn't exists";
            // Error Log
            createAirportCodeLog1(languageId, companyId, airportCode, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbAirportCode.get();
    }

    /**
     * Find AirportCode
     *
     * @param findAirportCode
     * @return
     */
    public List<ReplicaAirportCode> findAirportCodes(FindAirportCode findAirportCode) {

        ReplicaAirportCodeSpecification spec = new ReplicaAirportCodeSpecification(findAirportCode);
        List<ReplicaAirportCode> results = replicaAirportCodeRepository.findAll(spec);
        log.info("found AirportCodes --> {}", results);
        return results;
    }

    //========================================AirportCode_ErrorLog=================================================
    private void createAirportCodeLog(String languageId, String companyId, String airportCode, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(airportCode);
        errorLog.setMethod("Exception thrown in updateAirportCode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createAirportCodeLog1(String languageId, String companyId, String airportCode, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(airportCode);
        errorLog.setMethod("Exception thrown in getAirportCode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createAirportCodeLog2(AddAirportCode addAirportCode, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addAirportCode.getLanguageId());
        errorLog.setCompanyId(addAirportCode.getCompanyId());
        errorLog.setRefDocNumber(addAirportCode.getAirportCode());
        errorLog.setMethod("Exception thrown in createAirportCode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}



