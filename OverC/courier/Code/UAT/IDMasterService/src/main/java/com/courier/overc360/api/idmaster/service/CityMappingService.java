package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.citymapping.AddCityMapping;
import com.courier.overc360.api.idmaster.primary.model.citymapping.CityMapping;
import com.courier.overc360.api.idmaster.primary.model.citymapping.UpdateCityMapping;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.CityMappingRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.citymapping.FindCityMapping;
import com.courier.overc360.api.idmaster.replica.model.citymapping.ReplicaCityMapping;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCityMappingRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCityRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCityMappingSpecification;
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
public class CityMappingService {

    @Autowired
    private ReplicaCityRepository replicaCityRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private CityMappingRepository cityMappingRepository;

    @Autowired
    private ReplicaCityMappingRepository replicaCityMappingRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get CityMapping
     *
     * @param languageId
     * @param companyId
     * @param cityId
     * @param partnerId
     * @return
     */
    public CityMapping getCityMapping(String languageId, String companyId, String cityId, String partnerId) {

        Optional<CityMapping> dbCityMapping = cityMappingRepository.findByLanguageIdAndCompanyIdAndCityIdAndPartnerIdAndDeletionIndicator(
                languageId, companyId, cityId, partnerId, 0L);
        if (dbCityMapping.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + ", cityId - " + cityId + " and partnerId - " + partnerId + " doesn't exists";
            // Error Log
            createCityMappingLog1(languageId, companyId, cityId, partnerId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCityMapping.get();
    }

    /**
     * Create new CityMapping
     *
     * @param addCityMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public CityMapping createCityMapping(AddCityMapping addCityMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCityPresent = replicaCityRepository.existsByLanguageIdAndCompanyIdAndCityIdAndDeletionIndicator(
                    addCityMapping.getLanguageId(), addCityMapping.getCompanyId(),
                    addCityMapping.getCityId(), 0L);
            if (!dbCityPresent) {
                throw new BadRequestException("CityId - " + addCityMapping.getCityId() + ", companyId - " +
                        addCityMapping.getCompanyId() + " and languageId - " + addCityMapping.getLanguageId() + " doesn't exists");
            }

            boolean duplicateCityMappingPresent = replicaCityMappingRepository.existsByLanguageIdAndCompanyIdAndCityIdAndPartnerIdAndDeletionIndicator(
                    addCityMapping.getLanguageId(), addCityMapping.getCompanyId(), addCityMapping.getCityId(),
                    addCityMapping.getPartnerId(), 0L);
            if (duplicateCityMappingPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : partnerId - " +
                        addCityMapping.getPartnerId() + " and cityId - " + addCityMapping.getCityId());
            }
            log.info("new CityMapping --> {}", addCityMapping);
            IKeyValuePair iKeyValuePair = replicaCityMappingRepository.getDescription(addCityMapping.getLanguageId(),
                    addCityMapping.getCompanyId(), addCityMapping.getCityId());
            CityMapping newCityMapping = new CityMapping();
            BeanUtils.copyProperties(addCityMapping, newCityMapping, CommonUtils.getNullPropertyNames(addCityMapping));
            if (iKeyValuePair != null) {
                newCityMapping.setLanguageDescription(iKeyValuePair.getLangDesc());
                newCityMapping.setCompanyName(iKeyValuePair.getCompanyDesc());
                newCityMapping.setCityName(iKeyValuePair.getCityDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addCityMapping.getStatusId());
            if (statusDesc != null) {
                newCityMapping.setStatusDescription(statusDesc);
            }
            newCityMapping.setDeletionIndicator(0L);
            newCityMapping.setCreatedBy(loginUserID);
            newCityMapping.setCreatedOn(new Date());
            newCityMapping.setUpdatedBy(loginUserID);
            newCityMapping.setUpdatedOn(new Date());
            return cityMappingRepository.save(newCityMapping);
        } catch (Exception e) {
            // Error Log
            createCityMappingLog2(addCityMapping, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update CityMapping
     *
     * @param languageId
     * @param companyId
     * @param cityId
     * @param partnerId
     * @param updateCityMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public CityMapping updateCityMapping(String languageId, String companyId, String cityId, String partnerId,
                                         UpdateCityMapping updateCityMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            CityMapping dbCityMapping = getCityMapping(languageId, companyId, cityId, partnerId);
            BeanUtils.copyProperties(updateCityMapping, dbCityMapping, CommonUtils.getNullPropertyNames(updateCityMapping));
            if (updateCityMapping.getStatusId() != null && !updateCityMapping.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateCityMapping.getStatusId());
                if (statusDesc != null) {
                    dbCityMapping.setStatusDescription(statusDesc);
                }
            }
            dbCityMapping.setUpdatedBy(loginUserID);
            dbCityMapping.setUpdatedOn(new Date());
            return cityMappingRepository.save(dbCityMapping);
        } catch (Exception e) {
            // Error Log
            createCityMappingLog(languageId, companyId, cityId, partnerId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete CityMapping
     *
     * @param languageId
     * @param companyId
     * @param cityId
     * @param partnerId
     * @param loginUserID
     */
    public void deleteCityMapping(String languageId, String companyId, String cityId, String partnerId, String loginUserID) {
        CityMapping dbCityMapping = getCityMapping(languageId, companyId, cityId, partnerId);
        if (dbCityMapping != null) {
            dbCityMapping.setDeletionIndicator(1L);
            dbCityMapping.setUpdatedBy(loginUserID);
            dbCityMapping.setUpdatedOn(new Date());
            cityMappingRepository.save(dbCityMapping);
        } else {
            // Error Log
            createCityMappingLog1(languageId, companyId, cityId, partnerId, "Error in deleting partnerId - " + partnerId);
            throw new BadRequestException("Error in deleting partnerId - " + partnerId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All CityMapping Details
     *
     * @return
     */
    public List<ReplicaCityMapping> getAllCityMappings() {
        List<ReplicaCityMapping> cityMappingList = replicaCityMappingRepository.findAll();
        cityMappingList = cityMappingList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return cityMappingList;
    }

    /**
     * Get CityMapping
     *
     * @param languageId
     * @param companyId
     * @param cityId
     * @param partnerId
     * @return
     */
    public ReplicaCityMapping replicaGetCityMapping(String languageId, String companyId, String cityId, String partnerId) {

        Optional<ReplicaCityMapping> dbCityMapping = replicaCityMappingRepository.findByLanguageIdAndCompanyIdAndCityIdAndPartnerIdAndDeletionIndicator(
                languageId, companyId, cityId, partnerId, 0L);
        if (dbCityMapping.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + ", cityId - " + cityId + " and partnerId - " + partnerId + " doesn't exists";
            // Error Log
            createCityMappingLog1(languageId, companyId, cityId, partnerId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCityMapping.get();
    }

    /**
     * Find CityMappings
     *
     * @param findCityMapping
     * @return
     * @throws ParseException
     */
    public List<ReplicaCityMapping> findCityMappings(FindCityMapping findCityMapping) throws ParseException {

        ReplicaCityMappingSpecification spec = new ReplicaCityMappingSpecification(findCityMapping);
        List<ReplicaCityMapping> results = replicaCityMappingRepository.findAll(spec);
        log.info("found city Mappings --> " + results);
        return results;
    }

    //========================================CityMapping_ErrorLog=================================================
    private void createCityMappingLog(String languageId, String companyId, String cityId, String partnerId,
                                      String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(cityId);
        errorLog.setMethod("Exception thrown in updateCityMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createCityMappingLog1(String languageId, String companyId, String cityId, String partnerId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(cityId);
        errorLog.setMethod("Exception thrown in getCityMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCityMappingLog2(AddCityMapping addCityMapping, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addCityMapping.getLanguageId());
        errorLog.setCompanyId(addCityMapping.getCompanyId());
        errorLog.setRefDocNumber(addCityMapping.getPartnerId());
        errorLog.setReferenceField1(addCityMapping.getCityId());
        errorLog.setMethod("Exception thrown in createCityMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
