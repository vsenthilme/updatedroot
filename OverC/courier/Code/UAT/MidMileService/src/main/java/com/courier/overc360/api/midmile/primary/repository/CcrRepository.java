package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.ccr.Ccr;
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
public interface CcrRepository extends JpaRepository<Ccr, String>,
        JpaSpecificationExecutor<Ccr> {

    // Get Description
    @Query(value = "Select \n" +
            "tl.lang_text langDesc, \n" +
            "tc.c_name companyDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.lang_id = tc.lang_id \n" +
            "Where \n" +
            "tl.lang_id IN (:languageId) and \n" +
            "tc.c_id IN (:companyId) and \n" +
            "tl.is_deleted = 0 and \n" +
            "tc.is_deleted = 0", nativeQuery = true)
    IKeyValuePair getLAndCDescription(@Param(value = "languageId") String languageId,
                                      @Param(value = "companyId") String companyId);

    Optional<Ccr> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndCcrIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String consoleId, String ccrId, Long deletionIndicator);

    Optional<Ccr> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndCcrIdAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String consoleId, String ccrId, String pieceId, Long deletionIndicator);


    //Get IataKd
    @Query(value = "Select \n" +
            "IATA_KD iataKd \n" +
            "From tbliata  \n" +
            "Where \n" +
            "ORIGIN_CODE IN (:countryOfOrigin) and \n" +
            "LANG_ID IN (:languageId) and \n" +
            "C_ID IN (:companyId) and \n" +
            "is_deleted = 0", nativeQuery = true)
    IKeyValuePair getIataKd(@Param(value = "countryOfOrigin") String countryOfOrigin,
                            @Param(value = "languageId") String languageId,
                            @Param(value = "companyId") String companyId);

    List<Ccr> findAllByCcrIdAndDeletionIndicator(String ccrId, Long deletionIndicator);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity " +
            "SET event_code = :eventCode, " +
            "event_text = :eventText, " +
            "status_id = :statusId, " +
            "status_text = :statusText, " +
            "status_timestamp = getDate(), " +
            "EVENT_TIMESTAMP = getDate() " +
            "WHERE c_id = :companyId " +
            "AND lang_id = :languageId " +
            "AND partner_id = :partnerId " +
            "AND PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAirwayBill " +
            "AND PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill " +
            "AND is_deleted = 0",
            nativeQuery = true)
    public void updateEventCodeFromConsignment(@Param("companyId") String companyId,
                                               @Param("languageId") String languageId,
                                               @Param("partnerId") String partnerId,
                                               @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                               @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
                                               @Param("eventCode") String eventCode,
                                               @Param("eventText") String eventText,
                                               @Param("statusId") String statusId,
                                               @Param("statusText") String statusText);

    Optional<Ccr> findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndConsoleIdAndDeletionIndicator(
            String companyId, String languageId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String pieceId,
            String consoleId, Long deletionIndicator);

    @Modifying
    @Query(value = "update tblconsole set noti_status = 1 where console_id = :consoleId and " +
            " c_id = :companyId and lang_id = :languageId and is_deleted =0 ", nativeQuery = true)
    public void updateNotificationInConsoleTable(@Param("companyId") String companyId,
                                                 @Param("languageId") String languageId,
                                                 @Param("consoleId") String consoleId);

    @Modifying
    @Transactional
    @Query(value = "update tblhhtnotification set is_deleted = 1 WHERE token_id = :tokenId", nativeQuery = true)
    void deleteNotAccessToken(@Param("tokenId") String tokenId);


    @Modifying
    @Query(value = """
            UPDATE tblccr SET IS_DELETED = 1, UTD_ON = GETDATE(), UTD_BY = :loginUserID WHERE\s
            C_ID = :companyId AND LANG_ID = :languageId AND\s
            PARTNER_ID = :partnerId AND PARTNER_MASTER_AIRWAY_BILL = :partnerMAB AND PARTNER_HOUSE_AIRWAY_BILL = :partnerHAB AND IS_DELETED = 0""", nativeQuery = true)
    void deleteCcr(@Param("companyId") String companyId,
                   @Param("languageId") String languageId,
                   @Param("partnerId") String partnerId,
                   @Param("partnerMAB") String partnerMAB,
                   @Param("partnerHAB") String partnerHAB,
                   @Param("loginUserID") String loginUserID);

    @Modifying
    @Query(value = "update tblccr set console_id = :newConsole, ccr_id = :ccrId where PARTNER_HOUSE_AIRWAY_BILL = :PHAB and is_deleted = 0", nativeQuery = true)
    void updateCcr(@Param("PHAB") String phab,
                   @Param("newConsole") String newConsole,
                   @Param("ccrId") String ccrId);
}

