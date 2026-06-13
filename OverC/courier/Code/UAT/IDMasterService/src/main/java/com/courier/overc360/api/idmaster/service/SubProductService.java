package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.subproject.AddSubProduct;
import com.courier.overc360.api.idmaster.primary.model.subproject.SubProduct;
import com.courier.overc360.api.idmaster.primary.model.subproject.SubProductDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.subproject.UpdateSubProduct;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.SubProductRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.subproduct.FindSubProduct;
import com.courier.overc360.api.idmaster.replica.model.subproduct.ReplicaSubProduct;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaSubProductRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaSubProductSpecification;
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
public class SubProductService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private SubProductRepository subProductRepository;

    @Autowired
    private ReplicaSubProductRepository replicaSubProductRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get SubProduct
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @return
     */
    public SubProduct getSubProduct(String languageId, String companyId, String subProductId, String subProductValue) {

        Optional<SubProduct> dbSubProduct = subProductRepository.findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
                languageId, companyId, subProductId, subProductValue, 0L);
        if (dbSubProduct.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId +
                    ", subProductId - " + subProductId + " and subProductValue - " + subProductValue + " and doesn't exists";
            // Error Log
            createSubProductLog1(languageId, companyId, subProductId, subProductValue, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbSubProduct.get();
    }

    /**
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param subProductValue
     * @return
     */
    public List<SubProduct> getSubProductListForDelete(String languageId, String companyId, String subProductId, String subProductValue) {

        List<SubProduct> dbSubProductList = subProductRepository.getSubProductsWithQry(
                languageId, companyId, subProductId, subProductValue);
        if (dbSubProductList.isEmpty()) {
            String errMsg = "There are no SubProducts with given values";
            // Error Log
            createSubProductLog5(languageId, companyId, subProductId, subProductValue, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbSubProductList;
    }

    /**
     * Create SubProduct
     *
     * @param addSubProduct
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public SubProduct createSubProduct(AddSubProduct addSubProduct, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addSubProduct.getCompanyId(), addSubProduct.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addSubProduct.getCompanyId() + " and languageId - " +
                        addSubProduct.getLanguageId() + " doesn't exists");
            }

            boolean duplicateSubProductPresent = replicaSubProductRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
                    addSubProduct.getLanguageId(), addSubProduct.getCompanyId(), addSubProduct.getSubProductId(),
                    addSubProduct.getSubProductValue(), 0L);
            if (duplicateSubProductPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : subProductId - " + addSubProduct.getSubProductId());
            }
            log.info("new SubProduct --> {}", addSubProduct);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addSubProduct.getLanguageId(), addSubProduct.getCompanyId());
            SubProduct newSubProduct = new SubProduct();
            BeanUtils.copyProperties(addSubProduct, newSubProduct, CommonUtils.getNullPropertyNames(addSubProduct));
            if ((addSubProduct.getCompanyId() != null &&
                    (addSubProduct.getReferenceField10() != null && addSubProduct.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addSubProduct.getSubProductId() == null || addSubProduct.getSubProductId().isBlank()) {
                String NUM_RAN_OBJ = "SUBPRODUCT";
                String SUB_PRODUCT_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for SUB_PRODUCT_ID : " + SUB_PRODUCT_ID);
                newSubProduct.setSubProductId(SUB_PRODUCT_ID);
            }
            if (iKeyValuePair != null) {
                newSubProduct.setLanguageDescription(iKeyValuePair.getLangDesc());
                newSubProduct.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addSubProduct.getStatusId());
            if (statusDesc != null) {
                newSubProduct.setStatusDescription(statusDesc);
            }
            newSubProduct.setDeletionIndicator(0L);
            newSubProduct.setCreatedBy(loginUserID);
            newSubProduct.setCreatedOn(new Date());
            newSubProduct.setUpdatedBy(loginUserID);
            newSubProduct.setUpdatedOn(new Date());
            return subProductRepository.save(newSubProduct);
        } catch (Exception e) {
            // Error Log
            createSubProductLog2(addSubProduct, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Create SubProducts - bulk
     *
     * @param addSubProductList
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
//    public List<SubProduct> createSubProductBulk(List<AddSubProduct> addSubProductList, String loginUserID)
//            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
//
//        List<SubProduct> createdSubProductList = new ArrayList<>();
//        for (AddSubProduct addSubProduct : addSubProductList) {
//            SubProduct newSubProduct = createSubProduct(addSubProduct, loginUserID);
//            createdSubProductList.add(newSubProduct);
//        }
//        return createdSubProductList;
//    }
    @Transactional
    public List<SubProduct> createSubProductBulk(List<AddSubProduct> addSubProductList, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        try {
            List<SubProduct> createdSubProductList = new ArrayList<>();

            String NUM_RAN_OBJ = "SUBPRODUCT";
            String SUB_PRODUCT_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
            log.info("next Value from NumberRange for SUB_PRODUCT_ID : " + SUB_PRODUCT_ID);

            for (AddSubProduct addSubProduct : addSubProductList) {
                boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                        addSubProduct.getCompanyId(), addSubProduct.getLanguageId(), 0L);
                if (!dbCompanyPresent) {
                    throw new BadRequestException("CompanyId - " + addSubProduct.getCompanyId() + " and languageId - " +
                            addSubProduct.getLanguageId() + " doesn't exists");
                }

                boolean duplicateSubProductPresent = replicaSubProductRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
                        addSubProduct.getLanguageId(), addSubProduct.getCompanyId(), addSubProduct.getSubProductId(),
                        addSubProduct.getSubProductValue(), 0L);
                if (duplicateSubProductPresent) {
                    throw new BadRequestException("Record is getting Duplicated with the given values : subProductId - " + addSubProduct.getSubProductId());
                }
                log.info("new SubProduct --> {}", addSubProduct);
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addSubProduct.getLanguageId(), addSubProduct.getCompanyId());
                SubProduct newSubProduct = new SubProduct();
                BeanUtils.copyProperties(addSubProduct, newSubProduct, CommonUtils.getNullPropertyNames(addSubProduct));
                if ((addSubProduct.getCompanyId() != null &&
                        (addSubProduct.getReferenceField10() != null && addSubProduct.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addSubProduct.getSubProductId() == null || addSubProduct.getSubProductId().isBlank()) {
                    newSubProduct.setSubProductId(SUB_PRODUCT_ID);
                }
                if (iKeyValuePair != null) {
                    newSubProduct.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newSubProduct.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addSubProduct.getStatusId());
                if (statusDesc != null) {
                    newSubProduct.setStatusDescription(statusDesc);
                }
                newSubProduct.setDeletionIndicator(0L);
                newSubProduct.setCreatedBy(loginUserID);
                newSubProduct.setCreatedOn(new Date());
                newSubProduct.setUpdatedBy(loginUserID);
                newSubProduct.setUpdatedOn(new Date());
                SubProduct subProduct = subProductRepository.save(newSubProduct);
                createdSubProductList.add(subProduct);
            }
            return createdSubProductList;
        } catch (Exception e) {
            // Error Log
            createSubProductLog3(addSubProductList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update SubProduct Name using Stored Procedure
    private void updateSubProductDescSP(String languageId, String companyId, String subProductId,
                                        UpdateSubProduct updateSubProduct, SubProduct dbSubProduct) {

        if (updateSubProduct.getSubProductName() != null) {
            if (updateSubProduct.getSubProductName().isBlank()) {
                throw new BadRequestException("SubProduct Name cannot be blank");
            }
            boolean isSubProductNameChanged = !dbSubProduct.getSubProductName().equalsIgnoreCase(updateSubProduct.getSubProductName());
            if (isSubProductNameChanged) {
                String newSubProductDesc = updateSubProduct.getSubProductName();
                log.info("new SubProduct Name --> {}", newSubProductDesc);
                String oldSubProductDesc = dbSubProduct.getSubProductName();
                try {
                    // Updating subProductName in Product, Customer & Consignor Tables using Stored Procedure
                    subProductRepository.updateSubProductDescProc(languageId, companyId, subProductId, oldSubProductDesc, newSubProductDesc);
                    log.info("new SubProduct Name - {} updated in associated Masters Tables", newSubProductDesc);
                } catch (Exception e) {
                    log.error("Failed to update new SubProduct Name in associated Masters Tables : " + e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Update SubProduct
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param updateSubProduct
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public SubProduct updateSubProduct(String languageId, String companyId, String subProductId, String subProductValue,
                                       UpdateSubProduct updateSubProduct, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            SubProduct dbSubProduct = getSubProduct(languageId, companyId, subProductId, subProductValue);
            BeanUtils.copyProperties(updateSubProduct, dbSubProduct, CommonUtils.getNullPropertyNames(updateSubProduct));
            if (updateSubProduct.getStatusId() != null && !updateSubProduct.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateSubProduct.getStatusId());
                if (statusDesc != null) {
                    dbSubProduct.setStatusDescription(statusDesc);
                }
            }
            dbSubProduct.setUpdatedBy(loginUserID);
            dbSubProduct.setUpdatedOn(new Date());
            return subProductRepository.save(dbSubProduct);
        } catch (Exception e) {
            // Error Log
            createSubProductLog(languageId, companyId, subProductId, subProductValue, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public List<SubProduct> updateSubProductBulk(List<UpdateSubProduct> updateSubProductList, String loginUserID)
//            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
//
//        List<SubProduct> updatedSubProductList = new ArrayList<>();
//        for (UpdateSubProduct updateSubProduct : updateSubProductList) {
//            SubProduct subProduct = updateSubProduct(updateSubProduct.getLanguageId(), updateSubProduct.getCompanyId(),
//                    updateSubProduct.getSubProductId(), updateSubProduct.getSubProductValue(), updateSubProduct, loginUserID);
//            updatedSubProductList.add(subProduct);
//        }
//        return updatedSubProductList;
//    }

    /**
     * Update SubProducts - bulk
     *
     * @param updateSubProductList
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
    @Transactional
    public List<SubProduct> updateSubProductBulk(List<UpdateSubProduct> updateSubProductList, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        try {
            List<SubProduct> updatedSubProductList = new ArrayList<>();

            for (UpdateSubProduct updateSubProduct : updateSubProductList) {
                List<SubProduct> dbSubProductList = subProductRepository.findByLanguageIdAndCompanyIdAndSubProductIdAndDeletionIndicator(
                        updateSubProduct.getLanguageId(), updateSubProduct.getCompanyId(),
                        updateSubProduct.getSubProductId(), 0L);

                if (dbSubProductList != null && !dbSubProductList.isEmpty()) {
                    subProductRepository.deleteAll(dbSubProductList);
                }
            }

            for (UpdateSubProduct updateSubProduct : updateSubProductList) {

                SubProduct newSubProduct = new SubProduct();
                BeanUtils.copyProperties(updateSubProduct, newSubProduct, CommonUtils.getNullPropertyNames(updateSubProduct));
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(updateSubProduct.getLanguageId(), updateSubProduct.getCompanyId());

                if (iKeyValuePair != null) {
                    newSubProduct.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newSubProduct.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                if (updateSubProduct.getStatusId() != null && !updateSubProduct.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(updateSubProduct.getStatusId());
                    if (statusDesc != null) {
                        newSubProduct.setStatusDescription(statusDesc);
                    }
                }
                newSubProduct.setDeletionIndicator(0L);
                newSubProduct.setCreatedBy(loginUserID);
                newSubProduct.setCreatedOn(new Date());
                newSubProduct.setUpdatedBy(loginUserID);
                newSubProduct.setUpdatedOn(new Date());
                SubProduct subProduct = subProductRepository.save(newSubProduct);
                log.info("created subProduct --> {}", subProduct);
                updatedSubProductList.add(subProduct);
            }
            return updatedSubProductList;
        } catch (Exception e) {
            // Error Log
            createSubProductLog4(updateSubProductList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete SubProduct
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param loginUserID
     */
    public void deleteSubProduct(String languageId, String companyId, String subProductId, String subProductValue, String loginUserID) {

        SubProduct dbSubProduct = getSubProduct(languageId, companyId, subProductId, subProductValue);

        Long subProductCount = replicaSubProductRepository.getSubProductCount(languageId, companyId, subProductId);
        if (subProductCount != null) {
            if (subProductCount > 0) {
                log.info("subProductCount --> {}", subProductCount);
                String errMsg = "Records present in associated tables with subProductId - " + subProductId;
                createSubProductLog1(languageId, companyId, subProductId, subProductValue, errMsg);
                throw new BadRequestException(errMsg);
            }
        }

        if (dbSubProduct != null) {
            dbSubProduct.setDeletionIndicator(1L);
            dbSubProduct.setUpdatedBy(loginUserID);
            dbSubProduct.setUpdatedOn(new Date());
            subProductRepository.save(dbSubProduct);
        } else {
            // Error Log
            createSubProductLog1(languageId, companyId, subProductId, subProductValue, "Error in deleting subProductId - " + subProductId);
            throw new BadRequestException("Error in deleting subProductId - " + subProductId);
        }
    }

//    public void deleteSubProductBulk(List<SubProductDeleteInput> subProductDeleteInputList, String loginUserID) {
//
//        for (SubProductDeleteInput deleteInput : subProductDeleteInputList) {
//            deleteSubProduct(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getSubProductId(),
//                    deleteInput.getSubProductValue(), loginUserID);
//        }
//    }

    /**
     * Delete SubProducts - bulk
     *
     * @param subProductDeleteInputList
     * @param loginUserID
     */
    public void deleteSubProductBulk(List<SubProductDeleteInput> subProductDeleteInputList, String loginUserID) {

        if (subProductDeleteInputList != null && !subProductDeleteInputList.isEmpty()) {
            for (SubProductDeleteInput deleteInput : subProductDeleteInputList) {

                Long subProductCount = replicaSubProductRepository.getSubProductCount(
                        deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getSubProductId());
                if (subProductCount != null) {
                    if (subProductCount > 0) {
                        log.info("subProductCount --> {}", subProductCount);
                        String errMsg = "Records present in associated tables with subProductId - " + deleteInput.getSubProductId();
                        createSubProductLog1(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                                deleteInput.getSubProductId(), deleteInput.getSubProductValue(), errMsg);
                        throw new BadRequestException(errMsg);
                    }
                }

                List<SubProduct> dbSubProductList = getSubProductListForDelete(deleteInput.getLanguageId(),
                        deleteInput.getCompanyId(), deleteInput.getSubProductId(), deleteInput.getSubProductValue());
                log.info("Deleting SubProductId --> {}", deleteInput.getSubProductId());

                for (SubProduct dbSubProduct : dbSubProductList) {
                    dbSubProduct.setDeletionIndicator(1L);
                    dbSubProduct.setUpdatedBy(loginUserID);
                    dbSubProduct.setUpdatedOn(new Date());
                    subProductRepository.save(dbSubProduct);
                }
            }
        }
    }

    //===============================================Replica==================================================

    /**
     * Get SubProduct
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @return
     */
    public ReplicaSubProduct replicaGetSubProduct(String languageId, String companyId, String subProductId, String subProductValue) {

        Optional<ReplicaSubProduct> dbSubProduct = replicaSubProductRepository.findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
                languageId, companyId, subProductId, subProductValue, 0L);
        if (dbSubProduct.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId +
                    ", subProductId - " + subProductId + " and subProductValue - " + subProductValue + " and doesn't exists";
            // Error Log
            createSubProductLog1(languageId, companyId, subProductId, subProductValue, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbSubProduct.get();
    }

    /**
     * Get All SubProduct Details
     *
     * @return
     */
    public List<ReplicaSubProduct> getAllSubProducts() {
        List<ReplicaSubProduct> subProductList = replicaSubProductRepository.findAll();
        subProductList = subProductList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return subProductList;
    }


    /**
     * Find SubProduct
     *
     * @param findSubProduct
     * @return
     */
    public List<ReplicaSubProduct> findSubProducts(FindSubProduct findSubProduct) {

        ReplicaSubProductSpecification spec = new ReplicaSubProductSpecification(findSubProduct);
        List<ReplicaSubProduct> results = replicaSubProductRepository.findAll(spec);
        log.info("found subProducts --> {}", results);
        return results;
    }

    //==========================================SubProduct_ErrorLog====================================================
    private void createSubProductLog(String languageId, String companyId, String subProductId, String subProductValue,
                                     String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(subProductId);
        errorLog.setReferenceField1(subProductValue);
        errorLog.setMethod("Exception thrown in updateSubProduct");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createSubProductLog1(String languageId, String companyId, String subProductId, String subProductValue, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(subProductId);
        errorLog.setReferenceField1(subProductValue);
        errorLog.setMethod("Exception thrown in getSubProduct");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createSubProductLog2(AddSubProduct addSubProduct, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addSubProduct.getLanguageId());
        errorLog.setCompanyId(addSubProduct.getCompanyId());
        errorLog.setRefDocNumber(addSubProduct.getSubProductId());
        errorLog.setReferenceField1(addSubProduct.getSubProductValue());
        errorLog.setMethod("Exception thrown in createSubProduct");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createSubProductLog3(List<AddSubProduct> addSubProductList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (AddSubProduct addSubProduct : addSubProductList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addSubProduct.getLanguageId());
            errorLog.setCompanyId(addSubProduct.getCompanyId());
            errorLog.setRefDocNumber(addSubProduct.getSubProductId());
            errorLog.setReferenceField1(addSubProduct.getSubProductValue());
            errorLog.setMethod("Exception thrown in createSubProduct");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createSubProductLog4(List<UpdateSubProduct> updateSubProductList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateSubProduct updateSubProduct : updateSubProductList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(updateSubProduct.getLanguageId());
            errorLog.setCompanyId(updateSubProduct.getCompanyId());
            errorLog.setRefDocNumber(updateSubProduct.getSubProductId());
            errorLog.setReferenceField1(updateSubProduct.getSubProductValue());
            errorLog.setMethod("Exception thrown in updateSubProduct");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createSubProductLog5(String languageId, String companyId, String subProductId, String subProductValue, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(subProductId);
        if (subProductValue != null) {
            errorLog.setReferenceField1(subProductValue);
        }
        errorLog.setMethod("Exception thrown in getSubProductListForDelete");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

}
