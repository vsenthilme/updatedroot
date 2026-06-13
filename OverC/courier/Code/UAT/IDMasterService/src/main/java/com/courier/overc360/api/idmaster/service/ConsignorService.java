package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.consignor.AddConsignor;
import com.courier.overc360.api.idmaster.primary.model.consignor.Consignor;
import com.courier.overc360.api.idmaster.primary.model.consignor.ConsignorDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.consignor.UpdateConsignor;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.ConsignorRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.consignor.FindConsignor;
import com.courier.overc360.api.idmaster.replica.model.consignor.ReplicaConsignor;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaConsignorRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCustomerRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaConsignorSpecification;
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
public class ConsignorService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCustomerRepository replicaCustomerRepository;

    @Autowired
    private ConsignorRepository consignorRepository;

    @Autowired
    private ReplicaConsignorRepository replicaConsignorRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------PRIMARY------------------------------------------------------*/

    /**
     * Get Consignor
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @param customerId
     * @param consignorId
     * @return
     */
    public Consignor getConsignor(String languageId, String companyId, String subProductId, String subProductValue,
                                  String productId, String customerId, String consignorId) {

        Optional<Consignor> dbConsignor = consignorRepository.findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndCustomerIdAndConsignorIdAndDeletionIndicator(
                languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, 0L);
        if (dbConsignor.isEmpty()) {
            String errMsg = "The given values : consignorId - " + consignorId + ", customerId - " + customerId + ", productId - " + productId
                    + ", subProductId - " + subProductId + ", subProductValue - " + subProductValue
                    + ", companyId - " + companyId + " and languageId - " + languageId + " doesn't exists";
            // Error Log
            createConsignorLog1(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsignor.get();
    }

    /**
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param subProductValue
     * @param productId
     * @param customerId
     * @param consignorId
     * @return
     */
    private List<Consignor> getConsignorListForDelete(String languageId, String companyId, String subProductId, String subProductValue,
                                                      String productId, String customerId, String consignorId) {

        List<Consignor> dbConsignorList = consignorRepository.getConsignorsWithQry(
                languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId);
        if (dbConsignorList.isEmpty()) {
            String errMsg = "There are no Consignors with given values";
            // Error Log
            createConsignorLog5(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsignorList;
    }

    /**
     * Create new Consignor
     *
     * @param addConsignor
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Consignor createConsignor(AddConsignor addConsignor, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCustomerPresent = replicaCustomerRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndCustomerIdAndDeletionIndicator(
                    addConsignor.getLanguageId(), addConsignor.getCompanyId(), addConsignor.getSubProductId(), addConsignor.getSubProductValue(),
                    addConsignor.getProductId(), addConsignor.getCustomerId(), 0L);
            if (!dbCustomerPresent) {
                throw new BadRequestException("CustomerId - " + addConsignor.getCustomerId() + ", productId - " + addConsignor.getProductId()
                        + ", subProductId - " + addConsignor.getSubProductId() + ", subProductValue - " + addConsignor.getSubProductValue()
                        + ", companyId - " + addConsignor.getCompanyId() + " and languageId - " + addConsignor.getLanguageId() + " doesn't exists");
            }

            boolean duplicateConsignorPresent = replicaConsignorRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndCustomerIdAndConsignorIdAndDeletionIndicator(
                    addConsignor.getLanguageId(), addConsignor.getCompanyId(), addConsignor.getSubProductId(), addConsignor.getSubProductValue(),
                    addConsignor.getProductId(), addConsignor.getCustomerId(), addConsignor.getConsignorId(), 0L);
            if (duplicateConsignorPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : consignorId - " + addConsignor.getConsignorId());
            }

            log.info("new Consignor --> {}", addConsignor);
            IKeyValuePair iKeyValuePair = replicaCustomerRepository.getDescription(addConsignor.getLanguageId(), addConsignor.getCompanyId(),
                    addConsignor.getSubProductId(), addConsignor.getSubProductValue(), addConsignor.getProductId(), addConsignor.getCustomerId());
            Consignor newConsignor = new Consignor();
            BeanUtils.copyProperties(addConsignor, newConsignor, CommonUtils.getNullPropertyNames(addConsignor));
            if ((addConsignor.getConsignorId() != null &&
                    (addConsignor.getReferenceField10() != null && addConsignor.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addConsignor.getConsignorId() == null || addConsignor.getConsignorId().isBlank()) {
                String NUM_RAN_OBJ = "CONSIGNOR";
                String CONSIGNOR_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for CONSIGNOR : " + CONSIGNOR_ID);
                newConsignor.setConsignorId(CONSIGNOR_ID);
            }
            if (iKeyValuePair != null) {
                newConsignor.setLanguageDescription(iKeyValuePair.getLangDesc());
                newConsignor.setCompanyName(iKeyValuePair.getCompanyDesc());
                newConsignor.setSubProductName(iKeyValuePair.getSubProductDesc());
                newConsignor.setProductName(iKeyValuePair.getProductDesc());
                newConsignor.setCustomerName(iKeyValuePair.getCustomerDesc());
                newConsignor.setReferenceField1(iKeyValuePair.getSubProductValue());

                //Retrieve the product text
                String productText = iKeyValuePair.getProductText();
                //Convert UpperCase Remove All Space
                String modifiedProductDesc = productText.toUpperCase().replaceAll("\\s+", "");
                newConsignor.setProductText(modifiedProductDesc);
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addConsignor.getStatusId());
            if (statusDesc != null) {
                newConsignor.setStatusDescription(statusDesc);
            }
            newConsignor.setDeletionIndicator(0L);
            newConsignor.setCreatedBy(loginUserID);
            newConsignor.setCreatedOn(new Date());
            newConsignor.setUpdatedBy(loginUserID);
            newConsignor.setUpdatedOn(new Date());
            return consignorRepository.save(newConsignor);
        } catch (Exception e) {
            // Error Log
            createConsignorLog2(addConsignor, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Create Consignors - bulk
     *
     * @param addConsignorList
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
//    public List<Consignor> createConsignorBulk(List<AddConsignor> addConsignorList, String loginUserID)
//            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
//
//        List<Consignor> createdConsignorList = new ArrayList<>();
//        for (AddConsignor addConsignor : addConsignorList) {
//            Consignor newConsignor = createConsignor(addConsignor, loginUserID);
//            createdConsignorList.add(newConsignor);
//        }
//        return createdConsignorList;
//    }
    public List<Consignor> createConsignorBulk(List<AddConsignor> addConsignorList, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        try {
            List<Consignor> createdConsignorList = new ArrayList<>();

            String NUM_RAN_OBJ = "CONSIGNOR";
            String CONSIGNOR_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
            log.info("next Value from NumberRange for CONSIGNOR : " + CONSIGNOR_ID);

            for (AddConsignor addConsignor : addConsignorList) {
                boolean dbCustomerPresent = replicaCustomerRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndCustomerIdAndDeletionIndicator(
                        addConsignor.getLanguageId(), addConsignor.getCompanyId(), addConsignor.getSubProductId(), addConsignor.getSubProductValue(),
                        addConsignor.getProductId(), addConsignor.getCustomerId(), 0L);
                if (!dbCustomerPresent) {
                    throw new BadRequestException("CustomerId - " + addConsignor.getCustomerId() + ", productId - " + addConsignor.getProductId()
                            + ", subProductId - " + addConsignor.getSubProductId() + ", subProductValue - " + addConsignor.getSubProductValue()
                            + ", companyId - " + addConsignor.getCompanyId() + " and languageId - " + addConsignor.getLanguageId() + " doesn't exists");
                }

                boolean duplicateConsignorPresent = replicaConsignorRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndCustomerIdAndConsignorIdAndDeletionIndicator(
                        addConsignor.getLanguageId(), addConsignor.getCompanyId(), addConsignor.getSubProductId(), addConsignor.getSubProductValue(),
                        addConsignor.getProductId(), addConsignor.getCustomerId(), addConsignor.getConsignorId(), 0L);
                if (duplicateConsignorPresent) {
                    throw new BadRequestException("Record is getting Duplicated with the given values : consignorId - " + addConsignor.getConsignorId());
                }

                log.info("new Consignor --> {}", addConsignor);
                IKeyValuePair iKeyValuePair = replicaCustomerRepository.getDescription(addConsignor.getLanguageId(), addConsignor.getCompanyId(),
                        addConsignor.getSubProductId(), addConsignor.getSubProductValue(), addConsignor.getProductId(), addConsignor.getCustomerId());
                Consignor newConsignor = new Consignor();
                BeanUtils.copyProperties(addConsignor, newConsignor, CommonUtils.getNullPropertyNames(addConsignor));
                if ((addConsignor.getConsignorId() != null &&
                        (addConsignor.getReferenceField10() != null && addConsignor.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addConsignor.getConsignorId() == null || addConsignor.getConsignorId().isBlank()) {
                    newConsignor.setConsignorId(CONSIGNOR_ID);
                }
                if (iKeyValuePair != null) {
                    newConsignor.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newConsignor.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newConsignor.setSubProductName(iKeyValuePair.getSubProductDesc());
                    newConsignor.setProductName(iKeyValuePair.getProductDesc());
                    newConsignor.setCustomerName(iKeyValuePair.getCustomerDesc());
                    newConsignor.setReferenceField1(iKeyValuePair.getSubProductValue());

                    //Retrieve the product text
                    String productText = iKeyValuePair.getProductText();
                    //Convert UpperCase Remove All Space
                    String modifiedProductDesc = productText.toUpperCase().replaceAll("\\s+", "");
                    newConsignor.setProductText(modifiedProductDesc);
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addConsignor.getStatusId());
                if (statusDesc != null) {
                    newConsignor.setStatusDescription(statusDesc);
                }
                newConsignor.setDeletionIndicator(0L);
                newConsignor.setCreatedBy(loginUserID);
                newConsignor.setCreatedOn(new Date());
                newConsignor.setUpdatedBy(loginUserID);
                newConsignor.setUpdatedOn(new Date());
                Consignor consignor = consignorRepository.save(newConsignor);
                createdConsignorList.add(consignor);
            }
            return createdConsignorList;
        } catch (Exception e) {
            // Error Log
            createConsignorLog3(addConsignorList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Consignor
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @param customerId
     * @param consignorId
     * @param updateConsignor
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Consignor updateConsignor(String languageId, String companyId, String subProductId, String subProductValue, String productId,
                                     String customerId, String consignorId, UpdateConsignor updateConsignor, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Consignor dbConsignor = getConsignor(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId);
            BeanUtils.copyProperties(updateConsignor, dbConsignor, CommonUtils.getNullPropertyNames(updateConsignor));
            if (updateConsignor.getStatusId() != null && !updateConsignor.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateConsignor.getStatusId());
                if (statusDesc != null) {
                    dbConsignor.setStatusDescription(statusDesc);
                }
            }
            dbConsignor.setUpdatedBy(loginUserID);
            dbConsignor.setUpdatedOn(new Date());
            return consignorRepository.save(dbConsignor);
        } catch (Exception e) {
            // Error Log
            createConsignorLog(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public List<Consignor> updateConsignorBulk(List<UpdateConsignor> updateConsignorList, String loginUserID)
//            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
//
//        List<Consignor> updatedConsignorList = new ArrayList<>();
//        for (UpdateConsignor updateConsignor : updateConsignorList) {
//            Consignor dbConsignor = updateConsignor(updateConsignor.getLanguageId(), updateConsignor.getCompanyId(),
//                    updateConsignor.getSubProductId(), updateConsignor.getSubProductValue(), updateConsignor.getProductId(),
//                    updateConsignor.getCustomerId(), updateConsignor.getConsignorId(), updateConsignor, loginUserID);
//            updatedConsignorList.add(dbConsignor);
//        }
//        return updatedConsignorList;
//    }

    /**
     * Update Consignors - bulk
     *
     * @param updateConsignorList
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
    @Transactional
    public List<Consignor> updateConsignorBulk(List<UpdateConsignor> updateConsignorList, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        try {
            List<Consignor> updatedConsignorList = new ArrayList<>();

            for (UpdateConsignor updateConsignor : updateConsignorList) {
                List<Consignor> dbConsignorList = consignorRepository.findByLanguageIdAndCompanyIdAndConsignorIdAndDeletionIndicator(
                        updateConsignor.getLanguageId(), updateConsignor.getCompanyId(), updateConsignor.getConsignorId(), 0L);
                if (dbConsignorList != null && !dbConsignorList.isEmpty()) {
                    consignorRepository.deleteAll(dbConsignorList);
                }
            }

            for (UpdateConsignor updateConsignor : updateConsignorList) {

                Consignor newConsignor = new Consignor();
                BeanUtils.copyProperties(updateConsignor, newConsignor, CommonUtils.getNullPropertyNames(updateConsignor));
                IKeyValuePair iKeyValuePair = replicaCustomerRepository.getDescription(
                        updateConsignor.getLanguageId(), updateConsignor.getCompanyId(), updateConsignor.getSubProductId(),
                        updateConsignor.getSubProductValue(), updateConsignor.getProductId(), updateConsignor.getCustomerId());

                if (iKeyValuePair != null) {
                    newConsignor.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newConsignor.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newConsignor.setSubProductName(iKeyValuePair.getSubProductDesc());
                    newConsignor.setProductName(iKeyValuePair.getProductDesc());
                    newConsignor.setCustomerName(iKeyValuePair.getCustomerDesc());
                    newConsignor.setReferenceField1(iKeyValuePair.getSubProductValue());

                    //Retrieve the product text
                    String productText = iKeyValuePair.getProductText();
                    //Convert UpperCase Remove All Space
                    String modifiedProductDesc = productText.toUpperCase().replaceAll("\\s+", "");
                    newConsignor.setProductText(modifiedProductDesc);
                }
                if (updateConsignor.getStatusId() != null && !updateConsignor.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(updateConsignor.getStatusId());
                    if (statusDesc != null) {
                        newConsignor.setStatusDescription(statusDesc);
                    }
                }
                newConsignor.setDeletionIndicator(0L);
                newConsignor.setCreatedBy(loginUserID);
                newConsignor.setCreatedOn(new Date());
                newConsignor.setUpdatedBy(loginUserID);
                newConsignor.setUpdatedOn(new Date());
                Consignor consignor = consignorRepository.save(newConsignor);
                log.info("Created Consignor --> {}", consignor);
                updatedConsignorList.add(consignor);
            }
            return updatedConsignorList;
        } catch (Exception e) {
            // Error Log
            createConsignorLog4(updateConsignorList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Consignor
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @param customerId
     * @param consignorId
     * @param loginUserID
     */
    public void deleteConsignor(String languageId, String companyId, String subProductId, String subProductValue, String productId,
                                String customerId, String consignorId, String loginUserID) {

        Consignor dbConsignor = getConsignor(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId);
        if (dbConsignor != null) {
            dbConsignor.setDeletionIndicator(1L);
            dbConsignor.setUpdatedBy(loginUserID);
            dbConsignor.setUpdatedOn(new Date());
            consignorRepository.save(dbConsignor);
        } else {
            // Error Log
            createConsignorLog1(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId,
                    "Error in deleting consignorId - " + consignorId);
            throw new BadRequestException("Error in deleting consignorId - " + consignorId);
        }
    }

    /**
     * Delete Consignors - bulk
     *
     * @param consignorDeleteInputList
     * @param loginUserID
     */
    public void deleteConsignorBulk(List<ConsignorDeleteInput> consignorDeleteInputList, String loginUserID) {

        if (consignorDeleteInputList != null && !consignorDeleteInputList.isEmpty()) {
            for (ConsignorDeleteInput deleteInput : consignorDeleteInputList) {

                List<Consignor> dbConsignorList = getConsignorListForDelete(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                        deleteInput.getSubProductId(), deleteInput.getSubProductValue(), deleteInput.getProductId(),
                        deleteInput.getCustomerId(), deleteInput.getConsignorId());
                log.info("Deleting ConsignorId --> {}", deleteInput.getConsignorId());

                for (Consignor dbConsignor : dbConsignorList) {
                    dbConsignor.setDeletionIndicator(1L);
                    dbConsignor.setUpdatedBy(loginUserID);
                    dbConsignor.setUpdatedOn(new Date());
                    consignorRepository.save(dbConsignor);
                }
            }
        }
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    /**
     * Get All Consignor Details
     *
     * @return
     */
    public List<ReplicaConsignor> getAllConsignors() {
        List<ReplicaConsignor> consignorList = replicaConsignorRepository.findAll();
        consignorList = consignorList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return consignorList;
    }

    /**
     * Get Consignor
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @param customerId
     * @param consignorId
     * @return
     */
    public ReplicaConsignor getConsignorReplica(String languageId, String companyId, String subProductId, String subProductValue,
                                                String productId, String customerId, String consignorId) {

        Optional<ReplicaConsignor> dbConsignor = replicaConsignorRepository.findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndCustomerIdAndConsignorIdAndDeletionIndicator(
                languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, 0L);
        if (dbConsignor.isEmpty()) {
            String errMsg = "The given values : consignorId - " + consignorId + ", customerId - " + customerId + ", productId - " + productId
                    + ", subProductId - " + subProductId + ", subProductValue - " + subProductValue
                    + ", companyId - " + companyId + " and languageId - " + languageId + " doesn't exists";
            // Error Log
            createConsignorLog1(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsignor.get();
    }

    /**
     * Find Consignors
     *
     * @param findConsignor
     * @return
     * @throws ParseException
     */
    public List<ReplicaConsignor> findConsignors(FindConsignor findConsignor) throws ParseException {

        ReplicaConsignorSpecification spec = new ReplicaConsignorSpecification(findConsignor);
        List<ReplicaConsignor> results = replicaConsignorRepository.findAll(spec);
        log.info("found Consignors --> {}", results);
        return results;
    }

    //============================================Consignor_ErrorLog===================================================
    private void createConsignorLog(String languageId, String companyId, String subProductId, String subProductValue, String productId,
                                    String customerId, String consignorId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(consignorId);
        errorLog.setMethod("Exception thrown in updateConsignor");
        errorLog.setReferenceField1(subProductId);
        errorLog.setReferenceField2(productId);
        errorLog.setReferenceField3(customerId);
        errorLog.setReferenceField4(subProductValue);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createConsignorLog1(String languageId, String companyId, String subProductId, String subProductValue,
                                     String productId, String customerId, String consignorId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(consignorId);
        errorLog.setMethod("Exception thrown in getConsignor");
        errorLog.setReferenceField1(subProductId);
        errorLog.setReferenceField2(productId);
        errorLog.setReferenceField3(customerId);
        errorLog.setReferenceField4(subProductValue);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createConsignorLog2(AddConsignor addConsignor, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addConsignor.getLanguageId());
        errorLog.setCompanyId(addConsignor.getCompanyId());
        errorLog.setRefDocNumber(addConsignor.getConsignorId());
        errorLog.setMethod("Exception thrown in createConsignor");
        errorLog.setReferenceField1(addConsignor.getSubProductId());
        errorLog.setReferenceField2(addConsignor.getProductId());
        errorLog.setReferenceField3(addConsignor.getCustomerId());
        errorLog.setReferenceField4(addConsignor.getSubProductValue());
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createConsignorLog3(List<AddConsignor> addConsignorList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (AddConsignor addConsignor : addConsignorList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addConsignor.getLanguageId());
            errorLog.setCompanyId(addConsignor.getCompanyId());
            errorLog.setRefDocNumber(addConsignor.getConsignorId());
            errorLog.setMethod("Exception thrown in createConsignor");
            errorLog.setReferenceField1(addConsignor.getSubProductId());
            errorLog.setReferenceField2(addConsignor.getProductId());
            errorLog.setReferenceField3(addConsignor.getCustomerId());
            errorLog.setReferenceField4(addConsignor.getSubProductValue());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createConsignorLog4(List<UpdateConsignor> updateConsignorList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateConsignor updateConsignor : updateConsignorList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(updateConsignor.getLanguageId());
            errorLog.setCompanyId(updateConsignor.getCompanyId());
            errorLog.setRefDocNumber(updateConsignor.getConsignorId());
            errorLog.setMethod("Exception thrown in createConsignor");
            errorLog.setReferenceField1(updateConsignor.getSubProductId());
            errorLog.setReferenceField2(updateConsignor.getProductId());
            errorLog.setReferenceField3(updateConsignor.getCustomerId());
            errorLog.setReferenceField4(updateConsignor.getSubProductValue());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createConsignorLog5(String languageId, String companyId, String subProductId, String subProductValue,
                                     String productId, String customerId, String consignorId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(consignorId);
        errorLog.setMethod("Exception thrown in getConsignorListForDelete");
        if (subProductId != null && !subProductId.isEmpty()) {
            errorLog.setReferenceField1(subProductId);
        }
        if (subProductValue != null && !subProductValue.isEmpty()) {
            errorLog.setReferenceField2(subProductValue);
        }
        if (productId != null && !productId.isEmpty()) {
            errorLog.setReferenceField3(productId);
        }
        if (customerId != null && !customerId.isEmpty()) {
            errorLog.setReferenceField4(customerId);
        }
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

}
