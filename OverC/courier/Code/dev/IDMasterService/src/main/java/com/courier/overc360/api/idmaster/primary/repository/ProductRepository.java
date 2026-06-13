package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.product.Product;
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
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndDeletionIndicator(
            String languageId, String companyId, String subProductId, String subProductValue, String productId, Long deletionIndicator);

    List<Product> findByLanguageIdAndCompanyIdAndProductIdAndDeletionIndicator(
            String languageId, String companyId, String productId, Long deletionIndicator);


    // Updating productName in Consignor & Customer Tables using Stored Procedure
    @Transactional
    @Procedure(procedureName = "product_desc_update_proc")
    void updateProductDescProc(
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId,
            @Param(value = "subProductId") String subProductId,
            @Param(value = "productId") String productId,
            @Param(value = "oldProductDesc") String oldProductDesc,
            @Param(value = "newProductDesc") String newProductDesc);


    // Get Products With Query
    @Query(value = "Select * From tblproduct tp \n" +
            "Where tp.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tp.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tp.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:subProductId, NULL) IS NULL OR tp.SUB_PRODUCT_ID IN (:subProductId)) \n" +
            "AND (COALESCE(:subProductValue, NULL) IS NULL OR tp.SUB_PRODUCT_VALUE IN (:subProductValue)) \n" +
            "AND (COALESCE(:productId, NULL) IS NULL OR tp.PRODUCT_ID IN (:productId))", nativeQuery = true)
    List<Product> getProductsWithQry(
            @Param("languageId") String languageId,
            @Param("companyId") String companyId,
            @Param("subProductId") String subProductId,
            @Param("subProductValue") String subProductValue,
            @Param("productId") String productId);

}
