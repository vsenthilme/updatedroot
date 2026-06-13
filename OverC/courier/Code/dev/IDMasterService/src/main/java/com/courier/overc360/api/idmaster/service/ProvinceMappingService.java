package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.province.Province;
import com.courier.overc360.api.idmaster.primary.model.provincemapping.AddProvinceMapping;
import com.courier.overc360.api.idmaster.primary.model.provincemapping.ProvinceMapping;
import com.courier.overc360.api.idmaster.primary.model.provincemapping.UpdateProvinceMapping;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ProvinceMappingRepository;
import com.courier.overc360.api.idmaster.primary.repository.ProvinceRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.provincemapping.FindProvinceMapping;
import com.courier.overc360.api.idmaster.replica.model.provincemapping.ReplicaProvinceMapping;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaProvinceMappingRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaProvinceMappingSpecification;
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
public class ProvinceMappingService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ProvinceMappingRepository provinceMappingRepository;

    @Autowired
    private ReplicaProvinceMappingRepository replicaProvinceMappingRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------PRIMARY------------------------------------------------------*/

    /**
     * Get ProvinceMapping
     *
     * @param languageId
     * @param companyId
     * @param provinceId
     * @param partnerId
     * @return
     */
    public ProvinceMapping getProvinceMapping(String languageId, String companyId, String provinceId, String partnerId) {

        Optional<ProvinceMapping> dbProvinceMapping = provinceMappingRepository.findByLanguageIdAndCompanyIdAndProvinceIdAndPartnerIdAndDeletionIndicator(
                languageId, companyId, provinceId, partnerId, 0L);
        if (dbProvinceMapping.isEmpty()) {
            // Error Log
            createProvinceMappingLog1(languageId, companyId, provinceId, partnerId, "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + ", provinceId - " + provinceId + " and partnerId - " + partnerId + " doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + ", companyId - " + companyId +
                    ", provinceId - " + provinceId + " and partnerId - " + partnerId + " doesn't exists");
        }
        return dbProvinceMapping.get();
    }

    /**
     * Create new ProvinceMapping
     *
     * @param addProvinceMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ProvinceMapping createProvinceMapping(AddProvinceMapping addProvinceMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<Province> dbProvince = provinceRepository.findByLanguageIdAndCompanyIdAndProvinceIdAndDeletionIndicator(
                    addProvinceMapping.getLanguageId(), addProvinceMapping.getCompanyId(),
                    addProvinceMapping.getProvinceId(), 0L);

            Optional<ProvinceMapping> duplicateProvinceMapping = provinceMappingRepository.findByLanguageIdAndCompanyIdAndProvinceIdAndPartnerIdAndDeletionIndicator(
                    addProvinceMapping.getLanguageId(), addProvinceMapping.getCompanyId(), addProvinceMapping.getProvinceId(),
                    addProvinceMapping.getPartnerId(), 0L);

            if (dbProvince.isEmpty()) {
                throw new BadRequestException("ProvinceId - " + addProvinceMapping.getProvinceId() + ", companyId - " +
                        addProvinceMapping.getCompanyId() + " and languageId - " + addProvinceMapping.getLanguageId() + " doesn't exists");
            } else if (duplicateProvinceMapping.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : partnerId - " +
                        addProvinceMapping.getPartnerId() + " and provinceId - " + addProvinceMapping.getProvinceId());
            } else {
                log.info("new ProvinceMapping --> " + addProvinceMapping);
                IKeyValuePair iKeyValuePair = replicaProvinceMappingRepository.getDescription(addProvinceMapping.getLanguageId(),
                        addProvinceMapping.getCompanyId(), addProvinceMapping.getProvinceId());
                ProvinceMapping newProvinceMapping = new ProvinceMapping();
                BeanUtils.copyProperties(addProvinceMapping, newProvinceMapping, CommonUtils.getNullPropertyNames(addProvinceMapping));
                if (iKeyValuePair != null) {
                    newProvinceMapping.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newProvinceMapping.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newProvinceMapping.setProvinceName(iKeyValuePair.getProvinceDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addProvinceMapping.getStatusId());
                if (statusDesc != null) {
                    newProvinceMapping.setStatusDescription(statusDesc);
                }
                newProvinceMapping.setDeletionIndicator(0L);
                newProvinceMapping.setCreatedBy(loginUserID);
                newProvinceMapping.setCreatedOn(new Date());
                newProvinceMapping.setUpdatedBy(loginUserID);
                newProvinceMapping.setUpdatedOn(new Date());
                return provinceMappingRepository.save(newProvinceMapping);
            }
        } catch (Exception e) {
            // Error Log
            createProvinceMappingLog2(addProvinceMapping, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update ProvinceMapping
     *
     * @param languageId
     * @param companyId
     * @param provinceId
     * @param partnerId
     * @param updateProvinceMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ProvinceMapping updateProvinceMapping(String languageId, String companyId, String provinceId, String partnerId,
                                                 UpdateProvinceMapping updateProvinceMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ProvinceMapping dbProvinceMapping = getProvinceMapping(languageId, companyId, provinceId, partnerId);
            BeanUtils.copyProperties(updateProvinceMapping, dbProvinceMapping, CommonUtils.getNullPropertyNames(updateProvinceMapping));
            if (updateProvinceMapping.getStatusId() != null && !updateProvinceMapping.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateProvinceMapping.getStatusId());
                if (statusDesc != null) {
                    dbProvinceMapping.setStatusDescription(statusDesc);
                }
            }
            dbProvinceMapping.setUpdatedBy(loginUserID);
            dbProvinceMapping.setUpdatedOn(new Date());
            return provinceMappingRepository.save(dbProvinceMapping);
        } catch (Exception e) {
            // Error Log
            createProvinceMappingLog(languageId, companyId, provinceId, partnerId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete ProvinceMapping
     *
     * @param languageId
     * @param companyId
     * @param provinceId
     * @param partnerId
     * @param loginUserID
     */
    public void deleteProvinceMapping(String languageId, String companyId, String provinceId, String partnerId, String loginUserID) {
        ProvinceMapping dbProvinceMapping = getProvinceMapping(languageId, companyId, provinceId, partnerId);
        if (dbProvinceMapping != null) {
            dbProvinceMapping.setDeletionIndicator(1L);
            dbProvinceMapping.setUpdatedBy(loginUserID);
            dbProvinceMapping.setUpdatedOn(new Date());
            provinceMappingRepository.save(dbProvinceMapping);
        } else {
            // Error Log
            createProvinceMappingLog1(languageId, companyId, provinceId, partnerId, "Error in deleting PartnerId - " + partnerId);
            throw new BadRequestException("Error in deleting PartnerId - " + partnerId);
        }
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    /**
     * Get All ProvinceMapping Details
     *
     * @return
     */
    public List<ReplicaProvinceMapping> getAllProvinceMappings() {
        List<ReplicaProvinceMapping> provinceMappingList = replicaProvinceMappingRepository.findAll();
        provinceMappingList = provinceMappingList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return provinceMappingList;
    }

    /**
     * Get ProvinceMapping
     *
     * @param languageId
     * @param companyId
     * @param provinceId
     * @param partnerId
     * @return
     */
    public ReplicaProvinceMapping getProvinceMappingReplica(String languageId, String companyId, String provinceId, String partnerId) {

        Optional<ReplicaProvinceMapping> dbProvinceMapping = replicaProvinceMappingRepository.findByLanguageIdAndCompanyIdAndProvinceIdAndPartnerIdAndDeletionIndicator(
                languageId, companyId, provinceId, partnerId, 0L);
        if (dbProvinceMapping.isEmpty()) {
            // Error Log
            createProvinceMappingLog1(languageId, companyId, provinceId, partnerId, "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + ", provinceId - " + provinceId + " and partnerId - " + partnerId + " doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + ", companyId - " + companyId +
                    ", provinceId - " + provinceId + " and partnerId - " + partnerId + " doesn't exists");
        }
        return dbProvinceMapping.get();
    }

    /**
     * Find ProvinceMappings
     *
     * @param findProvinceMapping
     * @return
     * @throws ParseException
     */
    public List<ReplicaProvinceMapping> findProvinceMappings(FindProvinceMapping findProvinceMapping) throws ParseException {

        ReplicaProvinceMappingSpecification spec = new ReplicaProvinceMappingSpecification(findProvinceMapping);
        List<ReplicaProvinceMapping> results = replicaProvinceMappingRepository.findAll(spec);
        log.info("found Province Mappings --> " + results);
        return results;
    }

    //========================================ProvinceMapping_ErrorLog=================================================
    private void createProvinceMappingLog(String languageId, String companyId, String provinceId, String partnerId,
                                          String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(provinceId);
        errorLog.setMethod("Exception thrown in updateProvinceMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createProvinceMappingLog1(String languageId, String companyId, String provinceId, String partnerId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(provinceId);
        errorLog.setMethod("Exception thrown in getProvinceMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createProvinceMappingLog2(AddProvinceMapping addProvinceMapping, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addProvinceMapping.getLanguageId());
        errorLog.setCompanyId(addProvinceMapping.getCompanyId());
        errorLog.setRefDocNumber(addProvinceMapping.getPartnerId());
        errorLog.setReferenceField1(addProvinceMapping.getProvinceId());
        errorLog.setMethod("Exception thrown in createProvinceMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
