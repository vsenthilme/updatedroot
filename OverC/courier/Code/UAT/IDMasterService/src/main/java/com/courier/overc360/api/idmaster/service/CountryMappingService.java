package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.countryMapping.AddCountryMapping;
import com.courier.overc360.api.idmaster.primary.model.countryMapping.CountryMapping;
import com.courier.overc360.api.idmaster.primary.model.countryMapping.UpdateCountryMapping;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.CountryMappingRepository;
import com.courier.overc360.api.idmaster.primary.repository.CountryRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.countryMapping.FindCountryMapping;
import com.courier.overc360.api.idmaster.replica.model.countryMapping.ReplicaCountryMapping;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCountryMappingRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCountryRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCountryMappingSpecification;
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
public class CountryMappingService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCountryRepository replicaCountryRepository;

    @Autowired
    private CountryMappingRepository countryMappingRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ReplicaCountryMappingRepository replicaCountryMappingRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get CountryMapping
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param partnerId
     * @return
     */
    public CountryMapping getCountryMapping(String languageId, String companyId, String countryId, String partnerId) {

        Optional<CountryMapping> dbCountryMapping = countryMappingRepository.findByLanguageIdAndCompanyIdAndCountryIdAndPartnerIdAndDeletionIndicator(
                languageId, companyId, countryId, partnerId, 0L);
        if (dbCountryMapping.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", countryId - " + countryId + " and partnerId - " + partnerId + " doesn't exists";
            // Error Log
            createCountryMappingLog1(languageId, companyId, countryId, partnerId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCountryMapping.get();
    }

    /**
     * Create new CountryMapping
     *
     * @param addCountryMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public CountryMapping createCountryMapping(AddCountryMapping addCountryMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCountryPresent = replicaCountryRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndDeletionIndicator(
                    addCountryMapping.getLanguageId(), addCountryMapping.getCompanyId(),
                    addCountryMapping.getCountryId(), 0L);
            if (!dbCountryPresent) {
                throw new BadRequestException("CountryId - " + addCountryMapping.getCountryId() + ", companyId - " +
                        addCountryMapping.getCompanyId() + " and languageId - " + addCountryMapping.getLanguageId() + " doesn't exists");
            }

            boolean duplicateCountryMappingPresent = replicaCountryMappingRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndPartnerIdAndDeletionIndicator(
                    addCountryMapping.getLanguageId(), addCountryMapping.getCompanyId(), addCountryMapping.getCountryId(),
                    addCountryMapping.getPartnerId(), 0L);
            if (duplicateCountryMappingPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : partnerId - " +
                        addCountryMapping.getPartnerId() + " and countryId - " + addCountryMapping.getCountryId());
            }
            log.info("new CountryMapping --> {}", addCountryMapping);
            IKeyValuePair iKeyValuePair = replicaCountryRepository.getDescription(addCountryMapping.getLanguageId(),
                    addCountryMapping.getCompanyId(), addCountryMapping.getCountryId());
            CountryMapping newCountryMapping = new CountryMapping();
            BeanUtils.copyProperties(addCountryMapping, newCountryMapping, CommonUtils.getNullPropertyNames(addCountryMapping));
            if (iKeyValuePair != null) {
                newCountryMapping.setLanguageDescription(iKeyValuePair.getLangDesc());
                newCountryMapping.setCompanyName(iKeyValuePair.getCompanyDesc());
                newCountryMapping.setCountryName(iKeyValuePair.getCountryDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addCountryMapping.getStatusId());
            if (statusDesc != null) {
                newCountryMapping.setStatusDescription(statusDesc);
            }
            newCountryMapping.setDeletionIndicator(0L);
            newCountryMapping.setCreatedBy(loginUserID);
            newCountryMapping.setCreatedOn(new Date());
            newCountryMapping.setUpdatedBy(loginUserID);
            newCountryMapping.setUpdatedOn(new Date());
            return countryMappingRepository.save(newCountryMapping);
        } catch (Exception e) {
            // Error Log
            createCountryMappingLog2(addCountryMapping, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update CountryMapping
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param partnerId
     * @param updateCountryMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public CountryMapping updateCountryMapping(String languageId, String companyId, String countryId, String partnerId,
                                               UpdateCountryMapping updateCountryMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            CountryMapping dbCountryMapping = getCountryMapping(languageId, companyId, countryId, partnerId);
            BeanUtils.copyProperties(updateCountryMapping, dbCountryMapping, CommonUtils.getNullPropertyNames(updateCountryMapping));
            if (updateCountryMapping.getStatusId() != null && !updateCountryMapping.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateCountryMapping.getStatusId());
                if (statusDesc != null) {
                    dbCountryMapping.setStatusDescription(statusDesc);
                }
            }
            dbCountryMapping.setUpdatedBy(loginUserID);
            dbCountryMapping.setUpdatedOn(new Date());
            return countryMappingRepository.save(dbCountryMapping);
        } catch (Exception e) {
            // Error Log
            createCountryMappingLog(languageId, companyId, countryId, partnerId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete CountryMapping
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param partnerId
     * @param loginUserID
     */
    public void deleteCountryMapping(String languageId, String companyId, String countryId, String partnerId, String loginUserID) {
        CountryMapping dbCountryMapping = getCountryMapping(languageId, companyId, countryId, partnerId);
        if (dbCountryMapping != null) {
            dbCountryMapping.setDeletionIndicator(1L);
            dbCountryMapping.setUpdatedBy(loginUserID);
            dbCountryMapping.setUpdatedOn(new Date());
            countryMappingRepository.save(dbCountryMapping);
        } else {
            // Error Log
            createCountryMappingLog1(languageId, companyId, countryId, partnerId, "Error in deleting PartnerId - " + partnerId);
            throw new BadRequestException("Error in deleting PartnerId - " + partnerId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All CountryMapping Details
     *
     * @return
     */
    public List<ReplicaCountryMapping> getAllCountryMappings() {
        List<ReplicaCountryMapping> countryMappingList = replicaCountryMappingRepository.findAll();
        countryMappingList = countryMappingList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return countryMappingList;
    }

    /**
     * Get CountryMapping
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param partnerId
     * @return
     */
    public ReplicaCountryMapping replicaGetCountryMapping(String languageId, String companyId, String countryId, String partnerId) {

        Optional<ReplicaCountryMapping> dbCountryMapping = replicaCountryMappingRepository.findByLanguageIdAndCompanyIdAndCountryIdAndPartnerIdAndDeletionIndicator(
                languageId, companyId, countryId, partnerId, 0L);
        if (dbCountryMapping.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", countryId - " + countryId + " and partnerId - " + partnerId + " doesn't exists";
            // Error Log
            createCountryMappingLog1(languageId, companyId, countryId, partnerId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCountryMapping.get();
    }

    /**
     * Find CountryMappings
     *
     * @param findCountryMapping
     * @return
     * @throws ParseException
     */
    public List<ReplicaCountryMapping> findCountryMappings(FindCountryMapping findCountryMapping) throws ParseException {

        ReplicaCountryMappingSpecification spec = new ReplicaCountryMappingSpecification(findCountryMapping);
        List<ReplicaCountryMapping> results = replicaCountryMappingRepository.findAll(spec);
        log.info("found Country Mappings --> " + results);
        return results;
    }

    //========================================CountryMapping_ErrorLog=================================================
    private void createCountryMappingLog(String languageId, String companyId, String countryId, String partnerId,
                                         String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(countryId);
        errorLog.setMethod("Exception thrown in updateCountryMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createCountryMappingLog1(String languageId, String companyId, String countryId, String partnerId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(countryId);
        errorLog.setMethod("Exception thrown in getCountryMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCountryMappingLog2(AddCountryMapping addCountryMapping, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addCountryMapping.getLanguageId());
        errorLog.setCompanyId(addCountryMapping.getCompanyId());
        errorLog.setRefDocNumber(addCountryMapping.getPartnerId());
        errorLog.setReferenceField1(addCountryMapping.getCountryId());
        errorLog.setMethod("Exception thrown in createCountryMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
