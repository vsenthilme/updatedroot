package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.city.AddCity;
import com.courier.overc360.api.idmaster.primary.model.city.City;
import com.courier.overc360.api.idmaster.primary.model.city.UpdateCity;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.CityRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.city.FindCity;
import com.courier.overc360.api.idmaster.replica.model.city.ReplicaCity;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCityRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaDistrictRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCitySpecification;
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
public class CityService {

    @Autowired
    private ReplicaDistrictRepository replicaDistrictRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReplicaCityRepository replicaCityRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/


    /**
     * Get City Details
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param districtId
     * @param cityId
     * @return
     */
    public City getCity(String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId) {

        Optional<City> dbCity = cityRepository.findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndCityIdAndDeletionIndicator(
                languageId, companyId, countryId, provinceId, districtId, cityId, 0L);
        if (dbCity.isEmpty()) {
            String errMsg = "The given values - languageId: " + languageId + ", companyId: " + companyId + ", countryId: " + countryId
                    + ", provinceId: " + provinceId + ", districtId: " + districtId + " and cityId: " + cityId + " and doesn't exists";
            // Error Log
            createCityLog1(languageId, companyId, countryId, provinceId, districtId, cityId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCity.get();
    }

    /**
     * Create City
     *
     * @param addCity
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public City createCity(AddCity addCity, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbProvincePresent = replicaDistrictRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndDeletionIndicator(
                    addCity.getLanguageId(), addCity.getCompanyId(), addCity.getCountryId(),
                    addCity.getProvinceId(), addCity.getDistrictId(), 0L);

            boolean duplicateCityPresent = replicaCityRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndCityIdAndDeletionIndicator(
                    addCity.getLanguageId(), addCity.getCompanyId(), addCity.getCountryId(), addCity.getProvinceId(),
                    addCity.getDistrictId(), addCity.getCityId(), 0L);

            if (!dbProvincePresent) {
                throw new BadRequestException("LanguageId: " + addCity.getLanguageId() + ", CompanyId: " + addCity.getCompanyId() +
                        ", CountryId: " + addCity.getCountryId() + " and ProvinceId: " + addCity.getProvinceId() +
                        ", districtId: " + addCity.getDistrictId() + " doesn't exists");
            } else if (duplicateCityPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : cityId - " + addCity.getCityId());
            } else {
                log.info("new City --> " + addCity);
                IKeyValuePair iKeyValuePair = replicaCityRepository.getDescription(addCity.getLanguageId(), addCity.getCompanyId(),
                        addCity.getCountryId(), addCity.getProvinceId(), addCity.getDistrictId());
                City newCity = new City();
                BeanUtils.copyProperties(addCity, newCity, CommonUtils.getNullPropertyNames(addCity));
                if (addCity.getCityId() != null &&
                        (addCity.getReferenceField10() != null && addCity.getReferenceField10().equalsIgnoreCase("true")) ||
                        (addCity.getCityId() == null || addCity.getCityId().isBlank())) {
                    String NUM_RAN_OBJ = "CITY";
                    String CITY_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for CITY_ID : " + CITY_ID);
                    newCity.setCityId(CITY_ID);
                }
                if (iKeyValuePair != null) {
                    newCity.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newCity.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newCity.setCountryName(iKeyValuePair.getCountryDesc());
                    newCity.setProvinceName(iKeyValuePair.getProvinceDesc());
                    newCity.setDistrictName(iKeyValuePair.getDistrictDesc());
                    newCity.setCountryText(iKeyValuePair.getCountryText());
                    newCity.setProvinceText(iKeyValuePair.getProvinceText());
                    newCity.setDistrictText(iKeyValuePair.getDistrictText());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addCity.getStatusId());
                if (statusDesc != null) {
                    newCity.setStatusDescription(statusDesc);
                }
                newCity.setDeletionIndicator(0L);
                newCity.setCreatedBy(loginUserID);
                newCity.setCreatedOn(new Date());
                newCity.setUpdatedBy(loginUserID);
                newCity.setUpdatedOn(new Date());
                return cityRepository.save(newCity);
            }
        } catch (Exception e) {
            // Error Log
            createCityLog2(addCity, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update City
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param districtId
     * @param cityId
     * @param loginUserID
     * @param updateCity
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public City updateCity(String languageId, String companyId, String countryId, String provinceId, String districtId,
                           String cityId, String loginUserID, UpdateCity updateCity)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            City dbCity = getCity(languageId, companyId, countryId, provinceId, districtId, cityId);
//            if (updateCity.getCityName() != null) {
//                if (updateCity.getCityName().isBlank()) {
//                    throw new BadRequestException("City Name cannot be blank");
//                }
//                boolean isCityNameChanged = !dbCity.getCityName().equalsIgnoreCase(updateCity.getCityName());
//                if (isCityNameChanged) {
//                    String oldCityDesc = dbCity.getCityName();
//                    BeanUtils.copyProperties(updateCity, dbCity, CommonUtils.getNullPropertyNames(updateCity));
//                    dbCity.setUpdatedBy(loginUserID);
//                    dbCity.setUpdatedOn(new Date());
//                    City updatedCity = cityRepository.save(dbCity);
//
//                    // Updating CityName in CityMapping table
//                    cityRepository.cityDescUpdateProc(languageId, companyId, cityId, oldCityDesc, updateCity.getCityName());
//                    return updatedCity;
//                }
//            }
            BeanUtils.copyProperties(updateCity, dbCity, CommonUtils.getNullPropertyNames(updateCity));
            if (updateCity.getStatusId() != null && !updateCity.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateCity.getStatusId());
                if (statusDesc != null) {
                    dbCity.setStatusDescription(statusDesc);
                }
            }
            dbCity.setUpdatedBy(loginUserID);
            dbCity.setUpdatedOn(new Date());
            return cityRepository.save(dbCity);
        } catch (Exception e) {
            // Error Log
            createCityLog(languageId, companyId, countryId, provinceId, districtId, cityId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * Delete City
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param districtId
     * @param cityId
     * @param loginUserID
     */
    public void deleteCity(String languageId, String companyId, String countryId, String provinceId,
                           String districtId, String cityId, String loginUserID) {

        City dbCity = getCity(languageId, companyId, countryId, provinceId, districtId, cityId);
        if (dbCity != null) {
            dbCity.setDeletionIndicator(1L);
            dbCity.setUpdatedBy(loginUserID);
            dbCity.setUpdatedOn(new Date());
            cityRepository.save(dbCity);
        } else {
            // Error Log
            createCityLog1(languageId, companyId, countryId, provinceId, districtId, cityId, "Error in deleting CityId - " + cityId);
            throw new BadRequestException("Error in deleting CityId - " + cityId);
        }
    }


    /*======================================================REPLICA=====================================================*/

    /**
     * Get All City Details
     *
     * @return
     */
    public List<ReplicaCity> getAllCities() {
        List<ReplicaCity> cityList = replicaCityRepository.findAll();
        cityList = cityList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return cityList;
    }

    /**
     * Get City Details
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param districtId
     * @param cityId
     * @return
     */
    public ReplicaCity replicaGetCity(String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId) {

        Optional<ReplicaCity> dbCity = replicaCityRepository.findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndCityIdAndDeletionIndicator(
                languageId, companyId, countryId, provinceId, districtId, cityId, 0L);
        if (dbCity.isEmpty()) {
            String errMsg = "The given values - languageId: " + languageId + ", companyId: " + companyId + ", countryId: " + countryId
                    + ", provinceId: " + provinceId + ", districtId: " + districtId + " and cityId: " + cityId + " and doesn't exists";
            // Error Log
            createCityLog1(languageId, companyId, countryId, provinceId, districtId, cityId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCity.get();
    }

    /**
     * Find City
     *
     * @param findCity
     * @return
     */
    public List<ReplicaCity> findCity(FindCity findCity) {

        ReplicaCitySpecification spec = new ReplicaCitySpecification(findCity);
        List<ReplicaCity> results = replicaCityRepository.findAll(spec);
        log.info("found Cities --> " + results);
        return results;
    }


    //=============================================City_ErrorLog=======================================================
    private void createCityLog(String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId,
                               String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(cityId);
        errorLog.setReferenceField1(countryId);
        errorLog.setReferenceField2(provinceId);
        errorLog.setReferenceField3(districtId);
        errorLog.setMethod("Exception thrown in updateCity");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createCityLog1(String languageId, String companyId, String countryId, String provinceId,
                                String districtId, String cityId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(cityId);
        errorLog.setReferenceField1(countryId);
        errorLog.setReferenceField2(provinceId);
        errorLog.setReferenceField3(districtId);
        errorLog.setMethod("Exception thrown in getCity");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCityLog2(AddCity addCity, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addCity.getLanguageId());
        errorLog.setCompanyId(addCity.getCompanyId());
        errorLog.setRefDocNumber(addCity.getCityId());
        errorLog.setReferenceField1(addCity.getCountryId());
        errorLog.setReferenceField2(addCity.getProvinceId());
        errorLog.setReferenceField3(addCity.getDistrictId());
        errorLog.setMethod("Exception thrown in createCity");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
