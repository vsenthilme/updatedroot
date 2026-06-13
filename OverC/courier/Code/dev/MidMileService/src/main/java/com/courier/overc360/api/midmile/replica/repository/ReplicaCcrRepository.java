package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.ccr.ReplicaCcr;
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
public interface ReplicaCcrRepository extends JpaRepository<ReplicaCcr, String>,
        JpaSpecificationExecutor<ReplicaCcr> {

    Optional<ReplicaCcr> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndCcrIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String consoleId, String ccrId, Long deletionIndicator);

    Optional<ReplicaCcr> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndCcrIdAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String consoleId, String ccrId, String pieceId, Long deletionIndicator);

//    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndCustomsCcrNoAndDeletionIndicator(
//            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String customsCcrNo, Long deletionIndicator
//    );

    // Duplicate Header Check
    @Query(value = "Select \n" +
            "Case When Exists \n" +
            "(Select 1 From tblccr h \n" +
            "Where \n" +
            "h.LANG_ID = :languageId and \n" +
            "h.C_ID = :companyId and \n" +
            "h.PARTNER_ID = :partnerId and \n" +
            "h.PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill and \n" +
            "h.PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAirwayBill and \n" +
            "h.CONSOLE_ID = :consoleId and \n " +
            "h.IS_DELETED =0) \n" +
            "Then 1 \n" +
            "Else 0 \n" +
            "End", nativeQuery = true)
    Long duplicateExists(@Param(value = "languageId") String languageId,
                         @Param(value = "companyId") String companyId,
                         @Param(value = "partnerId") String partnerId,
                         @Param(value = "partnerMasterAirwayBill") String partnerMasterAirwayBill,
                         @Param(value = "partnerHouseAirwayBill") String partnerHouseAirwayBill,
                         @Param(value = "consoleId") String consoleId);

    //Get IataKd
    @Query(value = "Select \n" +
            "IATA_KD iataKd \n" +
            "From tbliata  \n" +
            "Where \n" +
            "ORIGIN_CODE IN (:countryOfOrigin) and \n" +
            "ORIGIN IN (:origin) and \n" +
            "LANG_ID IN (:languageId) and \n" +
            "C_ID IN (:companyId) and \n" +
            "is_deleted = 0", nativeQuery = true)
    IKeyValuePair getIataKd(@Param(value = "countryOfOrigin") String countryOfOrigin,
                            @Param(value = "origin") String origin,
                            @Param(value = "languageId") String languageId,
                            @Param(value = "companyId") String companyId);

    @Query(value = "select \n" +
            "(select to_currency_value currencyValue from tblcurrencyexchangerate where " +
            "c_id = :companyId and from_currency_id = :currencyId and is_deleted = 0) as currencyValue, \n" +
            "(select iata_kd iataKd from tbliata where " +
            "origin_code = :originCode and lang_id = :languageId and c_id = :companyId and is_deleted = 0) as iataKd",
            nativeQuery = true)
    Optional<IKeyValuePair> getIataCurrencyValue(@Param("companyId") String companyId,
                                                 @Param("languageId") String languageId,
                                                 @Param("currencyId") String currencyId,
                                                 @Param("originCode") String originCode);


    // Find CCRs with given Params Only
    @Query(value = "SELECT * FROM tblccr tc \n" +
            "WHERE tc.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID IN (:partnerId)) \n" +
            "AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR tc.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill)) \n" +
            "AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR tc.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill)) \n" +
            "AND (COALESCE(:ccrId, NULL) IS NULL OR tc.CCR_ID IN (:ccrId)) \n" +
            "AND (COALESCE(:consoleId, NULL) IS NULL OR tc.CONSOLE_ID IN (:consoleId))", nativeQuery = true)
    List<ReplicaCcr> findCCRsWithQry(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("partnerId") List<String> partnerId,
            @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
            @Param("partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
            @Param("ccrId") List<String> ccrId,
            @Param("consoleId") List<String> consoleId);


        @Query(value = "select token_id as tokenId from tblhhtnotification where " +
                " c_id = :companyId and usr_id in :userId and is_deleted = 0 ", nativeQuery = true)
    public List<String> getToken(@Param("companyId") String companyId,
                                 @Param("userId") List<String> userId);

        @Query(value = "select notification_text notificationText, USER_ROLE userRole from tblnotification where " +
                "c_id = :companyId and lang_id = :languageId and notification_id = :notificationId and is_deleted = 0 ", nativeQuery = true)
        public IKeyValuePair getNotificationId(@Param("companyId") String companyId,
                                               @Param("languageId") String languageId,
                                               @Param("notificationId") String notificationId);


        @Query(value = "select user_id userId from tblusermanagement where user_role_id = :roleId " +
                " and c_id = :companyId and lang_id = :languageId and is_deleted = 0 ", nativeQuery = true)
        public List<String> getUserId(@Param("companyId") String companyId,
                                      @Param("languageId") String languageId,
                                      @Param("roleId") String roleId);

    @Query(value = "select ESTIMATED_TIME_OF_ARRIVAL invoiceDate " +
            " from tblprealert where " +
            " PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAB and " +
            " PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAB and " +
            " partner_id = :partnerId and " +
            " c_id = :companyId and " +
            " lang_id = :languageId and " +
            " is_deleted = 0 ", nativeQuery = true)
    Optional<IKeyValuePair> getInvoice(@Param("companyId") String companyId,
                                       @Param("languageId") String languageId,
                                       @Param("partnerId") String partnerId,
                                       @Param("partnerHouseAB") String partnerHouseAB,
                                       @Param("partnerMasterAB") String partnerMasterAB);

}
