package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.console.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ConsoleRepository extends JpaRepository<Console, String>,
        JpaSpecificationExecutor<Console> {

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

    @Query(value = "Select * From tblconsole tc \n" +
            "Where \n" +
            "tc.CONSOLE_ID IN (:consoleId) and \n" +
            "tc.is_deleted = 0 ", nativeQuery = true)
    List<Console> getConsoleData(@Param(value = "consoleId") String consoleId);


    boolean existsByConsoleIdAndDeletionIndicator(String toConsoleId, Long deletionIndicator);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE tblconsignment_entity " +
//            "SET event_code = 6, " +
//            "CONSOLE_INDICATOR = 1, " +
//            "event_text = 'Console Created', " +
//            "EVENT_TIMESTAMP = getDate() " +
//            "WHERE c_id = :companyId " +
//            "AND lang_id = :languageId " +
//            "AND partner_id = :partnerId " +
//            "AND HOUSE_AIRWAY_BILL = :houseAirwayBill " +
//            "AND MASTER_AIRWAY_BILL = :masterAirwayBill " +
//            "AND is_deleted = 0",
//            nativeQuery = true)
//    public void updateEventCodeFromConsignment(@Param("companyId") String companyId,
//                                               @Param("languageId") String languageId,
//                                               @Param("partnerId") String partnerId,
//                                               @Param("houseAirwayBill") String houseAirwayBill,
//                                               @Param("masterAirwayBill") String masterAirwayBill);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE tblconsignment_entity " +
//            "SET event_code = :eventCode, " +
//            "event_text = :eventText, " +
//            "STATUS_ID = :statusCode, " +
//            "STATUS_TEXT = :statusText, " +
//            "STATUS_TIMESTAMP = getDate(), " +
//            "EVENT_TIMESTAMP = getDate() " +
//            "WHERE c_id = :companyId " +
//            "AND lang_id = :languageId " +
//            "AND partner_id = :partnerId " +
//            "AND HOUSE_AIRWAY_BILL = :houseAirwayBill " +
//            "AND MASTER_AIRWAY_BILL = :masterAirwayBill " +
//            "AND is_deleted = 0",
//            nativeQuery = true)
//    public void conUpdateBasedOnConsoleUpdate(@Param("companyId") String companyId,
//                                              @Param("languageId") String languageId,
//                                              @Param("partnerId") String partnerId,
//                                              @Param("houseAirwayBill") String houseAirwayBill,
//                                              @Param("masterAirwayBill") String masterAirwayBill,
//                                              @Param("statusCode") String statusCode,
//                                              @Param("eventCode") String eventCode,
//                                              @Param("statusText") String statusText,
//                                              @Param("eventText") String eventText);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE tblconsole " +
//            "SET event_code = :eventCode, " +
//            "event_text = :eventText, " +
//            "STATUS_ID = :statusCode, " +
//            "STATUS_TEXT = :statusText, " +
//            "STATUS_TIMESTAMP = getDate(), " +
//            "EVENT_TIMESTAMP = getDate() " +
//            "WHERE c_id = :companyId " +
//            "AND lang_id = :languageId " +
//            "AND partner_id = :partnerId " +
//            "AND HOUSE_AIRWAY_BILL = :houseAirwayBill " +
//            "AND MASTER_AIRWAY_BILL = :masterAirwayBill " +
//            "AND CONSOLE_ID = :consoleId " +
//            "AND is_deleted = 0",
//            nativeQuery = true)
//    public void consoleUpdateBasedOnCCRUpdate(@Param("companyId") String companyId,
//                                              @Param("languageId") String languageId,
//                                              @Param("partnerId") String partnerId,
//                                              @Param("houseAirwayBill") String houseAirwayBill,
//                                              @Param("masterAirwayBill") String masterAirwayBill,
//                                              @Param("statusCode") String statusCode,
//                                              @Param("eventCode") String eventCode,
//                                              @Param("statusText") String statusText,
//                                              @Param("eventText") String eventText,
//                                              @Param("consoleId") String consoleId);

    Console findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String consoleId, String pieceId, Long deletionIndicator);

    Console findByPartnerHouseAirwayBillAndConsoleIdAndPieceIdAndDeletionIndicator(
            String houseAirwayBill, String fromConsoleId, String pieceId, Long deletionIndicator);


    // Update Consignment Table On Console Create
    @Transactional
    @Modifying
    @Query(value = "Update tblconsignment_entity\n" +
            "Set \n" +
            "HAWB_TYP = :hawbType, \n" +
            "HAWB_TYP_ID = :hawbTypeId, \n" +
            "HAWB_TYP_TXT = :hawbTypeDescription, \n" +
            "HAWB_TIMESTAMP = GETDATE(), \n" +
            "HUB_CODE = :hubCode \n " +
            "Where IS_DELETED = 0 \n" +
            "AND LANG_ID = :languageId \n" +
            "And C_ID = :companyId \n" +
            "AND PARTNER_ID = :partnerId \n" +
            "AND PARTNER_MASTER_AB = :partnerMasterAirwayBill\n" +
            "AND PARTNER_HOUSE_AB = :partnerHouseAirwayBill", nativeQuery = true)
    void updateConsignmentOnConsoleUpdate(@Param("languageId") String languageId,
                                          @Param("companyId") String companyId,
                                          @Param("partnerId") String partnerId,
                                          @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                          @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
                                          @Param("hawbTypeDescription") String hawbTypeDescription,
                                          @Param("hawbTypeId") String hawbTypeId,
                                          @Param("hawbType") String hawbType,
                                          @Param("hubCode") String hubCode);

    @Transactional
    @Modifying
    @Query(value = "Update tblconsignment_entity\n" +
            "Set \n" +
            "HAWB_TYP = :hawbType, \n" +
            "HAWB_TYP_ID = :hawbTypeId, \n" +
            "HAWB_TYP_TXT = :hawbTypeDescription, \n" +
            "HAWB_TIMESTAMP = GETDATE(), \n" +
            "HUB_CODE = :hubCode, \n " +
            "HUB_NAME = :hubName \n " +
            "Where IS_DELETED = 0 \n" +
            "AND LANG_ID = :languageId \n" +
            "And C_ID = :companyId \n" +
            "AND PARTNER_ID = :partnerId \n" +
            "AND PARTNER_MASTER_AB = :partnerMasterAirwayBill\n" +
            "AND PARTNER_HOUSE_AB = :partnerHouseAirwayBill", nativeQuery = true)
    void updateConsignmentOnConsoleUpdate(@Param("languageId") String languageId,
                                          @Param("companyId") String companyId,
                                          @Param("partnerId") String partnerId,
                                          @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                          @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
                                          @Param("hawbTypeDescription") String hawbTypeDescription,
                                          @Param("hawbTypeId") String hawbTypeId,
                                          @Param("hawbType") String hawbType,
                                          @Param("hubCode") String hubCode,
                                          @Param("hubName") String hubName);

    // Update Consignment Table On Console Create
    @Transactional
    @Modifying
    @Query(value = "Update tblconsignment_entity\n" +
            "Set \n" +
            "HAWB_TYP = :hawbType, \n" +
            "HAWB_TYP_ID = :hawbTypeId, \n" +
            "HAWB_TYP_TXT = :hawbTypeDescription, \n" +
            "HAWB_TIMESTAMP = GETDATE(), \n" +
            "HUB_CODE = :hubCode, \n " +
            "CONS_BAG_ID = :bagId \n " +
            "Where IS_DELETED = 0 \n" +
            "AND LANG_ID = :languageId \n" +
            "And C_ID = :companyId \n" +
            "AND PARTNER_ID = :partnerId \n" +
            "AND PARTNER_MASTER_AB = :partnerMasterAirwayBill\n" +
            "AND PARTNER_HOUSE_AB = :partnerHouseAirwayBill", nativeQuery = true)
    void updateConsignmentOnConsoleCreate(@Param("languageId") String languageId,
                                          @Param("companyId") String companyId,
                                          @Param("partnerId") String partnerId,
                                          @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                          @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
                                          @Param("hawbTypeDescription") String hawbTypeDescription,
                                          @Param("hawbTypeId") String hawbTypeId,
                                          @Param("hawbType") String hawbType,
                                          @Param("hubCode") String hubCode,
                                          @Param("bagId") Long bagId);


    @Transactional
    @Modifying
    @Query(value = "Update tblconsignment_entity\n" +
            "Set \n" +
            "CONSOLE_INDICATOR = 1 \n" +
            "Where IS_DELETED = 0 \n" +
            "AND LANG_ID = :languageId \n" +
            "And C_ID = :companyId \n" +
            "AND PARTNER_ID = :partnerId \n" +
            "AND PARTNER_MASTER_AB = :partnerMasterAirwayBill\n" +
            "AND PARTNER_HOUSE_AB = :partnerHouseAirwayBill", nativeQuery = true)
    void updateConsignmentOnConsoleCreate(@Param("languageId") String languageId,
                                          @Param("companyId") String companyId,
                                          @Param("partnerId") String partnerId,
                                          @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                          @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Update PreAlert Table On Console Create
    @Transactional
    @Modifying
    @Query(value = "Update tblprealert\n" +
            "Set \n" +
            "HAWB_TYP = :hawbType,\n" +
            "HAWB_TYP_ID = :hawbTypeId,\n" +
            "HAWB_TYP_TXT = :hawbTypeDescription,\n" +
            "HAWB_TIMESTAMP = GETDATE()\n" +
            "Where IS_DELETED = 0 \n" +
            "AND LANG_ID = :languageId \n" +
            "And C_ID = :companyId \n" +
            "AND PARTNER_ID = :partnerId \n" +
            "AND PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill\n" +
            "AND PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAirwayBill", nativeQuery = true)
    void updatePreAlertOnConsoleCreate(@Param("languageId") String languageId,
                                       @Param("companyId") String companyId,
                                       @Param("partnerId") String partnerId,
                                       @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                       @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
                                       @Param("hawbTypeDescription") String hawbTypeDescription,
                                       @Param("hawbTypeId") String hawbTypeId,
                                       @Param("hawbType") String hawbType);

    @Transactional
    @Modifying
    @Query(value = "Update tblprealert\n" +
            "Set \n" +
            "HAWB_TYP = :hawbType,\n" +
            "HAWB_TYP_ID = :hawbTypeId,\n" +
            "HAWB_TYP_TXT = :hawbTypeDescription,\n" +
            "HAWB_TIMESTAMP = GETDATE(), \n" +
            "CUSTOM_DUTY = :totalDuty \n" +
            "Where IS_DELETED = 0 \n" +
            "AND LANG_ID = :languageId \n" +
            "And C_ID = :companyId \n" +
            "AND PARTNER_ID = :partnerId \n" +
            "AND PIECE_ID = :pieceId  \n " +
            "AND PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill\n" +
            "AND PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAirwayBill", nativeQuery = true)
    void updatePreAlertOnConsoleCreate(@Param("languageId") String languageId,
                                       @Param("companyId") String companyId,
                                       @Param("partnerId") String partnerId,
                                       @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                       @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
                                       @Param("hawbTypeDescription") String hawbTypeDescription,
                                       @Param("hawbTypeId") String hawbTypeId,
                                       @Param("hawbType") String hawbType,
                                       @Param("pieceId") String pieceId);

    @Modifying
    @Query(value = "update tblprealert \n " +
            "set \n " +
            "BAYAN_HV = coalesce(:customDuty, 0.0), " +
            "CUSTOM_DUTY = coalesce(:customDuty, 0.0) " +
            "where " +
            "c_id in (:companyId) and " +
            "lang_id in (:languageId) and " +
            "PARTNER_HOUSE_AIRWAY_BILL in (:houseAB) and " +
            "PARTNER_MASTER_AIRWAY_BILL in (:masterAB) and " +
            "partner_id in (:partnerId) and " +
            "is_deleted = 0 ", nativeQuery = true)
    void updateCustomDuty(@Param("companyId") String companyId,
                          @Param("languageId") String languageId,
                          @Param("masterAB") String masterAB,
                          @Param("houseAB") String houseAB,
                          @Param("partnerId") String partnerId,
                          @Param("customDuty") Double customDuty);

    // Update PieceDetails Table On Console Create
    @Transactional
    @Modifying
    @Query(value = "Update tblpiecedetails\n" +
            "Set \n" +
            "PIECE_TYP = :hawbType,\n" +
            "PIECE_TYP_ID = :hawbTypeId,\n" +
            "PIECE_TYP_TXT = :hawbTypeDescription,\n" +
            "PIECE_TIMESTAMP = GETDATE()\n" +
            "Where IS_DELETED = 0 \n" +
            "AND LANG_ID = :languageId \n" +
            "And C_ID = :companyId \n" +
            "AND PARTNER_ID = :partnerId \n" +
            "AND PIECE_ID = :pieceId \n " +
            "AND PARTNER_MASTER_AIRWAY_BILL = :masterAirwayBill\n" +
            "AND PARTNER_HOUSE_AIRWAY_BILL = :houseAirwayBill", nativeQuery = true)
    void updatePieceDetailsOnConsoleCreate(@Param("languageId") String languageId,
                                           @Param("companyId") String companyId,
                                           @Param("partnerId") String partnerId,
                                           @Param("houseAirwayBill") String houseAirwayBill,
                                           @Param("masterAirwayBill") String masterAirwayBill,
                                           @Param("hawbTypeDescription") String hawbTypeDescription,
                                           @Param("hawbTypeId") String hawbTypeId,
                                           @Param("hawbType") String hawbType,
                                           @Param("pieceId") String pieceId);

    Console findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, Long deletionIndicator);


    List<Console> findByConsoleIdAndDeletionIndicator(String consoleId, Long deletionIndicator);

    @Query(value = "select hub_name from tblhub where c_id = :companyId and lang_id = :languageId and hub_code = :hubCode and is_deleted =0", nativeQuery = true)
    public String getHubName(@Param("companyId") String companyId,
                             @Param("languageId") String languageId,
                             @Param("hubCode") String hubCode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tblconsole SET ccr_id = :ccrId WHERE\n" +
            " console_id = :consoleId\n" +
            " and c_id = :companyId\n" +
            " and lang_id = :languageId\n" +
            " and partner_id = :partnerId\n" +
            " and partner_house_airway_bill = :partnerHouseAB\n" +
            " and partner_master_airway_bill = :partnerMasterAB\n" +
            " and piece_id = :pieceId\n" +
            " and is_deleted=0", nativeQuery = true)
    void updateCCRID(@Param("consoleId") String consoleId,
                     @Param("ccrId") String ccrId,
                     @Param("partnerId") String partnerId,
                     @Param("companyId") String companyId,
                     @Param("languageId") String languageId,
                     @Param("partnerHouseAB") String partnerHouseAB,
                     @Param("partnerMasterAB") String partnerMasterAB,
                     @Param("pieceId") String pieceId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tblconsole\n" +
            "SET CCR_ID = :ccrId\n" +
            "WHERE IS_DELETED=0\n" +
            "And LANG_ID = :languageId\n" +
            "And C_ID = :companyId\n" +
            "And CONSOLE_ID = :consoleId", nativeQuery = true)
    int updateCCRIdInConsoleTbl(@Param("consoleId") String consoleId,
                                @Param("ccrId") String ccrId,
                                @Param("languageId") String languageId,
                                @Param("companyId") String companyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tblconsole " +
            "SET CALCULATED_TOTAL_DUTY = :totalDuty," +
            "TOTAL_DUTY = :totalDuty " +
            "WHERE IS_DELETED = 0 " +
            "AND C_ID = :companyId " +
            "AND LANG_ID = :languageId " +
            "AND PARTNER_ID = :partnerId " +
            "AND PARTNER_HOUSE_AIRWAY_BILL = :partnerHAB " +
            "AND PARTNER_MASTER_AIRWAY_BILL = :partnerMAB ", nativeQuery = true)
    void updateConsoleBayanValue(@Param("companyId") String companyId,
                                 @Param("languageId") String languageId,
                                 @Param("partnerId") String partnerId,
                                 @Param("partnerHAB") String partnerHAB,
                                 @Param("partnerMAB") String partnerMAB,
                                 @Param("totalDuty") Double totalDuty);

    @Modifying
    @Query(value = "UPDATE tblconsole " +
            "SET CUSTOMS_CCR_NO = :customsCcrNo, " +
            "PRIMARY_DO = :primaryDo, CUSTOMS_KD = :customsKd, " +
            "TOTAL_DUTY = :totalDuty, UTD_BY = :userId, UTD_ON = getDate() " +
            "WHERE " +
            "C_ID = :companyId AND LANG_ID = :languageId AND PARTNER_ID = :partnerId " +
            "AND PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAB AND PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill " +
            "AND IS_DELETED = 0", nativeQuery = true)
    void updateConsole(@Param("customsCcrNo") String customsCcrNo,
                       @Param("primaryDo") String primaryDo,
                       @Param("customsKd") String customsKd,
                       @Param("totalDuty") Double totalDuty,
                       @Param("userId") String userId,
                       @Param("companyId") String companyId,
                       @Param("languageId") String languageId, @Param("partnerId") String partnerId,
                       @Param("partnerHouseAB") String partnerHouseAB, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

//     @Query(value = " select sum(total_duty) from tblconsole where IS_DELETED = 0 and" +
//             " PARTNER_MASTER_AIRWAY_BILL =:partnerMAB group by PARTNER_MASTER_AIRWAY_BILL", nativeQuery = true)
//    public Double getTotalDuty(@Param("partnerMAB") String partnerMAB);

    @Query(value = "SELECT \n" +
            "SUM(max_total_duty) AS total_duty_sum\n" +
            "FROM \n" +
            "(SELECT \n" +
            "PARTNER_MASTER_AIRWAY_BILL, \n" +
            "MAX(total_duty) AS max_total_duty\n" +
            "FROM \n" +
            "tblconsole \n" +
            "WHERE \n" +
            "IS_DELETED = 0 \n" +
            "GROUP BY \n" +
            "PARTNER_HOUSE_AIRWAY_BILL, \n" +
            "PARTNER_MASTER_AIRWAY_BILL) t\n" +
            "WHERE PARTNER_MASTER_AIRWAY_BILL = :partnerMAB " +
            "GROUP BY PARTNER_MASTER_AIRWAY_BILL", nativeQuery = true)
     Double getTotalDuty(@Param("partnerMAB") String partnerMAB);                              // MAX Total_duty for multiple piece in same HouseAirwayBill

    Console findByPartnerHouseAirwayBillAndDeletionIndicator(String partnerHouseAirwayBill, Long deletionIndicator);
}
