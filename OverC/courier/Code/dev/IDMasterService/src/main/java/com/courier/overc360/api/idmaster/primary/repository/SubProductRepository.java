package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.subproject.SubProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SubProductRepository extends JpaRepository<SubProduct, String>, JpaSpecificationExecutor<SubProduct> {

    Optional<SubProduct> findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndDeletionIndicator(
            String languageId, String companyId, String subProductId, String subProductValue, Long deletionIndicator);

    List<SubProduct> findByLanguageIdAndCompanyIdAndSubProductIdAndDeletionIndicator(
            String languageId, String companyId, String subProductId, Long deletionIndicator);


    // Updating subProductName in Product, Customer & Consignor Tables using Stored Procedure
    @Transactional
    @Procedure(procedureName = "subproduct_desc_update_proc")
    void updateSubProductDescProc(
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId,
            @Param(value = "subProductId") String subProductId,
            @Param(value = "oldSubProductDesc") String oldSubProductDesc,
            @Param(value = "newSubProductDesc") String newSubProductDesc);


    // Get SubProducts With Query
    @Query(value = "Select * From tblsubproduct tsp \n" +
            "Where tsp.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tsp.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tsp.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:subProductId, NULL) IS NULL OR tsp.SUB_PRODUCT_ID IN (:subProductId)) \n" +
            "AND (COALESCE(:subProductValue, NULL) IS NULL OR tsp.SUB_PRODUCT_VALUE IN (:subProductValue))", nativeQuery = true)
    List<SubProduct> getSubProductsWithQry(
            @Param("languageId") String languageId,
            @Param("companyId") String companyId,
            @Param("subProductId") String subProductId,
            @Param("subProductValue") String subProductValue);

}
