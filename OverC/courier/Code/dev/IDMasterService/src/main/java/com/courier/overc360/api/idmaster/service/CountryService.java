package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.country.AddCountry;
import com.courier.overc360.api.idmaster.primary.model.country.Country;
import com.courier.overc360.api.idmaster.primary.model.country.UpdateCountry;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.CountryRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.country.FindCountry;
import com.courier.overc360.api.idmaster.replica.model.country.ReplicaCountry;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCountryRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCountrySpecification;
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

@Slf4j
@Service
public class CountryService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReplicaCountryRepository replicaCountryRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Country
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @return
     */
    public Country getCountry(String languageId, String companyId, String countryId) {

        Optional<Country> dbCountry = countryRepository.findByLanguageIdAndCompanyIdAndCountryIdAndDeletionIndicator(
                languageId, companyId, countryId, 0L);
        if (dbCountry.isEmpty()) {
            // Error Log
            createCountryLog1(languageId, companyId, countryId, "CountryId - " + countryId + " and given values doesn't exists");
            throw new BadRequestException("CountryId - " + countryId + " and given values doesn't exists");
        }
        return dbCountry.get();
    }

    /**
     * Create new Country
     *
     * @param addCountry
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Country createCountry(AddCountry addCountry, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addCountry.getCompanyId(), addCountry.getLanguageId(), 0L);

            boolean duplicateCountryPresent = replicaCountryRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndDeletionIndicator(
                    addCountry.getLanguageId(), addCountry.getCompanyId(), addCountry.getCountryId(), 0L);

            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addCountry.getCompanyId() + " and languageId - " + addCountry.getLanguageId() + " doesn't exists");
            } else if (duplicateCountryPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : countryId - " + addCountry.getCountryId());
            } else {
                log.info("new Country --> {}", addCountry);
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addCountry.getLanguageId(), addCountry.getCompanyId());
                Country newCountry = new Country();
                BeanUtils.copyProperties(addCountry, newCountry, CommonUtils.getNullPropertyNames(addCountry));
                if ((addCountry.getCountryId() != null &&
                        (addCountry.getReferenceField10() != null && addCountry.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addCountry.getCountryId() == null || addCountry.getCountryId().isBlank()) {
                    String NUM_RAN_OBJ = "COUNTRY";
                    String COUNTRY_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for COUNTRY_ID : " + COUNTRY_ID);
                    newCountry.setCountryId(COUNTRY_ID);
                }
                if (iKeyValuePair != null) {
                    newCountry.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newCountry.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addCountry.getStatusId());
                if (statusDesc != null) {
                    newCountry.setStatusDescription(statusDesc);
                }
                newCountry.setDeletionIndicator(0L);
                newCountry.setCreatedBy(loginUserID);
                newCountry.setCreatedOn(new Date());
                newCountry.setUpdatedBy(loginUserID);
                newCountry.setUpdatedOn(new Date());
                return countryRepository.save(newCountry);
            }
        } catch (Exception e) {
            // Error Log
            createCountryLog2(addCountry, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Country
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param updateCountry
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Country updateCountry(String languageId, String companyId, String countryId, UpdateCountry updateCountry, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Country dbCountry = getCountry(languageId, companyId, countryId);
//            if (updateCountry.getCountryName() != null) {
//                if (updateCountry.getCountryName().isBlank()) {
//                    throw new BadRequestException("Country Name cannot be blank");
//                }
//                boolean isCountryNameChanged = !dbCountry.getCountryName().equalsIgnoreCase(updateCountry.getCountryName());
//                if (isCountryNameChanged) {
//                    String oldCountryDesc = dbCountry.getCountryName();
//                    BeanUtils.copyProperties(updateCountry, dbCountry, CommonUtils.getNullPropertyNames(updateCountry));
//                    dbCountry.setUpdatedBy(loginUserID);
//                    dbCountry.setUpdatedOn(new Date());
//                    Country updatedCountry = countryRepository.save(dbCountry);
//
//                    // Updating Country Name in Province, District, City, Hub, CountryMapping and DistrictMapping tables
//                    countryRepository.countryDescUpdateProc(languageId, companyId, countryId, oldCountryDesc, updateCountry.getCountryName());
//                    return updatedCountry;
//                }
//            }
            BeanUtils.copyProperties(updateCountry, dbCountry, CommonUtils.getNullPropertyNames(updateCountry));
            if (updateCountry.getStatusId() != null && !updateCountry.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateCountry.getStatusId());
                if (statusDesc != null) {
                    dbCountry.setStatusDescription(statusDesc);
                }
            }
            dbCountry.setUpdatedBy(loginUserID);
            dbCountry.setUpdatedOn(new Date());
            return countryRepository.save(dbCountry);
        } catch (Exception e) {
            // Error Log
            createCountryLog(languageId, companyId, countryId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Country
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param loginUserID
     */
    public void deleteCountry(String languageId, String companyId, String countryId, String loginUserID) {

        Country dbCountry = getCountry(languageId, companyId, countryId);
        if (dbCountry != null) {
            dbCountry.setDeletionIndicator(1L);
            dbCountry.setUpdatedBy(loginUserID);
            dbCountry.setUpdatedOn(new Date());
            countryRepository.save(dbCountry);
        } else {
            // Error Log
            createCountryLog1(languageId, companyId, countryId, "Error in deleting CountryId - " + countryId);
            throw new BadRequestException("Error in deleting CountryId - " + countryId);
        }
    }

    //===============================================Replica==================================================

    /**
     * Get All Country Details
     *
     * @return
     */
    public List<ReplicaCountry> getAllCountries() {
        List<ReplicaCountry> countryList = replicaCountryRepository.findAll();
        countryList = countryList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return countryList;
    }

    /**
     * Get Country
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @return
     */
    public ReplicaCountry replicaGetCountry(String languageId, String companyId, String countryId) {

        Optional<ReplicaCountry> dbCountry = replicaCountryRepository.findByLanguageIdAndCompanyIdAndCountryIdAndDeletionIndicator(
                languageId, companyId, countryId, 0L);
        if (dbCountry.isEmpty()) {
            // Error Log
            createCountryLog1(languageId, companyId, countryId, "CountryId - " + countryId + " and given values doesn't exists");
            throw new BadRequestException("CountryId - " + countryId + " and given values doesn't exists");
        }
        return dbCountry.get();
    }

    /**
     * Find Country
     *
     * @param findCountry
     * @return
     * @throws Exception
     */
    public List<ReplicaCountry> findCountries(FindCountry findCountry) throws Exception {

        ReplicaCountrySpecification spec = new ReplicaCountrySpecification(findCountry);
        List<ReplicaCountry> results = replicaCountryRepository.findAll(spec);
        log.info("found Countries --> " + results);
        return results;
    }

    //=============================================Country_ErrorLog====================================================
    private void createCountryLog(String languageId, String companyId, String countryId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(countryId);
        errorLog.setMethod("Exception thrown in updateCountry");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createCountryLog1(String languageId, String companyId, String countryId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(countryId);
        errorLog.setMethod("Exception thrown in getCountry");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCountryLog2(AddCountry addCountry, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addCountry.getLanguageId());
        errorLog.setCompanyId(addCountry.getCompanyId());
        errorLog.setRefDocNumber(addCountry.getCountryId());
        errorLog.setMethod("Exception thrown in createCountry");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
