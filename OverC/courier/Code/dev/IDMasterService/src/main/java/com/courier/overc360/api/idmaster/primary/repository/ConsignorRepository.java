package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.consignor.Consignor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ConsignorRepository extends JpaRepository<Consignor, String>, JpaSpecificationExecutor<Consignor> {

    Optional<Consignor> findByLanguageIdAndCompanyIdAndSubProductIdAndSubProductValueAndProductIdAndCustomerIdAndConsignorIdAndDeletionIndicator(
            String languageId, String companyId, String subProductId, String subProductValue,
            String productId, String customerId, String consignorId, Long deletionIndicator);

    List<Consignor> findByLanguageIdAndCompanyIdAndConsignorIdAndDeletionIndicator(
            String languageId, String companyId, String consignorId, Long deletionIndicator);


    // Get Consignors With Query
    @Query(value = "Select * From tblconsignor tc \n" +
            "Where tc.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:subProductId, NULL) IS NULL OR tc.SUB_PRODUCT_ID IN (:subProductId)) \n" +
            "AND (COALESCE(:subProductValue, NULL) IS NULL OR tc.SUB_PRODUCT_VALUE IN (:subProductValue)) \n" +
            "AND (COALESCE(:productId, NULL) IS NULL OR tc.PRODUCT_ID IN (:productId)) \n" +
            "AND (COALESCE(:customerId, NULL) IS NULL OR tc.CUSTOMER_ID IN (:customerId)) \n" +
            "AND (COALESCE(:consignorId, NULL) IS NULL OR tc.CONSIGNOR_ID IN (:consignorId))", nativeQuery = true)
    List<Consignor> getConsignorsWithQry(
            @Param("languageId") String languageId,
            @Param("companyId") String companyId,
            @Param("subProductId") String subProductId,
            @Param("subProductValue") String subProductValue,
            @Param("productId") String productId,
            @Param("customerId") String customerId,
            @Param("consignorId") String consignorId);


}
