package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.fulFillmentPrice.AddFulfillmentPrice;
import com.courier.overc360.api.idmaster.primary.model.fulFillmentPrice.FulfillmentPrice;
import com.courier.overc360.api.idmaster.primary.model.rate.AddRate;
import com.courier.overc360.api.idmaster.primary.model.rate.DeleteRate;
import com.courier.overc360.api.idmaster.primary.model.rate.Rate;
import com.courier.overc360.api.idmaster.primary.model.rate.UpdateRate;
import com.courier.overc360.api.idmaster.primary.model.rateparameter.RateParameter;
import com.courier.overc360.api.idmaster.primary.repository.*;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.rate.FindRate;
import com.courier.overc360.api.idmaster.replica.model.rate.ReplicaRate;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaRateParameterRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaRateRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaRateSpecification;
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

@Service
@Slf4j
public class RateService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private RateParameterRepository rateParameterRepository;

    @Autowired
    private ReplicaRateParameterRepository replicaRateParameterRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private ReplicaRateRepository replicaRateRepository;

    @Autowired
    private LanguageRepository languageRepository;

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
     * Get Rate
     *
     * @param rateParameterId
     * @param companyId
     * @param languageId
     * @param partnerId
     * @param lineNo
     * @return
     */
    public Rate getRate(String rateParameterId, String companyId, String languageId, String partnerId, Long lineNo) {

        Optional<Rate> dbRate = rateRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndRateParameterIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, rateParameterId, lineNo, 0L);
        if (dbRate.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", RateParameterId - " + rateParameterId + ", LineNo - " + lineNo + " and partnerId - " + partnerId + " doesn't exists";
            //Error log
            createRateLog1(languageId, companyId, partnerId, rateParameterId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbRate.get();
    }

    /**
     * Create Rate
     *
     * @param addRateList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<Rate> createRate(List<AddRate> addRateList, String loginUserID) throws
            IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Rate> rateList = new ArrayList<>();
        try {
            addRateList.stream().forEach(addRate -> {
                Optional<RateParameter> dbRateParameter = rateParameterRepository.findByLanguageIdAndCompanyIdAndRateParameterIdAndDeletionIndicator(
                        addRate.getLanguageId(), addRate.getCompanyId(), addRate.getRateParameterId(), 0L);
                Optional<Rate> duplicateRate = rateRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndRateParameterIdAndLineNoAndDeletionIndicator(
                        addRate.getLanguageId(), addRate.getCompanyId(), addRate.getPartnerId(), addRate.getRateParameterId(), addRate.getLineNo(), 0L);
                if (dbRateParameter.isEmpty()) {
                    throw new BadRequestException("RateParameterId - " + addRate.getRateParameterId() + " doesn't exist");
                } else if (duplicateRate.isPresent()) {
                    throw new BadRequestException("Record is Getting Duplicated with given values : partnerId - " + addRate.getPartnerId());
                } else {
                    log.info("new Rate --> " + addRate);
                    Rate newRate = new Rate();
                    IKeyValuePair iKeyValuePair = replicaRateRepository.getDescription(addRate.getLanguageId(), addRate.getCompanyId(), addRate.getRateParameterId());
                    BeanUtils.copyProperties(addRate, newRate, CommonUtils.getNullPropertyNames(addRate));
                    if (iKeyValuePair != null) {
                        newRate.setLanguageDescription(iKeyValuePair.getLangDesc());
                        newRate.setCompanyName(iKeyValuePair.getCompanyDesc());
                        newRate.setRateParameterDescription(iKeyValuePair.getRateParameterDesc());
                    }
                    String statusDesc = replicaStatusRepository.getStatusDescription(addRate.getStatusId());
                    if (statusDesc != null) {
                        newRate.setStatusDescription(statusDesc);
                    }
                    newRate.setDeletionIndicator(0L);
                    newRate.setCreatedBy(loginUserID);
                    newRate.setUpdatedBy(loginUserID);
                    newRate.setCreatedOn(new Date());
                    newRate.setUpdatedOn(new Date());
                    rateList.add(rateRepository.save(newRate));
                }
            });
        } catch (Exception e) {
            // Error Log
//            createRateLog2(addRate, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return rateList;
    }

    /**
     * Update Rate
     *
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<Rate> updateRate(List<UpdateRate> updateRateList, String loginUserID) {
        List<Rate> rateList = new ArrayList<>();

        for (UpdateRate updatedRate : updateRateList) {
            Optional<Rate> dbRate = rateRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndRateParameterIdAndLineNoAndDeletionIndicator(
                    updatedRate.getLanguageId(), updatedRate.getCompanyId(), updatedRate.getPartnerId(), updatedRate.getRateParameterId(), updatedRate.getLineNo(), 0L);

            if (dbRate.isPresent()) {
                Rate newRate = dbRate.get();
                BeanUtils.copyProperties(updatedRate, newRate, CommonUtils.getNullPropertyNames(updatedRate));

                String statusDesc = replicaRateRepository.getStatusDescription(updatedRate.getStatusId());
                if (statusDesc != null) {
                    newRate.setStatusDescription(statusDesc);
                }
                newRate.setUpdatedOn(new Date());
                newRate.setUpdatedBy(loginUserID);
                Rate rate = rateRepository.save(newRate);
                rateList.add(rate);
            } else {
                Rate newRate = new Rate();
                BeanUtils.copyProperties(updatedRate, newRate, CommonUtils.getNullPropertyNames(updatedRate));
                newRate.setUpdatedBy(loginUserID);
                newRate.setUpdatedOn(new Date());
                rateList.add(rateRepository.save(newRate));
            }
        }
        return rateList;
    }

    /**
     * Delete Rate
     *
     * @param loginUserID
     */
    public void deleteRate(List<DeleteRate> deleteRateList, String loginUserID) {
        deleteRateList.forEach(rate -> {
            Rate dbRate = getRate(rate.getRateParameterId(), rate.getCompanyId(), rate.getLanguageId(), rate.getPartnerId(), rate.getLineNo());
            if (dbRate != null) {
                dbRate.setDeletionIndicator(1L);
                dbRate.setUpdatedBy(loginUserID);
                dbRate.setUpdatedOn(new Date());
                rateRepository.save(dbRate);
            } else {
                throw new EntityNotFoundException("Error in deleting RateParameterId - " + rate.getRateParameterId());
            }
        });
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All Rate Details
     *
     * @return
     */
    public List<ReplicaRate> getAllRates() {
        List<ReplicaRate> rateList = replicaRateRepository.findAll();
        rateList = rateList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return rateList;
    }

    /**
     * Get Rate
     *
     * @param rateParameterId
     * @param companyId
     * @param languageId
     * @param partnerId
     * @return
     */
    public ReplicaRate getReplicaRate(String rateParameterId, String companyId, String languageId, String partnerId, Long lineNo) {

        Optional<ReplicaRate> dbRate = replicaRateRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndRateParameterIdAndLineNoAndDeletionIndicator(languageId, companyId, partnerId, rateParameterId, lineNo, 0L);
        if (dbRate.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", RateParameterId - " + rateParameterId + ", LineNo - " + lineNo + " and partnerId - " + partnerId + " doesn't exists";
            //Error log
            createRateLog1(languageId, companyId, partnerId, rateParameterId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbRate.get();
    }

    /**
     * Find all Rates
     *
     * @param findRate
     * @return
     * @throws ParseException
     */
    public List<ReplicaRate> findRate(FindRate findRate) throws ParseException {
        ReplicaRateSpecification spec = new ReplicaRateSpecification(findRate);
        List<ReplicaRate> results = replicaRateRepository.findAll(spec);
        log.info("found Rate --> " + results);
        return results;
    }

    //========================================Rates_ErrorLog========================================================
    private void createRateLog(String languageId, String companyId, String partnerId, String rateParameterId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(rateParameterId);
        errorLog.setMethod("Exception thrown in updateRate");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createRateLog1(String languageId, String companyId, String partnerId, String rateParameterId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(rateParameterId);
        errorLog.setMethod("Exception thrown in getRate");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createRateLog2(AddRate addRate, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addRate.getLanguageId());
        errorLog.setCompanyId(addRate.getCompanyId());
        errorLog.setRefDocNumber(addRate.getPartnerId());
        errorLog.setReferenceField1(addRate.getRateParameterId());
        errorLog.setMethod("Exception thrown in createRate");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }


}
