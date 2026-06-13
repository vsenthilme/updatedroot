package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.product.ReplicaProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaProductRepository extends JpaRepository<ReplicaProduct, String>, JpaSpecificationExecutor<ReplicaProduct> {

    Optional<ReplicaProduct> findByLanguageIdAndCompanyIdAndSubProductIdAndProductIdAndDeletionIndicator(
            String languageId, String companyId, String subProductId, String productId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndSubProductIdAndProductIdAndSubProductValueAndDeletionIndicator(
            String languageId, String companyId, String subProductId, String productId, String subProductValue, Long deletionIndicator);

    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tcm.C_ID, ' - ', tcm.C_NAME) As companyDesc, \n" +
            "CONCAT (tsp.SUB_PRODUCT_ID, ' - ', tsp.SUB_PRODUCT_NAME) As subProductDesc, \n" +
            "CONCAT (tp.PRODUCT_ID, ' - ', tp.PRODUCT_NAME) As productDesc, \n" +
            "tp.PRODUCT_NAME As productText, \n " +
            "CONCAT (tsp.SUB_PRODUCT_VALUE, ' - ', tsp.REF_FIELD_1) As subProductValue \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tcm on tl.LANG_ID = tcm.LANG_ID \n" +
            "Join tblsubproduct tsp on tsp.LANG_ID = tl.LANG_ID and tsp.C_ID = tcm.C_ID \n" +
            "Join tblproduct tp on tp.LANG_ID = tl.LANG_ID and tp.C_ID = tcm.C_ID and tsp.SUB_PRODUCT_ID = tp.SUB_PRODUCT_ID and tsp.SUB_PRODUCT_VALUE = tp.SUB_PRODUCT_VALUE \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tcm.C_ID IN (:companyId) and \n" +
            "tsp.SUB_PRODUCT_ID IN (:subProductId) and \n" +
            "tsp.SUB_PRODUCT_VALUE IN (:subProductValue) and \n" +
            "tp.PRODUCT_ID IN (:productId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tcm.IS_DELETED = 0 and \n" +
            "tsp.IS_DELETED = 0 and \n" +
            "tp.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "subProductId") String subProductId,
                                 @Param(value = "subProductValue") String subProductValue,
                                 @Param(value = "productId") String productId);


    // Delete Validation Query for Product delete
    @Query(value = "Select COUNT (*) From (\n" +
            "Select 1 As col From tblcustomer Where IS_DELETED=0 AND PRODUCT_ID IN (:productId)\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:subProductId, NULL) IS NULL OR SUB_PRODUCT_ID IN (:subProductId)) \n" +
            "Union All\n" +
            "Select 1 As col From tblconsignor Where IS_DELETED=0 AND PRODUCT_ID IN (:productId)\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:subProductId, NULL) IS NULL OR SUB_PRODUCT_ID IN (:subProductId)) \n" +
            ") AS temp\n", nativeQuery = true)
    Long getProductCount(@Param(value = "languageId") String languageId,
                         @Param(value = "companyId") String companyId,
                         @Param(value = "subProductId") String subProductId,
                         @Param(value = "productId") String productId);

}
