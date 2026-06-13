package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.AddCompany;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.company.UpdateCompany;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.language.Language;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.LanguageRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.company.FindCompany;
import com.courier.overc360.api.idmaster.replica.model.company.ReplicaCompany;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCityRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaLanguageRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCompanySpecification;
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
public class CompanyService {

    @Autowired
    private ReplicaLanguageRepository replicaLanguageRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCityRepository replicaCityRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Company
     *
     * @param companyId
     * @param languageId
     * @return
     */
    public Company getCompany(String companyId, String languageId) {

        Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                companyId, languageId, 0L);
        if (dbCompany.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + " and languageId - " + languageId + " doesn't exists";
            // Error Log
            createCompanyLog1(languageId, companyId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCompany.get();
    }

    /**
     * Create Company
     *
     * @param addCompany
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Company createCompany(AddCompany addCompany, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<Language> dbLanguage = languageRepository.findByLanguageIdAndDeletionIndicator(
                    addCompany.getLanguageId(), 0L);

            Optional<Company> duplicateCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addCompany.getCompanyId(), addCompany.getLanguageId(), 0L);

            String cityDesc = null;
            String countryDesc = null;
            String provinceDesc = null;
            String districtDesc = null;

            if (addCompany.getCityId() != null) {
                cityDesc = replicaCityRepository.getCityDesc(addCompany.getCityId());
            }
            if (addCompany.getCountryId() != null) {
                countryDesc = replicaCityRepository.getCountryDesc(addCompany.getCountryId());
            }
            if (addCompany.getProvinceId() != null) {
                provinceDesc = replicaCityRepository.getProvinceDesc(addCompany.getProvinceId());
            }
            if (addCompany.getDistrictId() != null) {
                districtDesc = replicaCityRepository.getDistrictDesc(addCompany.getDistrictId());
            }

            if (dbLanguage.isEmpty()) {
                throw new BadRequestException("LanguageId - " + addCompany.getLanguageId() + " doesn't exists");
            } else if (duplicateCompany.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with given values : companyId - " + addCompany.getCompanyId());
            } else {
                log.info("new Company --> " + addCompany);
                Company newCompany = new Company();
                IKeyValuePair iKeyValuePair = replicaLanguageRepository.getDescription(addCompany.getLanguageId());
                BeanUtils.copyProperties(addCompany, newCompany, CommonUtils.getNullPropertyNames(addCompany));
                if ((addCompany.getCompanyId() != null &&
                        (addCompany.getReferenceField10() != null && addCompany.getReferenceField10().equalsIgnoreCase("true"))) ||
                        (addCompany.getCompanyId() == null || addCompany.getCompanyId().isBlank())) {

                    String NUM_RAN_OBJ = "COMPANY";
                    String C_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for C_ID : " + C_ID);
                    newCompany.setCompanyId(C_ID);
                }
                if (cityDesc != null && !cityDesc.isEmpty()) {
                    newCompany.setCityName(addCompany.getCityId() + cityDesc);
                }
                if (countryDesc != null && !countryDesc.isEmpty()) {
                    newCompany.setCountryName(addCompany.getCountryId() + countryDesc);
                }
                if (provinceDesc != null && !provinceDesc.isEmpty()) {
                    newCompany.setProvinceName(addCompany.getProvinceId() + provinceDesc);
                }
                if (districtDesc != null && !districtDesc.isEmpty()) {
                    newCompany.setDistrictName(addCompany.getDistrictId() + districtDesc);
                }
                if (iKeyValuePair != null) {
                    newCompany.setLanguageDescription(iKeyValuePair.getLangDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addCompany.getStatusId());
                if (statusDesc != null) {
                    newCompany.setStatusDescription(statusDesc);
                }
                newCompany.setDeletionIndicator(0L);
                newCompany.setCreatedBy(loginUserID);
                newCompany.setCreatedOn(new Date());
                newCompany.setUpdatedBy(loginUserID);
                newCompany.setUpdatedOn(new Date());
                return companyRepository.save(newCompany);
            }
        } catch (Exception e) {
            // Error Log
            createCompanyLog2(addCompany, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Company Name in all Masters Tables using Stored Procedure
    private void updateCompanyDescSP(String companyId, String languageId, UpdateCompany updateCompany, Company dbCompany) {

        if (updateCompany.getCompanyName() != null) {
            if (updateCompany.getCompanyName().isBlank()) {
                throw new BadRequestException("Company Name cannot be blank");
            }
            boolean isCompanyNameChanged = !dbCompany.getCompanyName().equalsIgnoreCase(updateCompany.getCompanyName());
            if (isCompanyNameChanged) {
                String newCompanyName = updateCompany.getCompanyName();
                log.info("new Company Name --> {}", newCompanyName);
                String oldCompanyDesc = dbCompany.getCompanyName();
                try {
                    // Update Company Name in all Masters Tables using Stored Procedure
                    companyRepository.updateCompanyDescProc(languageId, companyId, oldCompanyDesc, newCompanyName);
                    log.info("new Company Name - {} updated in all Masters Tables", newCompanyName);
                } catch (Exception e) {
                    log.error("Failed to update new Company Name in all Masters Tables : " + e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Update Company
     *
     * @param companyId
     * @param languageId
     * @param loginUserID
     * @param updateCompany
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Company updateCompany(String companyId, String languageId, String loginUserID, UpdateCompany updateCompany)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Company dbCompany = getCompany(companyId, languageId);
            BeanUtils.copyProperties(updateCompany, dbCompany, CommonUtils.getNullPropertyNames(updateCompany));

            String cityDesc = null;
            String countryDesc = null;
            String provinceDesc = null;
            String districtDesc = null;

            if (updateCompany.getCityId() != null) {
                cityDesc = replicaCityRepository.getCityDesc(updateCompany.getCityId());
                dbCompany.setCityName(updateCompany.getCityId() + cityDesc);
            }
            if (updateCompany.getCountryId() != null) {
                countryDesc = replicaCityRepository.getCountryDesc(updateCompany.getCountryId());
                dbCompany.setCountryName(updateCompany.getCountryId() + countryDesc);
            }
            if (updateCompany.getProvinceId() != null) {
                provinceDesc = replicaCityRepository.getProvinceDesc(updateCompany.getProvinceId());
                dbCompany.setProvinceName(updateCompany.getProvinceId() + provinceDesc);
            }
            if (updateCompany.getDistrictId() != null) {
                districtDesc = replicaCityRepository.getDistrictDesc(updateCompany.getDistrictId());
                dbCompany.setDistrictName(updateCompany.getDistrictId() + districtDesc);
            }
            if (updateCompany.getStatusId() != null && !updateCompany.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateCompany.getStatusId());
                if (statusDesc != null) {
                    dbCompany.setStatusDescription(statusDesc);
                }
            }
            dbCompany.setUpdatedBy(loginUserID);
            dbCompany.setUpdatedOn(new Date());
            return companyRepository.save(dbCompany);
        } catch (Exception e) {
            // Error Log
            createCompanyLog(languageId, companyId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Company
     *
     * @param companyId
     * @param languageId
     * @param loginUserID
     */
    public void deleteCompany(String companyId, String languageId, String loginUserID) {

        Company dbCompany = getCompany(companyId, languageId);

        Long companyCount = replicaCompanyRepository.getCompanyCount(languageId, companyId);
        if (companyCount != null) {
            if (companyCount > 0) {
                log.info("companyCount --> {}", companyCount);
                String errMsg = "Records present in associated tables with companyId - " + companyId;
                createCompanyLog1(languageId, companyId, errMsg);
                throw new BadRequestException(errMsg);
            }
        }

        if (dbCompany != null) {
            dbCompany.setDeletionIndicator(1L);
            dbCompany.setUpdatedBy(loginUserID);
            dbCompany.setUpdatedOn(new Date());
            companyRepository.save(dbCompany);
        } else {
            String errMsg = "Error in deleting CompanyId - " + companyId;
            // Error Log
            createCompanyLog1(languageId, companyId, errMsg);
            throw new BadRequestException(errMsg);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All Company Details
     *
     * @return
     */
    public List<ReplicaCompany> getAllCompanyDetails() {
        List<ReplicaCompany> companyList = replicaCompanyRepository.findAll();
        companyList = companyList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return companyList;
    }

    /**
     * Get Company
     *
     * @param companyId
     * @param languageId
     * @return
     */
    public ReplicaCompany replicaGetCompany(String companyId, String languageId) {

        Optional<ReplicaCompany> dbCompany = replicaCompanyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                companyId, languageId, 0L);
        if (dbCompany.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + " and languageId - " + languageId + " doesn't exists";
            // Error Log
            createCompanyLog1(languageId, companyId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCompany.get();
    }


    /**
     * Find Company
     *
     * @param findCompany
     * @return
     */
    public List<ReplicaCompany> findCompany(FindCompany findCompany) {

        ReplicaCompanySpecification spec = new ReplicaCompanySpecification(findCompany);
        List<ReplicaCompany> results = replicaCompanyRepository.findAll(spec);
        log.info("found Companies --> " + results);
        return results;
    }

    //===========================================Company_ErrorLog======================================================
    private void createCompanyLog(String languageId, String companyId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(companyId);
        errorLog.setMethod("Exception thrown in updateCompany");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createCompanyLog1(String languageId, String companyId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(companyId);
        errorLog.setMethod("Exception thrown in getCompany");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCompanyLog2(AddCompany addCompany, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addCompany.getLanguageId());
        errorLog.setCompanyId(addCompany.getCompanyId());
        errorLog.setRefDocNumber(addCompany.getCompanyId());
        errorLog.setMethod("Exception thrown in createCompany");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
