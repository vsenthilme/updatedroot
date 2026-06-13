package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.delivery.ReplicaDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface ReplicaDeliveryRepository extends JpaRepository<ReplicaDelivery,Long>, JpaSpecificationExecutor<ReplicaDelivery> {

    Optional<ReplicaDelivery> findByLanguageIdAndCompanyIdAndPieceIdAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String pieceId, String houseAirwayBill, Long deletionIndicator);


    // Get UserAssigned Count
    @Query(value = "SELECT COUNT(COURIER_ID) courier FROM tbldelivery " +
            "WHERE COURIER_ID = :user AND " +
            "(STATUS_ID = '48' OR STATUS_ID = '49') AND IS_DELETED = 0 ", nativeQuery = true)
    public Long pickerAssignedCount(@Param("user") String user);

    // Picker DeliveryCount
    @Query(value = "SELECT COUNT(COURIER_ID) courier FROM tbldelivery " +
            "WHERE COURIER_ID = :user AND " +
            "STATUS_ID in (:statusId) " +
            "AND IS_DELETED = 0 ", nativeQuery = true)
    public Long pickupDeliveryCount(@Param("user") String user,
                                    @Param("statusId") String statusId);

    // Get Latest ActualSequenceNo
//    @Query(value = "SELECT TOP 1 DELIVERY_SERVICE_TIME " +
//            "FROM tbldelivery " +
//            "WHERE IS_DELETED = 0 AND COURIER_ID = :user ORDER BY ACTUAL_SEQUENCE_NO DESC ", nativeQuery = true)
//     String getEta(@Param("user") String user);

    @Query(value = "SELECT TOP 1 ETA_DATE_TIME " +
            "FROM tbldelivery " +
            "WHERE IS_DELETED = 0 AND " +
            "(STATUS_ID = '48' OR STATUS_ID = '50') AND " +
            "COURIER_ID = :user ORDER BY ETA_DATE_TIME DESC ", nativeQuery = true)
    String getEta(@Param("user") String user);

//    // Get ETA Time
//    @Query(value = "SELECT DELIVERY_SERVICE_TIME FROM tbldelivery" +
//            " WHERE IS_DELETED=0 AND ACTUAL_SEQUENCE_NO = :sequenceNo", nativeQuery = true)
//    public Date getETA(@Param("sequenceNo") Long sequenceNo);



    @Query(value = "SELECT count(*) FROM tbldelivery WHERE IS_DELETED = 0 AND STATUS_ID = '16' \n" +
            "AND (COALESCE(:pieceId, NULL) IS NULL OR PIECE_ID = :pieceId) \n"+
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId) \n"+
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n"+
            "AND (COALESCE(:deliveryId, NULL) IS NULL OR DELIVERY_ID = :deliveryId) \n"+
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill) \n "+
            "AND (COALESCE(:courierId, NULL) IS NULL OR COURIER_ID = :courierId)",nativeQuery = true)
    Long getDeliveryInprogressCount(@Param("pieceId") String pieceId,
                                  @Param("languageId") String languageId,
                                  @Param("companyId") String companyId,
                                  @Param("deliveryId") String deliveryId,
                                  @Param("houseAirwayBill") String houseAirwayBill,
                                    @Param("courierId") String courierId);

    @Query(value = "SELECT * FROM tbldelivery tr \n" +
            "WHERE tr.IS_DELETED=0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:pieceId, NULL) IS NULL OR tr.PIECE_ID IN (:pieceId))\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))\n" +
            "AND (COALESCE(:deliveryId, NULL) IS NULL OR tr.DELIVERY_ID IN (:deliveryId))\n" +
            "AND (COALESCE(:courierId, NULL) IS NULL OR tr.COURIER_ID IN (:courierId))",nativeQuery = true)
    List<ReplicaDelivery> getDeliveryAssignedData(@Param("languageId") List<String> languageId,
                                                  @Param("companyId") List<String> companyId,
                                                  @Param("pieceId") List<String> pieceId,
                                                  @Param("houseAirwayBill") List<String> houseAirwayBill,
                                                  @Param("deliveryId") List<String> deliveryId,
                                                  @Param("courierId") List<String> courierId);


    @Query(value = "SELECT * FROM tbldelivery WHERE (COALESCE(:hubCode, NULL) IS NULL OR HUB_CODE IN (:hubCode)) " +
            " And (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (CTD_ON between COALESCE(CONVERT(VARCHAR(255), \n" +
            ":fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL))) " +
            " AND (COALESCE(:statusId, NULL) IS NULL OR STATUS_ID IN (:statusId)) " +
            " AND IS_DELETED = 0", nativeQuery = true)
    List<ReplicaDelivery> getDeliveryManifestData(@Param("hubCode") List<String> hubCode,
                                                  @Param("fromDate") Date fromDate,
                                                  @Param("toDate") Date toDate,
                                                  @Param("statusId") List<String> statusId);



    @Query(value = "SELECT COUNT(MANIFEST_NUMBER) FROM tbldelivery " +
            " WHERE MANIFEST_NUMBER = (:manifestNumber) AND IS_DELETED = 0", nativeQuery = true)
    Long getManifestCount(@Param("manifestNumber") String manifestNumber);

    @Query(value = "SELECT * FROM tbldelivery WHERE (COALESCE(:manifestNumber, NULL) IS NULL OR MANIFEST_NUMBER IN (:manifestNumber)) " +
            " AND IS_DELETED = 0", nativeQuery = true)
    List<ReplicaDelivery> getDeliveryData(@Param("manifestNumber") List<String> manifestNumber);

    @Query(value = "SELECT count(*) FROM tbldelivery WHERE IS_DELETED = 0 AND STATUS_ID = '48' \n" +
            "AND (COALESCE(:pieceId, NULL) IS NULL OR PIECE_ID = :pieceId) \n"+
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId) \n"+
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n"+
            "AND (COALESCE(:deliveryId, NULL) IS NULL OR DELIVERY_ID = :deliveryId) \n"+
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill) \n" +
            "AND (COALESCE(:courierId, NULL) IS NULL OR COURIER_ID = :courierId)",nativeQuery = true)
    Long getDeliveryAssignedCount(@Param("pieceId") String pieceId,
                                  @Param("languageId") String languageId,
                                  @Param("companyId") String companyId,
                                  @Param("deliveryId") String deliveryId,
                                  @Param("houseAirwayBill") String houseAirwayBill,
                                  @Param("courierId") String courierId);


    @Query(value = "Select type_text statusText \n " +
//            "CONCAT (ts.type_id, ' - ', ts.type_text) As statusText \n " +
            "from tblstatusevent ts \n" +
            "Where ts.type_id in (:statusId) and ts.is_deleted = 0", nativeQuery = true)
    String getStatusDescription(@Param("statusId") String statusId);

    @Query(value = "select SERVICE_TYPE_TEXT as serviceTypeText, WEIGHT weight, PARTNER_HOUSE_AB partnerHouseAirwayBill from tblconsignment_entity " +
            "where HOUSE_AIRWAY_BILL = :houseAB AND IS_DELETED = 0", nativeQuery = true)
     IKeyValuePair getCons(@Param("houseAB") String houseAB);

    @Query(value = "select max(DELIVERY_COUNT) from tblcustomer where " +
            "customer_id = :partnerId and is_deleted = 0", nativeQuery = true)
    Long deliveryCount(@Param("partnerId") String partnerId);

    @Query(value = "select count(hawb_typ_id) statusCount " +
            "from tblconsignment_entity " +
            "where IS_DELETED = 0 and hawb_typ_id in (12,51) " +
            "and (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (CTD_ON between COALESCE(CONVERT(VARCHAR(255), \n" +
            ":fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL))) ", nativeQuery = true)
    Object[] getUnAssigned(@Param("fromDate") Date fromDate,
                           @Param("toDate") Date toDate);

    @Query(value = "select count(status_id) statusCount " +
            "from tbldelivery " +
            "where IS_DELETED = 0 and status_id = 48 " +
            "and (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (CTD_ON between COALESCE(CONVERT(VARCHAR(255), \n" +
            ":fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL))) ", nativeQuery = true)
    Object[] getAssigned(@Param("fromDate") Date fromDate,
                            @Param("toDate") Date toDate);

    @Query(value = "select count(status_id) statusCount " +
            "from tbldelivery " +
            "where IS_DELETED = 0 and status_id = 50 " +
            "and (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (CTD_ON between COALESCE(CONVERT(VARCHAR(255), \n" +
            ":fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL))) ", nativeQuery = true)
    Object[] getAccepted(@Param("fromDate") Date fromDate,
                         @Param("toDate") Date toDate);

    @Query(value = "select count(status_id) statusCount " +
            "from tbldelivery " +
            "where IS_DELETED = 0 and status_id = 18 " +
            "and (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (CTD_ON between COALESCE(CONVERT(VARCHAR(255), \n" +
            ":fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL))) ", nativeQuery = true)
    Object[] getDelivered(@Param("fromDate") Date fromDate,
                         @Param("toDate") Date toDate);

    @Query(value = "select count(status_id) statusCount " +
            "from tbldelivery " +
            "where IS_DELETED = 0 and status_id in (55,54) " +
            "and (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (CTD_ON between COALESCE(CONVERT(VARCHAR(255), \n" +
            ":fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL))) ", nativeQuery = true)
    Object[] getUnDelivered(@Param("fromDate") Date fromDate,
                          @Param("toDate") Date toDate);

    @Query(value = "SELECT CHARGEABLE_WEIGHT weight from tblconsignment_entity where house_airway_bill = :houseAirwayBill and is_deleted = 0", nativeQuery = true)
    double getWeight(@Param("houseAirwayBill") String houseAirwayBill);
    @Query(value = "SELECT COD_AMOUNT codAmount from tblconsignment_entity where house_airway_bill = :houseAirwayBill and " +
            "PAYMENT_TYPE = 'COD' and is_deleted = 0", nativeQuery = true)
    String getCodAmount(@Param("houseAirwayBill") String houseAirwayBill);

}
