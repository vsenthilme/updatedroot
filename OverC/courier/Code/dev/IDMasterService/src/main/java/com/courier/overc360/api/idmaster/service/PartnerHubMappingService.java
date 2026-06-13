package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.partnerhubmapping.AddPartnerHubMapping;
import com.courier.overc360.api.idmaster.primary.model.partnerhubmapping.PartnerHubMapping;
import com.courier.overc360.api.idmaster.primary.model.partnerhubmapping.UpdatePartnerHubMapping;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.PartnerHubMappingRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.partnerhubmapping.FindPartnerHubMapping;
import com.courier.overc360.api.idmaster.replica.model.partnerhubmapping.ReplicaPartnerHubMapping;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaHubRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaPartnerHubMappingRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaPartnerHubMappingSpecification;
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
public class PartnerHubMappingService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaHubRepository replicaHubRepository;

    @Autowired
    private PartnerHubMappingRepository partnerHubMappingRepository;

    @Autowired
    private ReplicaPartnerHubMappingRepository replicaPartnerHubMappingRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------PRIMARY------------------------------------------------------*/

    /**
     * Get PartnerHubMapping
     *
     * @param languageId
     * @param companyId
     * @param hubCode
     * @param partnerType
     * @param partnerId
     * @return
     */
    public PartnerHubMapping getPartnerHubMapping(String languageId, String companyId, String hubCode, String partnerType, String partnerId , String productCode) {

        Optional<PartnerHubMapping> dbPartnerHubMapping =
                partnerHubMappingRepository.findByLanguageIdAndCompanyIdAndHubCodeAndPartnerTypeAndPartnerIdAndProductCodeAndDeletionIndicator(
                        languageId, companyId, hubCode, partnerType, partnerId, productCode,0L);
        if (dbPartnerHubMapping.isEmpty()) {
            // Error Log
            createPartnerHubMappingLog1(languageId, companyId, hubCode, partnerType, partnerId,
                    "PartnerId - " + partnerId + ", partnerType - " + partnerType + " and given values doesn't exists");
            throw new BadRequestException("PartnerId - " + partnerId + ", partnerType - " + partnerType + " and given values doesn't exists");
        }
        return dbPartnerHubMapping.get();
    }

    /**
     * Create new PartnerHubMapping
     *
     * @param addPartnerHubMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public PartnerHubMapping createPartnerHubMapping(AddPartnerHubMapping addPartnerHubMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbHubPresent = replicaHubRepository.existsByLanguageIdAndCompanyIdAndHubCodeAndDeletionIndicator(
                    addPartnerHubMapping.getLanguageId(), addPartnerHubMapping.getCompanyId(), addPartnerHubMapping.getHubCode(), 0L);
            if (!dbHubPresent) {
                throw new BadRequestException("HubCode - " + addPartnerHubMapping.getHubCode() + ", companyId - " + addPartnerHubMapping.getCompanyId() +
                        " and languageId - " + addPartnerHubMapping.getLanguageId() + " doesn't exists");
            }

            boolean duplicatePartnerHubMappingPresent =
                    replicaPartnerHubMappingRepository.existsByLanguageIdAndCompanyIdAndHubCodeAndPartnerTypeAndPartnerIdAndProductCodeAndDeletionIndicator(
                            addPartnerHubMapping.getLanguageId(), addPartnerHubMapping.getCompanyId(), addPartnerHubMapping.getHubCode(),
                            addPartnerHubMapping.getPartnerType(), addPartnerHubMapping.getPartnerId(), addPartnerHubMapping.getProductCode() ,0L);
            if (duplicatePartnerHubMappingPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : partnerId - " + addPartnerHubMapping.getPartnerId());
            }

            log.info("new PartnerHubMapping --> {}", addPartnerHubMapping);
            IKeyValuePair iKeyValuePair = replicaPartnerHubMappingRepository.getDescription(addPartnerHubMapping.getLanguageId(),
                    addPartnerHubMapping.getCompanyId(), addPartnerHubMapping.getHubCode());
            PartnerHubMapping newPartnerHubMapping = new PartnerHubMapping();
            BeanUtils.copyProperties(addPartnerHubMapping, newPartnerHubMapping, CommonUtils.getNullPropertyNames(addPartnerHubMapping));
            if (iKeyValuePair != null) {
                newPartnerHubMapping.setLanguageDescription(iKeyValuePair.getLangDesc());
                newPartnerHubMapping.setCompanyName(iKeyValuePair.getCompanyDesc());
                newPartnerHubMapping.setHubName(iKeyValuePair.getHubDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addPartnerHubMapping.getStatusId());
            if (statusDesc != null) {
                newPartnerHubMapping.setStatusDescription(statusDesc);
            }
            Optional<String> hubCategory = replicaPartnerHubMappingRepository.getHubCategory(addPartnerHubMapping.getLanguageId(),
                    addPartnerHubMapping.getCompanyId(), addPartnerHubMapping.getHubCode());
            hubCategory.ifPresent(newPartnerHubMapping::setHubCategory);

            newPartnerHubMapping.setDeletionIndicator(0L);
            newPartnerHubMapping.setCreatedBy(loginUserID);
            newPartnerHubMapping.setCreatedOn(new Date());
            newPartnerHubMapping.setUpdatedBy(loginUserID);
            newPartnerHubMapping.setUpdatedOn(new Date());
            return partnerHubMappingRepository.save(newPartnerHubMapping);
        } catch (Exception e) {
            // Error Log
            createPartnerHubMappingLog2(addPartnerHubMapping, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update PartnerHubMapping
     *
     * @param languageId
     * @param companyId
     * @param hubCode
     * @param partnerType
     * @param partnerId
     * @param updatePartnerHubMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public PartnerHubMapping updatePartnerHubMapping(String languageId, String companyId, String hubCode, String partnerType,
                                                     String partnerId, String productCode,UpdatePartnerHubMapping updatePartnerHubMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            PartnerHubMapping dbPartnerHubMapping = getPartnerHubMapping(languageId, companyId, hubCode, partnerType,partnerId,productCode);
            BeanUtils.copyProperties(updatePartnerHubMapping, dbPartnerHubMapping, CommonUtils.getNullPropertyNames(updatePartnerHubMapping));
            if (updatePartnerHubMapping.getStatusId() != null && !updatePartnerHubMapping.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updatePartnerHubMapping.getStatusId());
                if (statusDesc != null) {
                    dbPartnerHubMapping.setStatusDescription(statusDesc);
                }
            }
            dbPartnerHubMapping.setUpdatedBy(loginUserID);
            dbPartnerHubMapping.setUpdatedOn(new Date());
            return partnerHubMappingRepository.save(dbPartnerHubMapping);
        } catch (Exception e) {
            // Error Log
            createPartnerHubMappingLog(languageId, companyId, hubCode, partnerType, partnerId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete PartnerHubMapping
     *
     * @param languageId
     * @param companyId
     * @param hubCode
     * @param partnerType
     * @param partnerId
     * @param loginUserID
     */
    public void deletePartnerHubMapping(String languageId, String companyId, String hubCode, String partnerType, String partnerId, String loginUserID, String productCode) {

        PartnerHubMapping dbPartnerHubMapping = getPartnerHubMapping(languageId, companyId, hubCode, partnerType, partnerId, productCode);
        if (dbPartnerHubMapping != null) {
            dbPartnerHubMapping.setDeletionIndicator(1L);
            dbPartnerHubMapping.setUpdatedBy(loginUserID);
            dbPartnerHubMapping.setUpdatedOn(new Date());
            partnerHubMappingRepository.save(dbPartnerHubMapping);
        } else {
            // Error Log
            createPartnerHubMappingLog1(languageId, companyId, hubCode, partnerType, partnerId, "Error in deleting partnerId - " + partnerId);
            throw new BadRequestException("Error in deleting partnerId - " + partnerId);
        }
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    /**
     * Get All PartnerHubMapping Details
     *
     * @return
     */
    public List<ReplicaPartnerHubMapping> getAllPartnerHubMappings() {
        List<ReplicaPartnerHubMapping> partnerHubMappingList = replicaPartnerHubMappingRepository.findAll();
        partnerHubMappingList = partnerHubMappingList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return partnerHubMappingList;
    }

    /**
     * Get PartnerHubMapping
     *
     * @param languageId
     * @param companyId
     * @param hubCode
     * @param partnerType
     * @param partnerId
     * @return
     */
    public ReplicaPartnerHubMapping getPartnerHubMappingReplica(String languageId, String companyId, String hubCode, String partnerType, String partnerId,String productCode) {

        Optional<ReplicaPartnerHubMapping> dbPartnerHubMapping =
                replicaPartnerHubMappingRepository.findByLanguageIdAndCompanyIdAndHubCodeAndPartnerTypeAndPartnerIdAndDeletionIndicator(
                        languageId, companyId, hubCode, partnerType, partnerId, 0L);
        if (dbPartnerHubMapping.isEmpty()) {
            // Error Log
            createPartnerHubMappingLog1(languageId, companyId, hubCode, partnerType, partnerId,
                    "PartnerId - " + partnerId + ", partnerType - " + partnerType + " and given values doesn't exists");
            throw new BadRequestException("PartnerId - " + partnerId + ", partnerType - " + partnerType + " and given values doesn't exists");
        }
        return dbPartnerHubMapping.get();
    }

    /**
     * Find PartnerHubMappings
     *
     * @param findPartnerHubMapping
     * @return
     * @throws ParseException
     */
    public List<ReplicaPartnerHubMapping> findPartnerHubMappings(FindPartnerHubMapping findPartnerHubMapping) throws ParseException {

        ReplicaPartnerHubMappingSpecification spec = new ReplicaPartnerHubMappingSpecification(findPartnerHubMapping);
        List<ReplicaPartnerHubMapping> results = replicaPartnerHubMappingRepository.findAll(spec);
        log.info("found PartnerHubMappings --> " + results);
        return results;
    }

    //==========================================PartnerHubMapping_ErrorLog=============================================
    private void createPartnerHubMappingLog(String languageId, String companyId, String hubCode, String partnerType,
                                            String partnerId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(partnerType);
        errorLog.setReferenceField2(hubCode);
        errorLog.setMethod("Exception thrown in updatePartnerHubMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createPartnerHubMappingLog1(String languageId, String companyId, String hubCode, String partnerType,
                                             String partnerId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(partnerType);
        errorLog.setReferenceField2(hubCode);
        errorLog.setMethod("Exception thrown in getPartnerHubMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createPartnerHubMappingLog2(AddPartnerHubMapping addPartnerHubMapping, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addPartnerHubMapping.getLanguageId());
        errorLog.setCompanyId(addPartnerHubMapping.getCompanyId());
        errorLog.setRefDocNumber(addPartnerHubMapping.getPartnerId());
        errorLog.setReferenceField1(addPartnerHubMapping.getPartnerType());
        errorLog.setReferenceField2(addPartnerHubMapping.getHubCode());
        errorLog.setMethod("Exception thrown in createPartnerHubMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
