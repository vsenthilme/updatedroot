package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.consignmentType.AddConsignmentType;
import com.courier.overc360.api.idmaster.primary.model.consignmentType.ConsignmentType;
import com.courier.overc360.api.idmaster.primary.model.consignmentType.UpdateConsignmentType;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ConsignmentTypeRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.consignmentType.FindConsignmentType;
import com.courier.overc360.api.idmaster.replica.model.consignmentType.ReplicaConsignmentType;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaConsignmentTypeRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaConsignmentTypeSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsignmentTypeService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ConsignmentTypeRepository consignmentTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaConsignmentTypeRepository replicaConsignmentTypeRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Consignment Type
     *
     * @param companyId
     * @return
     */
    public ConsignmentType getConsignmentType(String companyId, String languageId, String consignmentTypeId) {
        Optional<ConsignmentType> dbConsignmentType = consignmentTypeRepository.findByCompanyIdAndLanguageIdAndConsignmentTypeIdAndDeletionIndicator(
                companyId, languageId, consignmentTypeId, 0L);
        if (dbConsignmentType.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and consignmentTypeId - " + consignmentTypeId + " doesn't exists";
            // Error Log
            createConsignmentTypeLog1(languageId, companyId, consignmentTypeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsignmentType.get();
    }

    /**
     * Create ConsignmentType
     *
     * @param addConsignmentType
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ConsignmentType createConsignmentType(AddConsignmentType addConsignmentType, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<ConsignmentType> duplicateConsignmentType = consignmentTypeRepository.findByCompanyIdAndLanguageIdAndConsignmentTypeIdAndDeletionIndicator(
                    addConsignmentType.getCompanyId(), addConsignmentType.getLanguageId(), addConsignmentType.getConsignmentTypeId(), 0L);

            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addConsignmentType.getCompanyId(), addConsignmentType.getLanguageId(), 0L);

            if (dbCompany.isEmpty()) {
                throw new BadRequestException("The given values : CompanyId - " + addConsignmentType.getCompanyId()
                        + " and LanguageId - " + addConsignmentType.getLanguageId() + " doesn't exists");
            } else if (duplicateConsignmentType.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : consignmentTypeId - " + addConsignmentType.getConsignmentTypeId());
            } else {
                log.info("new ConsignmentType --> " + addConsignmentType);
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addConsignmentType.getLanguageId(), addConsignmentType.getCompanyId());
                ConsignmentType newConsignmentType = new ConsignmentType();
                BeanUtils.copyProperties(addConsignmentType, newConsignmentType, CommonUtils.getNullPropertyNames(addConsignmentType));
                if ((addConsignmentType.getConsignmentTypeId() != null &&
                        (addConsignmentType.getReferenceField10() != null && addConsignmentType.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addConsignmentType.getConsignmentTypeId() == null || addConsignmentType.getConsignmentTypeId().isBlank()) {
                    String NUM_RAN_OBJ = "CONSIGNMENTTYPE";
                    String CONSIGNMENT_TYPE_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for CONSIGNMENT_TYPE_ID : " + CONSIGNMENT_TYPE_ID);
                    newConsignmentType.setConsignmentTypeId(CONSIGNMENT_TYPE_ID);
                }
                if (iKeyValuePair != null) {
                    newConsignmentType.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newConsignmentType.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addConsignmentType.getStatusId());
                if (statusDesc != null) {
                    newConsignmentType.setStatusDescription(statusDesc);
                }
                newConsignmentType.setDeletionIndicator(0L);
                newConsignmentType.setCreatedBy(loginUserID);
                newConsignmentType.setUpdatedBy(loginUserID);
                newConsignmentType.setCreatedOn(new Date());
                newConsignmentType.setUpdatedOn(new Date());
                return consignmentTypeRepository.save(newConsignmentType);
            }
        } catch (Exception e) {
            // Error Log
            createConsignmentTypeLog2(addConsignmentType, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update ConsignmentType
     *
     * @param companyId
     * @param languageId
     * @param consignmentTypeId
     * @param loginUserID
     * @param updateConsignmentType
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ConsignmentType updateConsignmentType(String companyId, String languageId, String consignmentTypeId, String loginUserID,
                                                 UpdateConsignmentType updateConsignmentType)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ConsignmentType dbConsignmentType = getConsignmentType(companyId, languageId, consignmentTypeId);
            BeanUtils.copyProperties(updateConsignmentType, dbConsignmentType, CommonUtils.getNullPropertyNames(updateConsignmentType));
            if (updateConsignmentType.getStatusId() != null && !updateConsignmentType.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateConsignmentType.getStatusId());
                if (statusDesc != null) {
                    dbConsignmentType.setStatusDescription(statusDesc);
                }
            }
            dbConsignmentType.setUpdatedBy(loginUserID);
            dbConsignmentType.setUpdatedOn(new Date());
            return consignmentTypeRepository.save(dbConsignmentType);
        } catch (Exception e) {
            // Error Log
            createConsignmentTypeLog(languageId, companyId, consignmentTypeId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete ConsignmentType
     *
     * @param loginUserID
     * @param languageId
     * @param consignmentTypeId
     * @param companyId
     */
    public void deleteConsignmentType(String companyId, String languageId, String consignmentTypeId, String loginUserID) {
        ConsignmentType dbConsignmentType = getConsignmentType(companyId, languageId, consignmentTypeId);
        if (dbConsignmentType != null) {
            dbConsignmentType.setDeletionIndicator(1L);
            dbConsignmentType.setUpdatedBy(loginUserID);
            dbConsignmentType.setUpdatedOn(new Date());
            consignmentTypeRepository.save(dbConsignmentType);
        } else {
            // Error Log
            createConsignmentTypeLog1(languageId, companyId, consignmentTypeId, "Error in deleting ConsignmentTypeId - " + consignmentTypeId);
            throw new EntityNotFoundException("Error in deleting ConsignmentTypeId - " + consignmentTypeId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All ConsignmentType Details
     *
     * @return
     */
    public List<ReplicaConsignmentType> getAllConsignmentTypes() {
        List<ReplicaConsignmentType> consignmentType = replicaConsignmentTypeRepository.findAll();
        consignmentType = consignmentType.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return consignmentType;
    }

    /**
     * Get ConsignmentType
     *
     * @param companyId
     * @return
     */
    public ReplicaConsignmentType getReplicaConsignmentType(String companyId, String languageId, String consignmentTypeId) {
        Optional<ReplicaConsignmentType> dbConsignmentType = replicaConsignmentTypeRepository.findByCompanyIdAndLanguageIdAndConsignmentTypeIdAndDeletionIndicator(
                companyId, languageId, consignmentTypeId, 0L);
        if (dbConsignmentType.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and consignmentTypeId - " + consignmentTypeId + " doesn't exists";
            // Error Log
            createConsignmentTypeLog1(languageId, companyId, consignmentTypeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsignmentType.get();
    }

    /**
     * Find ConsignmentType
     *
     * @param findConsignmentType
     * @return
     */
    public List<ReplicaConsignmentType> findConsignmentTypes(FindConsignmentType findConsignmentType) {

        ReplicaConsignmentTypeSpecification spec = new ReplicaConsignmentTypeSpecification(findConsignmentType);
        List<ReplicaConsignmentType> results = replicaConsignmentTypeRepository.findAll(spec);
        log.info("results: " + results);
        return results;
    }

    //=========================================ConsignmentType_ErrorLog====================================================
    private void createConsignmentTypeLog(String languageId, String companyId, String consignmentTypeId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(consignmentTypeId);
        errorLog.setMethod("Exception thrown in updateConsignmentType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createConsignmentTypeLog1(String languageId, String companyId, String consignmentTypeId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(consignmentTypeId);
        errorLog.setMethod("Exception thrown in getConsignmentType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createConsignmentTypeLog2(AddConsignmentType addConsignmentType, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addConsignmentType.getLanguageId());
        errorLog.setCompanyId(addConsignmentType.getCompanyId());
        errorLog.setRefDocNumber(addConsignmentType.getConsignmentTypeId());
        errorLog.setMethod("Exception thrown in createConsignmentType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
