package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.pickup.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.pickup.PartnerHubMapping;
import com.courier.overc360.api.midmile.replica.model.pickup.AppUser;
import com.courier.overc360.api.midmile.replica.model.pickup.ReplicaPickupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaPickupEntityRepository extends JpaRepository<ReplicaPickupEntity, Long>,
        JpaSpecificationExecutor<ReplicaPickupEntity> {

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId, Long deletionIndicator);

    Optional<ReplicaPickupEntity> findByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId, Long deletionIndicator);


    // Get ALl Non-Deleted Pickup Details
    @Query(value = "Select * From tblpickup_entity\n" +
            "Where IS_DELETED=0", nativeQuery = true)
    List<ReplicaPickupEntity> getAllNonDeletedPickup();


    // Find Pickup with optional Params
    @Query(value = "SELECT * FROM tblpickup_entity tr \n" +
            "WHERE tr.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tr.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))\n" +
            "AND (COALESCE(:pickupId, NULL) IS NULL OR tr.PICKUP_ID IN (:pickupId))\n" +
            "AND (COALESCE(:consCreate, NULL) IS NULL OR tr.CONSIGNMENT_CREATION IN (:consCreate)) \n" +
            "AND (COALESCE(:statusId, NULL) IS NULL OR tr.STATUS_ID IN (:statusId)) \n " +
            "AND (COALESCE(:courierId, NULL) IS NULL OR tr.COURIER_ID IN (:courierId)) \n " +
            "AND (COALESCE(:createdBy, NULL) IS NULL OR tr.CTD_BY IN (:createdBy)) \n " +
            "AND (COALESCE(:pickupEntityId, NULL) IS NULL OR tr.PICKUP_ENTITY_ID IN (:pickupEntityId)) \n " +
            "AND (COALESCE(:assignedBy, NULL) IS NULL OR tr.ASSIGNED_BY IN (:assignedBy)) \n " +
            "And (COALESCE(CONVERT(VARCHAR(255), :fromCreatedOn), NULL) IS NULL OR (tr.CTD_ON between COALESCE(CONVERT(VARCHAR(255), :fromCreatedOn), NULL) And COALESCE(CONVERT(VARCHAR(255), :toCreatedOn), NULL)))"
            , nativeQuery = true)
    List<ReplicaPickupEntity> findPickupWithOptionalParams(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("partnerId") List<String> partnerId,
            @Param("houseAirwayBill") List<String> houseAirwayBill,
            @Param("pickupId") List<String> pickupId,
            @Param("fromCreatedOn") Date fromCreatedOn,
            @Param("toCreatedOn") Date toCreatedOn,
            @Param("consCreate") String consCreation,
            @Param("statusId") List<String> statusId,
            @Param("courierId") List<String> courierId,
            @Param("createdBy") List<String> createdBy,
            @Param("pickupEntityId") List<Long> pickupEntityId,
            @Param("assignedBy") List<String> assignedBy);


    @Query(value = "SELECT count(*) FROM tblpickup_entity WHERE IS_DELETED = 0 AND STATUS_ID = '48' \n" +
            "AND (COALESCE(:courierId, NULL) IS NULL OR COURIER_ID = :courierId) \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID = :partnerId) \n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill) \n" +
            "AND (COALESCE(:pickupId, NULL) IS NULL OR PICKUP_ID = :pickupId)", nativeQuery = true)
    Long getPickupAssignedCount(@Param("courierId") String courierId,
                                @Param("languageId") String languageId,
                                @Param("companyId") String companyId,
                                @Param("partnerId") String partnerId,
                                @Param("houseAirwayBill") String houseAirwayBill,
                                @Param("pickupId") String pickupId);

    @Query(value = "SELECT count(*) FROM tblpickup_entity WHERE IS_DELETED = 0 AND STATUS_ID = '49' \n" +
            "AND (COALESCE(:courierId, NULL) IS NULL OR COURIER_ID = :courierId) \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID = :partnerId) \n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill) \n" +
            "AND (COALESCE(:pickupId, NULL) IS NULL OR PICKUP_ID = :pickupId)", nativeQuery = true)
    Long getPickupInprogressCount(@Param("courierId") String courierId,
                                  @Param("languageId") String languageId,
                                  @Param("companyId") String companyId,
                                  @Param("partnerId") String partnerId,
                                  @Param("houseAirwayBill") String houseAirwayBill,
                                  @Param("pickupId") String pickupId);

    @Query(value = "SELECT * FROM tblpickup_entity tr \n" +
            "WHERE tr.IS_DELETED=0 AND (tr.STATUS_ID = '48' OR tr.STATUS_ID = '49') \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tr.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))\n" +
            "AND (COALESCE(:courierId, NULL) IS NULL OR tr.COURIER_ID IN (:courierId))\n" +
            "AND (COALESCE(:pickupId, NULL) IS NULL OR tr.PICKUP_ID IN (:pickupId))", nativeQuery = true)
    List<ReplicaPickupEntity> getPickupAssignedData(@Param("languageId") List<String> languageId,
                                                    @Param("companyId") List<String> companyId,
                                                    @Param("partnerId") List<String> partnerId,
                                                    @Param("houseAirwayBill") List<String> houseAirwayBill,
                                                    @Param("pickupId") List<String> pickupId,
                                                    @Param("courierId") List<String> courierId);


    // Get User
    @Query(value = "SELECT APP_USER_ID appUserId, LANG_ID languageId," +
            "C_ID companyId, MOBILE_NUMBER mobileNumber, ADDRESS address, " +
            "LATITUDE latitude, LONGITUDE longitude FROM tblappuser " +
            "WHERE APP_USER_TYPE = 'Rider' " +
            "AND IS_DELETED = 0 ", nativeQuery = true)
    List<AppUser> getAppUser();

    @Query(value = "SELECT MOBILE_NUMBER mobileNo FROM tblappuser " +
            "WHERE APP_USER_ID = :userId " +
            "AND IS_DELETED = 0 ", nativeQuery = true)
    String getMobileNo(@Param("userId") String userId);

    // Get UserAssigned Count
    @Query(value = "SELECT COUNT(COURIER_ID) courier FROM tblpickup_entity " +
            "WHERE COURIER_ID = :user AND " +
            "(STATUS_ID = '48' OR STATUS_ID = '49') AND IS_DELETED = 0 ", nativeQuery = true)
    Long pickerAssignedCount(@Param("user") String user);

    // Picker DeliveryCount
    @Query(value = "SELECT COUNT(COURIER_ID) courier FROM tblpickup_entity " +
            "WHERE COURIER_ID = :user AND " +
            "STATUS_ID in (:statusId) " +
            "AND IS_DELETED = 0 ", nativeQuery = true)
    Long pickupDeliveryCount(@Param("user") String user,
                             @Param("statusId") String statusId);

    // Get Latest ActualSequenceNo
    @Query(value = "SELECT TOP 1 ETA_DATE_TIME " +
            "FROM tblpickup_entity " +
            "WHERE IS_DELETED = 0 AND " +
            "(STATUS_ID = '48' OR STATUS_ID = '49') AND " +
            "COURIER_ID = :user ORDER BY ETA_DATE_TIME DESC ", nativeQuery = true)
    String getEta(@Param("user") String user);

//    // Get ETA Time
//    @Query(value = "SELECT TOP 1 ETA_DATE_TIME FROM tblpickup_entity " +
//            "WHERE IS_DELETED = 0 AND ACTUAL_SEQUENCE_NO = :sequenceNo", nativeQuery = true)
//     Date getETA(@Param("sequenceNo") Long sequenceNo);


//    ReplicaPickupEntity findByLanguageIdAndCompanyIdAndCourierIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
//            String languageId, String companyId, String courierId, String partnerId, String houseAirwayBill , String pickupId,long l);


    @Query(value = "SELECT * FROM tblpickup_entity " +
            "WHERE STATUS_ID = '48' " +
            "AND IS_DELETED = 0", nativeQuery = true)
    List<ReplicaPickupEntity> getAllAssigned();

    @Query(value = "Select \n" +
            "tc.lang_id langId, \n" +
            "tc.lang_text langDesc, \n" +
            "tc.c_name companyDesc " +
            "from tblcompany tc \n" +
            "Where \n " +
            "tc.c_id in (:companyId) and \n" +
            "tc.is_deleted = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "companyId") String companyId);

    @Query(value = "Select type_text statusText \n" +
//            "CONCAT (ts.type_id, ' - ', ts.type_text) As statusText \n" +
            "from tblstatusevent ts \n" +
            "Where ts.type_id in (:statusId) and ts.is_deleted = 0", nativeQuery = true)
    String getStatusDescription(@Param(value = "statusId") String statusId);

    @Query(value = "SELECT tr.C_ID as companyId,tr.LANG_ID as langId,tr.C_NAME as companyDesc,tr.LANG_TXT as langDesc,tr.PIECE_ID as pieceId FROM tblpickup_entity tr \n" +
            "WHERE tr.IS_DELETED=0 \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))",nativeQuery = true)
    List<IKeyValuePair> getPickupData(@Param("companyId") String companyId,
                                      @Param("languageId") String languageId,
                                      @Param("houseAirwayBill") String houseAirwayBill);


    @Query(value = "SELECT tr.C_ID as companyId,tr.LANG_ID as langId,tr.C_NAME as companyDesc,tr.LANG_TEXT as langDesc,tr.PIECE_ID as pieceId,tr.CONSIGNMENT_ID as consignmentId,tr.PARTNER_ID as partnerId FROM tblconsignment_entity tr \n" +
            "WHERE tr.IS_DELETED=0 \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))",nativeQuery = true)
    List<IKeyValuePair> getConsignmentData(@Param("companyId") String companyId,
                                         @Param("languageId") String languageId,
                                         @Param("houseAirwayBill") String houseAirwayBill);


    @Query(value = "SELECT tr.PIECE_ID FROM tblpiecedetails tr \n" +
            "WHERE tr.IS_DELETED=0 \n" +
            "AND (COALESCE(:consignmentId, NULL) IS NULL OR tr.CONSIGNMENT_ID IN (:consignmentId))",nativeQuery = true)
    String getPiece(@Param("consignmentId") Long consignmentId);


    @Query(value = "SELECT tr.HUB_CODE as hubCode,tr.HUB_NAME as hubName FROM tblpartnerhubmapping tr \n" +
            "WHERE tr.IS_DELETED=0 \n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tr.PARTNER_ID IN (:partnerId))",nativeQuery = true)
    IKeyValuePair getHubCode(@Param("partnerId") String partnerId);

    @Query(value = "select max(PICKUP_COUNT) from tblcustomer where " +
            "customer_id = :partnerId and is_deleted = 0", nativeQuery = true)
    Long pickupCount(@Param("partnerId") String partnerId);

}
