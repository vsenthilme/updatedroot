package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.piecedetails.PieceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PieceDetailsRepository extends JpaRepository<PieceDetails,String>, JpaSpecificationExecutor<PieceDetails> {

    Optional<PieceDetails> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator
            (String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, Long deletionIndicator);

    PieceDetails findByCompanyIdAndLanguageIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator
            (String companyId, String languageId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, Long deletionIndicator);

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

    List<PieceDetails> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, Long deletionIndicator);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tblconsignment_entity " +
            "SET NET_WEIGHT = COALESCE(:totalWeight, NET_WEIGHT), " +
            "GROSS_WEIGHT = COALESCE(:totalWeight, GROSS_WEIGHT) " +
            "WHERE C_ID = :companyId " +
            "AND LANG_ID = :languageId " +
            "AND CONSIGNMENT_ID = :consignmentId " +
            "AND HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            "AND MASTER_AIRWAY_BILL = :masterAirwayBill " +
            "AND is_deleted = 0 ",
            nativeQuery = true)
    public void updateConsignment(@Param("companyId") String companyId,
                                  @Param("languageId") String languageId,
                                  @Param("consignmentId") Long consignmentId,
                                  @Param("houseAirwayBill") String houseAirwayBill,
                                  @Param("masterAirwayBill") String masterAirwayBill,
                                  @Param("totalWeight") String totalWeight);


    @Transactional
    @Modifying
    @Query(value =
            "UPDATE tblconsignment_info " +
                    "SET WEIGHT = COALESCE(:totalWeight, WEIGHT) " +
                    "WHERE CON_INFO_ID = :consignmentId ",
            nativeQuery = true)
    public void updateConsignmentInfo(@Param("consignmentId") Long consignmentId,
                                      @Param("totalWeight") String totalWeight);

    List<PieceDetails> findByHouseAirwayBill(String houseAirwayBill);


    @Query(value = "Select CONSIGNMENT_ID consignmentId From tblpiecedetails \n" +
            "Where IS_DELETED = 0 AND (COALESCE(:shippingLabelNo, null) IS NULL OR PIECE_ID IN (:shippingLabelNo)) \n" +
            "AND (COALESCE(:companyId,null) IS NULL OR C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:languageId,null) IS NULL OR LANG_ID IN (:languageId))", nativeQuery = true)
    Long getPiece(@Param("shippingLabelNo") String shippingLabelNo,
                  @Param("companyId") String companyId,
                  @Param("languageId") String languageId);

    PieceDetails findByPieceIdAndDeletionIndicator(String pieceId, Long deletionIndicator);
}

