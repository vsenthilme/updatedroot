package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.subproduct.ReplicaSubProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaSubProductRepository extends JpaRepository<ReplicaSubProduct, String>, JpaSpecificationExecutor<ReplicaSubProduct> {

    Optional<ReplicaSubProduct> findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
            String languageId, String companyId, String subProductId, String subProductValue, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
            String languageId, String companyId, String subProductId, String subProductValue, Long deletionIndicator);

    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tcm.C_ID, ' - ', tcm.C_NAME) As companyDesc, \n" +
            "CONCAT (tsp.SUB_PRODUCT_ID, ' - ', tsp.SUB_PRODUCT_NAME) As subProductDesc, \n" +
            "CONCAT (tsp.SUB_PRODUCT_VALUE, ' - ', tsp.REF_FIELD_1) As subProductValue \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tcm on tl.LANG_ID = tcm.LANG_ID \n" +
            "Join tblsubproduct tsp on tsp.LANG_ID = tl.LANG_ID and tsp.C_ID = tcm.C_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tcm.C_ID IN (:companyId) and \n" +
            "tsp.SUB_PRODUCT_ID IN (:subProductId) and \n" +
            "tsp.SUB_PRODUCT_VALUE IN (:subProductValue) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tcm.IS_DELETED = 0 and \n" +
            "tsp.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "subProductId") String subProductId,
                                 @Param(value = "subProductValue") String subProductValue);


    // Delete Validation Query for SubProduct delete
    @Query(value = "Select COUNT (*) From (\n" +
            "Select 1 As col From tblproduct Where IS_DELETED=0 AND SUB_PRODUCT_ID IN (:subProductId)\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) \n" +
            "Union All\n" +
            "Select 1 As col From tblcustomer Where IS_DELETED=0 AND SUB_PRODUCT_ID IN (:subProductId)\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) \n" +
            "Union All\n" +
            "Select 1 As col From tblconsignor Where IS_DELETED=0 AND SUB_PRODUCT_ID IN (:subProductId)\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) \n" +
            ") AS temp\n", nativeQuery = true)
    Long getSubProductCount(@Param(value = "languageId") String languageId,
                            @Param(value = "companyId") String companyId,
                            @Param(value = "subProductId") String subProductId);

}
