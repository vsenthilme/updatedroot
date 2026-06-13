package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.dto.ConsignmentImpl;
import com.courier.overc360.api.midmile.replica.model.prealert.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaPreAlertRepository extends JpaRepository<ReplicaPreAlert, String>, JpaSpecificationExecutor<ReplicaPreAlert> {

    Optional<ReplicaPreAlert> findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
            String companyId, String languageId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, Long deletionIndicator);


    // Get Partner Name
    @Query(value = "Select Top 1 tc.PARTNER_NAME as partnerName, \n" +
            "ADD_DESTINATION_DETAILS as addDest, \n " +
            "ADD_ORIGIN_DETAILS as addOrigin \n " +
            "From tblconsignment_entity tc\n" +
            "Where tc.IS_DELETED=0\n" +
            "AND tc.LANG_ID = :languageId\n" +
            "AND tc.C_ID = :companyId\n" +
            "AND tc.PARTNER_ID = :partnerId\n" +
//            "AND tc.PARTNER_MASTER_AB = :partnerMasterAirwayBill\n" +
            "AND tc.PARTNER_HOUSE_AB = :partnerHouseAirwayBill", nativeQuery = true)
    Optional<IKeyValuePair> getPartnerName(
            @Param("languageId") String languageId,
            @Param("companyId") String companyId,
            @Param("partnerId") String partnerId,
//            @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
            @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill);


    // Get No of Shipments Scanned
    @Query(value = "Select COUNT(*) From tblprealert tp\n" +
            "Where tp.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tp.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tp.C_ID IN (:companyId))\n" +
//            "AND (COALESCE(:partnerId, NULL) IS NULL OR tp.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR tp.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill))\n" +
            "AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR tp.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill))\n" +
            "And (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (tp.CTD_ON between COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL)))", nativeQuery = true)
    long getNoOfShipmentsScanned(
            @Param("languageId") String languageId,
            @Param("companyId") String companyId,
//            @Param("partnerId") String partnerId,
            @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
            @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);


    // Get All PARTNER_MASTER_AIRWAY_BILL count
    @Query(value = "SELECT tp.PARTNER_MASTER_AIRWAY_BILL As partnerMasterAirwayBill, COUNT(*) AS pMawbCount\n" +
            "FROM tblprealert tp\n" +
            "WHERE tp.IS_DELETED = 0\n" +
            "And tp.HAWB_TYP_ID != 9\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tp.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tp.C_ID IN (:companyId))\n" +
            "GROUP BY tp.PARTNER_MASTER_AIRWAY_BILL", nativeQuery = true)
    List<ConsignmentImpl> getAllPMawbCount(@Param("languageId") String languageId,
                                           @Param("companyId") String companyId);

    @Query(value = "SELECT tp.PARTNER_MASTER_AIRWAY_BILL As partnerMasterAirwayBill, tp.LANG_ID AS languageId, tp.C_ID AS companyId, COUNT(*) AS pMawbCount\n" +
            "FROM tblprealert tp\n" +
            "WHERE tp.IS_DELETED = 0\n" +
            "AND tp.HAWB_TYP_ID != 9\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tp.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tp.C_ID IN (:companyId))\n" +
            "GROUP BY tp.PARTNER_MASTER_AIRWAY_BILL, tp.LANG_ID, tp.C_ID", nativeQuery = true)
    List<ConsignmentImpl> getAllPMawbCount(@Param("languageId") List<String> languageId,
                                           @Param("companyId") List<String> companyId);


    boolean existsByLanguageIdAndCompanyIdAndDeletionIndicator(
            String languageId, String companyId, Long deletionIndicator);


    @Query(value = "SELECT p.C_ID AS companyId, p.LANG_ID AS languageId, p.PARTNER_ID AS partnerId, " +
            "p.MASTER_AIRWAY_BILL AS masterAirwayBill, p.HOUSE_AIRWAY_BILL AS houseAirwayBill, " +
            "p.PARTNER_HOUSE_AIRWAY_BILL AS partnerHouseAirwayBill, p.PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill, " +
            "p.C_NAME AS companyName, p.LANG_TEXT AS languageDescription, p.TOTAL_WEIGHT AS totalWeight, " +
            "p.FLIGHT_NO AS flightNo, p.CONSOLE_INDICATOR AS consoleIndicator, " +
            "p.CON_VALUE_LOCAL AS consignmentValueLocal, p.MANIFEST_INDICATOR AS manifestIndicator, " +
            "p.FLIGHT_NAME AS flightName, p.ESTIMATED_TIME_OF_DEPARTURE AS estimatedTimeOfDeparture, " +
            "p.ESTIMATED_TIME_OF_ARRIVAL AS estimatedTimeOfArrival, p.NO_OF_PIECES AS noOfPieces, " +
            "p.CONSIGNMENT_VALUE AS consignmentValue, p.EXCHANGE_RATE AS exchangeRate, p.IATA AS iata, " +
            "p.CUSTOMS_INSURANCE AS customsInsurance, p.DUTY AS duty, p.ADD_IATA AS addIata, p.ADD_INSURANCE AS addInsurance, " +
            "p.CUSTOMS_VALUE AS customsValue, p.CALCULATED_TOTAL_DUTY AS calculatedTotalDuty, p.BAYAN_HV AS bayanHv, " +
            "p.CURRENCY AS currency, p.DESCRIPTION AS description, p.CONSIGNEE_NAME AS consigneeName, " +
            "p.SHIPPER AS shipper, p.ORIGIN AS origin, p.ORIGIN_CODE AS originCode, p.HS_CODE AS hsCode, " +
            "p.PARTNER_TYPE AS partnerType, p.PARTNER_NAME AS partnerName, p.INCO_TERM AS incoTerm, " +
            "p.HAWB_TYP AS hawbType, p.HAWB_TYP_ID AS hawbTypeId, p.HAWB_TYP_TXT AS hawbTypeDescription, " +
            "p.HAWB_TIMESTAMP AS hawbTimeStamp, p.CON_LOCAL_ID AS consignmentLocalId, " +
            "p.ADD_DESTINATION_DETAILS as addDestinationDetails, p.ADD_ORIGIN_DETAILS as addOriginDetails, " +
            "p.HUB_CODE AS hubCode, p.HUB_NAME AS hubName, p.IS_DELETED AS deletionIndicator, " +
            "p.LABOURS AS labours, p.OTHER_CHARGES AS otherCharges, p.OTHERS AS others, " +
            "p.CUSTOM_DUTY AS customDuty, p.SPECIAL_APPROVALS AS specialApprovals, p.APPROVALS AS approvals, " +
            "p.TOTAL_COST_PER_SHIPMENT AS totalCostPerShipment, p.TOTAL_VALUE_SHIPMENT AS totalValueShipment, " +
            "p.NAS_DELIVERY AS nasDelivery, p.GLOBAL AS global, p.HANDLING_FORK AS handlingFork, p.STAMP_CHARGES AS stampCharges, " +
            "p.HANDLING_FEES AS handlingFees, p.CLEARANCE_CHARGE AS clearanceCharge," +
            "p.CONSIGNEE_PH_NO AS consigneePhoneNo, p.REF_FIELD_1 AS referenceField1, p.REF_FIELD_2 AS referenceField2, " +
            "p.REF_FIELD_3 AS referenceField3, p.REF_FIELD_4 AS referenceField4, " +
            "p.REF_FIELD_5 AS referenceField5, p.REF_FIELD_6 AS referenceField6, " +
            "p.REF_FIELD_7 AS referenceField7, p.REF_FIELD_8 AS referenceField8, " +
            "p.REF_FIELD_9 AS referenceField9, p.REF_FIELD_10 AS referenceField10, " +
            "p.CTD_BY AS createdBy, p.CTD_ON AS createdOn, p.UTD_BY AS updatedBy, " +
            "p.UTD_ON AS updatedOn, p.SUB_CUSTOMER_ID AS subCustomerId, " +
            "p.SUB_CUSTOMER_NAME AS subCustomerName,p.INVOICE AS invoice,p.DDP_INVOICE_NO AS ddpInvoiceNo, " +
            "p.ORIGIN_FLIGHT_COUNTRY AS originFlightCountry, counts.countHawb " +
            "FROM tblprealert p \n" +
            "JOIN (\n" +
            "    SELECT PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill,\n" +
            "           COUNT(HOUSE_AIRWAY_BILL) AS countHawb\n" +
            "    FROM tblprealert\n" +
            "    WHERE IS_DELETED = 0\n" +
            "    AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId))\n" +
            "    AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId))\n" +
            "    AND (COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID IN (:partnerId))\n" +
            "    AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill))\n" +
            "    AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill))\n" +
//            "    AND (COALESCE(:hsCode, NULL) IS NULL OR HS_CODE IN (:hsCode))\n" +
//            "    AND (COALESCE(:invoice, NULL) IS NULL OR INVOICE IN (:invoice))\n " +
            "    GROUP BY PARTNER_MASTER_AIRWAY_BILL\n" +
            ") counts ON p.PARTNER_MASTER_AIRWAY_BILL = counts.partnerMasterAirwayBill\n" +
            "WHERE p.IS_DELETED = 0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR p.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR p.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR p.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR p.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill))\n" +
            "AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR p.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill))\n" +
            "AND (COALESCE(:hsCode, NULL) IS NULL OR p.HS_CODE IN (:hsCode))\n" +
            "AND (COALESCE(:invoice, NULL) IS NULL OR p.INVOICE IN (:invoice))\n " +
            "AND (COALESCE(:invoiceNo, NULL) IS NULL OR p.DDP_INVOICE_NO IN (:invoiceNo))\n" +
            "AND (COALESCE(:subCustomerId, NULL) IS NULL OR p.SUB_CUSTOMER_ID IN (:subCustomerId))\n" +
            "AND (COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) IS NULL OR \n" +
            " (p.CTD_ON between COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) \n" +
            " AND COALESCE(CONVERT(VARCHAR(225), :toDate), NULL)))", nativeQuery = true)
    public List<ReplicaPreAlertProjection> findPreAlertWithHawb(
            @Param("companyId") List<String> companyId,
            @Param("languageId") List<String> languageId,
            @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
            @Param("partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
            @Param("partnerId") List<String> partnerId,
            @Param("hsCode") List<String> hsCode,
            @Param("invoice") String invoice,
            @Param("invoiceNo") List<String> invoiceNo,
            @Param("subCustomerId") List<String> subCustomerId,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);



    // CalculatedCount
    @Query(value = "select partner_master_airway_bill partnerMasterAirwayBill ,count(house_airway_bill) noOfShipments, MAX(SHIPPER) shipper, \n " +
    "sum(consignment_value) totalConsignmentValue, MAX(CURRENCY) currency, \n" +
    "sum(customs_value) totalCustomsValue, MAX(IATA) iata, MAX(CUSTOMS_INSURANCE) customsInsurance, \n" +
    "sum(CALCULATED_TOTAL_DUTY) totalDutyValue \n" +
    "from tblprealert where IS_DELETED = 0 group by PARTNER_MASTER_AIRWAY_BILL", nativeQuery = true)
    public List<CustomsCalculationReport> getCalculatedCount();

    // Count for No of Shipments
    @Query(value = "SELECT COUNT(house_airway_bill) AS noOfShipments " +
            "FROM tblprealert " +
            "WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND IS_DELETED = 0 " +
            "GROUP BY partner_master_airway_bill",
            nativeQuery = true)
    Long noOfShipments(@Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);


    @Query(value = "SELECT partner_master_airway_bill partnerMasterAB, " +
            "COUNT(house_airway_bill) shipment, " +
            "MAX(PARTNER_ID) partnerId, " +
            "MAX(c_id) companyId, " +
            "MAX(lang_id) langId " +
            "FROM tblprealert " +
            "WHERE (:fromDate IS NULL OR ctd_on >= CAST(:fromDate AS datetime2)) AND " +
            "      (:toDate IS NULL OR ctd_on <= CAST(:toDate AS datetime2)) AND " +
            "      (coalesce(:partnerMasterAB, NULL) IS NULL OR partner_master_airway_bill IN (:partnerMasterAB)) AND " +
            "      (coalesce(:partnerId, NULL) IS NULL OR SUB_CUSTOMER_ID = :partnerId) AND is_deleted = 0 AND invoice = 0 AND inco_term = 'DDP' " +
            "GROUP BY partner_master_airway_bill", nativeQuery = true)
    public List<IKeyValuePair> getPartnerMaster(@Param("fromDate") Date fromDate,
                                                @Param("toDate") Date toDate,
                                                @Param("partnerMasterAB") List<String> partnerMasterAB,
                                                @Param("partnerId") String partnerId);

//    @Query(value = "SELECT partner_master_airway_bill AS partnerMasterAB, " +
//            "COUNT(house_airway_bill) AS shipment, " +
//            "MAX(partner_id) AS partnerId, " +
//            "MAX(c_id) AS companyId, " +
//            "MAX(lang_id) AS langId " +
//            "FROM tblprealert " +
//            "WHERE (ctd_on >= CASE WHEN :fromDate IS NOT NULL THEN CAST(:fromDate AS datetime2) ELSE '1900-01-01' END) AND " +
//            "      (ctd_on <= CASE WHEN :toDate IS NOT NULL THEN CAST(:toDate AS datetime2) ELSE GETDATE() END) AND " +
//            "      (COALESCE(:partnerMasterAB, NULL) IS NULL OR partner_master_airway_bill IN (:partnerMasterAB)) AND " +
//            "      (COALESCE(:partnerId, NULL) IS NULL OR partner_id = :partnerId) AND is_deleted = 0 " +
//            "GROUP BY partner_master_airway_bill", nativeQuery = true)
//    public List<IKeyValuePair> getPartnerMaster(@Param("fromDate") Date fromDate,
//                                                @Param("toDate") Date toDate,
//                                                @Param("partnerMasterAB") List<String> partnerMasterAB,
//                                                @Param("partnerId") String partnerId);

//    @Query(value = "SELECT partner_master_airway_bill AS partnerMasterAB, " +
//            "COUNT(house_airway_bill) AS shipment, " +
//            "MAX(partner_id) AS partnerId, " +
//            "MAX(c_id) AS companyId, " +
//            "MAX(lang_id) AS langId " +
//            "FROM tblprealert " +
//            "WHERE " +
//            "(COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (CTD_ON between COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL)))" +
//            "      (COALESCE(:partnerMasterAB, NULL) IS NULL OR partner_master_airway_bill IN (:partnerMasterAB)) AND " +
//            "      (COALESCE(:partnerId, NULL) IS NULL OR partner_id = :partnerId) AND is_deleted = 0 " +
//            "GROUP BY partner_master_airway_bill", nativeQuery = true)
//    public List<IKeyValuePair> getPartnerMaster(@Param("fromDate") Date fromDate,
//                                                @Param("toDate") Date toDate,
//                                                @Param("partnerMasterAB") List<String> partnerMasterAB,
//                                                @Param("partnerId") String partnerId);

//    @Query(value = "SELECT partner_master_airway_bill AS partnerMasterAB, " +
//            "COUNT(house_airway_bill) AS shipment, " +
//            "MAX(partner_id) AS partnerId, " +
//            "MAX(c_id) AS companyId, " +
//            "MAX(lang_id) AS langId " +
//            "FROM tblprealert " +
//            "WHERE " +
//            "      (:fromDate IS NULL OR (CTD_ON BETWEEN :fromDate AND :toDate)) AND " +
//            "      (:partnerMasterAB IS NULL OR partner_master_airway_bill IN (:partnerMasterAB)) AND " +
//            "      (:partnerId IS NULL OR partner_id = :partnerId) AND " +
//            "      is_deleted = 0 " +
//            "GROUP BY partner_master_airway_bill", nativeQuery = true)
//    public List<IKeyValuePair> getPartnerMaster(@Param("fromDate") Date fromDate,
//                                                @Param("toDate") Date toDate,
//                                                @Param("partnerMasterAB") List<String> partnerMasterAB,
//                                                @Param("partnerId") String partnerId);


    // Count for No of Shipments - Approve custom costing
    @Query(value = "SELECT C_ID,LANG_ID,SUB_CUSTOMER_ID,PARTNER_MASTER_AIRWAY_BILL,count(PARTNER_HOUSE_AIRWAY_BILL) shipments " +
            "FROM tblprealert " +
            "WHERE partner_master_airway_bill in :partnerMasterAirwayBill AND C_ID in :companyId AND LANG_ID in :languageId AND IS_DELETED = 0 " +
            "GROUP BY PARTNER_MASTER_AIRWAY_BILL,C_ID,LANG_ID,SUB_CUSTOMER_ID",nativeQuery = true)
    List<String[]> shipmentsCount(@Param("languageId") List<String> languageId,
                                  @Param("companyId") List<String> companyId,
                                  @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill);



    @Query(value = "SELECT LANG_ID languageId, C_ID companyId, PARTNER_ID partnerId, PARTNER_NAME partnerName, " +
            "PARTNER_MASTER_AIRWAY_BILL partnerMasterAirwayBill, PARTNER_HOUSE_AIRWAY_BILL partnerHouseAirwayBill, " +
            "SUB_CUSTOMER_ID subCustomerId, SUB_CUSTOMER_NAME subCustomerName, INVOICE invoice, DDP_INVOICE_NO ddpInvoiceNo FROM tblprealert \n" +
            "WHERE IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill))\n" +
            "AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill))\n" +
            "AND (COALESCE(:hsCode, NULL) IS NULL OR HS_CODE IN (:hsCode))\n" +
            "AND (COALESCE(:invoice, NULL) IS NULL OR INVOICE IN (:invoice))\n " +
            "AND (COALESCE(:invoiceNo, NULL) IS NULL OR DDP_INVOICE_NO IN (:invoiceNo))\n" +
            "AND (COALESCE(:subCustomerId, NULL) IS NULL OR SUB_CUSTOMER_ID IN (:subCustomerId))\n" +
            "AND (COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) IS NULL OR \n" +
            " (CTD_ON between COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) \n" +
            " AND COALESCE(CONVERT(VARCHAR(225), :toDate), NULL)))", nativeQuery = true)
    List<PreAlertResponse> findPreAlertNew(@Param("companyId") List<String> companyId,
                                           @Param("languageId") List<String> languageId,
                                           @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
                                           @Param("partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
                                           @Param("partnerId") List<String> partnerId,
                                           @Param("hsCode") List<String> hsCode,
                                           @Param("invoice") String invoice,
                                           @Param("invoiceNo") List<String> invoiceNo,
                                           @Param("subCustomerId") List<String> subCustomerId,
                                           @Param("fromDate") Date fromDate,
                                           @Param("toDate") Date toDate);


    @Query(value = "Select top 1 SUB_CUSTOMER_ID as subCustomerId,SUB_CUSTOMER_NAME as subCustomerName from tblprealert where IS_DELETED = 0 AND \n" +
            "(COALESCE(:partnerMasterAirwayBill,NULL) IS NULL OR PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill)", nativeQuery = true)
    IKeyValuePair findSubcustomerId(@Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);
}