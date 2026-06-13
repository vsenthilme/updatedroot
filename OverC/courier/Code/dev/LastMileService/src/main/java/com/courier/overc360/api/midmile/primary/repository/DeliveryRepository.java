package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface DeliveryRepository extends JpaRepository<Delivery, Long>, JpaSpecificationExecutor<Delivery> {


    Optional<Delivery> findByLanguageIdAndCompanyIdAndPieceIdAndHouseAirwayBillAndDeletionIndicator(String languageId, String companyId, String pieceId, String houseAirwayBill, long l);

    @Query(value = "SELECT * FROM tbldelivery WHERE IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID = :languageId)\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID = :companyId)\n" +
            "AND (COALESCE(:pieceId, NULL) IS NULL OR tr.PIECE_ID = :pieceId)\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL = :houseAirwayBill)\n" +
            "AND (COALESCE(:deliveryId, NULL) IS NULL OR tr.DELIVERY_ID = :deliveryId)", nativeQuery = true)
    Delivery getHouseAirWayBill(@Param("languageId") String languageId,
                                @Param("companyId") String companyId,
                                @Param("pieceId") String pieceId,
                                @Param("houseAirwayBill") String houseAirwayBill,
                                @Param("deliveryId") String deliveryId);

    @Query(value = "SELECT * FROM tbldelivery WHERE IS_DELETED = 0 \n" +
            "AND (COALESCE(:pieceId, NULL) IS NULL OR PIECE_ID = :pieceId)", nativeQuery = true)
    Delivery getPiece(String pieceId);

    @Query(value = "SELECT * FROM tbldelivery WHERE IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId)\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId)\n" +
            "AND (COALESCE(:pieceId, NULL) IS NULL OR PIECE_ID = :pieceId)\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill)\n" +
            "AND (COALESCE(:deliveryId, NULL) IS NULL OR DELIVERY_ID = :deliveryId)", nativeQuery = true)
    List<Delivery> updateDeliveryStatus(@Param("languageId") String languageId,
                                        @Param("companyId") String companyId,
                                        @Param("pieceId") String pieceId,
                                        @Param("houseAirwayBill") String houseAirwayBill,
                                        @Param("deliveryId") String deliveryId);


    @Query(value = """
            select REASONS_TEXT from tblreasonslistdelivery\s
             where is_deleted = 0 and\s
            c_id = :companyId and lang_id = :languageId and REASONS_ID = :ndrId\s""", nativeQuery = true)
    String getNdrText(@Param("languageId") String languageId,
                      @Param("companyId") String companyId,
                      @Param("ndrId") String ndrId);


    @Modifying
    @Query(value = "update tblconsignment_entity SET  " +
            "HAWB_TYP_ID = :statusId, HAWB_TYP_TXT = :text, HAWB_TIMESTAMP = GETDATE(), " +
            "PAYMENT_COLLECTED = COALESCE(:payment, PAYMENT_COLLECTED) " +
            "WHERE " +
            "(COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID = :partnerId) " +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill)", nativeQuery = true)
    void updateDeliveryConsignment(
            @Param("statusId") String statusId,
            @Param("text") String text,
            @Param("partnerId") String partnerId,
            @Param("houseAirwayBill") String houseAirwayBill,
            @Param("payment") Double payment);

    @Modifying
    @Query(value = "UPDATE tblconsignment_entity set " +
            "FRIGHT_CHARGE = :charge, COD_CHARGE = :codCharge WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill AND IS_DELETED = 0", nativeQuery = true)
    void updateCharge(@Param("charge") Double charge,
                      @Param("codCharge") Double codCharge,
                      @Param("houseAirwayBill") String houseAirwayBill);


    @Query(value = "SELECT CUSTOMER_ID, BILLING_FREQUENCY FROM tblcustomer WHERE BILL_GENERATION = 0 AND IS_DELETED = 0", nativeQuery = true)
    List<Object[]> getCustomerId();

    @Query(value = "SELECT top 1 BILLING_FREQUENCY_DATE_1 date1, BILLING_FREQUENCY_DATE_2 date2, BILLING_FREQUENCY_DATE_3 date3, BILLING_FREQUENCY_DATE_4 date4, " +
            "BILLING_FREQUENCY_DATE_5 date5 FROM tblcustomer WHERE CUSTOMER_ID = :customerId AND BILL_GENERATION = 0 AND IS_DELETED = 0", nativeQuery = true)
    Object[] getDate(@Param("customerId") String customerId);

    @Query(value = "SELECT MAX(BILLING_FREQUENCY_DATE_1) date1 FROM tblcustomer WHERE CUSTOMER_ID = :customerId AND BILL_GENERATION = 0 AND IS_DELETED = 0", nativeQuery = true)
    Date date1(@Param("customerId") String customerId);
    @Query(value = "SELECT MAX(BILLING_FREQUENCY_DATE_2) date2 FROM tblcustomer WHERE CUSTOMER_ID = :customerId AND BILL_GENERATION = 0 AND IS_DELETED = 0", nativeQuery = true)
    Date date2(@Param("customerId") String customerId);
    @Query(value = "SELECT MAX(BILLING_FREQUENCY_DATE_3) date3 FROM tblcustomer WHERE CUSTOMER_ID = :customerId AND BILL_GENERATION = 0 AND IS_DELETED = 0", nativeQuery = true)
    Date date3(@Param("customerId") String customerId);
    @Query(value = "SELECT MAX(BILLING_FREQUENCY_DATE_4) date4 FROM tblcustomer WHERE CUSTOMER_ID = :customerId AND BILL_GENERATION = 0 AND IS_DELETED = 0", nativeQuery = true)
    Date date4(@Param("customerId") String customerId);
    @Query(value = "SELECT MAX(BILLING_FREQUENCY_DATE_5) date5 FROM tblcustomer WHERE CUSTOMER_ID = :customerId AND BILL_GENERATION = 0 AND IS_DELETED = 0", nativeQuery = true)
    Date date5(@Param("customerId") String customerId);

//    @Query(value = "select MAX(C_ID), MAX(LANG_ID), sum(CEILING_VALUE), sum(CHARGEABLE_WEIGHT), sum(FRIGHT_CHARGE), sum(COD_CHARGE), sum(FULFILMENT_CHARGE), \n" +
//            "sum(RTO_CHARGE), sum(ASR_CHARGE), sum(MOVEMENT_CHARGE), sum(TRUCK_CHARGE) from tblconsignment_entity where PARTNER_ID = :partnerId and CTD_ON \n" +
//            "group by PARTNER_ID ", nativeQuery = true)
//    IKeyValuePair getChargeValue(@Param("partnerId") String partnerId);

    @Query(value = "SELECT MAX(C_ID) companyId, MAX(LANG_ID) langId, SUM(CEILING_VALUE) ceilingValue, " +
            "SUM(CHARGEABLE_WEIGHT) chargeableWeight, SUM(FRIGHT_CHARGE) frightCharge, " +
            "SUM(COD_CHARGE) codCharge, SUM(FULFILMENT_CHARGE) fulfilmentCharge, " +
            "SUM(RTO_CHARGE) rtoCharge, SUM(ASR_CHARGE) asrCharge, SUM(MOVEMENT_CHARGE) movementCharge, " +
            "SUM(TRUCK_CHARGE) truckCharge, " +
            "SUM(CASE WHEN CEILING_VALUE IS NOT NULL AND CEILING_VALUE != 0 THEN 1 ELSE 0 END) AS ceilingValueCount, " +
            "SUM(CASE WHEN CHARGEABLE_WEIGHT IS NOT NULL AND CHARGEABLE_WEIGHT != 0 THEN 1 ELSE 0 END) AS chargeableWeightCount, " +
            "SUM(CASE WHEN FRIGHT_CHARGE IS NOT NULL AND FRIGHT_CHARGE != 0 THEN 1 ELSE 0 END) AS frightChargeCount, " +
            "SUM(CASE WHEN COD_CHARGE IS NOT NULL AND COD_CHARGE != 0 THEN 1 ELSE 0 END) AS codChargeCount, " +
            "SUM(CASE WHEN FULFILMENT_CHARGE IS NOT NULL AND FULFILMENT_CHARGE != 0 THEN 1 ELSE 0 END) AS fulfilmentChargeCount, " +
            "SUM(CASE WHEN RTO_CHARGE IS NOT NULL AND RTO_CHARGE != 0 THEN 1 ELSE 0 END) AS rtoChargeCount, " +
            "SUM(CASE WHEN ASR_CHARGE IS NOT NULL AND ASR_CHARGE != 0 THEN 1 ELSE 0 END) AS asrChargeCount, " +
            "SUM(CASE WHEN MOVEMENT_CHARGE IS NOT NULL AND MOVEMENT_CHARGE != 0 THEN 1 ELSE 0 END) AS movementChargeCount, " +
            "SUM(CASE WHEN TRUCK_CHARGE IS NOT NULL AND TRUCK_CHARGE != 0 THEN 1 ELSE 0 END) AS truckChargeCount " +
            "FROM tblconsignment_entity " +
            "WHERE PARTNER_ID = :partnerId " +
            "AND HAWB_TYP_ID = '18' " +
            "AND CTD_ON BETWEEN DATEADD(HOUR, -24, GETDATE()) AND GETDATE() " +
            "GROUP BY PARTNER_ID", nativeQuery = true)
    IKeyValuePair getChargeValue(@Param("partnerId") String partnerId);

    @Query(value = "SELECT MAX(C_ID) companyId, MAX(LANG_ID) langId, SUM(CEILING_VALUE) ceilingValue, SUM(CHARGEABLE_WEIGHT) chargeableWeight, " +
            "SUM(FRIGHT_CHARGE) frightCharge, SUM(COD_CHARGE) codCharge, SUM(FULFILMENT_CHARGE) fulfilmentCharge, SUM(RTO_CHARGE) rtoCharge, " +
            "SUM(ASR_CHARGE) asrCharge, SUM(MOVEMENT_CHARGE) movementCharge, SUM(TRUCK_CHARGE) truckCharge, " +
            "SUM(CASE WHEN CEILING_VALUE IS NOT NULL AND CEILING_VALUE != 0 THEN 1 ELSE 0 END) AS ceilingValueCount, " +
            "SUM(CASE WHEN CHARGEABLE_WEIGHT IS NOT NULL AND CHARGEABLE_WEIGHT != 0 THEN 1 ELSE 0 END) AS chargeableWeightCount, " +
            "SUM(CASE WHEN FRIGHT_CHARGE IS NOT NULL AND FRIGHT_CHARGE != 0 THEN 1 ELSE 0 END) AS frightChargeCount, " +
            "SUM(CASE WHEN COD_CHARGE IS NOT NULL AND COD_CHARGE != 0 THEN 1 ELSE 0 END) AS codChargeCount, " +
            "SUM(CASE WHEN FULFILMENT_CHARGE IS NOT NULL AND FULFILMENT_CHARGE != 0 THEN 1 ELSE 0 END) AS fulfilmentChargeCount, " +
            "SUM(CASE WHEN RTO_CHARGE IS NOT NULL AND RTO_CHARGE != 0 THEN 1 ELSE 0 END) AS rtoChargeCount, " +
            "SUM(CASE WHEN ASR_CHARGE IS NOT NULL AND ASR_CHARGE != 0 THEN 1 ELSE 0 END) AS asrChargeCount, " +
            "SUM(CASE WHEN MOVEMENT_CHARGE IS NOT NULL AND MOVEMENT_CHARGE != 0 THEN 1 ELSE 0 END) AS movementChargeCount, " +
            "SUM(CASE WHEN TRUCK_CHARGE IS NOT NULL AND TRUCK_CHARGE != 0 THEN 1 ELSE 0 END) AS truckChargeCount " +
            "FROM tblconsignment_entity " +
            "WHERE PARTNER_ID = :partnerId " +
            "AND HAWB_TYP_ID = '18' " +
            "AND CTD_ON BETWEEN :startDate AND :endDate " +
            "GROUP BY PARTNER_ID", nativeQuery = true)
    IKeyValuePair getChargeValueForOneMonth(@Param("partnerId") String partnerId,
                                            @Param("startDate") Date startDate,
                                            @Param("endDate") Date endDate);

    @Query(value = "SELECT MAX(C_ID) companyId, MAX(LANG_ID) langId, SUM(CEILING_VALUE) ceilingValue, " +
            "SUM(CHARGEABLE_WEIGHT) chargeableWeight, SUM(FRIGHT_CHARGE) frightCharge, SUM(COD_CHARGE) codCharge, " +
            "SUM(FULFILMENT_CHARGE) fulfilmentCharge, SUM(RTO_CHARGE) rtoCharge, SUM(ASR_CHARGE) asrCharge, " +
            "SUM(MOVEMENT_CHARGE) movementCharge, SUM(TRUCK_CHARGE) truckCharge, " +
            "SUM(CASE WHEN CEILING_VALUE IS NOT NULL AND CEILING_VALUE != 0 THEN 1 ELSE 0 END) AS ceilingValueCount, " +
            "SUM(CASE WHEN CHARGEABLE_WEIGHT IS NOT NULL AND CHARGEABLE_WEIGHT != 0 THEN 1 ELSE 0 END) AS chargeableWeightCount, " +
            "SUM(CASE WHEN FRIGHT_CHARGE IS NOT NULL AND FRIGHT_CHARGE != 0 THEN 1 ELSE 0 END) AS frightChargeCount, " +
            "SUM(CASE WHEN COD_CHARGE IS NOT NULL AND COD_CHARGE != 0 THEN 1 ELSE 0 END) AS codChargeCount, " +
            "SUM(CASE WHEN FULFILMENT_CHARGE IS NOT NULL AND FULFILMENT_CHARGE != 0 THEN 1 ELSE 0 END) AS fulfilmentChargeCount, " +
            "SUM(CASE WHEN RTO_CHARGE IS NOT NULL AND RTO_CHARGE != 0 THEN 1 ELSE 0 END) AS rtoChargeCount, " +
            "SUM(CASE WHEN ASR_CHARGE IS NOT NULL AND ASR_CHARGE != 0 THEN 1 ELSE 0 END) AS asrChargeCount, " +
            "SUM(CASE WHEN MOVEMENT_CHARGE IS NOT NULL AND MOVEMENT_CHARGE != 0 THEN 1 ELSE 0 END) AS movementChargeCount, " +
            "SUM(CASE WHEN TRUCK_CHARGE IS NOT NULL AND TRUCK_CHARGE != 0 THEN 1 ELSE 0 END) AS truckChargeCount " +
            "FROM tblconsignment_entity " +
            "WHERE PARTNER_ID = :partnerId " +
            "AND HAWB_TYP_ID = '18' " +
            "AND (COALESCE(:startDate, NULL) IS NULL OR CAST(CTD_ON AS datetime2) BETWEEN CAST(:startDate AS datetime2) AND CAST(:endDate AS datetime2)) " +
//            "AND CTD_ON BETWEEN :startDate AND :endDate " +
            "GROUP BY PARTNER_ID", nativeQuery = true)
    IKeyValuePair getChargeValueForDateWise(@Param("partnerId") String partnerId,
                                            @Param("startDate") Date startDate,
                                            @Param("endDate") Date endDate);



    @Modifying
    @Query(value = "UPDATE tbldelivery  " +
            " SET STATUS_ID = :statusId, " +
            "    STATUS_TEXT = (SELECT TYPE_TEXT " +
            "                            FROM tblstatusevent  " +
            "                            WHERE TYPE_ID = :statusId) " +
            " WHERE MANIFEST_NUMBER = :manifestNumber " +
            "  AND C_ID = :companyId " +
            "  AND LANG_ID = :languageId ", nativeQuery = true)
    void updateStatusDeliveryData(@Param("languageId") String languageId,
                                  @Param("companyId") String companyId,
                                  @Param("manifestNumber") String manifestNumber,
                                  @Param("statusId") String statusId);

    @Query(value = "SELECT HOUSE_AIRWAY_BILL houseAirwayBill FROM tbldelivery " +
            " WHERE MANIFEST_NUMBER = (:manifestNumber) AND IS_DELETED = 0", nativeQuery = true)
    List<String> getHouseAirwayBillFromManifest(@Param("manifestNumber") String manifestNumber);

    @Modifying
    @Query(value = "UPDATE tblconsignment_entity  " +
            " SET STATUS_ID = :statusId, " +
            "     STATUS_TEXT = (SELECT TYPE_TEXT " +
            "                              FROM tblstatusevent  " +
            "                              WHERE TYPE_ID = :statusId ) " +
            " WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            "   AND C_ID = :companyId " +
            "   AND LANG_ID = :languageId ", nativeQuery = true)
    void updateStatusConsignmentData(@Param("houseAirwayBill") String houseAirwayBill,
                                     @Param("statusId") String statusId,
                                     @Param("languageId") String languageId,
                                     @Param("companyId") String companyId);

    @Query(value = "SELECT PIECE_ID pieceId FROM tblpiecedetails " +
            " WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            " AND C_ID = :companyId " +
            " AND LANG_ID = :languageId " +
            " AND IS_DELETED = 0 ", nativeQuery = true)
    String getPieceId(@Param("houseAirwayBill") String houseAirwayBill,
                      @Param("languageId") String languageId,
                      @Param("companyId") String companyId);

    @Query(value = "SELECT PIECE_TYP pieceType FROM tblpiecedetails " +
            " WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            " AND C_ID = :companyId " +
            " AND LANG_ID = :languageId " +
            " AND IS_DELETED = 0 ", nativeQuery = true)
    String getPieceType(@Param("houseAirwayBill") String houseAirwayBill,
                      @Param("languageId") String languageId,
                      @Param("companyId") String companyId);

    @Query(value = "SELECT PIECE_TYP_ID pieceTypeId FROM tblpiecedetails " +
            " WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            " AND C_ID = :companyId " +
            " AND LANG_ID = :languageId " +
            " AND IS_DELETED = 0 ", nativeQuery = true)
    String getPieceTypeId(@Param("houseAirwayBill") String houseAirwayBill,
                        @Param("languageId") String languageId,
                        @Param("companyId") String companyId);

    @Query(value = "SELECT PIECE_TYP_TXT pieceTypeText FROM tblpiecedetails " +
            " WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            " AND C_ID = :companyId " +
            " AND LANG_ID = :languageId " +
            " AND IS_DELETED = 0 ", nativeQuery = true)
    String getPieceTypeDesc(@Param("houseAirwayBill") String houseAirwayBill,
                          @Param("languageId") String languageId,
                          @Param("companyId") String companyId);

    @Query(value = "SELECT HAWB_TYP hawbType FROM tblconsignment_entity " +
            " WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            " AND C_ID = :companyId " +
            " AND LANG_ID = :languageId " +
            " AND IS_DELETED = 0 ", nativeQuery = true)
    String getHawbType(@Param("houseAirwayBill") String houseAirwayBill,
                       @Param("languageId") String languageId,
                       @Param("companyId") String companyId);

    @Query(value = "SELECT HAWB_TYP_ID hawbTypeId FROM tblconsignment_entity " +
            " WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            " AND C_ID = :companyId " +
            " AND LANG_ID = :languageId " +
            " AND IS_DELETED = 0 ", nativeQuery = true)
    String getHawbTypeId(@Param("houseAirwayBill") String houseAirwayBill,
                       @Param("languageId") String languageId,
                       @Param("companyId") String companyId);

    @Query(value = "SELECT HAWB_TYP_TXT hawbTypeText FROM tblconsignment_entity " +
            " WHERE HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            " AND C_ID = :companyId " +
            " AND LANG_ID = :languageId " +
            " AND IS_DELETED = 0 ", nativeQuery = true)
    String getHawbTypeDescription(@Param("houseAirwayBill") String houseAirwayBill,
                         @Param("languageId") String languageId,
                         @Param("companyId") String companyId);

}
