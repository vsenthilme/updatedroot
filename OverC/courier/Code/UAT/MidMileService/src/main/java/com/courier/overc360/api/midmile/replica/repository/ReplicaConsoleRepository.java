package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.console.Console;
import com.courier.overc360.api.midmile.replica.model.console.ConsoleProjection;
import com.courier.overc360.api.midmile.primary.model.console.MobileApp;
import com.courier.overc360.api.midmile.replica.model.console.ConsoleImpl;
import com.courier.overc360.api.midmile.replica.model.console.ReplicaConsole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface ReplicaConsoleRepository extends JpaRepository<ReplicaConsole, String>,
        JpaSpecificationExecutor<ReplicaConsole> {

    Optional<ReplicaConsole> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, String consoleId, Long deletionIndicator);

    // Duplicate Header Check
    @Query(value = "Select \n" +
            "Case When Exists \n" +
            "(Select 1 From tblconsole h \n" +
            "Where \n" +
            "h.LANG_ID = :languageId and \n" +
            "h.C_ID = :companyId and \n" +
            "h.PARTNER_ID = :partnerId and \n" +
            "h.Partner_Master_Airway_Bill = :partnerMasterAirwayBill and \n" +
            "h.PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAirwayBill and \n" +
            "h.IS_DELETED =0) \n" +
            "Then 1 \n" +
            "Else 0 \n" +
            "End", nativeQuery = true)
    Long duplicateExists(@Param(value = "languageId") String languageId,
                         @Param(value = "companyId") String companyId,
                         @Param(value = "partnerId") String partnerId,
                         @Param(value = "partnerMasterAirwayBill") String partnerMasterAirwayBill,
                         @Param(value = "partnerHouseAirwayBill") String partnerHouseAirwayBill);


    @Query(value = "select count(special_approval_id) from tblhscode where " +
            "c_id in (:companyId) and hs_code in (:hsCode) and is_deleted = 0", nativeQuery = true)
     Long getSpecialApproval(@Param(value = "companyId") String companyId,
                                     @Param(value = "hsCode") String hsCode);

    @Query(value = "select special_approval_id, special_approval_text  from tblhscode where " +
            "c_id in (:companyId) and hs_code in (:hsCode) and is_deleted = 0", nativeQuery = true)
    List<Object[]> getSpecialApprovalList(@Param(value = "companyId") String companyId,
                                        @Param(value = "hsCode") String hsCode);

    @Query(value = "SELECT DISTINCT(HS_CODE) hsCode, C_ID companyCode from tblconsole where " +
            "IS_DELETED = 0 AND CONSOLE_ID = :console", nativeQuery = true)
    List<Object[]> getHsCode(@Param(value = "console") String console);

    // Find Consoles with given Params Only
    @Query(value = "SELECT * FROM tblconsole tc\n" +
            "WHERE tc.IS_DELETED = 0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR tc.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill))\n" +
            "AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR tc.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill))\n" +
            "AND (COALESCE(:consoleId, NULL) IS NULL OR tc.CONSOLE_ID IN (:consoleId))\n" +
            "AND (COALESCE(:unconsolidatedIndicator, NULL) IS NULL OR tc.UNCONSOLIDATED IN (:unconsolidatedIndicator)) \n " +
            "AND (COALESCE(:hawbTypeId, NULL) IS NULL OR tc.HAWB_TYP_ID IN (:hawbTypeId))", nativeQuery = true)
    List<ReplicaConsole> findConsolesWithQry(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("partnerId") List<String> partnerId,
            @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
            @Param("partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
            @Param("consoleId") List<String> consoleId,
            @Param("unconsolidatedIndicator") List<Long> unconsolidatedIndicator,
            @Param("hawbTypeId") List<String> hawbTypeId);


//    @Query(value = "SELECT t.PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill, " +
//            "t.PARTNER_ID AS partnerId, " +
//            "t.PARTNER_NAME AS partnerName, " +
//            "t.CTD_ON AS createdOn " +
//            "FROM tblconsole t " +
//            "INNER JOIN ( " +
//            "    SELECT PARTNER_MASTER_AIRWAY_BILL, PIECE_ID as pieceId, MAX(CTD_ON) AS max_date " +
//            "    FROM tblconsole " +
//            "    WHERE HAWB_TYP_ID = 45 AND HAWB_TYP = 'EVENT' AND is_deleted = 0 " +
//            "    GROUP BY PARTNER_MASTER_AIRWAY_BILL, PIECE_ID" +
//            ") sub ON t.PARTNER_MASTER_AIRWAY_BILL = sub.PARTNER_MASTER_AIRWAY_BILL AND t.CTD_ON = sub.max_date " +
//            " AND t.PIECE_ID = sub.pieceId " +
//            "WHERE t.HAWB_TYP_ID = 45 AND HAWB_TYP = 'EVENT' AND t.is_deleted = 0",
//            nativeQuery = true)
//    List<MobileApp> getMobileApp();

    @Query(value = "SELECT PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill, MAX(CTD_ON) AS createdOn, MAX(PARTNER_ID) AS partnerId, MAX(PARTNER_NAME) AS partnerName \n" +
            "FROM tblconsole \n" +
            "WHERE HAWB_TYP_ID = 45 AND HAWB_TYP = 'EVENT' AND is_deleted = 0 \n" +
            "GROUP BY PARTNER_MASTER_AIRWAY_BILL ",
            nativeQuery = true)
    List<MobileApp> getMobileApp();

    @Query(value = "select invoice_type invoiceType, " +
            " invoice_number invoiceNumber, " +
            " invoice_date invoiceDate from tblconsignment_entity where " +
            " partner_house_ab = :partnerHouseAB and " +
            " partner_master_ab = :partnerMasterAB and " +
            " partner_id = :partnerId and " +
            " c_id = :companyId and " +
            " lang_id = :languageId and " +
            " is_deleted = 0 ", nativeQuery = true)
    Optional<IKeyValuePair> getInvoice(@Param("companyId") String companyId,
                                       @Param("languageId") String languageId,
                                       @Param("partnerId") String partnerId,
                                       @Param("partnerHouseAB") String partnerHouseAB,
                                       @Param("partnerMasterAB") String partnerMasterAB);

    @Query(value = "SELECT SUM(TOTAL_DUTY) FROM tblconsole " +
            "WHERE IS_DELETED = 0 AND PARTNER_MASTER_AIRWAY_BILL = :partnerMAB AND INCO_TERMS = 'DDP' " +
            "GROUP BY PARTNER_MASTER_AIRWAY_BILL", nativeQuery = true)
    Double getTotalDuty(@Param("partnerMAB")String partnerMAB);

    List<Console> findByConsoleIdAndDeletionIndicator(String consoleId, Long deletionIndicator);


    // Get Mobile Dashboard Count
    @Query(value = "Select COUNT(*) From (\n" +
            "Select COUNT(*) As consoleCount\n" +
            "From tblconsole tc \n" +
            "Where tc.IS_DELETED=0\n" +
            "And tc.LANG_ID = :languageId\n" +
            "And tc.C_ID = :companyId\n" +
            "And tc.HAWB_TYP_ID = :hawbTypeId\n" +
            "Group By tc.PARTNER_MASTER_AIRWAY_BILL\n" +
            ") x", nativeQuery = true)
    long getMobileDashboardCount(@Param("languageId") String languageId,
                                 @Param("companyId") String companyId,
                                 @Param("hawbTypeId") String hawbTypeId);

    boolean existsByLanguageIdAndCompanyIdAndPartnerMasterAirwayBillAndConsoleIdAndDeletionIndicator(
            String languageId, String companyId, String partnerMasterAirwayBill, String consoleId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndPartnerMasterAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerMasterAirwayBill, Long deletionIndicator);


    // Get total Sum of NetWeight and TotalQuantity
//    @Query(value = "SELECT\n" +
//            "COALESCE(SUM(TRY_CONVERT(float, tc.NET_WEIGHT)), 0) AS totalWeight,\n" +
//            "COALESCE(SUM(TRY_CONVERT(bigint, tc.TOTAL_QUANTITY)), 0) AS totalQuantity\n" +
//            "FROM tblconsole tc\n" +
//            "WHERE tc.IS_DELETED = 0\n" +
//            "AND tc.LANG_ID = :languageId\n" +
//            "AND tc.C_ID = :companyId\n" +
//            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID = :partnerId) \n" +
//            "AND (COALESCE(:consoleId, NULL) IS NULL OR tc.CONSOLE_ID = :consoleId) \n" +
////            "AND tc.CONSOLE_ID = :consoleId\n" +
//            "AND tc.PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAB", nativeQuery = true)
//    ConsoleImpl getSumValues(@Param("languageId") String languageId,
//                             @Param("companyId") String companyId,
//                             @Param("partnerId") String partnerId,
//                             @Param("consoleId") String consoleId,
//                             @Param("partnerMasterAB") String partnerMasterAB);


    @Query(value = "SELECT \n" +
            "tc.CONSOLE_ID As consoleId,\n" +
            "MAX(tc.LANG_TEXT) As langDesc, \n" +
            "MAX(tc.C_NAME) As companyDesc,\n" +
            "MAX(tc.PARTNER_ID) As partnerId,\n" +
            "MAX(tc.PARTNER_NAME) As partnerName,\n" +
            "MAX(tc.PARTNER_TYPE) As partnerType,\n" +
            "MAX(tc.CONSIGNEE_NAME) As consigneeName,\n" +
            "MAX(tc.MASTER_AIRWAY_BILL) As masterAirwayBill,\n" +
            "MAX(tc.COUNTRY_OF_ORIGIN) As origin,\n" +
            "MAX(tc.AIRPORT_ORIGIN_CODE) As airportCode,\n" + // airportOriginCode
            "MAX(tc.REF_FIELD_1) As referenceField1, \n" +
            "MAX(tc.ORIGIN_FLIGHT_COUNTRY) As originFlightCountry, \n" +
            "COALESCE(SUM(CAST(tc.NET_WEIGHT AS decimal(18, 2))), 0) AS totalWeight, \n" +
            "COALESCE(SUM(TRY_CONVERT(bigint, tc.TOTAL_QUANTITY)), 0) AS totalQuantity\n" +
            "FROM tblconsole tc\n" +
            "WHERE tc.IS_DELETED = 0\n" +
            "AND tc.LANG_ID = :languageId\n" +
            "AND tc.C_ID = :companyId\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID = :partnerId)\n" +
            "AND (COALESCE(:consoleId, NULL) IS NULL OR tc.CONSOLE_ID = :consoleId)\n" +
            "AND tc.PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAB\n" +
            "GROUP BY tc.CONSOLE_ID", nativeQuery = true)
    List<ConsoleImpl> getSumValuesGroupedByConsoleId(@Param("languageId") String languageId,
                                                     @Param("companyId") String companyId,
                                                     @Param("partnerId") String partnerId,
                                                     @Param("consoleId") String consoleId,
                                                     @Param("partnerMasterAB") String partnerMasterAB);


    // Get Location Sheet values
    @Query(value = "Select Top 1 \n" +
            "tc.LANG_TEXT As langDesc, \n" +
            "tc.C_NAME As companyDesc,\n" +
            "tc.PARTNER_NAME As partnerName,\n" +
            "tc.PARTNER_TYPE As partnerType,\n" +
            "tc.CONSIGNEE_NAME As consigneeName,\n" +
//            "tc.MASTER_AIRWAY_BILL As masterAirwayBill,\n" +
            "tc.COUNTRY_OF_ORIGIN As origin\n" +
            "From tblconsole tc\n" +
            "Where tc.IS_DELETED=0\n" +
            "AND tc.LANG_ID = :languageId\n" +
            "AND tc.C_ID = :companyId\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID = :partnerId) \n" +
            "AND (COALESCE(:consoleId, NULL) IS NULL OR tc.CONSOLE_ID = :consoleId) \n" +
//            "AND tc.CONSOLE_ID = :consoleId\n" +
            "AND tc.PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAB", nativeQuery = true)
    ConsoleImpl getLocationSheetValues(@Param("languageId") String languageId,
                                       @Param("companyId") String companyId,
                                       @Param("partnerId") String partnerId,
                                       @Param("consoleId") String consoleId,
                                       @Param("partnerMasterAB") String partnerMasterAB);


    // Get No of Consoles
    @Query(value = "Select COUNT(*) From tblconsole tc\n" +
            "Where tc.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
//            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:partnerMasterAB, NULL) IS NULL OR tc.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAB))\n" +
            "AND (COALESCE(:partnerHouseAB, NULL) IS NULL OR tc.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAB))\n" +
            "And tc.UNCONSOLIDATED = :unconsolidatedIndicator\n" +
            "And (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (tc.CTD_ON between COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL)))", nativeQuery = true)
    long getNoOfConsoles(@Param("languageId") String languageId,
                         @Param("companyId") String companyId,
//                         @Param("partnerId") String partnerId,
                         @Param("partnerMasterAB") String partnerMasterAB,
                         @Param("partnerHouseAB") String partnerHouseAB,
                         @Param("unconsolidatedIndicator") Long unconsolidatedIndicator,
                         @Param("fromDate") Date fromDate,
                         @Param("toDate") Date toDate);


    // Get Scanning Officer and Time
    @Query(value = "Select Top 1\n" +
            "tc.SCANNED_BY As scannedBy,\n" +
            "tc.SCANNED_ON As scannedOn\n" +
            "From tblconsole tc\n" +
            "Where tc.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
//            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:partnerMasterAB, NULL) IS NULL OR tc.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAB))\n" +
            "AND (COALESCE(:partnerHouseAB, NULL) IS NULL OR tc.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAB))\n" +
//            "And tc.REF_FIELD_10 = 'SCAN'\n" +
            "ORDER BY tc.SCANNED_ON DESC", nativeQuery = true)
    ConsoleImpl getScanValues(@Param("languageId") String languageId,
                              @Param("companyId") String companyId,
//                              @Param("partnerId") String partnerId,
                              @Param("partnerMasterAB") String partnerMasterAB,
                              @Param("partnerHouseAB") String partnerHouseAB);

    boolean existsByLanguageIdAndCompanyIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerMasterAirwayBill, String partnerHouseAirwayBill, Long deletionIndicator);


    // Get Consoles Count by P-MAWB
    @Query(value = "SELECT COUNT(*) FROM tblconsole tc\n" +
            "WHERE tc.IS_DELETED = 0\n" +
            "And tc.UNCONSOLIDATED = 0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerMasterAB, NULL) IS NULL OR tc.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAB))", nativeQuery = true)
    long getConsoleCountByPMawb(@Param("languageId") String languageId,
                                @Param("companyId") String companyId,
                                @Param("partnerMasterAB") String partnerMasterAB);

    @Query(value = "SELECT tc.PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill, tc.LANG_ID AS languageId, tc.C_ID AS companyId, COUNT(*) AS count\n" +
            "FROM tblconsole tc\n" +
            "WHERE tc.IS_DELETED = 0\n" +
            "AND tc.UNCONSOLIDATED = 0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
            "GROUP BY tc.PARTNER_MASTER_AIRWAY_BILL, tc.LANG_ID, tc.C_ID", nativeQuery = true)
    List<Object[]> getConsoleCountByPMawb(@Param("languageId") List<String> languageId,
                                          @Param("companyId") List<String> companyId);


    @Query(value = "SELECT\n" +
            "    CONSOLE_ID AS consoleId,\n" +
            "    LANG_ID AS languageId,\n" +
            "    C_ID AS companyId,\n" +
            "    PARTNER_ID AS partnerId,\n" +
            "    PARTNER_HOUSE_AIRWAY_BILL AS partnerHouseAirwayBill,\n" +
            "    PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill,\n" +
            "    PIECE_ID AS pieceId,\n" +
            "    HOUSE_AIRWAY_BILL AS houseAirwayBill,\n" +
            "    MASTER_AIRWAY_BILL AS masterAirwayBill,\n" +
            "    C_NAME AS companyName,\n" +
            "    CCR_ID AS ccrId,\n" +
            "    CONSIGNMENT_CURRENCY AS consignmentCurrency,\n" +
            "    CONSIGNMENT_VALUE AS consignmentValue,\n" +
            "    CONSOLE_NAME AS consoleName,\n" +
            "    CONSOLE_GROUP_NAME AS consoleGroupName,\n" +
            "    EXCHANGE_RATE AS exchangeRate,\n" +
            "    IATA AS iata,\n" +
            "    CUSTOMS_INSURANCE AS customsInsurance,\n" +
            "    DUTY AS duty,\n" +
            "    CONSIGNMENT_VALUE_LOCAL AS consignmentValueLocal,\n" +
            "    ADD_IATA AS addIata,\n" +
            "    ADD_INSURANCE AS addInsurance,\n" +
            "    CUSTOMS_VALUE AS customsValue,\n" +
            "    CALCULATED_TOTAL_DUTY AS calculatedTotalDuty,\n" +
            "    LANG_TEXT AS languageDescription,\n" +
            "    PARTNER_TYPE AS partnerType,\n" +
            "    PARTNER_NAME AS partnerName,\n" +
            "    PRIMARY_DO AS primaryDo,\n" +
            "    SECONDARY_DO AS secondaryDo,\n" +
            "    NO_OF_PACKAGE_MAWB AS noOfPackageMawb,\n" +
            "    NO_OF_PIECE_HAWB AS noOfPieceHawb,\n" +
            "    BONDED_ID AS bondedId,\n" +
            "    EXPECTED_DUTY AS expectedDuty,\n" +
            "    CUSTOMS_CURRENCY AS customsCurrency,\n" +
            "    DESCRIPTION AS description,\n" +
            "    NET_WEIGHT AS netWeight,\n" +
            "    MANIFESTED_GROSS_WEIGHT AS manifestedGrossWeight,\n" +
            "    GROSS_WEIGHT AS grossWeight,\n" +
            "    TARE_WEIGHT AS tareWeight,\n" +
            "    MANIFESTED_QUANTITY AS manifestedQuantity,\n" +
            "    LANDED_QUANTITY AS landedQuantity,\n" +
            "    TOTAL_QUANTITY AS totalQuantity,\n" +
            "    VOLUME AS volume,\n" +
            "    FINAL_DESTINATION AS finalDestination,\n" +
            "    NOTIFY_PARTY AS notifyParty,\n" +
            "    NO_OF_PIECES AS noOfPieces,\n" +
            "    PAYMENT_TYPE AS paymentType,\n" +
            "    IS_EXEMPTED AS isExempted,\n" +
            "    EXEMPTION_FOR AS exemptionFor,\n" +
            "    EXEMPTION_BENEFICIARY AS exemptionBeneficiary,\n" +
            "    EXEMPTION_REFERENCE AS exemptionReference,\n" +
            "    SHIPMENT_BAG_ID AS shipmentBagId,\n" +
            "    DUTY_PERCENTAGE AS dutyPercentage,\n" +
            "    IATA_CHARGE AS iataCharge,\n" +
            "    DDU_CHARGE AS dduCharge,\n" +
            "    SPECIAL_APPROVAL_CHARGE AS specialApprovalCharge,\n" +
            "    CONSIGNEE_NAME AS consigneeName,\n" +
            "    SHIPPER_NAME AS shipperName,\n" +
            "    REMARKS AS remarks,\n" +
            "    IS_CONSOLIDATED_SHIPMENT AS isConsolidatedShipment,\n" +
            "    IS_SPLIT_BILL_OF_LADING AS isSplitBillOfLading,\n" +
            "    IS_PENDING_SHIPMENT AS isPendingShipment,\n" +
            "    GOODS_TYPE AS goodsType,\n" +
            "    COUNTRY_OF_ORIGIN AS countryOfOrigin,\n" +
            "    ACTUAL_CURRENCY AS actualCurrency,\n" +
            "    ACTUAL_VALUE AS actualValue,\n" +
            "    SPECIAL_APPROVAL_VALUE AS specialApprovalValue,\n" +
            "    AIRPORT_ORIGIN_CODE AS airportOriginCode,\n" +
            "    CUSTOMS_KD AS customsKd,\n" +
            "    TOTAL_DUTY AS totalDuty,\n" +
            "    PRODUCT_ID AS productId,\n" +
            "    PRODUCT_NAME AS productName,\n" +
            "    SUB_PRODUCT_ID AS subProductId,\n" +
            "    SUB_PRODUCT_NAME AS subProductName,\n" +
            "    SERVICE_TYPE_ID AS serviceTypeId,\n" +
            "    SERVICE_TYPE_TEXT AS serviceTypeName,\n" +
            "    SHIPPER_ID AS shipperId,\n" +
            "    CONSIGNEE_CIVIL_ID AS consigneeCivilId,\n" +
            "    INCO_TERMS AS incoTerm,\n" +
            "    INVOICE_NUMBER AS invoiceNumber,\n" +
            "    INVOICE_DATE AS invoiceDate,\n" +
            "    INVOICE_TYPE AS invoiceType,\n" +
            "    INVOICE_SUPPLIER_NAME AS invoiceSupplierName,\n" +
            "    HS_CODE AS hsCode,\n" +
            "    GOODS_DESCRIPTION AS goodsDescription,\n" +
            "    QUANTITY AS quantity,\n" +
            "    FREIGHT_CURRENCY AS freightCurrency,\n" +
            "    FREIGHT_CHARGES AS freightCharges,\n" +
            "    DECLARED_VALUE AS declaredValue,\n" +
            "    CURRENCY AS currency,\n" +
            "    HUB_CODE AS hubCode,\n" +
            "    HUB_NAME AS hubName,\n" +
            "    IS_DELETED AS deletionIndicator,\n" +
            "    CON_LOCAL_ID AS consignmentLocalId,\n" +
            "    HAWB_TYP AS hawbType,\n" +
            "    HAWB_TYP_ID AS hawbTypeId,\n" +
            "    HAWB_TYP_TXT AS hawbTypeDescription,\n" +
            "    HAWB_TIMESTAMP AS hawbTimeStamp,\n" +
            "    PIECE_TYP AS pieceType,\n" +
            "    PIECE_TYP_ID AS pieceTypeId,\n" +
            "    PIECE_TYP_TXT AS pieceTypeDescription,\n" +
            "    PIECE_TIMESTAMP AS pieceTimeStamp,\n" +
            "    CUSTOMS_CCR_NO AS customsCcrNo,\n" +
            "    FLIGHT_NAME AS flightName,\n" +
            "    FLIGHT_NO AS flightNo,\n" +
            "    SUB_CUSTOMER_ID AS subCustomerId,\n" +
            "    SUB_CUSTOMER_NAME AS subCustomerName,\n" +
            "    LINE_NUMBER AS lineNumber,\n" +
            "    REF_FIELD_1 AS referenceField1,\n" +
            "    REF_FIELD_2 AS referenceField2,\n" +
            "    REF_FIELD_3 AS referenceField3,\n" +
            "    REF_FIELD_4 AS referenceField4,\n" +
            "    REF_FIELD_5 AS referenceField5,\n" +
            "    REF_FIELD_6 AS referenceField6,\n" +
            "    REF_FIELD_7 AS referenceField7,\n" +
            "    REF_FIELD_8 AS referenceField8,\n" +
            "    REF_FIELD_9 AS referenceField9,\n" +
            "    REF_FIELD_10 AS referenceField10,\n" +
            "    CTD_BY AS createdBy,\n" +
            "    CTD_ON AS createdOn,\n" +
            "    UTD_BY AS updatedBy,\n" +
            "    UTD_ON AS updatedOn,\n" +
            "    SCANNED_BY AS scannedBy,\n" +
            "    SCANNED_ON AS scannedOn,\n" +
            "    UNCONSOLIDATED AS unconsolidatedFlag," +
            "    counts.countConsole, \n" +
            "    fees.handlingFee \n " +
            "FROM tblconsole c \n" +
            "JOIN ( \n" +
            "    SELECT \n" +
            "    PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill,\n" +
            "    COUNT(DISTINCT CONSOLE_ID) AS countConsole\n" +
            "    FROM tblconsole\n" +
            "    WHERE IS_DELETED = 0\n" +
            "    AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId))\n" +
            "    AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId))\n" +
            "    AND (COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID IN (:partnerId))\n" +
            "    AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill))\n" +
            "    AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill))\n" +
            "    AND (COALESCE(:consoleId, NULL) IS NULL OR CONSOLE_ID IN (:consoleId))\n" +
            "    AND (COALESCE(:unconsolidatedFlag, NULL) IS NULL OR UNCONSOLIDATED IN (:unconsolidatedFlag))\n" +
            "    AND (COALESCE(:subCustomerId, NULL) IS NULL OR SUB_CUSTOMER_ID IN (:subCustomerId)) \n" +
            "    AND (COALESCE(:hawbTypeId, NULL) IS NULL OR HAWB_TYP_ID IN (:hawbTypeId))\n" +
            "    GROUP BY PARTNER_MASTER_AIRWAY_BILL\n" +
            ") counts ON counts.partnerMasterAirwayBill = c.PARTNER_MASTER_AIRWAY_BILL \n" +
            " LEFT JOIN ( \n " +
            " SELECT \n " +
            " CLEARANCE_FEE handlingFee, \n" +
            " LANG_ID languageId, \n " +
            " C_ID companyId \n " +
            " FROM tblcustomcharges \n " +
            " WHERE IS_DELETED = 0 \n " +
            " AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) \n " +
            " AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) \n " +
            " ) fees \n " +
            " ON fees.languageId = c.LANG_ID \n " +
            " AND fees.companyId = c.C_ID \n " +
            "WHERE c.IS_DELETED = 0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR c.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR c.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR c.PARTNER_ID IN (:partnerId)) \n" +
            "AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR c.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill)) \n" +
            "AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR c.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill)) \n" +
            "AND (COALESCE(:consoleId, NULL) IS NULL OR c.CONSOLE_ID IN (:consoleId)) \n" +
            "AND (COALESCE(:unconsolidatedFlag, NULL) IS NULL OR c.UNCONSOLIDATED IN (:unconsolidatedFlag)) \n" +
            "AND (COALESCE(:hawbTypeId, NULL) IS NULL OR c.HAWB_TYP_ID IN (:hawbTypeId))", nativeQuery = true)
    List<ConsoleProjection> findConsolidatedData(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("partnerId") List<String> partnerId,
            @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
            @Param("partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
            @Param("hawbTypeId") List<String> hawbTypeId,
            @Param("unconsolidatedFlag") List<Long> unconsolidatedFlag,
            @Param("consoleId") List<String> consoleId,
            @Param("subCustomerId") List<String> subCustomerId);


    // Get Shipment
    @Query(value = "select top 1 no_of_shipments as shipment, \n " +
            "  consignment_value as consignmentValue from tbllogicmaster \n " +
            " where c_id in (:companyId) and \n" +
            " lang_id in (:languageId) and \n " +
            " is_deleted = 0 ", nativeQuery = true)
    IKeyValuePair getShipment(@Param("companyId") String companyId,
                              @Param("languageId") String languageId);


}
