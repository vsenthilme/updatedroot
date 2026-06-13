package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.district.AddDistrict;
import com.courier.overc360.api.idmaster.primary.model.district.District;
import com.courier.overc360.api.idmaster.primary.model.district.UpdateDistrict;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.DistrictRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ProvinceRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.district.FindDistrict;
import com.courier.overc360.api.idmaster.replica.model.district.ReplicaDistrict;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaDistrictRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaProvinceRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaDistrictSpecification;
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
public class DistrictService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaProvinceRepository replicaProvinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ReplicaDistrictRepository replicaDistrictRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get District
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param districtId
     * @return
     */
    public District getDistrict(String languageId, String companyId, String countryId, String provinceId, String districtId) {

        Optional<District> dbDistrict = districtRepository.findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndDeletionIndicator(
                languageId, companyId, countryId, provinceId, districtId, 0L);
        if (dbDistrict.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId + ", countryId - " + countryId
                    + ", provinceId - " + provinceId + " and districtId - " + districtId + " doesn't exists";
            // Error Log
            createDistrictLog1(languageId, companyId, countryId, provinceId, districtId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbDistrict.get();
    }

    /**
     * Create District
     *
     * @param addDistrict
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public District createDistrict(AddDistrict addDistrict, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbProvincePresent = replicaProvinceRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDeletionIndicator(
                    addDistrict.getLanguageId(), addDistrict.getCompanyId(), addDistrict.getCountryId(), addDistrict.getProvinceId(), 0L);

            boolean duplicateDistrictPresent = replicaDistrictRepository.existsByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndDeletionIndicator(
                    addDistrict.getLanguageId(), addDistrict.getCompanyId(), addDistrict.getCountryId(),
                    addDistrict.getProvinceId(), addDistrict.getDistrictId(), 0L);

            if (!dbProvincePresent) {
                throw new BadRequestException("ProvinceId - " + addDistrict.getProvinceId() + ", CompanyId - " + addDistrict.getCompanyId()
                        + ", LanguageId - " + addDistrict.getLanguageId() + ", CountryId - " + addDistrict.getCountryId() + " doesn't exists");
            } else if (duplicateDistrictPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : districtId - " + addDistrict.getDistrictId());
            } else {
                log.info("new District --> " + addDistrict);
                IKeyValuePair iKeyValuePair = replicaProvinceRepository.getDescription(addDistrict.getLanguageId(),
                        addDistrict.getCompanyId(), addDistrict.getCountryId(), addDistrict.getProvinceId());

                District newDistrict = new District();
                BeanUtils.copyProperties(addDistrict, newDistrict, CommonUtils.getNullPropertyNames(addDistrict));
                if ((addDistrict.getDistrictId() != null &&
                        (addDistrict.getReferenceField10() != null && addDistrict.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addDistrict.getDistrictId() == null || addDistrict.getDistrictId().isBlank()) {
                    String NUM_RAN_OBJ = "DISTRICT";
                    String DISTRICT_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    newDistrict.setDistrictId(DISTRICT_ID);
                }
                if (iKeyValuePair != null) {
                    newDistrict.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newDistrict.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newDistrict.setCountryName(iKeyValuePair.getCountryDesc());
                    newDistrict.setProvinceName(iKeyValuePair.getProvinceDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addDistrict.getStatusId());
                if (statusDesc != null) {
                    newDistrict.setStatusDescription(statusDesc);
                }
                newDistrict.setDeletionIndicator(0L);
                newDistrict.setCreatedBy(loginUserID);
                newDistrict.setCreatedOn(new Date());
                newDistrict.setUpdatedBy(loginUserID);
                newDistrict.setUpdatedOn(new Date());
                return districtRepository.save(newDistrict);
            }
        } catch (Exception e) {
            // Error Log
            createDistrictLog2(addDistrict, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update District
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param districtId
     * @param updateDistrict
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public District updateDistrict(String languageId, String companyId, String countryId, String provinceId,
                                   String districtId, UpdateDistrict updateDistrict, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            District dbDistrict = getDistrict(languageId, companyId, countryId, provinceId, districtId);
            BeanUtils.copyProperties(updateDistrict, dbDistrict, CommonUtils.getNullPropertyNames(updateDistrict));
            if (updateDistrict.getStatusId() != null && !updateDistrict.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateDistrict.getStatusId());
                if (statusDesc != null) {
                    dbDistrict.setStatusDescription(statusDesc);
                }
            }
            dbDistrict.setUpdatedBy(loginUserID);
            dbDistrict.setUpdatedOn(new Date());
            return districtRepository.save(dbDistrict);
        } catch (Exception e) {
            // Error Log
            createDistrictLog(languageId, companyId, countryId, provinceId, districtId, e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete District
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param districtId
     * @param loginUserID
     */
    public void deleteDistrict(String languageId, String companyId, String countryId, String provinceId, String districtId, String loginUserID) {

        District dbDistrict = getDistrict(languageId, companyId, countryId, provinceId, districtId);
        if (dbDistrict != null) {
            dbDistrict.setDeletionIndicator(1L);
            dbDistrict.setUpdatedBy(loginUserID);
            dbDistrict.setUpdatedOn(new Date());
            districtRepository.save(dbDistrict);
        } else {
            // Error Log
            createDistrictLog1(languageId, companyId, countryId, provinceId, districtId, "Error in deleting DistrictId - " + districtId);
            throw new BadRequestException("Error in deleting DistrictId - " + districtId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All District Details
     *
     * @return
     */
    public List<ReplicaDistrict> getAllDistricts() {
        List<ReplicaDistrict> districtList = replicaDistrictRepository.findAll();
        districtList = districtList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return districtList;
    }

    /**
     * Get District
     *
     * @param languageId
     * @param companyId
     * @param countryId
     * @param provinceId
     * @param districtId
     * @return
     */
    public ReplicaDistrict getReplicaDistrict(String languageId, String companyId, String countryId, String provinceId, String districtId) {

        Optional<ReplicaDistrict> dbDistrict = replicaDistrictRepository.findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndDeletionIndicator(languageId, companyId, countryId, provinceId, districtId, 0L);
        if (dbDistrict.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId + ", countryId - " + countryId
                    + ", provinceId - " + provinceId + " and districtId - " + districtId + " doesn't exists";
            // Error Log
            createDistrictLog1(languageId, companyId, countryId, provinceId, districtId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbDistrict.get();
    }

    /**
     * Find District
     *
     * @param findDistrict
     * @return
     * @throws ParseException
     */
    public List<ReplicaDistrict> findDistrict(FindDistrict findDistrict) throws ParseException {
        ReplicaDistrictSpecification spec = new ReplicaDistrictSpecification(findDistrict);
        List<ReplicaDistrict> results = replicaDistrictRepository.findAll(spec);
        log.info("found Replica District --> " + results);
        return results;
    }

    //==============================================District_ErrorLog==================================================
    private void createDistrictLog(String languageId, String companyId, String countryId, String provinceId, String districtId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(districtId);
        errorLog.setReferenceField1(countryId);
        errorLog.setReferenceField2(provinceId);
        errorLog.setMethod("Exception thrown in updateDistrict");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createDistrictLog1(String languageId, String companyId, String countryId, String provinceId, String districtId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(districtId);
        errorLog.setReferenceField1(countryId);
        errorLog.setReferenceField2(provinceId);
        errorLog.setMethod("Exception thrown in getDistrict");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createDistrictLog2(AddDistrict addDistrict, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addDistrict.getLanguageId());
        errorLog.setCompanyId(addDistrict.getCompanyId());
        errorLog.setRefDocNumber(addDistrict.getDistrictId());
        errorLog.setReferenceField1(addDistrict.getCountryId());
        errorLog.setReferenceField2(addDistrict.getProvinceId());
        errorLog.setMethod("Exception thrown in createDistrict");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
