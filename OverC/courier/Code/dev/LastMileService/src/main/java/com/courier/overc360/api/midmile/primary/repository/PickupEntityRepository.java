package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.Consolidation;
import com.courier.overc360.api.midmile.primary.model.pickup.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.replica.model.pickup.FindConsolidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PickupEntityRepository extends JpaRepository<PickupEntity, Long> {

    Optional<PickupEntity> findByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String houseAirwayBill, Long deletionIndicator);


    Optional<PickupEntity> findByLanguageIdAndCompanyIdAndPieceIdAndHouseAirwayBillAndDeletionIndicator(String languageId, String companyId, String pieceId, String houseAirwayBill, Long deletionIndicator);


    @Modifying
    @Query(value = """
            UPDATE tblappuser SET LATITUDE = :lat, \s
            LONGITUDE = :longitude, ADDRESS = :address \s
            WHERE IS_DELETED = 0 AND \s
            C_ID = :companyId AND \s
            LANG_ID = :languageId AND \s
            APP_USER_ID = :appUserId""", nativeQuery = true)
    void updateAppUser(@Param("lat") Double lat,
                       @Param("longitude") Double longitude,
                       @Param("address") String address,
                       @Param("companyId") String companyId,
                       @Param("languageId") String languageId,
                       @Param("appUserId") String appUserId);

    @Query(value = """
            SELECT * FROM tblpickup_entity tr\s
            WHERE tr.IS_DELETED=0 \s
            AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID = :languageId)
            AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID = :companyId)
            AND (COALESCE(:partnerId, NULL) IS NULL OR tr.PARTNER_ID = :partnerId)
            AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL = :houseAirwayBill)
            AND (COALESCE(:pickupEntityId, NULL) IS NULL OR tr.PICKUP_ENTITY_ID = :pickupEntityId)
            AND (COALESCE(:pickupId, NULL) IS NULL OR tr.PICKUP_ID = :pickupId)""", nativeQuery = true)
    List<PickupEntity> updatePickupStatus(@Param("languageId") String languageId,
                                          @Param("companyId") String companyId,
                                          @Param("partnerId") String partnerId,
                                          @Param("houseAirwayBill") String houseAirwayBill,
                                          @Param("pickupId") String pickupId,
                                          @Param("pickupEntityId") Long pickupEntityId);


    @Query(value = """
            SELECT * FROM tblpickup_entity tr\s
            WHERE tr.IS_DELETED=0 \s
            AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID = :languageId)
            AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID = :companyId)
            AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL = :houseAirwayBill)
            AND (COALESCE(:pieceId, NULL) IS NULL OR tr.PIECE_ID = :pieceId)
            AND (COALESCE(:pickupId, NULL) IS NULL OR tr.PICKUP_ID = :pickpId)""", nativeQuery = true)
    PickupEntity updateStatusByPieceId(@Param("languageId") String languageId,
                                       @Param("companyId") String companyId,
                                       @Param("houseAirwayBill") String houseAirwayBill,
                                       @Param("pieceId") String pieceId,
                                       @Param("pickupId") String pickupId);

    @Query(value = """
            select REASONS_TEXT from tblreasonslistpickup\s
             where is_deleted = 0 and\s
            c_id = :companyId and lang_id = :languageId and reasons_id = :nprId\s""", nativeQuery = true)
    String getNprText(@Param("languageId") String languageId,
                      @Param("companyId") String companyId,
                      @Param("nprId") String nprId);


    @Modifying
    @Query(value = "update tblpickup_entity set PICKUP_ID = :pickupId WHERE (COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID = :partnerId)", nativeQuery = true)
    void updtaePickupId(@Param("pickupId") String pickupId,
                        @Param("partnerId") String partnerId);

    @Modifying
    @Query(value = "update tblconsignment_entity set PICKUP_ID = :pickupId, PICKUP_ENTITY_ID = :entityId, " +
            "HAWB_TYP_ID = :statusId, HAWB_TYP_TXT = :text, HAWB_TIMESTAMP = GETDATE() " +
            "WHERE " +
            "(COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID = :partnerId) " +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill)", nativeQuery = true)
    void updatePickupIdInConsignment(@Param("pickupId") String pickupId,
                                     @Param("statusId") String statusId,
                                     @Param("text") String text,
                                     @Param("entityId") Long entityId,
                                     @Param("partnerId") String partnerId,
                                     @Param("houseAirwayBill") String houseAirwayBill);

    @Modifying
    @Query(value = "update tblconsignment_entity set HAWB_TYP_ID = :statusId, " +
            "HAWB_TYP_TXT = :statusText, HAWB_TIMESTAMP = GETDATE() " +
            "WHERE " +
            "PICKUP_ID = :pickupId AND IS_DELETED = 0", nativeQuery = true)
    void updateConsignment(@Param("statusId") String statusId,
                           @Param("statusText") String statusText,
                           @Param("pickupId") String pickupId);

    @Modifying
    @Query(value = "update tblconsignmentstatus set HAWB_TYP_ID = :statusId, " +
            "PIECE_TYP_ID = :statusId, HAWB_TYP_TXT = :statusText, " +
            "PIECE_TYP_TXT = :statusText, HAWB_TIMESTAMP = GETDATE(), PIECE_TIMESTAMP = GETDATE() " +
            "WHERE " +
            "HOUSE_AIRWAY_BILL = :houseAB AND IS_DELETED = 0", nativeQuery = true)
    void updateConsignmentStatus(@Param("statusId") String statusId,
                                 @Param("statusText") String statusText,
                                 @Param("houseAB") String houseAB);

    @Modifying
    @Query(value = "update tblpiecedetails set PIECE_TYP_ID = :statusId, " +
            "PIECE_TYP_TXT = :statusText, PIECE_TIMESTAMP = GETDATE() " +
            "WHERE " +
            "HOUSE_AIRWAY_BILL = :houseAB AND IS_DELETED = 0", nativeQuery = true)
    void updatePieceTable(@Param("statusId") String statusId,
                          @Param("statusText") String statusText,
                          @Param("houseAB") String houseAB);

    @Query(value = """
            select top 1 PICKUP_COUNT pickupCount\s
            from tblcustomer where customer_id in (:customerId) and\s
            lang_id in (:languageId) and\s
            c_id in (:companyId) and is_deleted = 0\s""", nativeQuery = true)
    String getPickupCount(@Param(value = "customerId") String customerId,
                          @Param(value = "languageId") String languageId,
                          @Param(value = "companyId") String companyId);

    @Query(value = """
            select top 1 DELIVERY_COUNT deliveryCount\s
            from tblcustomer where customer_id in (:customerId) and\s
            lang_id in (:languageId) and\s
            c_id in (:companyId) and is_deleted = 0\s""", nativeQuery = true)
    String getDeliveryCount(@Param(value = "customerId") String customerId,
                          @Param(value = "languageId") String languageId,
                          @Param(value = "companyId") String companyId);

    @Modifying
    @Query(value = "UPDATE tblpickup_entity SET " +
            "IS_DELETED = 1, UTD_ON = getDate(), UTD_BY = :userId " +
            "WHERE IS_DELETED = 0 AND " +
            "(COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId) AND " +
            "(COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) AND " +
            "(COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID = :partnerId) AND " +
            "(COALESCE(:houseAB, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAB) AND " +
            "(COALESCE(:pickupId, NULL) IS NULL OR PICKUP_ID = :pickupId)", nativeQuery = true)
    void updatePickupId(@Param(value = "languageId") String languageId,
                        @Param(value = "companyId") String companyId,
                        @Param(value = "partnerId") String partnerId,
                        @Param(value = "houseAB") String houseAB,
                        @Param(value = "pickupId") String pickupId,
                        @Param(value = "userId") String userId);

    PickupEntity findByPickupEntityIdAndDeletionIndicator(Long pickupEntityId, Long deletionIndicator);



    @Query(value = "SELECT\n" +
            "    c.CONSIGNMENT_ID AS consignmentId,\n" +
            "    c.CONS_BAG_ID AS consignmentBagId,\n" +
            "    c.LANG_ID AS languageId,\n" +
            "    c.LANG_TEXT AS languageDescription,\n" +
            "    c.C_ID AS companyId,\n" +
            "    c.C_NAME AS companyName,\n" +
            "    c.PARTNER_TYPE AS partnerType,\n" +
            "    c.PARTNER_ID AS partnerId,\n" +
            "    c.PARTNER_NAME AS partnerName,\n" +
            "    c.STATUS_ID AS statusId,\n" +
            "    c.STATUS_TEXT AS statusDescription,\n" +
            "    c.STATUS_TIMESTAMP AS statusTimestamp,\n" +
            "    c.PAYMENT_TYPE AS paymentType,\n" +
            "    c.WEIGHT AS weight,\n" +
            "    c.PRODUCT_ID AS productId,\n" +
            "    c.PRODUCT_NAME AS productName,\n" +
            "    c.SERVICE_TYPE_ID AS serviceTypeId,\n" +
            "    c.SERVICE_TYPE_TEXT AS serviceTypeText,\n" +
            "    c.HOUSE_AIRWAY_BILL AS houseAirwayBill,\n" +
            "    c.REMARK AS remark,\n" +
            "    c.CUSTOMER_CODE AS customerCode,\n" +
            "    c.CUSTOMER_REFERENCE_NUMBER AS customerReferenceNumber,\n" +
            "    c.CONSIGNMENT_TYPE AS consignmentType,\n" +
            "    c.MOVEMENT_TYPE AS movementType,\n" +
            "    c.LOAD_TYPE AS loadType,\n" +
            "    c.DESCRIPTION AS description,\n" +
            "    c.COD_AMOUNT AS codAmount,\n" +
            "    c.COD_FAVOR_OF AS codFavorOf,\n" +
            "    c.COD_COLLECTION_MODE AS codCollectionMode,\n" +
            "    c.PRIORITY AS priority,\n" +
            "    c.PICKUP_OTP AS pickupOtp,\n" +
            "    c.RTO_OTP AS rtoOtp,\n" +
            "    c.CURRENCY AS currency,\n" +
            "    c.PICKUP_SERVICE_TIME AS pickupServiceTime,\n" +
            "    c.PICKUP_TIME_SLOT_START AS pickupTimeSlotStart,\n" +
            "    c.PICKUP_TIME_SLOT_END AS pickupTimeSlotEnd,\n" +
            "    c.HUB_CODE AS hubCode,\n" +
            "    c.INVOICE_AMOUNT AS invoiceAmount,\n" +
            "    c.INVOICE_URL AS invoiceUrl,\n" +
            "    c.PRODUCT_CODE AS productCode,\n" +
            "    c.IS_CUSTOMS_DECLARABLE AS isCustomsDeclarable,\n" +
            "    c.SUB_PRODUCT_ID AS subProductId,\n" +
            "    c.SUB_PRODUCT_NAME AS subProductName,\n" +
            "    c.ADD_ORIGIN_DETAILS AS addOriginDetails,\n" +
            "    c.ADD_DESTINATION_DETAILS AS addDestinationDetails,\n" +
            "    o.ADDRESS_HUB_CODE AS pickupHubCode,\n" +
            "    o.EMAIL AS emailId,\n" +
            "    o.COMPANY_NAME AS pickCompanyName,\n" +
            "    o.NAME AS name,\n" +
            "    o.PHONE AS phone,\n" +
            "    o.ALTERNATE_PHONE AS alternatePhone,\n" +
            "    o.ADDRESS_LINE_1 AS addressLine1,\n" +
            "    o.ADDRESS_LINE_2 AS addressLine2,\n" +
            "    o.PIN_CODE AS pincode,\n" +
            "    o.DISTRICT AS district,\n" +
            "    o.CITY AS city,\n" +
            "    o.STATE AS state,\n" +
            "    o.COUNTRY AS country,\n" +
            "    o.LATITUDE AS latitude,\n" +
            "    o.LONGITUDE AS longitude,\n" +
            "    d.EMAIL AS destEmailId,\n" +
            "    d.COMPANY_NAME AS destCompanyName,\n" +
            "    d.NAME AS destName,\n" +
            "    d.PHONE AS destPhone,\n" +
            "    d.ALTERNATE_PHONE AS destAlternatePhone,\n" +
            "    d.ADDRESS_LINE_1 AS destAddressLine1,\n" +
            "    d.ADDRESS_LINE_2 AS destAddressLine2,\n" +
            "    d.PINCODE AS destPincode,\n" +
            "    d.DISTRICT AS destDistrict,\n" +
            "    d.CITY AS destCity,\n" +
            "    d.STATE AS destState,\n" +
            "    d.COUNTRY AS destCountry,\n" +
            "    d.LATITUDE AS destLatitude,\n" +
            "    d.LONGITUDE AS destLongitude,\n" +
            "    p.PIECE_ID AS pieceId\n" +
            "FROM tblconsignment_entity c inner join tblorigindetails o on c.consignment_id = o.ORIGIN_ID " +
            "inner join tbldestdetails d on c.CONSIGNMENT_ID = d.DEST_DETAIL_ID " +
            "inner join tblpiecedetails p on p.consignment_id = c.consignment_id WHERE c.IS_DELETED=0 \n" +
            "AND c.HOUSE_AIRWAY_BILL IN (:consignmentId)",nativeQuery = true)
    List<Consolidation> getConsignmentData(@Param("consignmentId") List<String> consignmentId);


    @Modifying
    @Query(value = "update tblconsignment_entity set HAWB_TYP_ID = :statusId, " +
            "HAWB_TYP_TXT = :statusText " +
            "WHERE " +
            "HOUSE_AIRWAY_BILL = :hawb AND IS_DELETED = 0", nativeQuery = true)
    void updateConsignmentWithPickup(@Param("statusId") String statusId,
                           @Param("statusText") String statusText,
                           @Param("hawb") String hawb);


    @Modifying
    @Transactional
    @Query(value = "UPDATE tblpickup_entity SET STATUS_ID = (:statusId), STATUS_TEXT = (:statusText)" +
            " WHERE HOUSE_AIRWAY_BILL = (:houseAirwayBill) AND IS_DELETED = 0", nativeQuery = true)
    void updateStatus(@Param("statusId") String statusId,
                      @Param("statusText") String statusText,
                      @Param("houseAirwayBill") String houseAirwayBill);


    @Modifying
    @Query(value = "update tblconsignment_entity set HAWB_TYP_ID = 12, " +
            "HAWB_TYP_TXT = :status, HAWB_TIMESTAMP = GETDATE()," +
            "HUB_CODE = :hubCode, HUB_NAME = :hubName " +
            "WHERE HOUSE_AIRWAY_BILL = (:houseAirwayBill) AND IS_DELETED = 0", nativeQuery = true)
    void updateStatusAndHub(@Param("status") String status,
                            @Param("hubCode") String hubCode,
                            @Param("hubName") String hubName,
                            @Param("houseAirwayBill") String houseAirwayBill);


    @Modifying
    @Transactional
    @Query(value = "UPDATE tblconsignment_entity SET HAWB_TYP_ID = (:statusId), HAWB_TYP_TXT = (:statusText)" +
            "WHERE HOUSE_AIRWAY_BILL = (:houseAirwayBill) AND IS_DELETED = 0", nativeQuery = true)
    void updatePickedUpStatus(@Param("statusId") String statusId,
                              @Param("statusText") String statusText,
                              @Param("houseAirwayBill") String houseAirwayBill);

    @Query(value = "SELECT\n" +
            "    c.CONSIGNMENT_ID AS consignmentId,\n" +
            "    c.CONS_BAG_ID AS consignmentBagId,\n" +
            "    c.LANG_ID AS languageId,\n" +
            "    c.LANG_TEXT AS languageDescription,\n" +
            "    c.C_ID AS companyId,\n" +
            "    c.C_NAME AS companyName,\n" +
            "    c.PARTNER_TYPE AS partnerType,\n" +
            "    c.PARTNER_ID AS partnerId,\n" +
            "    c.PARTNER_NAME AS partnerName,\n" +
            "    c.STATUS_ID AS statusId,\n" +
            "    c.STATUS_TEXT AS statusDescription,\n" +
            "    c.STATUS_TIMESTAMP AS statusTimestamp,\n" +
            "    c.PAYMENT_TYPE AS paymentType,\n" +
            "    c.WEIGHT AS weight,\n" +
            "    c.PRODUCT_ID AS productId,\n" +
            "    c.PRODUCT_NAME AS productName,\n" +
            "    c.SERVICE_TYPE_ID AS serviceTypeId,\n" +
            "    c.SERVICE_TYPE_TEXT AS serviceTypeText,\n" +
            "    c.HOUSE_AIRWAY_BILL AS houseAirwayBill,\n" +
            "    c.REMARK AS remark,\n" +
            "    c.CUSTOMER_CODE AS customerCode,\n" +
            "    c.CUSTOMER_REFERENCE_NUMBER AS customerReferenceNumber,\n" +
            "    c.CONSIGNMENT_TYPE AS consignmentType,\n" +
            "    c.MOVEMENT_TYPE AS movementType,\n" +
            "    c.LOAD_TYPE AS loadType,\n" +
            "    c.DESCRIPTION AS description,\n" +
            "    c.COD_AMOUNT AS codAmount,\n" +
            "    c.COD_FAVOR_OF AS codFavorOf,\n" +
            "    c.COD_COLLECTION_MODE AS codCollectionMode,\n" +
            "    c.PRIORITY AS priority,\n" +
            "    c.PICKUP_OTP AS pickupOtp,\n" +
            "    c.RTO_OTP AS rtoOtp,\n" +
            "    c.CURRENCY AS currency,\n" +
            "    c.PICKUP_SERVICE_TIME AS pickupServiceTime,\n" +
            "    c.PICKUP_TIME_SLOT_START AS pickupTimeSlotStart,\n" +
            "    c.PICKUP_TIME_SLOT_END AS pickupTimeSlotEnd,\n" +
            "    c.HUB_CODE AS hubCode,\n" +
            "    c.INVOICE_AMOUNT AS invoiceAmount,\n" +
            "    c.INVOICE_URL AS invoiceUrl,\n" +
            "    c.PRODUCT_CODE AS productCode,\n" +
            "    c.IS_CUSTOMS_DECLARABLE AS isCustomsDeclarable,\n" +
            "    c.SUB_PRODUCT_ID AS subProductId,\n" +
            "    c.SUB_PRODUCT_NAME AS subProductName,\n" +
            "    c.ADD_ORIGIN_DETAILS AS addOriginDetails,\n" +
            "    c.ADD_DESTINATION_DETAILS AS addDestinationDetails,\n" +
            "    o.ADDRESS_HUB_CODE AS pickupHubCode,\n" +
            "    o.EMAIL AS emailId,\n" +
            "    o.COMPANY_NAME AS pickCompanyName,\n" +
            "    o.NAME AS name,\n" +
            "    o.PHONE AS phone,\n" +
            "    o.ALTERNATE_PHONE AS alternatePhone,\n" +
            "    o.ADDRESS_LINE_1 AS addressLine1,\n" +
            "    o.ADDRESS_LINE_2 AS addressLine2,\n" +
            "    o.PIN_CODE AS pincode,\n" +
            "    o.DISTRICT AS district,\n" +
            "    o.CITY AS city,\n" +
            "    o.STATE AS state,\n" +
            "    o.COUNTRY AS country,\n" +
            "    o.LATITUDE AS latitude,\n" +
            "    o.LONGITUDE AS longitude,\n" +
            "    d.EMAIL AS destEmailId,\n" +
            "    d.COMPANY_NAME AS destCompanyName,\n" +
            "    d.NAME AS destName,\n" +
            "    d.PHONE AS destPhone,\n" +
            "    d.ALTERNATE_PHONE AS destAlternatePhone,\n" +
            "    d.ADDRESS_LINE_1 AS destAddressLine1,\n" +
            "    d.ADDRESS_LINE_2 AS destAddressLine2,\n" +
            "    d.PINCODE AS destPincode,\n" +
            "    d.DISTRICT AS destDistrict,\n" +
            "    d.CITY AS destCity,\n" +
            "    d.STATE AS destState,\n" +
            "    d.COUNTRY AS destCountry,\n" +
            "    d.LATITUDE AS destLatitude,\n" +
            "    d.LONGITUDE AS destLongitude,\n" +
            "    p.PIECE_ID AS pieceId\n" +
            "FROM tblconsignment_entity c inner join tblorigindetails o on c.consignment_id = o.ORIGIN_ID " +
            "inner join tbldestdetails d on c.CONSIGNMENT_ID = d.DEST_DETAIL_ID " +
            "inner join tblpiecedetails p on p.consignment_id = c.consignment_id WHERE c.IS_DELETED=0 \n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR c.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))",nativeQuery = true)
    Consolidation getConsignment(@Param("houseAirwayBill") String houseAirwayBill);
}
