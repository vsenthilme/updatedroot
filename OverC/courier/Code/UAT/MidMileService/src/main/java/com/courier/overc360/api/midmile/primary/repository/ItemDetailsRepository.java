package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.itemdetails.ItemDetails;
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
public interface ItemDetailsRepository extends JpaRepository<ItemDetails, String>, JpaSpecificationExecutor<ItemDetails> {

    Optional<ItemDetails> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId, Long deletionIndicator);

    List<ItemDetails> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, Long deletionIndicator);


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

//    List<ItemDetails> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
//            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, Long deletionIndicator);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tblpiecedetails " +
            "SET LENGTH = COALESCE(:totalLength, LENGTH), " +
            "HEIGHT = COALESCE(:totalHeight, HEIGHT), " +
            "WEIGHT = COALESCE(:totalWeight, WEIGHT), " +
            "VOLUME = COALESCE(:totalVolume, VOLUME) " +
            "WHERE C_ID = :companyId " +
            "AND LANG_ID = :languageId " +
            "AND PIECE_ID = :pieceId " +
            "AND HOUSE_AIRWAY_BILL = :houseAirwayBill " +
            "AND MASTER_AIRWAY_BILL = :masterAirwayBill " +
            "AND is_deleted = 0",
            nativeQuery = true)
    public void updatePiece(@Param("companyId") String companyId,
                            @Param("languageId") String languageId,
                            @Param("pieceId") String pieceId,
                            @Param("houseAirwayBill") String houseAirwayBill,
                            @Param("masterAirwayBill") String masterAirwayBill,
                            @Param("totalLength") String totalLength,
                            @Param("totalHeight") String totalHeight,
                            @Param("totalWeight") String totalWeight,
                            @Param("totalVolume") String totalVolume);

    List<ItemDetails> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, Long deletionIndicator);

}
