package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignment.AddConsignment;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.replica.model.consignment.ConsignmentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ConsignmentEntityRepository extends JpaRepository<ConsignmentEntity, Long> {

    Optional<ConsignmentEntity> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String mawb, String hawb, Long deletionIndicator);

    Optional<ConsignmentEntity> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String mawb, String hawb, Long deletionIndicator);

    ConsignmentEntity findByCompanyIdAndLanguageIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
            String companyId, String languageId, String partnerId, String mawb, String hawb, Long deletionIndicator);

    Optional<ConsignmentEntity> findByHouseAirwayBillAndDeletionIndicator(String houseAirwayBill, Long deletionIndicator);

//    @Query(value = "SELECT status_code AS statusId, status_text AS statusText, event_text AS eventText " +
//            "FROM tblevent " +
//            "WHERE C_ID = :companyId AND " +
//            "STATUS_CODE = :statusCode AND " +
//            "EVENT_CODE = :eventCode AND " +
//            "IS_DELETED = 0", nativeQuery = true)
//    IKeyValuePair getStatusEventText(@Param("companyId") String companyId,
//                                               @Param("statusCode") String statusCode,
//                                               @Param("eventCode") String eventCode);

//    @Query(value = "SELECT " +
//            "(SELECT status_text FROM tblstatus WHERE " +
//            "lang_id = :languageId AND status_id = :statusId AND is_deleted = 0) AS statusText, " +
//            "(SELECT event_text FROM tblevent WHERE " +
//            "c_id = :companyId AND event_code = :eventCode AND lang_id = :languageId AND is_deleted = 0) AS eventText",
//            nativeQuery = true)
//    Optional<IKeyValuePair> getStatusEventText(@Param("languageId") String languageId,
//                                               @Param("companyId") String companyId,
//                                               @Param("statusId") String statusId,
//                                               @Param("eventCode") String eventCode);

//    @Query(value = "select status_text as statusText from tblstatus " +
//            "where lang_id = :languageId AND status_id = :statusId AND is_deleted = 0", nativeQuery = true)
//    Optional<IKeyValuePair> getStatusText(@Param("languageId") String languageId,
//                                          @Param("statusId") String statusId);

//    @Query(value = "SELECT event_text as eventText FROM tblevent WHERE " +
//            " c_id = :companyId AND event_code = :eventCode AND lang_id = :languageId AND is_deleted = 0", nativeQuery = true)
//Optional<IKeyValuePair> getEventText(@Param("languageId") String languageId,
//                                     @Param("companyId") String companyId,
//                                     @Param("eventCode") String eventCode);


    @Query(value = "select type as type, type_text as typeText from tblstatusevent where c_id = :companyId and \n" +
            "lang_id = :languageId and type_id = :typeId and is_deleted = 0", nativeQuery = true)
    Optional<IKeyValuePair> statusText(@Param("companyId") String companyId,
                                       @Param("languageId") String languageId,
                                       @Param("typeId") String typeId);

    @Query(value = "select type_text as typeText from tblstatusevent where c_id = :companyId and \n" +
            "lang_id = :languageId and type_id = :typeId and is_deleted = 0", nativeQuery = true)
    Optional<String> statusEventText(@Param("companyId") String companyId,
                                     @Param("languageId") String languageId,
                                     @Param("typeId") String typeId);

    @Query(value = "select CONSIGNMENT_ID as consignmentId, PAYMENT_TYPE as paymentType, SPECIAL_APPROVAL_CHARGE as specialApprovalCharge " +
            " from tblconsignment_entity where PARTNER_HOUSE_AB = :partnerHouseAirwayBill and \n" +
            " LANG_ID = :languageId and C_ID = :companyId and is_deleted = 0", nativeQuery = true)
    Optional<IKeyValuePair> consignmentId(@Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
                                          @Param("languageId") String languageId,
                                          @Param("companyId") String companyId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tblpiecedetails " +
            "SET PIECE_VALUE = :pieceValue " +
            "WHERE c_id = :companyId " +
            "AND lang_id = :languageId " +
            "AND partner_id = :partnerId " +
            "AND HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            "AND MASTER_AIRWAY_BILL = :masterAirwayBill " +
            "AND PIECE_ID = :pieceId " +
            "AND is_deleted = 0",
            nativeQuery = true)
    public void updatePieceValue(@Param("companyId") String companyId,
                                 @Param("languageId") String languageId,
                                 @Param("partnerId") String partnerId,
                                 @Param("houseAirwayBill") String houseAirwayBill,
                                 @Param("masterAirwayBill") String masterAirwayBill,
                                 @Param("pieceId") String pieceId,
                                 @Param("pieceValue") String pieceValue);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity " +
            "SET CONSIGNMENT_VALUE = :consignmentValue, " +
            "CONSIGNMENT_VALUE_LOCAL = :consignmentValueLocal, " +
            "ADD_IATA = :addIata, " +
            "ADD_INSURANCE = :addInsurance, " +
            "CUSTOMS_VALUE = :customsValue, " +
            "VOLUME = :volume, " +
            "CALCULATED_TOTAL_DUTY = :calculatedDuty " +
            "WHERE c_id = :companyId " +
            "AND lang_id = :languageId " +
            "AND partner_id = :partnerId " +
            "AND HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            "AND MASTER_AIRWAY_BILL = :masterAirwayBill " +
            "AND is_deleted = 0",
            nativeQuery = true)
    public void updateConsignment(@Param("companyId") String companyId,
                                  @Param("languageId") String languageId,
                                  @Param("partnerId") String partnerId,
                                  @Param("houseAirwayBill") String houseAirwayBill,
                                  @Param("masterAirwayBill") String masterAirwayBill,
                                  @Param("consignmentValue") String consignmentValue,
                                  @Param("consignmentValueLocal") String consignmentValueLocal,
                                  @Param("addIata") String addIata,
                                  @Param("addInsurance") String addInsurance,
                                  @Param("customsValue") String customsValue,
                                  @Param("calculatedDuty") String calculatedDuty,
                                  @Param("volume") String volume);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity " +
            "SET VOLUME = :volume " +
            "WHERE c_id = :companyId " +
            "AND lang_id = :languageId " +
            "AND partner_id = :partnerId " +
            "AND HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            "AND MASTER_AIRWAY_BILL = :masterAirwayBill " +
            "AND is_deleted = 0",
            nativeQuery = true)
    public void updateConsignment(@Param("companyId") String companyId,
                                  @Param("languageId") String languageId,
                                  @Param("partnerId") String partnerId,
                                  @Param("houseAirwayBill") String houseAirwayBill,
                                  @Param("masterAirwayBill") String masterAirwayBill,
                                  @Param("volume") String volume);


    @Modifying
    @Query(value = "update tblconsignment_entity " +
            "set PARTNER_MASTER_AB = :partnerMasterAB," +
            "CONSIGNMENT_VALUE = COALESCE(:consignmentValue, 0.0), " +
            "EXCHANGE_RATE = COALESCE(:exchangeRate, 0.0), " +
            "IATA_CHARGE = COALESCE(:iata, 0.0), " +
            "CONSIGNMENT_VALUE_LOCAL = COALESCE(:consignmentLocalValue, 0.0), " +
            "ADD_IATA = COALESCE(:addIata, 0.0), " +
            "ADD_INSURANCE = COALESCE(:addInsurance, 0.0), " +
            "CUSTOMS_VALUE = COALESCE(:customsValue, 0.0), " +
            "CALCULATED_TOTAL_DUTY = COALESCE(:calculatedTotalDuty, 0.0), " +
            "CUSTOMS_INSURANCE = COALESCE(:customsInsurance, 0.0) " +
            "WHERE c_id in (:companyId) and lang_id in (:languageId) and " +
            "partner_id in (:partnerId) and partner_house_ab in (:partnerHouseAB) and " +
            "is_deleted = 0 ", nativeQuery = true)
    public void updateConsignment(@Param(value = "companyId") String companyId,
                                  @Param("languageId") String languageId,
                                  @Param("partnerId") String partnerId,
                                  @Param("partnerHouseAB") String partnerHouseAB,
                                  @Param("partnerMasterAB") String partnerMasterAB,
                                  @Param("consignmentValue") Double consignmentValue,
                                  @Param("exchangeRate") Double exchangeRate,
                                  @Param("iata") Double iata,
                                  @Param("consignmentLocalValue") Double consignmentLocalValue,
                                  @Param("addIata") Double addIata,
                                  @Param("addInsurance") Double addInsurance,
                                  @Param("customsValue") Double customsValue,
                                  @Param("calculatedTotalDuty") Double calculatedTotalDuty,
                                  @Param("customsInsurance") Double customsInsurance);

    @Modifying
    @Query(value = "update tblpiecedetails " +
            "set PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAB where " +
            "c_id in (:companyId) and lang_id in (:languageId) and " +
            "partner_id in (:partnerId) and PARTNER_HOUSE_AIRWAY_BILL in (:partnerHouseAB) and " +
            "is_deleted = 0 ", nativeQuery = true)
    public void updatePieceId(@Param("companyId") String companyId,
                              @Param("languageId") String languageId,
                              @Param("partnerId") String partnerId,
                              @Param("partnerHouseAB") String partnerHouseAB,
                              @Param("partnerMasterAB") String partnerMasterAB);

    // Special Approval Value Update tblConsignment_Entity
    @Modifying
    @Transactional
    @Query(value = "update tblconsignment_entity " +
            " set SPECIAL_APPROVAL_CHARGE = COALESCE(:specialApp, 0.0) " +
            " where partner_master_ab = :pmab and " +
            " c_id = :companyId and lang_id = :languageId and " +
            " partner_id = :partnerId and is_deleted = 0", nativeQuery = true)
    public void updateSpecialApproval(@Param("pmab") String pmab,
                                      @Param("companyId") String companyId,
                                      @Param("languageId") String languageId,
                                      @Param("specialApp") Double specialApp,
                                      @Param("partnerId") String partnerId);

    // TotalDuty Value Update tblConsignment_Entity
    @Modifying
    @Transactional
    @Query(value = "update tblconsignment_entity " +
            " set TOTAL_DUTY = COALESCE(:totalDuty, 0.0) " +
            " where partner_master_ab = :pmab and " +
            " c_id = :companyId and lang_id = :languageId and " +
            " is_deleted = 0", nativeQuery = true)
    public void updateTotalDutyFromCustomCost(@Param("pmab") String pmab,
                                              @Param("companyId") String companyId,
                                              @Param("languageId") String languageId,
                                              @Param("totalDuty") Double totalDuty);


    ConsignmentEntity findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerHouseAirwayBill, Long deletionIndicator);

    // Getting Consignment for Customs Costing
    @Query(value = "SELECT * FROM tblconsignment_entity WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    List<ConsignmentEntity> consignment(@Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);


    // Bulk update Consignment for Customs Costing Nas-delivery
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET NAS_DELIVERY = COALESCE(NAS_DELIVERY, 0) + :calculatedAmount " +
            "WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateNasDelivery(@Param("calculatedAmount") Double calculatedAmount, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing Global
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET GLOBAL = COALESCE(GLOBAL, 0) + :global" +
            " WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateGlobal(@Param("global") Double global, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing Approval
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET APPROVAL = COALESCE(APPROVAL, 0) + :approval " +
            "WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateApproval(@Param("approval") Double approval, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing Handling Fork
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET HANDLING_FORK = COALESCE(HANDLING_FORK, 0) + :handlingFork " +
            "WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateHandlingFork(@Param("handlingFork") Double handlingFork, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing stampCharges
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET STAMP_CHARGES = COALESCE(STAMP_CHARGES, 0) + :stampCharges" +
            " WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateStampCharges(@Param("stampCharges") Double stampCharges, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing clearanceCharge
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET CLEARANCE_CHARGE = COALESCE(CLEARANCE_CHARGE, 0) + :clearanceCharge " +
            "WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateClearanceCharge(@Param("clearanceCharge") Double clearanceCharge, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing handlingFees
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET HANDLING_FEES = COALESCE(HANDLING_FEES, 0) + :handlingFees" +
            " WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateHandlingFees(@Param("handlingFees") Double handlingFees, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing foodApprovals
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET FOOD_APPROVALS = COALESCE(FOOD_APPROVALS, 0) + :foodApprovals" +
            " WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateFoodApprovals(@Param("foodApprovals") Double foodApprovals, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing otherApprovals
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET OTHER_APPROVALS = COALESCE(OTHER_APPROVALS, 0) + :otherApprovals" +
            " WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateOtherApprovals(@Param("otherApprovals") Double otherApprovals, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing SpecialApprovalCharge
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity SET SPECIAL_APPROVAL_CHARGE = COALESCE(SPECIAL_APPROVAL_CHARGE, 0) + :specialApprovalCharge" +
            " WHERE PARTNER_MASTER_AB = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateSpecialApproval(@Param("specialApprovalCharge") Double specialApprovalCharge, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    @Query(value = "Select CONSIGNMENT_ID consignmentId From tblconsignment_entity \n" +
            "Where IS_DELETED = 0 AND (COALESCE(:scanId, null) IS NULL OR HOUSE_AIRWAY_BILL IN (:scanId)) \n" +
            "AND (COALESCE(:companyId,null) IS NULL OR C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:languageId,null) IS NULL OR LANG_ID IN (:languageId))", nativeQuery = true)
    Long getHouseAirWayBill(@Param("scanId") String scanId,
                            @Param("companyId") String companyId,
                            @Param("languageId") String languageId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE th " +
            "SET th.ZONE_ID = stm.ZONE_ID, " +
            "   th.ZONE_TEXT = COALESCE(cityTbl.CITY_NAME, districtTbl.DISTRICT_NAME, provinceTbl.PROVINCE_NAME), " +
            "   th.ZONE_TYPE = ztm.ZONE_TYPE_TEXT, " +
            "   th.STORAGE_LOCATION = stm.STORAGE_TYPE_ID, " +
            "   th.STORAGE_TEXT = stm.STORAGE_TYPE_TEXT " +
            "FROM tblconsignment_entity th " +
            "INNER JOIN tblpartnerhubmapping phm ON phm.PARTNER_ID = th.PARTNER_ID " +
            "INNER JOIN tblzonetypemaster ztm ON ztm.HUB_CODE = phm.HUB_CODE " +
            "INNER JOIN tbldestdetails dd ON dd.DEST_DETAIL_ID = th.CONSIGNMENT_ID " +
            "LEFT JOIN tblcity cityTbl ON cityTbl.CITY_ID = dd.CITY AND ztm.ZONE_TYPE_TEXT = 'City' " +
            "LEFT JOIN tbldistrict districtTbl ON districtTbl.DISTRICT_ID = dd.DISTRICT AND ztm.ZONE_TYPE_TEXT = 'District' " +
            "LEFT JOIN tblprovince provinceTbl ON provinceTbl.PROVINCE_ID = dd.STATE AND ztm.ZONE_TYPE_TEXT = 'State' " +
            "INNER JOIN tblzonemaster zm ON zm.ZONE_TEXT = COALESCE(cityTbl.CITY_NAME, districtTbl.DISTRICT_NAME, provinceTbl.PROVINCE_NAME) " +
            "INNER JOIN tblstoragetypemaster stm ON stm.ZONE_ID = zm.ZONE_ID " +
            "WHERE th.IS_DELETED = 0 " +
            "AND th.CONSIGNMENT_ID = (:consignmentEntity)", nativeQuery = true)
    void updateRefFieldWithStorageType(@Param("consignmentEntity") Long consignmentEntity);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE th " +
            "SET th.STORAGE_LOCATION = x.storage_type_id, " +
            "    th.STORAGE_TEXT = x.storage_type_text, " +
            "    th.ZONE_ID = x.original_zone_id, " +
            "    th.ZONE_TEXT = x.original_zone_text, " +
            "    th.HUB_CODE = x.hub_code, " +
            "    th.ZONE_TYPE = x.zone_type " +
            "FROM tblconsignment_entity th " +
            "INNER JOIN ( " +
            "     SELECT stm.c_ID, stm.lang_id, " +
            "            CASE " +
            "                WHEN stm.ZONE_TYPE_ID = :zoneTypeId THEN :zoneId " +  // Use zoneTypeId and zoneId from parameters
            "            END AS final_zone_id, " +
            "            stm.STORAGE_TYPE_ID AS storage_type_id, " +
            "            stm.STORAGE_TYPE_TEXT AS storage_type_text, " +
//            "            CONCAT(stm.STORAGE_TYPE_ID, ' - ' ,stm.STORAGE_TYPE_TEXT) AS value, " +
            "            stm.HUB_CODE AS hub_code, " +
            "            CONCAT(stm.ZONE_TYPE_ID, ' - ', ztm.ZONE_TYPE_TEXT) AS zone_type, " +
            "            stm.ZONE_ID AS original_zone_id, " +
            "            zm.ZONE_TEXT AS original_zone_text " +
//            "            CONCAT(stm.ZONE_ID, ' - ', zm.ZONE_TEXT) AS original_zone_text " +
            "    FROM tblstoragetypemaster stm " +
            "    LEFT JOIN tbldestdetails dt " +
            "         ON (dt.CITY = stm.ZONE_ID OR dt.DISTRICT = stm.ZONE_ID OR dt.STATE = stm.ZONE_ID) " +
            "    LEFT JOIN tblconsignment_entity th ON th.PARTNER_ID = stm.ZONE_ID " +
            "    LEFT JOIN tblzonetypemaster ztm ON ztm.ZONE_TYPE_ID = stm.ZONE_TYPE_ID " +
            "    LEFT JOIN tblzonemaster zm ON zm.ZONE_ID = stm.ZONE_ID " +
            "    WHERE stm.IS_DELETED = 0 " +
            "    AND stm.ZONE_ID = (:zoneId) " +
            " ) x " +
            "ON th.C_ID = x.c_ID AND th.LANG_ID = x.lang_id " +
            "WHERE th.IS_DELETED = 0 AND th.CONSIGNMENT_ID = :consignmentEntity", nativeQuery = true)
    void updateStorageWithZoneDetails(
            @Param("consignmentEntity") Long consignmentEntity,
            @Param("zoneId") String zoneId,
            @Param("zoneTypeId") String zoneType
    );

    @Modifying
    @Query(value = "UPDATE tblpickup_entity " +
            "SET HOUSE_AIRWAY_BILL = :houseAB, PIECE_ID = :pieceId, CONSIGNMENT_CREATION = '1' " +
            "WHERE IS_DELETED = 0 AND PICKUP_ENTITY_ID = :pickupId ", nativeQuery = true)
    void updatePickup(@Param(value = "houseAB") String houseAB,
                      @Param(value = "pieceId") String pieceId,
                      @Param(value = "pickupId") Long pickupId);


    @Query(value = "SELECT * FROM tblconsignment_entity WHERE IS_DELETED = 0 " +
            "AND CONSIGNMENT_ID = :consignmentEntity", nativeQuery = true)
    ConsignmentEntity getData(@Param("consignmentEntity") Long consignmentEntity);

    ConsignmentEntity findByConsignmentIdAndDeletionIndicator(Long consignmentId, Long deletionIndicator);


    @Query(value = "SELECT * FROM tblconsignment_entity WHERE IS_DELETED = 0 " +
            "AND HOUSE_AIRWAY_BILL = :scanId", nativeQuery = true)
    ConsignmentEntity getHAWBValue(String scanId);

    @Query(value = "SELECT * FROM tblconsignment_entity WHERE IS_DELETED = 0 " +
            "AND PIECE_ID = :scanId", nativeQuery = true)
    ConsignmentEntity getPiece(String scanId);

    boolean existsByPickupEntityIdAndDeletionIndicator(Long pickupEntityId, Long deletionIndicator);

    @Query(value = "select type_text as typeText from tblstatusevent where \n" +
            "type_id = :typeId and is_deleted = 0", nativeQuery = true)
    String statusText(@Param("typeId") String typeId);

    @Query(value = "SELECT TOP 1 HUB_CODE, HUB_NAME from tblpartnerhubmapping " +
            "WHERE IS_DELETED = 0 AND PARTNER_ID = :partnerId AND " +
            "PARTNER_TYPE = :partnerType AND LANG_ID = :langId AND " +
            "C_ID = :companyId AND PRODUCT_CODE = :productCode", nativeQuery = true)
    Object[] getHubCode(@Param("partnerId") String partnerId, @Param("partnerType") String partnerType,
                        @Param("langId") String languageId, @Param("companyId") String companyId,
                        @Param("productCode") String productCode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tblconsignment_entity SET INVOICE_URL = (:invoiceUrl) " +
            " WHERE HOUSE_AIRWAY_BILL = (:houseAirwayBill) AND IS_DELETED = 0", nativeQuery = true)
    void updateInvoiceUrl(@Param("houseAirwayBill") String houseAirwayBill,
                          @Param("invoiceUrl") String invoiceUrl);


    @Modifying
    @Transactional
    @Query(value = "UPDATE tblconsignment_entity SET hawb_typ_id = (:statusId), HAWB_TYP_TXT = (:statusText)" +
            " WHERE CONSIGNMENT_ID = (:consignmentId) AND IS_DELETED = 0", nativeQuery = true)
    void updateStatus(@Param("statusId") String statusId,
                      @Param("statusText") String statusText,
                      @Param("consignmentId") Long consignmentId);


    @Modifying
    @Query(value = "update tbldelivery SET  " +
            "status_id = 16, status_text = :statusText, STATUS_TIMESTAMP = GETDATE() " +
            "WHERE IS_DELETED = 0 \n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill)", nativeQuery = true)
    void updateDeliveryConsignment(@Param("statusText") String statusText,
                                   @Param("houseAirwayBill") String houseAirwayBill);

//        @Query(value = """
//                SELECT
//                c.CONSIGNMENT_ID consignmentId, c.LANG_ID languageId, c.LANG_TEXT languageDescription, c.C_ID companyId, c.C_NAME companyName, c.PARTNER_ID partnerId,
//                pd.PIECE_DETAILS_ID pieceDetails_pieceDetailsId , pd.LANG_ID pieceDetails_languageId, pd.C_ID as pieceDetails_companyId,
//                c.PARTNER_TYPE partnerType, c.PARTNER_NAME partnerName, c.MASTER_AIRWAY_BILL masterAirwayBill, c.HOUSE_AIRWAY_BILL houseAirwayBill FROM tblconsignment_entity c
//                LEFT JOIN tblpiecedetails pd ON c.CONSIGNMENT_ID = pd.CONSIGNMENT_ID
//                """, nativeQuery = true)
//        List<ConsignmentSummary>findConsignmentAndWithDetails();


    @Query(value = """
                SELECT
                    c.CONSIGNMENT_ID, c.LANG_ID, c.LANG_TEXT, c.C_ID, c.C_NAME,
                    c.PARTNER_ID, c.PARTNER_TYPE, c.PARTNER_NAME,
                    c.MASTER_AIRWAY_BILL, c.HOUSE_AIRWAY_BILL,
                    pd.PIECE_DETAILS_ID
                FROM tblconsignment_entity c
                LEFT JOIN tblpiecedetails pd ON c.CONSIGNMENT_ID = pd.CONSIGNMENT_ID
            """, nativeQuery = true)
    List<Object[]> findConsignmentAndWithDetailsRaw();


//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE tblconsignment_entity SET " +
//            "CEILING_VALUE = COALESCE(:ceilingValue, 0.0), " +
//            "CHARGEABLE_WEIGHT = COALESCE(:chargeableWeight, 0.0), " +
//            "FRIGHT_CHARGE = COALESCE(:frightCharge, 0.0), " +
//            "COD_CHARGE = COALESCE(:codCharge, 0.0), " +
//            "FULFILMENT_CHARGE = COALESCE(:fulfilmentCharge, 0.0), " +
//            "RTO_CHARGE = COALESCE(:rtoCharge, 0.0), " +
//            "ASR_CHARGE = COALESCE(:asrCharge, 0.0), " +
//            "MOVEMENT_CHARGE = COALESCE(:movementCharge, 0.0), " +
//            "TRUCK_CHARGE = COALESCE(:truckCharge, 0.0), " +
//            "OUTBOUND_CLEARANCE = COALESCE(:outboundClearance, 0.0) " +
//            "WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
//            "AND IS_DELETED = 0", nativeQuery = true)
//    void updateFinance(
//            @Param("houseAirwayBill") String houseAirwayBill,
//            @Param("ceilingValue") Double ceilingValue,
//            @Param("chargeableWeight") Double chargeableWeight,
//            @Param("frightCharge") Double frightCharge,
//            @Param("codCharge") Double codCharge,
//            @Param("fulfilmentCharge") Double fulfilmentCharge,
//            @Param("rtoCharge") Double rtoCharge,
//            @Param("asrCharge") Double asrCharge,
//            @Param("movementCharge") Double movementCharge,
//            @Param("truckCharge") Double truckCharge,
//            @Param("outboundClearance") Double outboundClearance
//    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE tblconsignment_entity SET " +
            "CEILING_VALUE = CASE WHEN :ceilingValue IS NOT NULL AND :ceilingValue != 0 THEN :ceilingValue ELSE CEILING_VALUE END, " +
            "CHARGEABLE_WEIGHT = CASE WHEN :chargeableWeight IS NOT NULL AND :chargeableWeight != 0 THEN :chargeableWeight ELSE CHARGEABLE_WEIGHT END, " +
            "FRIGHT_CHARGE = CASE WHEN :frightCharge IS NOT NULL AND :frightCharge != 0 THEN :frightCharge ELSE FRIGHT_CHARGE END, " +
            "COD_CHARGE = CASE WHEN :codCharge IS NOT NULL AND :codCharge != 0 THEN :codCharge ELSE COD_CHARGE END, " +
            "FULFILMENT_CHARGE = CASE WHEN :fulfilmentCharge IS NOT NULL AND :fulfilmentCharge != 0 THEN :fulfilmentCharge ELSE FULFILMENT_CHARGE END, " +
            "RTO_CHARGE = CASE WHEN :rtoCharge IS NOT NULL AND :rtoCharge != 0 THEN :rtoCharge ELSE RTO_CHARGE END, " +
            "ASR_CHARGE = CASE WHEN :asrCharge IS NOT NULL AND :asrCharge != 0 THEN :asrCharge ELSE ASR_CHARGE END, " +
            "MOVEMENT_CHARGE = CASE WHEN :movementCharge IS NOT NULL AND :movementCharge != 0 THEN :movementCharge ELSE MOVEMENT_CHARGE END, " +
            "TRUCK_CHARGE = CASE WHEN :truckCharge IS NOT NULL AND :truckCharge != 0 THEN :truckCharge ELSE TRUCK_CHARGE END, " +
            "OUTBOUND_CLEARANCE = CASE WHEN :outboundClearance IS NOT NULL AND :outboundClearance != 0 THEN :outboundClearance ELSE OUTBOUND_CLEARANCE END " +
            "WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            "AND IS_DELETED = 0", nativeQuery = true)
    void updateFinance(
            @Param("houseAirwayBill") String houseAirwayBill,
            @Param("ceilingValue") Double ceilingValue,
            @Param("chargeableWeight") Double chargeableWeight,
            @Param("frightCharge") Double frightCharge,
            @Param("codCharge") Double codCharge,
            @Param("fulfilmentCharge") Double fulfilmentCharge,
            @Param("rtoCharge") Double rtoCharge,
            @Param("asrCharge") Double asrCharge,
            @Param("movementCharge") Double movementCharge,
            @Param("truckCharge") Double truckCharge,
            @Param("outboundClearance") Double outboundClearance
    );


    @Modifying
    @Transactional
    @Query(value = "UPDATE tblconsignment_entity SET " +
            "CONS_BAG_ID = :bagId, " +
            "HAWB_TYP_ID = :statusId," +
            "HAWB_TYP_TXT = :text, " +
            "HAWB_TYP = 'STATUS' " +
            "WHERE " +
            "HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            "AND C_ID = :companyId " +
            "AND LANG_ID = :languageId " +
            "AND IS_DELETED = 0", nativeQuery = true)
    void updateConsignmentBagTracking(@Param("bagId") Long bagId,
                                      @Param("companyId") String companyId,
                                      @Param("languageId") String languageId,
                                      @Param("houseAirwayBill") String houseAirwayBill,
                                      @Param("statusId") String statusId,
                                      @Param("text") String text);

    @Procedure(name = "GetConsignmentList")
    List<ConsignmentEntity> getConsignmentList();

}
