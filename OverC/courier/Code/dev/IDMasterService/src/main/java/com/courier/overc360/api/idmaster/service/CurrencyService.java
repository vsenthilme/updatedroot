package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.currency.AddCurrency;
import com.courier.overc360.api.idmaster.primary.model.currency.Currency;
import com.courier.overc360.api.idmaster.primary.model.currency.UpdateCurrency;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.CurrencyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.currency.FindCurrency;
import com.courier.overc360.api.idmaster.replica.model.currency.ReplicaCurrency;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCurrencyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCurrencySpecification;
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
public class CurrencyService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ReplicaCurrencyRepository replicaCurrencyRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private NumberRangeService numberRangeService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------*/

    /**
     * Get Currency
     *
     * @param currencyId
     * @return
     */
    public Currency getCurrency(String currencyId) {

        Optional<Currency> dbCurrency = currencyRepository.findByCurrencyIdAndDeletionIndicator(currencyId, 0L);
        if (dbCurrency.isEmpty()) {
            throw new BadRequestException("CurrencyId - " + currencyId + " doesn't exists");
        }
        return dbCurrency.get();
    }

    /**
     * Create new Currency
     *
     * @param addCurrency
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Currency createCurrency(AddCurrency addCurrency, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<Currency> duplicateCurrency = currencyRepository.findByCurrencyIdAndDeletionIndicator(
                    addCurrency.getCurrencyId(), 0L);

            if (duplicateCurrency.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : currencyId - " + addCurrency.getCurrencyId());
            } else {
                log.info("new Currency --> " + addCurrency);
                Currency newCurrency = new Currency();
                BeanUtils.copyProperties(addCurrency, newCurrency, CommonUtils.getNullPropertyNames(addCurrency));
                if ((addCurrency.getCurrencyId() != null &&
                        (addCurrency.getReferenceField10() != null && addCurrency.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addCurrency.getCurrencyId() == null || addCurrency.getCurrencyId().isBlank()) {
                    String NUM_RAN_OBJ = "CURRENCY";
                    String CURRENCY_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for CURRENCY_ID : " + CURRENCY_ID);
                    addCurrency.setCurrencyId(CURRENCY_ID);
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addCurrency.getStatusId());
                if (statusDesc != null) {
                    newCurrency.setStatusDescription(statusDesc);
                }
                newCurrency.setDeletionIndicator(0L);
                newCurrency.setCreatedBy(loginUserID);
                newCurrency.setCreatedOn(new Date());
                newCurrency.setUpdatedBy(loginUserID);
                newCurrency.setUpdatedOn(new Date());
                return currencyRepository.save(newCurrency);
            }
        } catch (Exception e) {
            // Error Log
            createCurrencyLog2(addCurrency, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Currency
     *
     * @param currencyId
     * @param updateCurrency
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Currency updateCurrency(String currencyId, UpdateCurrency updateCurrency, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Currency dbCurrency = getCurrency(currencyId);
            BeanUtils.copyProperties(updateCurrency, dbCurrency, CommonUtils.getNullPropertyNames(updateCurrency));
            if (updateCurrency.getStatusId() != null && !updateCurrency.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateCurrency.getStatusId());
                if (statusDesc != null) {
                    dbCurrency.setStatusDescription(statusDesc);
                }
            }
            dbCurrency.setUpdatedBy(loginUserID);
            dbCurrency.setUpdatedOn(new Date());
            return currencyRepository.save(dbCurrency);
        } catch (Exception e) {
            // Error Log
            createCurrencyLog(currencyId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Currency
     *
     * @param currencyId
     * @param loginUserID
     */
    public void deleteCurrency(String currencyId, String loginUserID) {

        Currency dbCurrency = getCurrency(currencyId);
        if (dbCurrency != null) {
            dbCurrency.setDeletionIndicator(1L);
            dbCurrency.setUpdatedBy(loginUserID);
            dbCurrency.setUpdatedOn(new Date());
            currencyRepository.save(dbCurrency);
        } else {
            // Error Log
            createCurrencyLog1(currencyId, "Error in deleting CurrencyId - " + currencyId);
            throw new BadRequestException("Error in deleting CurrencyId - " + currencyId);
        }
    }

    //===============================================Replica==================================================

    /**
     * Get All Currency Details
     *
     * @return
     */
    public List<ReplicaCurrency> getAllCurrencies() {
        List<ReplicaCurrency> currencyList = replicaCurrencyRepository.findAll();
        currencyList = currencyList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return currencyList;
    }

    /**
     * Get Currency
     *
     * @param currencyId
     * @return
     */
    public ReplicaCurrency replicaGetCurrency(String currencyId) {

        Optional<ReplicaCurrency> dbCurrency = replicaCurrencyRepository.findByCurrencyIdAndDeletionIndicator(currencyId, 0L);
        if (dbCurrency.isEmpty()) {
            // Error Log
            createCurrencyLog1(currencyId, "CurrencyId - " + currencyId + " doesn't exists");
            throw new BadRequestException("CurrencyId - " + currencyId + " doesn't exists");
        }
        return dbCurrency.get();
    }

    /**
     * Find Currency
     *
     * @param findCurrency
     * @return
     * @throws ParseException
     */
    public List<ReplicaCurrency> findCurrency(FindCurrency findCurrency) throws ParseException {

        ReplicaCurrencySpecification spec = new ReplicaCurrencySpecification(findCurrency);
        List<ReplicaCurrency> results = replicaCurrencyRepository.findAll(spec);
        log.info("found Currencies --> " + results);
        return results;
    }

    //=============================================Country_ErrorLog====================================================
    private void createCurrencyLog(String currencyId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setRefDocNumber(currencyId);
        errorLog.setMethod("Exception thrown in updateCurrency");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createCurrencyLog1(String currencyId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setRefDocNumber(currencyId);
        errorLog.setMethod("Exception thrown in getCurrency");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCurrencyLog2(AddCurrency addCurrency, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setRefDocNumber(addCurrency.getCurrencyId());
        errorLog.setMethod("Exception thrown in createCurrency");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
