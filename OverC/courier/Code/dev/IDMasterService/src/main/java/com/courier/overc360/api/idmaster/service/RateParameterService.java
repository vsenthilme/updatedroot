package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.rateparameter.AddRateParameter;
import com.courier.overc360.api.idmaster.primary.model.rateparameter.RateParameter;
import com.courier.overc360.api.idmaster.primary.model.rateparameter.UpdateRateParameter;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.RateParameterRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.rateparameter.FindRateParameter;
import com.courier.overc360.api.idmaster.replica.model.rateparameter.ReplicaRateParameter;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaRateParameterRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaRateParameterSpecification;
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
public class RateParameterService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private RateParameterRepository rateParameterRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReplicaRateParameterRepository replicaRateParameterRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get RateParameter
     *
     * @param rateParameterId
     * @param companyId
     * @param languageId
     * @return
     */
    public RateParameter getRateParameter(String rateParameterId, String companyId, String languageId) {

        Optional<RateParameter> dbRateParameter = rateParameterRepository.findByLanguageIdAndCompanyIdAndRateParameterIdAndDeletionIndicator(
                languageId, companyId, rateParameterId, 0L);
        if (dbRateParameter.isEmpty()) {
            createRateParameterLog1(rateParameterId, companyId, languageId, "RateParameterId - " + rateParameterId
                    + ", companyId - " + companyId + "and languageId - " + languageId + " doesn't exists");
            throw new BadRequestException("RateParameterId - " + rateParameterId + ", companyId - " + companyId +
                    "and languageId - " + languageId + " doesn't exists");
        }
        return dbRateParameter.get();
    }

    /**
     * Create new RateParameter
     *
     * @param addRateParameter
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public RateParameter createRateParameter(AddRateParameter addRateParameter, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<RateParameter> duplicateRateParameter = rateParameterRepository.findByLanguageIdAndCompanyIdAndRateParameterIdAndDeletionIndicator(
                    addRateParameter.getLanguageId(), addRateParameter.getCompanyId(), addRateParameter.getRateParameterId(), 0L);

            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addRateParameter.getCompanyId(), addRateParameter.getLanguageId(), 0L);

            if (dbCompany.isEmpty()) {
                throw new BadRequestException("CompanyId - " + addRateParameter.getCompanyId() + ", languageId - " + addRateParameter.getLanguageId() + " doesn't exists");
            } else if (duplicateRateParameter.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : rateParameterId - " + addRateParameter.getRateParameterId());
            } else {
                log.info("new RateParameter --> " + addRateParameter);
                RateParameter newRateParameter = new RateParameter();
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addRateParameter.getLanguageId(), addRateParameter.getCompanyId());
                BeanUtils.copyProperties(addRateParameter, newRateParameter, CommonUtils.getNullPropertyNames(addRateParameter));
                if ((addRateParameter.getRateParameterId() != null &&
                        (addRateParameter.getReferenceField10() != null && addRateParameter.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addRateParameter.getRateParameterId() == null || addRateParameter.getRateParameterId().isBlank()) {
                    String NUM_RAN_OBJ = "RATEPARAMETER";
                    String RATE_PARAMETER_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for RATE_PARAMETER_ID : " + RATE_PARAMETER_ID);
                    newRateParameter.setRateParameterId(RATE_PARAMETER_ID);
                }
                if (iKeyValuePair != null) {
                    newRateParameter.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newRateParameter.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addRateParameter.getStatusId());
                if (statusDesc != null) {
                    newRateParameter.setStatusDescription(statusDesc);
                }
                newRateParameter.setDeletionIndicator(0L);
                newRateParameter.setCreatedBy(loginUserID);
                newRateParameter.setCreatedOn(new Date());
                newRateParameter.setUpdatedBy(loginUserID);
                newRateParameter.setUpdatedOn(new Date());
                return rateParameterRepository.save(newRateParameter);
            }
        } catch (Exception e) {
            // Error Log
            createRateParameterLog2(addRateParameter, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update RateParameter
     *
     * @param companyId
     * @param rateParameterId
     * @param updateRateParameter
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public RateParameter updateRateParameter(String companyId, String rateParameterId, String languageId,
                                             UpdateRateParameter updateRateParameter, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            RateParameter dbRateParameter = getRateParameter(rateParameterId, companyId, languageId);
            BeanUtils.copyProperties(updateRateParameter, dbRateParameter, CommonUtils.getNullPropertyNames(updateRateParameter));
            if (updateRateParameter.getStatusId() != null && !updateRateParameter.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateRateParameter.getStatusId());
                if (statusDesc != null) {
                    dbRateParameter.setStatusDescription(statusDesc);
                }
            }
            dbRateParameter.setUpdatedBy(loginUserID);
            dbRateParameter.setUpdatedOn(new Date());
            return rateParameterRepository.save(dbRateParameter);
        } catch (Exception e) {
            // Error Log
            createRateParameterLog(rateParameterId, companyId, languageId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete RateParameter
     *
     * @param rateParameterId
     * @param companyId
     * @param languageId
     * @param loginUserID
     */
    public void deleteRateParameter(String rateParameterId, String companyId, String languageId, String loginUserID) {

        RateParameter dbRateParameter = getRateParameter(rateParameterId, companyId, languageId);
        if (dbRateParameter != null) {
            dbRateParameter.setDeletionIndicator(1L);
            dbRateParameter.setUpdatedBy(loginUserID);
            dbRateParameter.setUpdatedOn(new Date());
            rateParameterRepository.save(dbRateParameter);
        } else {
            // Error Log
            createRateParameterLog1(rateParameterId, companyId, languageId, "Error in deleting RateParameterId - " + rateParameterId);
            throw new BadRequestException("Error in deleting RateParameterId - " + rateParameterId);
        }
    }
    //===============================================Replica==================================================

    /**
     * Get All RateParameter Details
     *
     * @return
     */
    public List<ReplicaRateParameter> getAllRateParameter() {
        List<ReplicaRateParameter> rateParameterList = replicaRateParameterRepository.findAll();
        rateParameterList = rateParameterList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return rateParameterList;
    }

    /**
     * Get RateParameter
     *
     * @param rateParameterId
     * @param companyId
     * @param languageId
     * @return
     */
    public ReplicaRateParameter replicaGetRateParameter(String rateParameterId, String companyId, String languageId) {

        Optional<ReplicaRateParameter> dbRateParameter = replicaRateParameterRepository.findByLanguageIdAndCompanyIdAndRateParameterIdAndDeletionIndicator(
                languageId, companyId, rateParameterId, 0L);
        if (dbRateParameter.isEmpty()) {
            // Error Log
            createRateParameterLog1(rateParameterId, companyId, languageId, "RateParameterId - " + rateParameterId
                    + ", companyId - " + companyId + "and languageId - " + languageId + " doesn't exists");
            throw new BadRequestException("RateParameterId - " + rateParameterId + ", companyId - " + companyId +
                    "and languageId - " + languageId + " doesn't exists");
        }
        return dbRateParameter.get();
    }

    /**
     * Find RateParameter
     *
     * @param findRateParameter
     * @return
     * @throws ParseException
     */
    public List<ReplicaRateParameter> findRateParameter(FindRateParameter findRateParameter) throws ParseException {

        ReplicaRateParameterSpecification spec = new ReplicaRateParameterSpecification(findRateParameter);
        List<ReplicaRateParameter> results = replicaRateParameterRepository.findAll(spec);
        log.info("found RateParameters --> " + results);
        return results;
    }

    //====================================RateParameter_ErrorLog=======================================================
    private void createRateParameterLog(String rateParameterId, String companyId, String languageId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(rateParameterId);
        errorLog.setErrorMessage(error);
        errorLog.setMethod("Exception thrown in updateRateParameter");
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createRateParameterLog1(String rateParameterId, String companyId, String languageId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(rateParameterId);
        errorLog.setErrorMessage(error);
        errorLog.setMethod("Exception thrown in getRateParameter");
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createRateParameterLog2(AddRateParameter addRateParameter, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addRateParameter.getLanguageId());
        errorLog.setCompanyId(addRateParameter.getCompanyId());
        errorLog.setRefDocNumber(addRateParameter.getRateParameterId());
        errorLog.setErrorMessage(error);
        errorLog.setMethod("Exception thrown in createRateParameter");
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}

