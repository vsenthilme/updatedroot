package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.districtMapping.AddDistrictMapping;
import com.courier.overc360.api.idmaster.primary.model.districtMapping.DistrictMapping;
import com.courier.overc360.api.idmaster.primary.model.districtMapping.UpdateDistrictMapping;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.DistrictMappingRepository;
import com.courier.overc360.api.idmaster.primary.repository.DistrictRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.districtMapping.FindDistrictMapping;
import com.courier.overc360.api.idmaster.replica.model.districtMapping.ReplicaDistrictMapping;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaDistrictMappingRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaDistrictRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaDistrictMappingSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
public class DistrictMappingService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaDistrictRepository replicaDistrictRepository;

    @Autowired
    private DistrictMappingRepository districtMappingRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ReplicaDistrictMappingRepository replicaDistrictMappingRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get DistrictMapping
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param districtId
     * @return
     */
    public DistrictMapping getDistrictMapping(String languageId, String companyId, String partnerId, String districtId) {

        Optional<DistrictMapping> dbDistrictMapping = districtMappingRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndDistrictIdAndDeletionIndicator
                (languageId, companyId, partnerId, districtId, 0L);
        if (dbDistrictMapping.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + " , partnerId - " + partnerId + " and districtId - " + districtId + " doesn't exists";
            // Error Log
            createDistrictMappingLog1(languageId, companyId, partnerId, districtId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbDistrictMapping.get();
    }

    /***
     * Create DistrictMapping
     *
     * @param addDistrictMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public DistrictMapping createDistrictMapping(AddDistrictMapping addDistrictMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbDistrictPresent = replicaDistrictRepository.existsByLanguageIdAndCompanyIdAndDistrictIdAndDeletionIndicator(
                    addDistrictMapping.getLanguageId(), addDistrictMapping.getCompanyId(), addDistrictMapping.getDistrictId(), 0L);
            if (!dbDistrictPresent) {
                throw new BadRequestException("DistrictId - " + addDistrictMapping.getDistrictId() + ", companyId - " + addDistrictMapping.getCompanyId() +
                        " , languageId - " + addDistrictMapping.getLanguageId() + " doesn't exists");
            }

            boolean duplicateDistrictMappingPresent = replicaDistrictMappingRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndDistrictIdAndDeletionIndicator(
                    addDistrictMapping.getLanguageId(), addDistrictMapping.getCompanyId(),
                    addDistrictMapping.getPartnerId(), addDistrictMapping.getDistrictId(), 0L);
            if (duplicateDistrictMappingPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : partnerId - " +
                        addDistrictMapping.getPartnerId() + " districtId - " + addDistrictMapping.getDistrictId() + ", companyId - " + addDistrictMapping.getCompanyId() +
                        " and languageId - " + addDistrictMapping.getLanguageId());
            }
            log.info("new DistrictMapping --> " + addDistrictMapping);
            IKeyValuePair iKeyValuePair = replicaDistrictMappingRepository.getDescription(addDistrictMapping.getLanguageId(),
                    addDistrictMapping.getCompanyId(), addDistrictMapping.getDistrictId());

            DistrictMapping newDistrictMapping = new DistrictMapping();
            BeanUtils.copyProperties(addDistrictMapping, newDistrictMapping, CommonUtils.getNullPropertyNames(addDistrictMapping));

            if (iKeyValuePair != null) {
                newDistrictMapping.setLanguageDescription(iKeyValuePair.getLangDesc());
                newDistrictMapping.setCompanyName(iKeyValuePair.getCompanyDesc());
                newDistrictMapping.setDistrictName(iKeyValuePair.getDistrictDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addDistrictMapping.getStatusId());
            if (statusDesc != null) {
                newDistrictMapping.setStatusDescription(statusDesc);
            }
            newDistrictMapping.setDeletionIndicator(0L);
            newDistrictMapping.setCreatedBy(loginUserID);
            newDistrictMapping.setCreatedOn(new Date());
            newDistrictMapping.setUpdatedBy(loginUserID);
            newDistrictMapping.setUpdatedOn(new Date());
            return districtMappingRepository.save(newDistrictMapping);
        } catch (Exception e) {
            // Error Log
            createDistrictMappingLog2(addDistrictMapping, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Update DistrictMapping
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param districtId
     * @param updateDistrictMapping
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public DistrictMapping updateDistrictMapping(String languageId, String companyId, String partnerId,
                                                 String districtId, UpdateDistrictMapping updateDistrictMapping, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            DistrictMapping dbDistrictMapping = getDistrictMapping(languageId, companyId, partnerId, districtId);
            BeanUtils.copyProperties(updateDistrictMapping, dbDistrictMapping, CommonUtils.getNullPropertyNames(updateDistrictMapping));
            if (updateDistrictMapping.getStatusId() != null && !updateDistrictMapping.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateDistrictMapping.getStatusId());
                if (statusDesc != null) {
                    dbDistrictMapping.setStatusDescription(statusDesc);
                }
            }
            dbDistrictMapping.setUpdatedBy(loginUserID);
            dbDistrictMapping.setUpdatedOn(new Date());
            return districtMappingRepository.save(dbDistrictMapping);
        } catch (Exception e) {
            // Error Log
            createDistrictMappingLog(languageId, companyId, partnerId, districtId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete DistrictMapping
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param districtId
     * @param loginUserID
     */
    public void deleteDistrictMapping(String languageId, String companyId, String partnerId, String districtId, String loginUserID) {
        DistrictMapping dbDistrictMapping = getDistrictMapping(languageId, companyId, partnerId, districtId);
        if (dbDistrictMapping != null) {
            dbDistrictMapping.setDeletionIndicator(1L);
            dbDistrictMapping.setUpdatedBy(loginUserID);
            dbDistrictMapping.setUpdatedOn(new Date());
            districtMappingRepository.save(dbDistrictMapping);
        } else {
            // Error Log
            createDistrictMappingLog1(languageId, companyId, partnerId, districtId, "Error in deleting partnerId - " + partnerId);
            throw new BadRequestException("Error in deleting partnerId - " + partnerId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get all DistrictMapping Details
     *
     * @return
     */
    public List<ReplicaDistrictMapping> getAllDistrictMappings() {
        List<ReplicaDistrictMapping> districtMappingList = replicaDistrictMappingRepository.findAll();
        districtMappingList = districtMappingList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return districtMappingList;
    }

    /**
     * Get DistrictMapping
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param districtId
     * @return
     */
    public ReplicaDistrictMapping replicaGetDistrictMapping(String languageId, String companyId, String partnerId,
                                                            String districtId) {

        Optional<ReplicaDistrictMapping> dbDistrictMapping = replicaDistrictMappingRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndDistrictIdAndDeletionIndicator
                (languageId, companyId, partnerId, districtId, 0L);

        if (dbDistrictMapping.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + " , partnerId - " + partnerId + " and districtId - " + districtId + " doesn't exists";
            // Error Log
            createDistrictMappingLog1(languageId, companyId, partnerId, districtId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbDistrictMapping.get();
    }

    /**
     * Find DistrictMappings
     *
     * @param findDistrictMapping
     * @return
     * @throws ParseException
     */
    public List<ReplicaDistrictMapping> findDistrictMappings(FindDistrictMapping findDistrictMapping) throws ParseException {

        ReplicaDistrictMappingSpecification spec = new ReplicaDistrictMappingSpecification(findDistrictMapping);
        List<ReplicaDistrictMapping> results = replicaDistrictMappingRepository.findAll(spec);
        log.info("found District Mappings --> " + results);
        return results;
    }

    //========================================DistrictMapping_ErrorLog=================================================
    private void createDistrictMappingLog(String languageId, String companyId, String partnerId,
                                          String districtId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(districtId);
        errorLog.setMethod("Exception thrown in updateDistrictMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createDistrictMappingLog1(String languageId, String companyId, String partnerId,
                                           String districtId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(partnerId);
        errorLog.setReferenceField1(districtId);
        errorLog.setMethod("Exception thrown in getDistrictMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createDistrictMappingLog2(AddDistrictMapping addDistrictMapping, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addDistrictMapping.getLanguageId());
        errorLog.setCompanyId(addDistrictMapping.getCompanyId());
        errorLog.setRefDocNumber(addDistrictMapping.getPartnerId());
        errorLog.setReferenceField1(addDistrictMapping.getDistrictId());
        errorLog.setMethod("Exception thrown in createDistrictMapping");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}
