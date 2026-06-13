package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndCustomerIdAndDeletionIndicator(
            String languageId, String companyId, String subProductId, String subProductValue,
            String productId, String customerId, Long deletionIndicator);

    List<Customer> findByLanguageIdAndCompanyIdAndCustomerIdAndDeletionIndicator(
            String languageId, String companyId, String customerId, Long deletionIndicator);


//    // Updating customerName in ConsignorTable using Stored Procedure
//    @Transactional
//    @Procedure(procedureName = "customer_desc_update_proc")
//    void updateCustomerDescProc(
//            @Param(value = "languageId") String languageId,
//            @Param(value = "companyId") String companyId,
//            @Param(value = "subProductId") String subProductId,
//            @Param(value = "productId") String productId,
//            @Param(value = "customerId") String customerId,
//            @Param(value = "oldCustomerDesc") String oldCustomerDesc,
//            @Param(value = "newCustomerDesc") String newCustomerDesc);


    // Updating Customer Name in Consignor Table using SQL Query
    @Modifying
    @Transactional
    @Query(value = "Update tblconsignor \n" +
            "Set CUSTOMER_NAME = :newCustomerDesc \n" +
            "Where \n" +
            "LANG_ID = :languageId and \n" +
            "C_ID = :companyId and \n" +
            "SUB_PRODUCT_ID = :subProductId and \n" +
            "PRODUCT_ID = :productId and \n" +
            "CUSTOMER_ID = :customerId and \n" +
            "CUSTOMER_NAME = :oldCustomerDesc and \n" +
            "IS_DELETED = 0", nativeQuery = true)
    long updateCustomerName(
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId,
            @Param(value = "subProductId") String subProductId,
            @Param(value = "productId") String productId,
            @Param(value = "customerId") String customerId,
            @Param(value = "oldCustomerDesc") String oldCustomerDesc,
            @Param(value = "newCustomerDesc") String newCustomerDesc);


    // Get Customers With Query
    @Query(value = "Select * From tblcustomer tc \n" +
            "Where tc.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:subProductId, NULL) IS NULL OR tc.SUB_PRODUCT_ID IN (:subProductId)) \n" +
            "AND (COALESCE(:subProductValue, NULL) IS NULL OR tc.SUB_PRODUCT_VALUE IN (:subProductValue)) \n" +
            "AND (COALESCE(:productId, NULL) IS NULL OR tc.PRODUCT_ID IN (:productId)) \n" +
            "AND (COALESCE(:customerId, NULL) IS NULL OR tc.CUSTOMER_ID IN (:customerId))", nativeQuery = true)
    List<Customer> getCustomersWithQry(
            @Param("languageId") String languageId,
            @Param("companyId") String companyId,
            @Param("subProductId") String subProductId,
            @Param("subProductValue") String subProductValue,
            @Param("productId") String productId,
            @Param("customerId") String customerId);


    // Ory with both null and empty String checks
//    @Query(value = "Select * From tblcustomer tc \n" +
//            "Where tc.IS_DELETED = 0 \n" +
//            "AND (COALESCE(:languageId, '') = '' OR tc.LANG_ID = :languageId) \n" +
//            "AND (COALESCE(:companyId, '') = '' OR tc.C_ID = :companyId) \n" +
//            "AND (COALESCE(:subProductId, '') = '' OR tc.SUB_PRODUCT_ID = :subProductId) \n" +
//            "AND (COALESCE(:subProductValue, '') = '' OR tc.SUB_PRODUCT_VALUE = :subProductValue) \n" +
//            "AND (COALESCE(:productId, '') = '' OR tc.PRODUCT_ID = :productId) \n" +
//            "AND (COALESCE(:customerId, '') = '' OR tc.CUSTOMER_ID = :customerId)", nativeQuery = true)
//    List<Customer> getCustomersWithQry(
//            @Param("languageId") String languageId,
//            @Param("companyId") String companyId,
//            @Param("subProductId") String subProductId,
//            @Param("subProductValue") String subProductValue,
//            @Param("productId") String productId,
//            @Param("customerId") String customerId);


}
