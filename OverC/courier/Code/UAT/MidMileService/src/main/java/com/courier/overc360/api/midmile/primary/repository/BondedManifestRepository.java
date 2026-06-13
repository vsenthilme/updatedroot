package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.bondedmanifest.BondedManifest;
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
public interface BondedManifestRepository extends JpaRepository<BondedManifest, String>,
        JpaSpecificationExecutor<BondedManifest> {

    // Get Description
    @Query(value = """
            Select\s
            tl.lang_text langDesc,\s
            tc.c_name companyDesc\s
            From tbllanguage tl\s
            Join tblcompany tc on tl.lang_id = tc.lang_id\s
            Where\s
            tl.lang_id IN (:languageId) and\s
            tc.c_id IN (:companyId) and\s
            tl.is_deleted = 0 and\s
            tc.is_deleted = 0""", nativeQuery = true)
    IKeyValuePair getLAndCDescription(@Param(value = "languageId") String languageId,
                                      @Param(value = "companyId") String companyId);

    Optional<BondedManifest> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndBondedIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String bondedId, Long deletionIndicator);

    @Query(value = """
            Select\s
            top 1 CONSIGNOR_NAME consignorName,\s
            PRODUCT_ID productId,\s
            SUB_PRODUCT_ID subProductId,\s
            PRODUCT_TEXT productName,\s
            SUB_PRODUCT_NAME subProductName\s
            From tblconsignor \s
            Where\s
            CONSIGNOR_ID IN (:shipperId) and\s
            LANG_ID IN (:languageId) and\s
            C_ID IN (:companyId) and\s
            is_deleted = 0""", nativeQuery = true)
    IKeyValuePair getProductId(@Param(value = "shipperId") String shipperId,
                               @Param(value = "languageId") String languageId,
                               @Param(value = "companyId") String companyId);

    @Query(value = """
            select top 1 customer_name customerName,\s
             product_id productId, product_text productName,\s
              sub_product_id subProductId, sub_product_name subProductName\s
              from tblcustomer where customer_id in (:customerId) and\s
              lang_id in (:languageId) and\s
              c_id in (:companyId) and is_deleted = 0\s""", nativeQuery = true)
    IKeyValuePair getProductIdFromCustomer(@Param(value = "customerId") String customerId,
                                           @Param(value = "languageId") String languageId,
                                           @Param(value = "companyId") String companyId);

    @Query(value = "Select * From tblbondedmanifestheader h \n" +
            "Where h.IS_DELETED = 0", nativeQuery = true)
    List<BondedManifest> getAllNonDeletedHeaders();

    @Query(value = """
            Select\s
            TO_CURRENCY_VALUE currencyValue,\s
            TO_CURRENCY_ID currencyId\s
             From tblcurrencyexchangerate \s
            Where\s
            C_ID IN (:companyId) and\s
            FROM_CURRENCY_ID IN (:freightCurrency) and\s
            is_deleted = 0""", nativeQuery = true)
    IKeyValuePair getToCurrencyValue(@Param(value = "companyId") String companyId,
                                     @Param(value = "freightCurrency") String freightCurrency);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity " +
            "SET MANIFEST_INDICATOR = 1 " +
            "WHERE c_id = :companyId " +
            "AND lang_id = :languageId " +
            "AND partner_id = :partnerId " +
            "AND PARTNER_HOUSE_AB = :partnerHouseAirwayBill " +
            "AND PARTNER_MASTER_AB = :partnerMasterAirwayBill " +
            "AND is_deleted = 0",
            nativeQuery = true)
    public void updateManifest(@Param("companyId") String companyId,
                                               @Param("languageId") String languageId,
                                               @Param("partnerId") String partnerId,
                                               @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                               @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);


    BondedManifest findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, Long deletionIndicator);

    @Modifying
    @Query(value = """
            UPDATE tblbondedmanifest SET IS_DELETED = 1, UTD_ON = GETDATE(), UTD_BY = :loginUserID\s
            WHERE C_ID = :companyId AND LANG_ID = :languageId AND PARTNER_ID = :partnerId AND\s
            PARTNER_MASTER_AIRWAY_BILL = :partnerMAB AND PARTNER_HOUSE_AIRWAY_BILL = :partnerHAB AND IS_DELETED = 0""", nativeQuery = true)
    void deleteBondedManifest(@Param("companyId") String companyId,
                              @Param("languageId") String languageId,
                              @Param("partnerId") String partnerId,
                              @Param("partnerMAB") String partnerMAB,
                              @Param("partnerHAB") String partnerHAB,
                              @Param("loginUserID") String loginUserID);
}
