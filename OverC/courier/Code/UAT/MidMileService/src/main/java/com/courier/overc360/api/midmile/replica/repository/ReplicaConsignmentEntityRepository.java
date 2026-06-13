package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentInvoice;
import com.courier.overc360.api.midmile.replica.model.consignment.ConsignmentData;
import com.courier.overc360.api.midmile.replica.model.consignment.Finance;
import com.courier.overc360.api.midmile.replica.model.consignment.FindHouseAirwayBill;
import com.courier.overc360.api.midmile.replica.model.consignment.ReplicaConsignmentEntity;
import com.courier.overc360.api.midmile.replica.model.dto.ConsignmentImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface ReplicaConsignmentEntityRepository extends JpaRepository<ReplicaConsignmentEntity, Long>, JpaSpecificationExecutor<ReplicaConsignmentEntity> {


    @Query(value = "Select \n" +
            "tc.lang_id langId, \n" +
            "tc.lang_text langDesc, \n" +
            "tc.c_name companyDesc " +
            "from tblcompany tc \n" +
            "Where \n " +
            "tc.c_id in (:companyId) and \n" +
            "tc.is_deleted = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "companyId") String companyId);

    @Query(value = "Select \n" +
            "STATUS_ID statusId, \n" +
            "STATUS_TEXT statusDesc \n" +
            "from tblstatus \n" +
            "where \n" +
            "STATUS_ID in (:statusId) and \n" +
            "IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getStatusDescription(@Param(value = "statusId") String statusId);


    @Query(value = "Select " +
            " ts.type_text As statusText \n" +
            " from tblstatusevent ts \n" +
            " Where ts.type_id in (:statusId) and ts.is_deleted = 0", nativeQuery = true)
    String getStatusEventDescription(@Param(value = "statusId") String statusId);

    @Query(value = "Select \n" +
            "CONSIGNMENT_ID consignmentId,\n" +
            "NO_OF_PIECE_HAWB noOfPieceHawb,\n" +
            "PARTNER_HOUSE_AB partnerHouseAirwayBill,\n" +
            "NO_OF_PACKAGE_HAWB noOfPackageHawb,\n" +
            "DECLARED_VALUE declaredValue,\n" +
            "CONSIGNMENT_CURRENCY consignmentCurrency,\n" +
            "INCO_TERMS incoTerms,\n" +
            "COUNTRY_OF_ORIGIN countryOfOrigin,\n" +
            "GROSS_WEIGHT grossWeight,\n" +
            "PAYMENT_TYPE paymentType,\n" +
            "CTD_ON createdOn from tblconsignment_entity where \n" +
            "(COALESCE(:consignmentId, null) IS NULL OR (CONSIGNMENT_ID IN (:consignmentId))) and \n" +
            "(COALESCE(:languageId, null) IS NULL OR (LANG_ID IN (:languageId))) and \n" +
            "(COALESCE(:companyId, null) IS NULL OR (C_ID IN (:companyId))) and \n" +
            "(COALESCE(:partnerId, null) IS NULL OR (PARTNER_ID IN (:partnerId))) and \n" +
            "(COALESCE(:masterAirwayBill, null) IS NULL OR (MASTER_AIRWAY_BILL IN (:masterAirwayBill))) and \n" +
            "(COALESCE(:houseAirwayBill, null) IS NULL OR (HOUSE_AIRWAY_BILL IN (:houseAirwayBill))) and \n" +
            "(COALESCE(:statusId, null) IS NULL OR (STATUS_ID IN (:statusId))) and \n" +
            "(COALESCE(:shipperId, null) IS NULL OR (SHIPPER_ID IN (:shipperId))) and \n" +
            "(COALESCE(:partnerHouseAirwayBill, null) IS NULL OR (PARTNER_HOUSE_AB IN (:partnerHouseAirwayBill))) and \n" +
            "(COALESCE(:partnerMasterAirwayBill, null) IS NULL OR (PARTNER_MASTER_AB IN (:partnerMasterAirwayBill))) and \n" +
            "is_deleted = 0", nativeQuery = true)
    List<ConsignmentImpl> getConsignmentImpl(@Param(value = "consignmentId") List<Long> consignmentId,
                                             @Param(value = "languageId") List<String> languageId,
                                             @Param(value = "companyId") List<String> companyId,
                                             @Param(value = "partnerId") List<String> partnerId,
                                             @Param(value = "masterAirwayBill") List<String> masterAirwayBill,
                                             @Param(value = "houseAirwayBill") List<String> houseAirwayBill,
                                             @Param(value = "statusId") List<String> statusId,
                                             @Param(value = "shipperId") List<String> shipperId,
                                             @Param(value = "partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
                                             @Param(value = "partnerMasterAirwayBill") List<String> partnerMasterAirwayBill);


    @Query(value = "Select \n" +
            "tl.COMPANY_NAME orgName, tl.ADDRESS_LINE_1 orgAddressLine1, tl.ADDRESS_LINE_2 orgAddressLine2, tl.CITY orgCity, tl.COUNTRY orgCountry, tl.PHONE orgPhone, \n" +
            "td.COMPANY_NAME destName, td.ADDRESS_LINE_1 destAddressLine1, td.ADDRESS_LINE_2 destAddressLine2, td.CITY destCity, td.COUNTRY destCountry, td.PHONE destPhone, \n" +
            "ti.HS_CODE hsCode, ti.DESCRIPTION goodsDescription, ti.WEIGHT itemWeight, ti.DECLARED_VALUE unitValue, ti.DECLARED_VALUE totalValue, \n" +
            "tc.NO_OF_PIECE_HAWB quantity, tc.CONSIGNMENT_CURRENCY currency, tc.COUNTRY_OF_ORIGIN countryOfOrigin, tc.INCO_TERMS incoTerms, \n" +
            "tc.NO_OF_PACKAGE_HAWB pieces, tc.GROSS_WEIGHT weight, tc.CTD_ON createdOn, tc.PARTNER_HOUSE_AB awb, tc.CONSIGNMENT_VALUE totalCiv, \n" +
            "CASE WHEN tc.PAYMENT_TYPE = 'prepaid' THEN tc.CONSIGNMENT_VALUE ELSE '0' END AS prepaid \n" +
            "From tblconsignment_entity tc \n" +
            "Join tblitemdetails ti on tc.CONSIGNMENT_ID = ti.CONSIGNMENT_ID \n" +
            "Join tbldestdetails td on tc.CONSIGNMENT_ID = td.DEST_DETAIL_ID \n" +
            "Join tblorigindetails tl on tc.CONSIGNMENT_ID = tl.ORIGIN_ID \n" +
            "Where \n" +
            "(COALESCE(:houseAirwayBill, null) IS NULL OR tc.HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) and \n" +
            "(COALESCE(:partnerHouseAirwayBill, null) IS NULL OR tc.PARTNER_HOUSE_AB IN (:partnerHouseAirwayBill)) and \n" +
            "(COALESCE(:partnerMasterAirwayBill, null) IS NULL OR tc.PARTNER_MASTER_AB IN (:partnerMasterAirwayBill)) and \n" +
            "(COALESCE(:companyId, null) IS NULL OR tc.C_ID IN (:companyId)) and \n" +
            "tc.is_deleted = 0",
            nativeQuery = true)
    List<ConsignmentInvoice> getConsignmentInvoice(@Param("houseAirwayBill") List<String> houseAirwayBill,
                                                   @Param("partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
                                                   @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
                                                   @Param("companyId") List<String> companyId);

    @Query(value = "Select \n" +
            "tl.COMPANY_NAME orgName, concat(tl.address_line_1,',',tl.address_line_2) originAddress, tl.CITY orgCity, tl.COUNTRY orgCountry, tl.PHONE orgPhone, \n" +
            "td.COMPANY_NAME destName, concat(td.address_line_1,',',td.address_line_2) destinationAddress, td.CITY destCity, td.COUNTRY destCountry, td.PHONE destPhone, \n" +
            "tc.CONSIGNMENT_ID consignmentId, tc.HOUSE_AIRWAY_BILL houseAirwayBill, \n" +
            "tc.NO_OF_PIECE_HAWB quantity, tc.CONSIGNMENT_CURRENCY currency, tc.COUNTRY_OF_ORIGIN countryOfOrigin, tc.INCO_TERMS incoTerms, \n" +
            "tc.NO_OF_PACKAGE_HAWB pieces, tc.GROSS_WEIGHT weight, tc.CTD_ON createdOn, tc.PARTNER_HOUSE_AB awb, tc.CONSIGNMENT_VALUE totalCiv, \n" +
            "CASE WHEN tc.PAYMENT_TYPE = 'prepaid' THEN tc.CONSIGNMENT_VALUE ELSE '0' END AS prepaid \n" +
            "From tblconsignment_entity tc \n" +
            "Join tbldestdetails td on tc.CONSIGNMENT_ID = td.DEST_DETAIL_ID \n" +
            "Join tblorigindetails tl on tc.CONSIGNMENT_ID = tl.ORIGIN_ID \n" +
            "Where \n" +
            "(COALESCE(:houseAirwayBill, null) IS NULL OR tc.HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) and \n" +
            "(COALESCE(:partnerHouseAirwayBill, null) IS NULL OR tc.PARTNER_HOUSE_AB IN (:partnerHouseAirwayBill)) and \n" +
            "(COALESCE(:partnerMasterAirwayBill, null) IS NULL OR tc.PARTNER_MASTER_AB IN (:partnerMasterAirwayBill)) and \n" +
            "(COALESCE(:companyId, null) IS NULL OR tc.C_ID IN (:companyId)) and \n" +
            "tc.is_deleted = 0", nativeQuery = true)
    List<ConsignmentInvoice> getConsignmentInvoiceHeader(@Param("houseAirwayBill") List<String> houseAirwayBill,
                                                         @Param("partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
                                                         @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
                                                         @Param("companyId") List<String> companyId);

//    @Query(value = "Select \n" +
//            "ti.HS_CODE hsCode, ti.DESCRIPTION goodsDescription, ti.WEIGHT itemWeight, ti.DECLARED_VALUE unitValue, ti.DECLARED_VALUE totalValue \n" +
//            "From tblitemdetails ti \n" +
//            "Where \n" +
//            "ti.CONSIGNMENT_ID IN (:consignmentId) and \n" +
//            "ti.is_deleted = 0", nativeQuery = true)
//    List<ConsignmentInvoice> getConsignmentInvoiceLine(@Param("consignmentId") Long consignmentId);

    @Query(value = "Select \n" +
            "ti.HS_CODE hsCode, ti.DESCRIPTION goodsDescription, ti.WEIGHT itemWeight, ti.DECLARED_VALUE unitValue, ti.DECLARED_VALUE totalValue \n" +
            "From tblitemdetails ti \n" +
            "Where \n" +
            "(COALESCE(:masterAirwayBill, null) IS NULL OR ti.MASTER_AIRWAY_BILL IN (:masterAirwayBill)) and \n" +
            "(COALESCE(:houseAirwayBill, null) IS NULL OR ti.HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) and \n" +
            "ti.is_deleted = 0", nativeQuery = true)
    List<ConsignmentInvoice> getConsignmentInvoiceLine(@Param("masterAirwayBill") String masterAirwayBill,
                                                       @Param("houseAirwayBill") String houseAirwayBill);


    @Query(value = "select MASTER_AIRWAY_BILL as masterAirwayBill from tblconsignment_entity " +
            " where LANG_ID in (:languageId) and C_ID in (:companyId) and PARTNER_HOUSE_AB in (:partnerHouseAB)" +
            " and partner_id in (:partnerId) and is_deleted = 0 ", nativeQuery = true)
    public String getMasterAirwayBill(@Param("languageId") String languageId,
                                      @Param("companyId") String companyId,
                                      @Param("partnerId") String partnerId,
                                      @Param("partnerHouseAB") String partnerHouseAB);


    List<ReplicaConsignmentEntity> findByLanguageIdAndCompanyIdAndHouseAirwayBillAndHawbTypeIdAndDeletionIndicator(
            String languageId, String companyId, String houseAirwayBill, String hawbTypeId, Long deletionIndicator);

    List<ReplicaConsignmentEntity> findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHawbTypeIdAndDeletionIndicator(
            String languageId, String companyId, String partnerHouseAirwayBill, String hawbTypeId, Long deletionIndicator);

    List<ReplicaConsignmentEntity> findByLanguageIdAndCompanyIdAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String houseAirwayBill, Long deletionIndicator);

    List<ReplicaConsignmentEntity> findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerHouseAirwayBill, Long deletionIndicator);

    // Fetch Consignments with given Params Only
//    @Query(value = "Select * From tblconsignment_entity tc\n" +
//            "Where tc.IS_DELETED=0\n" +
//            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
//            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
//            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID IN (:partnerId))\n" +
//            "AND (COALESCE(:masterAirwayBill, NULL) IS NULL OR tc.MASTER_AIRWAY_BILL IN (:masterAirwayBill))\n" +
//            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tc.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))", nativeQuery = true)
//    List<ReplicaConsignmentEntity> fetchConsignmentsWithQry(@Param(value = "languageId") List<String> languageId,
//                                                            @Param(value = "companyId") List<String> companyId,
//                                                            @Param(value = "partnerId") List<String> partnerId,
//                                                            @Param(value = "masterAirwayBill") List<String> masterAirwayBill,
//                                                            @Param(value = "houseAirwayBill") List<String> houseAirwayBill);

    @Query(value = "SELECT * FROM tblconsignment_entity tc " +
            "WHERE tc.IS_DELETED=0 " +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId)) " +
            "AND (COALESCE(:companyId,NULL) IS NULL OR tc.C_ID IN (:companyId)) " +
            "AND (COALESCE(:partnerId,NULL) IS NULL OR tc.PARTNER_ID IN (:partnerId)) " +
            "AND (COALESCE(:masterAirwayBill,NULL) IS NULL OR tc.MASTER_AIRWAY_BILL IN (:masterAirwayBill)) " +
            "AND (COALESCE(:houseAirwayBill,NULL) IS NULL OR tc.HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) " +
            "AND (COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) IS NULL OR " +
            "(CTD_ON between COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) " +
            "AND COALESCE(CONVERT(VARCHAR(225), :toDate), NULL))) " +
            "ORDER BY tc.CONSIGNMENT_ID " +
            "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<ReplicaConsignmentEntity> fetchConsignmentsWithQry(@Param("languageId") List<String> languageId,
                                                            @Param("companyId") List<String> companyId,
                                                            @Param("partnerId") List<String> partnerId,
                                                            @Param("masterAirwayBill") List<String> masterAirwayBill,
                                                            @Param("houseAirwayBill") List<String> houseAirwayBill,
                                                            @Param("fromDate") Date fromDate,
                                                            @Param("toDate") Date toDate,
                                                            @Param("limit") int limit,
                                                            @Param("offset") int offset);


    @Query(value = "SELECT TOP 1 COUNTRY_TEXT countryName, \n" +
            "CITY_NAME cityName, \n" +
            "DISTRICT_TEXT districtName, \n " +
            "PROVINCE_TEXT provinceName \n " +
            "FROM tblcity \n " +
            "WHERE IS_DELETED = 0 \n " +
            "AND (COALESCE(:cityId, NULL) IS NULL OR CITY_ID IN (:cityId)) \n" +
            "AND (COALESCE(:countryId, NULL) IS NULL OR COUNTRY_ID IN (:countryId)) \n " +
            "AND (COALESCE(:districtId, NULL) IS NULL OR DISTRICT_ID IN (:districtId)) \n" +
            "AND (COALESCE(:provinceId, NULL) IS NULL OR PROVINCE_ID IN (:provinceId))", nativeQuery = true)
    public IKeyValuePair getDescription(@Param("cityId") String cityId,
                                        @Param("countryId") String countryId,
                                        @Param("districtId") String districtId,
                                        @Param("provinceId") String provinceId);

    @Query(value = "SELECT count(*) FROM tblconsignment_entity WHERE IS_DELETED = 0 AND HAWB_TYP_ID = '12' \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId)", nativeQuery = true)
    Long getInscanCount(String companyId, String languageId);

    @Query(value = "SELECT count(*) FROM tblconsignment_entity WHERE IS_DELETED = 0 AND HAWB_TYP_ID = '51' \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId)", nativeQuery = true)
    Long getReceiveCount(String companyId, String languageId);

    @Query(value = "SELECT count(*) FROM tblconsignment_entity WHERE IS_DELETED = 0 AND HAWB_TYP_ID = '50' \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId)", nativeQuery = true)
    Long getReceiveCountOutScan(String companyId, String languageId);

    @Query(value = "SELECT * FROM tblconsignment_entity WHERE IS_DELETED = 0 AND HAWB_TYP_ID = '12' \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId)", nativeQuery = true)
    List<ReplicaConsignmentEntity> getInscanData(String languageId, String companyId);

    @Query(value = "SELECT * from tblconsignment_entity \n" +
            "where HAWB_TYP_ID = '51' and is_deleted = 0", nativeQuery = true)
    List<ReplicaConsignmentEntity> getConsignmentWithStatus();

    @Query(value = "SELECT * FROM tblconsignment_entity WHERE IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID = :languageId) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID = :companyId) \n" +
            "AND (COALESCE(:hawbTypeId, NULL) IS NULL OR HAWB_TYP_ID = :hawbTypeId) \n" +
            "AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR PARTNER_HOUSE_AB = :partnerHouseAirwayBill)", nativeQuery = true)
    List<ReplicaConsignmentEntity> getReceivingConsignment(@Param("companyId") String companyId,
                                                           @Param("languageId") String languageId,
                                                           @Param("hawbTypeId") String hawbTypeId,
                                                           @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill);

    @Query(value = "SELECT * FROM tblconsignment_entity tr \n" +
            "WHERE tr.IS_DELETED=0 " +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))\n" +
            "AND (COALESCE(:statusId, NULL) IS NULL OR tr.STATUS_ID IN (:statusId)) \n" +
            "AND (COALESCE(:pickupId,NULL) IS NULL OR tr.PICKUP_ID IN (:pickupId))", nativeQuery = true)
    List<ReplicaConsignmentEntity> getPieceDetails(@Param("languageId") List<String> languageId,
                                                   @Param("companyId") List<String> companyId,
                                                   @Param("houseAirwayBill") List<String> houseAirwayBill,
                                                   @Param("statusId") List<String> statusId,
                                                   @Param("pickupId") List<String> pickupId);


    @Query(value = "SELECT HOUSE_AIRWAY_BILL houseAirwayBill, PARTNER_ID partnerId, PARTNER_NAME partnerName FROM tblconsignment_entity \n" +
            "WHERE IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId))\n" +
            "AND (COALESCE(:statusId, NULL) IS NULL OR STATUS_ID IN (:statusId))\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) IS NULL OR \n" +
            "(CTD_ON between COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) \n" +
            "AND COALESCE(CONVERT(VARCHAR(225), :toDate), NULL)))", nativeQuery = true)
    List<FindHouseAirwayBill> getHouseAirwayBillList(@Param("languageId") String languageId,
                                                     @Param("companyId") String companyId,
                                                     @Param("statusId") String statusId,
                                                     @Param("partnerId") String partnerId,
                                                     @Param("fromDate") Date fromDate,
                                                     @Param("toDate") Date toDate);


    @Procedure(name = "fetchConsignmentsWithQryProc")
    List<ReplicaConsignmentEntity> fetchConsignmentsWithQryProc(
            @Param("languageId") String languageId,
            @Param("companyId") String companyId,
            @Param("partnerId") String partnerId,
            @Param("masterAirwayBill") String masterAirwayBill,
            @Param("houseAirwayBill") String houseAirwayBill,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = "Select HUB_NAME from tblhub Where HUB_CODE = :hubCode and is_deleted = 0", nativeQuery = true)
    String getHubName(@Param("hubCode") String hubCode);

    @Query(value = "select max(aging_count) from tblcustomer where " +
            "customer_id = :partnerId and is_deleted = 0", nativeQuery = true)
    Long agingCount(@Param("partnerId") String partnerId);

    @Query(value = "SELECT * FROM tblconsignment_entity WHERE " +
            " IS_DELETED = 0 " +
            " AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) " +
            " AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) " +
            " AND (COALESCE(:hubCode, NULL) IS NULL OR HUB_CODE IN (:hubCode)) " +
            " AND (COALESCE(:statusId, NULL) IS NULL OR HAWB_TYP_ID IN (:statusId)) ", nativeQuery = true)
    List<ReplicaConsignmentEntity> getAllConsignmentData(@Param("languageId") List<String> languageId,
                                                         @Param("companyId") List<String> companyId,
                                                         @Param("hubCode") List<String> hubCode,
                                                         @Param("statusId") List<String> statusId);


    @Query(value = "SELECT ASSIGNED_HUB_CODE FROM tblappuser WHERE IS_DELETED = 0 AND LANG_ID = :languageId AND C_ID = :companyId AND APP_USER_ID = :userId", nativeQuery = true)
    String assignedName(@Param("languageId") String languageId,
                               @Param("companyId") String companyId,
                               @Param("userId") String userId);

    @Query(value = "SELECT HOUSE_AIRWAY_BILL houseAirwayBill, C_ID companyId, LANG_ID languageId, PARTNER_ID partnerId, " +
            "CEILING_VALUE ceilingValue, CHARGEABLE_WEIGHT chargeableWeight, FRIGHT_CHARGE frightCharge, COD_CHARGE codCharge, " +
            "FULFILMENT_CHARGE fulfilmentCharge, RTO_CHARGE rtoCharge, ASR_CHARGE asrCharge, MOVEMENT_CHARGE movementCharge, " +
            "TRUCK_CHARGE truckCharge, PAYMENT_COLLECTED paymentCollected, TOTAL_LMD_CHARGES totalLmdCharges, OUTBOUND_CLEARANCE outboundClearance," +
            "ADD_ORIGIN_DETAILS addOriginDetails, ADD_DESTINATION_DETAILS addDestinationDetails, GOODS_DESCRIPTION goodsDescription, " +
            "HS_CODE hsCode, NO_OF_PIECE_HAWB noOfPieceHawb, WEIGHT weight, INCO_TERMS incoTerms " +
            "FROM tblconsignment_entity " +
            "WHERE IS_DELETED = 0 " +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) " +
            "AND (COALESCE(:companyId,NULL) IS NULL OR C_ID IN (:companyId)) " +
            "AND (COALESCE(:partnerId,NULL) IS NULL OR PARTNER_ID IN (:partnerId)) " +
            "AND (COALESCE(:masterAirwayBill,NULL) IS NULL OR MASTER_AIRWAY_BILL IN (:masterAirwayBill)) " +
            "AND (COALESCE(:houseAirwayBill,NULL) IS NULL OR HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) " +
            "AND (COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) IS NULL OR " +
            "(CTD_ON between COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) " +
            "AND COALESCE(CONVERT(VARCHAR(225), :toDate), NULL))) ", nativeQuery = true)
    List<Finance> findFiance(@Param("languageId") List<String> languageId,
                             @Param("companyId") List<String> companyId,
                             @Param("partnerId") List<String> partnerId,
                             @Param("masterAirwayBill") List<String> masterAirwayBill,
                             @Param("houseAirwayBill") List<String> houseAirwayBill,
                             @Param("fromDate") Date fromDate,
                             @Param("toDate") Date toDate);

    @Query(value = "SELECT CONSIGNMENT_ID consignmentId, LANG_ID languageId, LANG_TEXT languageDescription, C_ID companyId, " +
            "C_NAME companyName, PARTNER_ID partnerId, PARTNER_NAME partnerName, MASTER_AIRWAY_BILL masterAirwayBill, HOUSE_AIRWAY_BILL houseAirwayBill, " +
            "NO_OF_PIECE_HAWB noOfPieceHawb, PARTNER_MASTER_AB partnerMasterAirwayBill, PARTNER_HOUSE_AB partnerHouseAirwayBill, PRODUCT_ID productId, " +
            "PRODUCT_NAME productName, SUB_PRODUCT_ID subProductId, SUB_PRODUCT_NAME subProductName, SERVICE_TYPE_ID serviceTypeId, SERVICE_TYPE_TEXT serviceTypeText, " +
            "INCO_TERMS incoTerms, CUSTOMER_REFERENCE_NUMBER customerReferenceNumber, HAWB_TYP hawbType, HAWB_TYP_ID hawbTypeId, HAWB_TYP_TXT hawbTypeDescription, HAWB_TIMESTAMP hawbTimeStamp, " +
            "ADD_ORIGIN_DETAILS addOriginDetails, ADD_DESTINATION_DETAILS addDestinationDetails, PAYMENT_TYPE paymentType, CTD_BY createdBy, CTD_ON createdOn " +
            "FROM tblconsignment_entity " +
            "WHERE IS_DELETED = 0 " +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) " +
            "AND (COALESCE(:companyId,NULL) IS NULL OR C_ID IN (:companyId)) " +
            "AND (COALESCE(:partnerId,NULL) IS NULL OR PARTNER_ID IN (:partnerId)) " +
            "AND (COALESCE(:masterAirwayBill,NULL) IS NULL OR MASTER_AIRWAY_BILL IN (:masterAirwayBill)) " +
            "AND (COALESCE(:houseAirwayBill,NULL) IS NULL OR HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) " +
            "AND (COALESCE(:hawbTypeId,NULL) IS NULL OR HAWB_TYP_ID IN (:hawbTypeId)) " +
            "AND (COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) IS NULL OR " +
            "(CTD_ON between COALESCE(CONVERT(VARCHAR(225), :fromDate), NULL) " +
            "AND COALESCE(CONVERT(VARCHAR(225), :toDate), NULL))) ", nativeQuery = true)
     List<ConsignmentData> getConsignment(@Param("languageId") List<String> languageId,
                                          @Param("companyId") List<String> companyId,
                                          @Param("partnerId") List<String> partnerId,
                                          @Param("masterAirwayBill") List<String> masterAirwayBill,
                                          @Param("houseAirwayBill") List<String> houseAirwayBill,
                                          @Param("hawbTypeId") List<String> hawbTypeId,
                                          @Param("fromDate") Date fromDate,
                                          @Param("toDate") Date toDate);


    @Procedure(name = "GetConsignmentList")
    List<ConsignmentData> getConsignmentList();

}