package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.product.AddProduct;
import com.courier.overc360.api.idmaster.primary.model.product.Product;
import com.courier.overc360.api.idmaster.primary.model.product.ProductDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.product.UpdateProduct;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ProductRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.product.FindProduct;
import com.courier.overc360.api.idmaster.replica.model.product.ReplicaProduct;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaProductRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaSubProductRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaProductSpecification;
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
public class ProductService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaSubProductRepository replicaSubProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReplicaProductRepository replicaProductRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Product
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @return
     */
    public Product getProduct(String languageId, String companyId, String subProductId, String productId, String subProductValue) {

        Optional<Product> dbProduct = productRepository.findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndDeletionIndicator(
                languageId, companyId, subProductId, subProductValue, productId, 0L);
        if (dbProduct.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId + ", subProductId - " + subProductId
                    + ", subProductValue - " + subProductValue + " and productId - " + productId + " and doesn't exists";
            // Error Log
            createProductLog1(languageId, companyId, subProductId, subProductValue, productId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbProduct.get();
    }

    /**
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @return
     */
    public List<Product> getProductListForDelete(String languageId, String companyId, String subProductId, String subProductValue, String productId) {

        List<Product> dbProductList = productRepository.getProductsWithQry(
                languageId, companyId, subProductId, subProductValue, productId);
        if (dbProductList.isEmpty()) {
            String errMsg = "There are no Products with given values";
            // Error Log
            createProductLog5(languageId, companyId, subProductId, subProductValue, productId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbProductList;
    }

    /**
     * Create Product
     *
     * @param addProduct
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Product createProduct(AddProduct addProduct, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbSubProductPresent = replicaSubProductRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
                    addProduct.getLanguageId(), addProduct.getCompanyId(), addProduct.getSubProductId(),
                    addProduct.getSubProductValue(), 0L);
            if (!dbSubProductPresent) {
                throw new BadRequestException("SubProductId - " + addProduct.getSubProductId() + ", subProductValue - " + addProduct.getSubProductValue() +
                        ", companyId - " + addProduct.getCompanyId() + " and languageId - " + addProduct.getLanguageId() + " doesn't exists");
            }

            boolean duplicateProductPresent = replicaProductRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndProductIdAndSubProductValueAndDeletionIndicator(
                    addProduct.getLanguageId(), addProduct.getCompanyId(), addProduct.getSubProductId(),
                    addProduct.getProductId(), addProduct.getSubProductValue(), 0L);
            if (duplicateProductPresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : productId - " + addProduct.getProductId());
            }

            log.info("new Product --> " + addProduct);
            IKeyValuePair iKeyValuePair = replicaSubProductRepository.getDescription(addProduct.getLanguageId(),
                    addProduct.getCompanyId(), addProduct.getSubProductId(), addProduct.getSubProductValue());
            Product newProduct = new Product();
            BeanUtils.copyProperties(addProduct, newProduct, CommonUtils.getNullPropertyNames(addProduct));
            if ((addProduct.getProductId() != null &&
                    (addProduct.getReferenceField10() != null && addProduct.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addProduct.getProductId() == null || addProduct.getProductId().isBlank()) {
                String NUM_RAN_OBJ = "PRODUCT";
                String PRODUCT_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for PRODUCT_ID : " + PRODUCT_ID);
                newProduct.setProductId(PRODUCT_ID);
            }
            if (iKeyValuePair != null) {
                newProduct.setLanguageDescription(iKeyValuePair.getLangDesc());
                newProduct.setCompanyName(iKeyValuePair.getCompanyDesc());
                newProduct.setSubProductName(iKeyValuePair.getSubProductDesc());
                newProduct.setReferenceField1(iKeyValuePair.getSubProductValue());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addProduct.getStatusId());
            if (statusDesc != null) {
                newProduct.setStatusDescription(statusDesc);
            }
            newProduct.setDeletionIndicator(0L);
            newProduct.setCreatedBy(loginUserID);
            newProduct.setCreatedOn(new Date());
            newProduct.setUpdatedBy(loginUserID);
            newProduct.setUpdatedOn(new Date());
            return productRepository.save(newProduct);
        } catch (Exception e) {
            // Error Log
            createProductLog2(addProduct, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Create Products - bulk
     *
     * @param addProductList
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
//    public List<Product> createProductBulk(List<AddProduct> addProductList, String loginUserID)
//            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
//
//        List<Product> createdProductList = new ArrayList<>();
//        for (AddProduct addProduct : addProductList) {
//            Product newProduct = createProduct(addProduct, loginUserID);
//            createdProductList.add(newProduct);
//        }
//        return createdProductList;
//    }
    @Transactional
    public List<Product> createProductBulk(List<AddProduct> addProductList, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        try {
            List<Product> createdProductList = new ArrayList<>();

            String NUM_RAN_OBJ = "PRODUCT";
            String PRODUCT_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
            log.info("next Value from NumberRange for PRODUCT_ID : " + PRODUCT_ID);

            for (AddProduct addProduct : addProductList) {
                boolean dbSubProductPresent = replicaSubProductRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
                        addProduct.getLanguageId(), addProduct.getCompanyId(), addProduct.getSubProductId(),
                        addProduct.getSubProductValue(), 0L);
                if (!dbSubProductPresent) {
                    throw new BadRequestException("SubProductId - " + addProduct.getSubProductId() + ", subProductValue - " + addProduct.getSubProductValue() +
                            ", companyId - " + addProduct.getCompanyId() + " and languageId - " + addProduct.getLanguageId() + " doesn't exists");
                }

                boolean duplicateProductPresent = replicaProductRepository.existsByLanguageIdAndCompanyIdAndSubProductIdAndProductIdAndSubProductValueAndDeletionIndicator(
                        addProduct.getLanguageId(), addProduct.getCompanyId(), addProduct.getSubProductId(),
                        addProduct.getProductId(), addProduct.getSubProductValue(), 0L);
                if (duplicateProductPresent) {
                    throw new BadRequestException("Record is getting Duplicated with the given values : productId - " + addProduct.getProductId());
                }

                log.info("new Product --> " + addProduct);
                IKeyValuePair iKeyValuePair = replicaSubProductRepository.getDescription(addProduct.getLanguageId(),
                        addProduct.getCompanyId(), addProduct.getSubProductId(), addProduct.getSubProductValue());
                Product newProduct = new Product();
                BeanUtils.copyProperties(addProduct, newProduct, CommonUtils.getNullPropertyNames(addProduct));
                if ((addProduct.getProductId() != null &&
                        (addProduct.getReferenceField10() != null && addProduct.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addProduct.getProductId() == null || addProduct.getProductId().isBlank()) {
                    newProduct.setProductId(PRODUCT_ID);
                }
                if (iKeyValuePair != null) {
                    newProduct.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newProduct.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newProduct.setSubProductName(iKeyValuePair.getSubProductDesc());
                    newProduct.setReferenceField1(iKeyValuePair.getSubProductValue());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addProduct.getStatusId());
                if (statusDesc != null) {
                    newProduct.setStatusDescription(statusDesc);
                }
                newProduct.setDeletionIndicator(0L);
                newProduct.setCreatedBy(loginUserID);
                newProduct.setCreatedOn(new Date());
                newProduct.setUpdatedBy(loginUserID);
                newProduct.setUpdatedOn(new Date());
                Product product = productRepository.save(newProduct);
                createdProductList.add(product);
            }
            return createdProductList;
        } catch (Exception e) {
            // Error Log
            createProductLog3(addProductList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Product Name using Stored Procedure
    private void updateProductDescSP(String languageId, String companyId, String subProductId, String productId,
                                     UpdateProduct updateProduct, Product dbProduct) {
        if (updateProduct.getProductName() != null) {
            if (updateProduct.getProductName().isBlank()) {
                throw new BadRequestException("Product Name cannot be blank");
            }
            boolean isProductNameChanged = !dbProduct.getProductName().equalsIgnoreCase(updateProduct.getProductName());
            if (isProductNameChanged) {
                String newProductDesc = updateProduct.getProductName();
                log.info("new Product Name --> {}", newProductDesc);
                String oldProductDesc = dbProduct.getProductName();
                try {
                    // Updating productName in Consignor & Customer Tables using Stored Procedure
                    productRepository.updateProductDescProc(languageId, companyId, subProductId, productId, oldProductDesc, newProductDesc);
                    log.info("new Product Name - {} updated in associated Masters Tables", newProductDesc);
                } catch (Exception e) {
                    log.error("Failed to update new Product Name in associated Masters Tables : " + e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Update Product
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @param updateProduct
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Product updateProduct(String languageId, String companyId, String subProductId, String productId,
                                 String subProductValue, UpdateProduct updateProduct, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Product dbProduct = getProduct(languageId, companyId, subProductId, productId, subProductValue);
            BeanUtils.copyProperties(updateProduct, dbProduct, CommonUtils.getNullPropertyNames(updateProduct));
            if (updateProduct.getStatusId() != null && !updateProduct.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateProduct.getStatusId());
                if (statusDesc != null) {
                    dbProduct.setStatusDescription(statusDesc);
                }
            }
            dbProduct.setUpdatedBy(loginUserID);
            dbProduct.setUpdatedOn(new Date());
            return productRepository.save(dbProduct);
        } catch (Exception e) {
            // Error Log
            createProductLog(languageId, companyId, subProductId, productId, subProductValue, e.toString());
            throw new RuntimeException(e);
        }
    }

//    public List<Product> updateProductBulk(List<UpdateProduct> updateProductList, String loginUserID)
//            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
//
//        List<Product> updatedProductList = new ArrayList<>();
//        for (UpdateProduct updateProduct : updateProductList) {
//            Product dbProduct = updateProduct(updateProduct.getLanguageId(), updateProduct.getCompanyId(),
//                    updateProduct.getSubProductId(), updateProduct.getProductId(), updateProduct.getSubProductValue(), updateProduct, loginUserID);
//            updatedProductList.add(dbProduct);
//        }
//        return updatedProductList;
//    }

    /**
     * Update Products - bulk
     *
     * @param updateProductList
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
    @Transactional
    public List<Product> updateProductBulk(List<UpdateProduct> updateProductList, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        try {
            List<Product> updatedProductList = new ArrayList<>();

            for (UpdateProduct updateProduct : updateProductList) {
                List<Product> dbProductList = productRepository.findByLanguageIdAndCompanyIdAndProductIdAndDeletionIndicator(
                        updateProduct.getLanguageId(), updateProduct.getCompanyId(), updateProduct.getProductId(), 0L);

                if (dbProductList != null && !dbProductList.isEmpty()) {
                    productRepository.deleteAll(dbProductList);
                }
            }

            for (UpdateProduct updateProduct : updateProductList) {

                Product newProduct = new Product();
                BeanUtils.copyProperties(updateProduct, newProduct, CommonUtils.getNullPropertyNames(updateProduct));
                IKeyValuePair iKeyValuePair = replicaSubProductRepository.getDescription(updateProduct.getLanguageId(),
                        updateProduct.getCompanyId(), updateProduct.getSubProductId(), updateProduct.getSubProductValue());

                if (iKeyValuePair != null) {
                    newProduct.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newProduct.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newProduct.setSubProductName(iKeyValuePair.getSubProductDesc());
                    newProduct.setReferenceField1(iKeyValuePair.getSubProductValue());
                }
                if (updateProduct.getStatusId() != null && !updateProduct.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(updateProduct.getStatusId());
                    if (statusDesc != null) {
                        newProduct.setStatusDescription(statusDesc);
                    }
                }
                newProduct.setDeletionIndicator(0L);
                newProduct.setCreatedBy(loginUserID);
                newProduct.setCreatedOn(new Date());
                newProduct.setUpdatedBy(loginUserID);
                newProduct.setUpdatedOn(new Date());
                Product product = productRepository.save(newProduct);
                log.info("created Product --> {}", product);
                updatedProductList.add(product);
            }
            return updatedProductList;
        } catch (Exception e) {
            // Error Log
            createProductLog4(updateProductList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Product
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @param loginUserID
     */
    public void deleteProduct(String languageId, String companyId, String subProductId,
                              String productId, String subProductValue, String loginUserID) {

        Product dbProduct = getProduct(languageId, companyId, subProductId, productId, subProductValue);

        Long productCount = replicaProductRepository.getProductCount(languageId, companyId, subProductId, productId);
        if (productCount != null) {
            if (productCount > 0) {
                log.info("productCount --> {}", productCount);
                String errMsg = "Records present in associated tables with productId - " + productId;
                // Error Log
                createProductLog1(languageId, companyId, subProductId, subProductValue, productId, errMsg);
                throw new BadRequestException(errMsg);
            }
        }

        if (dbProduct != null) {
            dbProduct.setDeletionIndicator(1L);
            dbProduct.setUpdatedBy(loginUserID);
            dbProduct.setUpdatedOn(new Date());
            productRepository.save(dbProduct);
        } else {
            // Error Log
            createProductLog1(languageId, companyId, subProductId, subProductValue, productId, "Error in deleting ProductId - " + productId);
            throw new BadRequestException("Error in deleting ProductId - " + productId);
        }
    }

    /**
     * Delete Products - Bulk
     *
     * @param productDeleteInputList
     * @param loginUserID
     */
    public void deleteProductBulk(List<ProductDeleteInput> productDeleteInputList, String loginUserID) {

        if (productDeleteInputList != null && !productDeleteInputList.isEmpty()) {
            for (ProductDeleteInput deleteInput : productDeleteInputList) {

                Long productCount = replicaProductRepository.getProductCount(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                        deleteInput.getSubProductId(), deleteInput.getProductId());
                if (productCount != null) {
                    if (productCount > 0) {
                        log.info("productCount --> {}", productCount);
                        String errMsg = "Records present in associated tables with productId - " + deleteInput.getProductId();
                        // Error Log
                        createProductLog1(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getSubProductId(),
                                deleteInput.getSubProductValue(), deleteInput.getProductId(), errMsg);
                        throw new BadRequestException(errMsg);
                    }
                }

                List<Product> dbProductList = getProductListForDelete(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                        deleteInput.getSubProductId(), deleteInput.getSubProductValue(), deleteInput.getProductId());
                log.info("Deleting ProductId --> {}", deleteInput.getProductId());

                for (Product dbProduct : dbProductList) {
                    dbProduct.setDeletionIndicator(1L);
                    dbProduct.setUpdatedBy(loginUserID);
                    dbProduct.setUpdatedOn(new Date());
                    productRepository.save(dbProduct);
                }
            }
        }
    }

    /*================================================REPLICA========================================================*/

    /**
     * Get All Product Details
     *
     * @return
     */
    public List<ReplicaProduct> getAllProducts() {
        List<ReplicaProduct> productList = replicaProductRepository.findAll();
        productList = productList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return productList;
    }

    /**
     * Get Product
     *
     * @param languageId
     * @param companyId
     * @param subProductId
     * @param productId
     * @return
     */
    public ReplicaProduct getReplicaProduct(String languageId, String companyId, String subProductId, String productId, String subProductValue) {

        Optional<ReplicaProduct> dbProduct = replicaProductRepository.findByLanguageIdAndCompanyIdAndSubProductIdAndProductIdAndDeletionIndicator(
                languageId, companyId, subProductId, productId, 0L);
        if (dbProduct.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId + ", subProductId - " + subProductId
                    + ", subProductValue - " + subProductValue + " and productId - " + productId + " and doesn't exists";
            // Error Log
            createProductLog1(languageId, companyId, subProductId, subProductValue, productId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbProduct.get();
    }

    public List<ReplicaProduct> findProducts(FindProduct findProduct) throws ParseException {
        ReplicaProductSpecification spec = new ReplicaProductSpecification(findProduct);
        List<ReplicaProduct> results = replicaProductRepository.findAll(spec);
        log.info("found Products --> {}", results);
        return results;
    }

    //=============================================Product_ErrorLog====================================================
    private void createProductLog(String languageId, String companyId, String subProductId, String productId,
                                  String subProductValue, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(productId);
        errorLog.setReferenceField1(subProductId);
        errorLog.setReferenceField2(subProductValue);
        errorLog.setMethod("Exception thrown in updateProduct");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createProductLog1(String languageId, String companyId, String subProductId,
                                   String subProductValue, String productId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(productId);
        errorLog.setReferenceField1(subProductId);
        errorLog.setReferenceField2(subProductValue);
        errorLog.setMethod("Exception thrown in getProduct");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createProductLog2(AddProduct addProduct, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addProduct.getLanguageId());
        errorLog.setCompanyId(addProduct.getCompanyId());
        errorLog.setRefDocNumber(addProduct.getProductId());
        errorLog.setReferenceField1(addProduct.getSubProductId());
        errorLog.setReferenceField2(addProduct.getSubProductValue());
        errorLog.setMethod("Exception thrown in createProduct");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createProductLog3(List<AddProduct> addProductList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (AddProduct addProduct : addProductList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addProduct.getLanguageId());
            errorLog.setCompanyId(addProduct.getCompanyId());
            errorLog.setRefDocNumber(addProduct.getProductId());
            errorLog.setReferenceField1(addProduct.getSubProductId());
            errorLog.setReferenceField2(addProduct.getSubProductValue());
            errorLog.setMethod("Exception thrown in createProduct");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createProductLog4(List<UpdateProduct> updateProductList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateProduct updateProduct : updateProductList) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(updateProduct.getLanguageId());
            errorLog.setCompanyId(updateProduct.getCompanyId());
            errorLog.setRefDocNumber(updateProduct.getProductId());
            errorLog.setReferenceField1(updateProduct.getSubProductId());
            errorLog.setReferenceField2(updateProduct.getSubProductValue());
            errorLog.setMethod("Exception thrown in updateProduct");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createProductLog5(String languageId, String companyId, String subProductId, String subProductValue,
                                   String productId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(productId);
        if (subProductId != null && !subProductId.isEmpty()) {
            errorLog.setReferenceField1(subProductId);
        }
        if (subProductValue != null && !subProductValue.isEmpty()) {
            errorLog.setReferenceField2(subProductValue);
        }
        errorLog.setMethod("Exception thrown in getProductListForDelete");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

}
