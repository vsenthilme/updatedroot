package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ImageReferenceRepository extends JpaRepository<ImageReference, String>, JpaSpecificationExecutor<ImageReference> {

    Optional<ImageReference> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndImageRefIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId, String imageRefId,Long deletionIndicator
    );

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



//    @Modifying
//    @Transactional
//    @Query(value = " update tblimagereference set is_deleted = 1 where " +
//            " (c_id = :companyId) and (lang_id = :languageId) and (partner_id = :partnerId) and  " +
//            " (house_airway_bill = :houseAB) and (master_airway_bill = :masterAB) and  " +
//            " (piece_id = :pieceId or :pieceId is null) and (piece_item_id = :pieceItemId or :pieceItemId is null) and " +
//            " is_deleted = 0", nativeQuery = true)
//    public void updateImageTable(@Param("companyId") String companyId, @Param("languageId") String languageId,
//                                 @Param("partnerId") String partnerId, @Param("houseAB") String houseAB,
//                                 @Param("masterAB") String masterAB, @Param("pieceId") String pieceId,
//                                 @Param("pieceItemId") String pieceItemId, @Param("loginUserID") String loginUserID);

    @Modifying
    @Transactional
    @Query(value = " update tblimagereference set is_deleted = 1, UTD_ON = getDate(), UTD_BY = :loginUserID where " +
            " (c_id = :companyId) and (lang_id = :languageId) and (partner_id = :partnerId) and  " +
            " (house_airway_bill = :houseAB) and (master_airway_bill = :masterAB) and  " +
            " (piece_id = :pieceId) and " +
            " is_deleted = 0", nativeQuery = true)
    public void updateImageTable(@Param("companyId") String companyId, @Param("languageId") String languageId,
                                 @Param("partnerId") String partnerId, @Param("houseAB") String houseAB,
                                 @Param("masterAB") String masterAB, @Param("pieceId") String pieceId,
                                 @Param("loginUserID") String loginUserID);

    @Modifying
    @Transactional
    @Query(value = " update tblimagereference set is_deleted = 1, UTD_ON = getDate(), UTD_BY = :loginUserID where " +
            " (c_id = :companyId) and (lang_id = :languageId) and (partner_id = :partnerId) and  " +
            " (house_airway_bill = :houseAB) and (master_airway_bill = :masterAB) and  " +
            " is_deleted = 0", nativeQuery = true)
    public void updateImageTable(@Param("companyId") String companyId, @Param("languageId") String languageId,
                                 @Param("partnerId") String partnerId, @Param("houseAB") String houseAB,
                                 @Param("masterAB") String masterAB, @Param("loginUserID") String loginUserID);

    List<ImageReference> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId, Long deletionIndicator);

    ImageReference findByImageRefIdAndDeletionIndicator(String imageRefId, Long deletionIndicator);

    List<ImageReference> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, Long deletionIndicator);
}
