package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.province.AddProvince;
import com.courier.overc360.api.idmaster.primary.model.province.Province;
import com.courier.overc360.api.idmaster.primary.model.province.UpdateProvince;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ProvinceRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.province.FindProvince;
import com.courier.overc360.api.idmaster.replica.model.province.ReplicaProvince;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCountryRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaProvinceRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaProvinceSpecification;
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

@Service
@Slf4j
public class ProvinceService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCountryRepository replicaCountryRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ReplicaProvinceRepository replicaProvinceRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Province
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @return
     */
    public Province getProvince(String languageId, String companyId, String countryId, String provinceId) {

        Optional<Province> dbProvince = provinceRepository.findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDeletionIndicator(
                languageId, companyId, countryId, provinceId, 0L);
        if (dbProvince.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", countryId - " + countryId + " and provinceId - " + provinceId + " doesn't exists";
            // Error Log
            createProvinceLog1(languageId, companyId, countryId, provinceId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbProvince.get();
    }

    /**
     * Create Province
     *
     * @param addProvince
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Province createProvince(AddProvince addProvince, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCountryPresent = replicaCountryRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndDeletionIndicator(
                    addProvince.getLanguageId(), addProvince.getCompanyId(), addProvince.getCountryId(), 0L);
            if (!dbCountryPresent) {
                throw new BadRequestException("CountryId - " + addProvince.getCountryId() + ", companyId - " + addProvince.getCompanyId() +
                        " and languageId - " + addProvince.getLanguageId() + " doesn't exists");
            }

            boolean duplicateProvincePresent = replicaProvinceRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDeletionIndicator(
                    addProvince.getLanguageId(), addProvince.getCompanyId(), addProvince.getCountryId(), addProvince.getProvinceId(), 0L);
            if (duplicateProvincePresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values");
            }

            log.info("new Province --> " + addProvince);
            Province newProvince = new Province();
            IKeyValuePair iKeyValuePair = replicaCountryRepository.getDescription(addProvince.getLanguageId(),
                    addProvince.getCompanyId(), addProvince.getCountryId());
            BeanUtils.copyProperties(addProvince, newProvince, CommonUtils.getNullPropertyNames(addProvince));
            if ((addProvince.getProvinceId() != null &&
                    (addProvince.getReferenceField10() != null && addProvince.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addProvince.getProvinceId() == null || addProvince.getProvinceId().isBlank()) {
                String NUM_RAN_OBJ = "PROVINCE";
                String PROVINCE_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for PROVINCE_ID : " + PROVINCE_ID);
                newProvince.setProvinceId(PROVINCE_ID);
            }
            if (iKeyValuePair != null) {
                newProvince.setLanguageDescription(iKeyValuePair.getLangDesc());
                newProvince.setCompanyName(iKeyValuePair.getCompanyDesc());
                newProvince.setCountryName(iKeyValuePair.getCountryDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addProvince.getStatusId());
            if (statusDesc != null) {
                newProvince.setStatusDescription(statusDesc);
            }
            newProvince.setDeletionIndicator(0L);
            newProvince.setCreatedBy(loginUserID);
            newProvince.setCreatedOn(new Date());
            newProvince.setUpdatedBy(loginUserID);
            newProvince.setUpdatedOn(new Date());
            return provinceRepository.save(newProvince);
        } catch (Exception e) {
            // Error Log
            createProvinceLog2(addProvince, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Province
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param updateProvince
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Province updateProvince(String languageId, String companyId, String countryId, String provinceId, UpdateProvince updateProvince, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Province dbProvince = getProvince(languageId, companyId, countryId, provinceId);
//            if (updateProvince.getProvinceName() != null) {
//                if (updateProvince.getProvinceName().isBlank()) {
//                    throw new BadRequestException("Province Name cannot be blank");
//                }
//                boolean isProvinceNameChanged = !dbProvince.getProvinceName().equalsIgnoreCase(updateProvince.getProvinceName());
//                if (isProvinceNameChanged) {
//                    String oldProvinceDesc = dbProvince.getProvinceName();
//                    BeanUtils.copyProperties(updateProvince, dbProvince, CommonUtils.getNullPropertyNames(updateProvince));
//                    dbProvince.setUpdatedBy(loginUserID);
//                    dbProvince.setUpdatedOn(new Date());
//                    Province updatedProvince = provinceRepository.save(dbProvince);
//
//                    // Updating ProvinceName in District, City, ProvinceMapping and DistrictMapping tables
//                    provinceRepository.provinceDescUpdateProc(languageId, companyId, countryId, provinceId, oldProvinceDesc, updateProvince.getProvinceName());
//                    return updatedProvince;
//                }
//            }
            BeanUtils.copyProperties(updateProvince, dbProvince, CommonUtils.getNullPropertyNames(updateProvince));
            if (updateProvince.getStatusId() != null && !updateProvince.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateProvince.getStatusId());
                if (statusDesc != null) {
                    dbProvince.setStatusDescription(statusDesc);
                }
            }
            dbProvince.setUpdatedBy(loginUserID);
            dbProvince.setUpdatedOn(new Date());
            return provinceRepository.save(dbProvince);
        } catch (Exception e) {
            // Error Log
            createProvinceLog(languageId, companyId, countryId, provinceId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Province
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param loginUserID
     */
    public void deleteProvince(String languageId, String companyId, String countryId, String provinceId, String loginUserID) {

        Province dbProvince = getProvince(languageId, companyId, countryId, provinceId);
        if (dbProvince != null) {
            dbProvince.setDeletionIndicator(1L);
            dbProvince.setUpdatedBy(loginUserID);
            dbProvince.setUpdatedOn(new Date());
            provinceRepository.save(dbProvince);
        } else {
            // Error Log
            createProvinceLog1(languageId, companyId, countryId, provinceId, "Error in deleting ProvinceId - " + provinceId);
            throw new BadRequestException("Error in deleting ProvinceId - " + provinceId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All Province
     *
     * @return
     */
    public List<ReplicaProvince> getAllProvinces() {
        List<ReplicaProvince> provinceList = replicaProvinceRepository.findAll();
        provinceList = provinceList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return provinceList;
    }

    /**
     * Get Province
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @return
     */
    public ReplicaProvince getReplicaProvince(String languageId, String companyId, String countryId, String provinceId) {

        Optional<ReplicaProvince> dbProvince = replicaProvinceRepository.findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDeletionIndicator(
                languageId, companyId, countryId, provinceId, 0L);
        if (dbProvince.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", countryId - " + countryId + " and provinceId - " + provinceId + " doesn't exists";
            // Error Log
            createProvinceLog1(languageId, companyId, countryId, provinceId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbProvince.get();
    }

    /**
     * Find Province
     *
     * @param findProvince
     * @return
     * @throws ParseException
     */
    public List<ReplicaProvince> replicaFindProvinces(FindProvince findProvince) throws ParseException {

        ReplicaProvinceSpecification spec = new ReplicaProvinceSpecification(findProvince);
        List<ReplicaProvince> results = replicaProvinceRepository.findAll(spec);
        log.info("found Provinces --> " + results);
        return results;
    }

    //========================================Province_ErrorLog========================================================
    private void createProvinceLog(String languageId, String companyId, String countryId, String provinceId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(provinceId);
        errorLog.setReferenceField1(countryId);
        errorLog.setMethod("Exception thrown in updateProvince");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createProvinceLog1(String languageId, String companyId, String countryId, String provinceId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(provinceId);
        errorLog.setReferenceField1(countryId);
        errorLog.setMethod("Exception thrown in getProvince");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createProvinceLog2(AddProvince addProvince, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addProvince.getLanguageId());
        errorLog.setCompanyId(addProvince.getCompanyId());
        errorLog.setRefDocNumber(addProvince.getProvinceId());
        errorLog.setReferenceField1(addProvince.getCountryId());
        errorLog.setMethod("Exception thrown in createProvince");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
