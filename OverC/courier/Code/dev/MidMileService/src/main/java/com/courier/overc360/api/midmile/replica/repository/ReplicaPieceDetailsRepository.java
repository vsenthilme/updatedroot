package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.dto.LabelFormOutput;
import com.courier.overc360.api.midmile.replica.model.dto.PieceDetailsImpl;
import com.courier.overc360.api.midmile.replica.model.piecedetails.ReplicaPieceDetails;
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
public interface ReplicaPieceDetailsRepository extends JpaRepository<ReplicaPieceDetails, String>, JpaSpecificationExecutor<ReplicaPieceDetails> {

    Optional<ReplicaPieceDetails> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator
            (String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, Long deletionIndicator);


    @Query(value = "Select PIECE_ID as pieceId from tblpiecedetails where " +
            "lang_id = :languageId and c_id = :companyId and partner_id = :partnerId and " +
            " PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAB and " +
            " PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAB and " +
            " is_deleted = 0 ", nativeQuery = true)
    List<String> getPieceId(@Param(value = "languageId") String languageId,
                            @Param(value = "companyId") String companyId,
                            @Param(value = "partnerId") String partnerId,
                            @Param(value = "partnerHouseAB") String partnerHouseAB,
                            @Param(value = "partnerMasterAB") String partnerMasterAB);

    @Query(value = "Select PIECE_ID as pieceId from tblpiecedetails where " +
            "lang_id = :languageId and c_id = :companyId and partner_id = :partnerId and " +
            " PARTNER_HOUSE_AIRWAY_BILL = :partnerHouseAB and " +
            " is_deleted = 0 ", nativeQuery = true)
    List<String> getPieceId(@Param(value = "languageId") String languageId,
                            @Param(value = "companyId") String companyId,
                            @Param(value = "partnerId") String partnerId,
                            @Param(value = "partnerHouseAB") String partnerHouseAB);

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
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId);

    @Query(value = "Select \n" +
            "PARTNER_HOUSE_AIRWAY_BILL partnerHouseAirwayBill,\n" +
            "DESCRIPTION description,\n" +
            "DECLARED_VALUE declaredValue,\n" +
            "WEIGHT weight,\n" +
            "HS_CODE hsCode,\n" +
            "CONSIGNMENT_ID consignmentId from tblpiecedetails where \n" +
            "CONSIGNMENT_ID = :consignmentId and \n" +
            "is_deleted = 0", nativeQuery = true)
    List<PieceDetailsImpl> getPieceDetailsImpl(@Param(value = "consignmentId") Long consignmentId);

//    List<ReplicaPieceDetails> findByLanguageIdAndCompanyIdAndConsignmentIdAndDeletionIndicator(
//            String languageId, String companyId, Long consignmentId, Long deletionIndicator);

    @Query(value = "Select * From tblpiecedetails tp\n" +
            "Where tp.IS_DELETED=0\n" +
            "And tp.LANG_ID = :languageId\n" +
            "And tp.C_ID = :companyId\n" +
            "And tp.CONSIGNMENT_ID = :consignmentId", nativeQuery = true)
    List<ReplicaPieceDetails> getAllPieceDetails(@Param(value = "languageId") String languageId,
                                                 @Param(value = "companyId") String companyId,
                                                 @Param(value = "consignmentId") Long consignmentId);

    @Query(value = " \n" +
            "CREATE TABLE #LFO \n" +
            "(Label_ID int IDENTITY(1,1) PRIMARY KEY, \n" +
            "CONSIGNMENT_ID bigint, \n" +
            "CUSTOMER_REFERENCE_NUMBER nvarchar(50), \n" +
            "HOUSE_AIRWAY_BILL nvarchar(50), \n" +
            "COUNTRY_OF_ORIGIN nvarchar(50), \n" +
            "PARTNER_NAME nvarchar(50), \n" +
            "INCO_TERMS nvarchar(50), \n" +
            "SERVICE_TYPE_TEXT nvarchar(50), \n" +
            "TOTAL_DUTY nvarchar(50), \n" +
            "NO_OF_PIECE_HAWB nvarchar(50), \n" +
            "CONSIGNMENT_CURRENCY nvarchar(50), \n" +
            "PARTNER_HOUSE_AB nvarchar(50), \n" +
            "GROSS_WEIGHT nvarchar(50), \n" +
            "MODE_OF_TRANSPORT nvarchar(50), \n" +
            "INSURANCE nvarchar(50), \n" +
            "COD nvarchar(10), \n" +
            "LOAD_TYPE nvarchar(50), \n" +
            "PIECE_ID nvarchar(50), \n" +
            "PIECE_PRODUCT_CODE nvarchar(50), \n" +
            "PIECE_VALUE nvarchar(50), \n" +
            "TAGS nvarchar(50), \n" +
            "GOODS_TYPE nvarchar(3000), \n" +
            "ORG_NAME nvarchar(50), \n" +
            "ORG_PHONE nvarchar(50), \n" +
            "ORG_ALTERNATE_PHONE nvarchar(50), \n" +
            "ORG_ADDRESS nvarchar(3000), \n" +
            "ORG_CITY nvarchar(50), \n" +
            "ORG_STATE nvarchar(50), \n" +
            "ORG_COUNTRY nvarchar(50), \n" +
            "DST_NAME nvarchar(50), \n" +
            "DST_PHONE nvarchar(50), \n" +
            "DST_ALTERNATE_PHONE nvarchar(50), \n" +
            "DST_ADDRESS nvarchar(3000), \n" +
            "DST_CITY nvarchar(50), \n" +
            "DST_STATE nvarchar(50), \n" +
            "DST_COUNTRY nvarchar(50),\n" +
            "DATE dateTime); \n" +

            "INSERT INTO #LFO(CONSIGNMENT_ID, PIECE_ID, PIECE_PRODUCT_CODE, PIECE_VALUE, TAGS, DATE) \n" +
            "SELECT CONSIGNMENT_ID, PIECE_ID, PIECE_PRODUCT_CODE, PIECE_VALUE, TAGS, :date FROM tblpiecedetails where is_deleted = 0 and \n" +
            "(COALESCE(:piecesId, null) IS NULL OR (PIECE_ID IN (:piecesId))) and \n" +
            "(COALESCE(:houseAirwayBill, null) IS NULL OR (HOUSE_AIRWAY_BILL IN (:houseAirwayBill))) and \n" +
            "(COALESCE(:languageId, null) IS NULL OR (LANG_ID IN (:languageId))) and \n" +
            "(COALESCE(:companyId, null) IS NULL OR (C_ID IN (:companyId)))\n" +

            "select CONSIGNMENT_ID into #cgmt FROM tblpiecedetails where is_deleted = 0 and \n" +
            "(COALESCE(:piecesId, null) IS NULL OR (PIECE_ID IN (:piecesId))) and \n" +
            "(COALESCE(:houseAirwayBill, null) IS NULL OR (HOUSE_AIRWAY_BILL IN (:houseAirwayBill))) and \n" +
            "(COALESCE(:languageId, null) IS NULL OR (LANG_ID IN (:languageId))) and \n" +
            "(COALESCE(:companyId, null) IS NULL OR (C_ID IN (:companyId)))\n" +
            "group by CONSIGNMENT_ID \n" +

            "UPDATE TH SET TH.GOODS_TYPE = X.NAME FROM #LFO TH INNER JOIN \n" +
            "(select PIECE_ID,STRING_AGG(DESCRIPTION,',') Name from tblitemdetails where is_deleted = 0 group by PIECE_ID) x  \n" +
            "ON th.PIECE_ID = x.PIECE_ID \n" +

            "UPDATE TH SET TH.DST_NAME = X.NAME, th.DST_PHONE = x.PHONE, th.DST_ALTERNATE_PHONE = x.ALTERNATE_PHONE, th.DST_ADDRESS = x.address, \n" +
            "th.DST_CITY = x.CITY, th.DST_STATE = x.state, th.DST_COUNTRY = x.COUNTRY FROM #LFO TH INNER JOIN \n" +
            "(SELECT NAME, PHONE, ALTERNATE_PHONE, concat(address_line_1,',',address_line_2) address, city, state, country, DEST_DETAIL_ID  \n" +
            "FROM tbldestdetails where DEST_DETAIL_ID in (select CONSIGNMENT_ID from #cgmt)) x ON th.CONSIGNMENT_ID = x.DEST_DETAIL_ID \n" +

            "UPDATE TH SET TH.ORG_NAME = X.NAME, th.ORG_PHONE = x.PHONE, th.ORG_ALTERNATE_PHONE = x.ALTERNATE_PHONE, th.ORG_ADDRESS = x.address, \n" +
            "th.ORG_CITY = x.CITY, th.ORG_STATE = x.state, th.ORG_COUNTRY = x.COUNTRY FROM #LFO TH INNER JOIN \n" +
            "(SELECT NAME, PHONE, ALTERNATE_PHONE, concat(address_line_1,',',address_line_2) address, city, state, country, ORIGIN_ID  \n" +
            "FROM tblorigindetails where ORIGIN_ID in (select CONSIGNMENT_ID from #cgmt)) x ON th.CONSIGNMENT_ID = x.ORIGIN_ID \n" +

            "UPDATE TH SET TH.CUSTOMER_REFERENCE_NUMBER = X.CUSTOMER_REFERENCE_NUMBER, th.HOUSE_AIRWAY_BILL = x.HOUSE_AIRWAY_BILL, \n" +
            "th.COUNTRY_OF_ORIGIN = x.COUNTRY_OF_ORIGIN, th.PARTNER_NAME = x.PARTNER_NAME, th.INCO_TERMS = x.INCO_TERMS, \n" +
            "th.SERVICE_TYPE_TEXT = x.SERVICE_TYPE_TEXT, th.TOTAL_DUTY = x.TOTAL_DUTY, th.NO_OF_PIECE_HAWB = x.NO_OF_PIECE_HAWB, \n" +
            "th.CONSIGNMENT_CURRENCY = x.CONSIGNMENT_CURRENCY, th.PARTNER_HOUSE_AB = x.PARTNER_HOUSE_AB, th.GROSS_WEIGHT = x.GROSS_WEIGHT, \n" +
            "th.MODE_OF_TRANSPORT = x.MODE_OF_TRANSPORT, th.INSURANCE = x.INSURANCE, th.COD = x.cod \n" +
            "FROM #LFO TH INNER JOIN \n" +
            "(SELECT CUSTOMER_REFERENCE_NUMBER, HOUSE_AIRWAY_BILL, COUNTRY_OF_ORIGIN, PARTNER_NAME, INCO_TERMS, SERVICE_TYPE_TEXT, \n" +
            "TOTAL_DUTY, NO_OF_PIECE_HAWB, CONSIGNMENT_CURRENCY, PARTNER_HOUSE_AB, GROSS_WEIGHT, MODE_OF_TRANSPORT, INSURANCE, \n" +
            "(case when PAYMENT_TYPE = 'cod' then 'Yes' else 'No' end) cod, \n" +
            "CONSIGNMENT_ID FROM tblconsignment_entity \n" +
            "where is_deleted = 0 and CONSIGNMENT_ID in (select CONSIGNMENT_ID from #cgmt) \n" +
            ") x ON th.CONSIGNMENT_ID = x.CONSIGNMENT_ID \n" +

            "select \n" +
            "CONSIGNMENT_ID consignmentId, \n" +
            "CUSTOMER_REFERENCE_NUMBER customerReferenceNumber, \n" +
            "HOUSE_AIRWAY_BILL houseAirwayBill, \n" +
            "COUNTRY_OF_ORIGIN countryOfOrigin, \n" +
            "PARTNER_NAME partnerName, \n" +
            "INCO_TERMS incoTerms, \n" +
            "SERVICE_TYPE_TEXT serviceTypeText, \n" +
            "TOTAL_DUTY totalDuty, \n" +
            "NO_OF_PIECE_HAWB noOfPieceHawb, \n" +
            "CONSIGNMENT_CURRENCY consignmentCurrency, \n" +
            "PARTNER_HOUSE_AB partnerHouseAirwayBill, \n" +
            "GROSS_WEIGHT grossWeight, \n" +
            "MODE_OF_TRANSPORT modeOfTransport, \n" +
            "INSURANCE insurance, \n" +
            "COD cod, \n" +
            "TAGS tags, \n" +
            "GOODS_TYPE goodsType, \n" +
            "LOAD_TYPE loadType, \n" +
            "PIECE_ID pieceId, \n" +
            "PIECE_PRODUCT_CODE pieceProductCode, \n" +
            "PIECE_VALUE pieceValue, \n" +
            "ORG_NAME originName, \n" +
            "ORG_PHONE originPhone, \n" +
            "ORG_ALTERNATE_PHONE originAlternatePhone, \n" +
            "ORG_ADDRESS originAddress, \n" +
            "ORG_CITY originCity, \n" +
            "ORG_STATE originState, \n" +
            "ORG_COUNTRY originCountry, \n" +
            "DST_NAME destinationName, \n" +
            "DST_PHONE destinationPhone, \n" +
            "DST_ALTERNATE_PHONE destinationAlternatePhone, \n" +
            "DST_ADDRESS destinationAddress, \n" +
            "DST_CITY destinationCity, \n" +
            "DST_STATE destinationState, \n" +
            "DST_COUNTRY destinationCountry, \n" +
            "DATE createdOn \n" +
            "from #LFO ", nativeQuery = true)
    List<LabelFormOutput> getLabelPdfOutput(@Param(value = "languageId") List<String> languageId,
                                            @Param(value = "companyId") List<String> companyId,
                                            @Param(value = "piecesId") List<String> piecesId,
                                            @Param(value = "houseAirwayBill") List<String> houseAirwayBill,
                                            @Param(value = "date") Date date);


    Optional<ReplicaPieceDetails> findByLanguageIdAndCompanyIdAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String pieceId, Long deletionIndicator);

    @Query(value = "Select Top 1 tp.HOUSE_AIRWAY_BILL\n" +
            "From tblpiecedetails tp\n" +
            "Where tp.IS_DELETED=0\n" +
            "And tp.LANG_ID = :languageId\n" +
            "And tp.C_ID = :companyId\n" +
            "And tp.PIECE_ID = :pieceId", nativeQuery = true)
    String getHawbWithPieceId(@Param(value = "languageId") String languageId,
                              @Param(value = "companyId") String companyId,
                              @Param(value = "pieceId") String pieceId);


    @Query(value = "Select tp.CONSIGNMENT_ID\n" +
            "From tblpiecedetails tp\n" +
            "Where tp.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId))\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL IN (:houseAirwayBill))\n" +
            "AND (COALESCE(:pieceId, NULL) IS NULL OR PIECE_ID IN (:pieceId))", nativeQuery = true)
    Long findConsignment(@Param(value = "languageId") String languageId,
                         @Param(value = "companyId") String companyId,
                         @Param(value = "houseAirwayBill") String houseAirwayBill,
                         @Param(value = "pieceId") String pieceId);
}
