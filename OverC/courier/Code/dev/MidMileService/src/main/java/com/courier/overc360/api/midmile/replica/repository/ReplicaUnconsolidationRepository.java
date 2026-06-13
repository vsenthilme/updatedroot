package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.console.unconsolidation.ReplicaUnconsolidation;
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
public interface ReplicaUnconsolidationRepository extends JpaRepository<ReplicaUnconsolidation, String>, JpaSpecificationExecutor<ReplicaUnconsolidation> {

    Optional<ReplicaUnconsolidation> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
            String partnerHouseAirwayBill, String pieceId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
            String partnerHouseAirwayBill, String pieceId, Long deletionIndicator);


    // Get Description
    @Query(value = "Select\n" +
            "tl.LANG_TEXT langDesc,\n" +
            "tc.C_NAME companyDesc\n" +
            "From tbllanguage tl\n" +
            "Join tblcompany tc on tl.LANG_ID = tc.LANG_ID\n" +
            "Where\n" +
            "tl.LANG_ID IN (:languageId) and\n" +
            "tc.C_ID IN (:companyId) and\n" +
            "tl.IS_DELETED = 0 and\n" +
            "tc.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getLAndCDescription(@Param(value = "languageId") String languageId,
                                      @Param(value = "companyId") String companyId);


    // Get All Non-deleted Unconsolidations
    @Query(value = "Select * From tblunconsolidation\n" +
            "Where IS_DELETED=0", nativeQuery = true)
    List<ReplicaUnconsolidation> getAllNonDeletedUnconsolidations();


    // Find Unconsolidated Shipments with given Params Only
    @Query(value = "SELECT * FROM tblunconsolidation tc\n" +
            "WHERE tc.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerMasterAirwayBill, NULL) IS NULL OR tc.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAirwayBill))\n" +
            "AND (COALESCE(:partnerHouseAirwayBill, NULL) IS NULL OR tc.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAirwayBill))\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:unconsolidatedIndicator, NULL) IS NULL OR tc.UNCONSOLIDATED IN (:unconsolidatedIndicator))", nativeQuery = true)
    List<ReplicaUnconsolidation> findUnconsolidatedShipmentsWithQry(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("partnerId") List<String> partnerId,
            @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
            @Param("partnerHouseAirwayBill") List<String> partnerHouseAirwayBill,
            @Param("unconsolidatedIndicator") List<Long> unconsolidatedIndicator);


    // Get No of Unconsolidated Shipments
    @Query(value = "Select COUNT(*) From tblunconsolidation tc\n" +
            "Where tc.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
//            "AND (COALESCE(:partnerId, NULL) IS NULL OR tc.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:partnerMasterAB, NULL) IS NULL OR tc.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAB))\n" +
            "AND (COALESCE(:partnerHouseAB, NULL) IS NULL OR tc.PARTNER_HOUSE_AIRWAY_BILL IN (:partnerHouseAB))\n" +
            "And tc.UNCONSOLIDATED = 1\n" +
            "And (COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) IS NULL OR (tc.CTD_ON between COALESCE(CONVERT(VARCHAR(255), :fromDate), NULL) And COALESCE(CONVERT(VARCHAR(255), :toDate), NULL)))", nativeQuery = true)
    long getNoOfUnconsolidatedShipments(@Param("languageId") String languageId,
                                        @Param("companyId") String companyId,
//                                        @Param("partnerId") String partnerId,
                                        @Param("partnerMasterAB") String partnerMasterAB,
                                        @Param("partnerHouseAB") String partnerHouseAB,
//                                        @Param("unconsolidatedIndicator") Long unconsolidatedIndicator,
                                        @Param("fromDate") Date fromDate,
                                        @Param("toDate") Date toDate);


    // Get Unconsolidated Shipments Count by P-MAWB
    @Query(value = "SELECT COUNT(*) FROM tblunconsolidation tc\n" +
            "WHERE tc.IS_DELETED=0\n" +
            "And tc.UNCONSOLIDATED = 1\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerMasterAB, NULL) IS NULL OR tc.PARTNER_MASTER_AIRWAY_BILL IN (:partnerMasterAB))", nativeQuery = true)
    long getUnconsolidatedCountByPMawb(@Param("languageId") String languageId,
                                       @Param("companyId") String companyId,
                                       @Param("partnerMasterAB") String partnerMasterAB);

    //    @Query(value = "SELECT tc.PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill, tc.LANG_ID AS languageId, tc.C_ID AS companyId, COUNT(*) AS count\n" +
//            "FROM tblunconsolidation tc\n" +
//            "WHERE tc.IS_DELETED = 0\n" +
//            "AND tc.UNCONSOLIDATED = 1\n" +
//            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
//            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
//            "GROUP BY tc.PARTNER_MASTER_AIRWAY_BILL, tc.LANG_ID, tc.C_ID", nativeQuery = true)
//    Map<String, Long> getUnconsolidatedCountByPMawb(@Param("languageId") List<String> languageId,
//                                                    @Param("companyId") List<String> companyId);
    @Query(value = "SELECT tc.PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill, tc.LANG_ID AS languageId, tc.C_ID AS companyId, COUNT(*) AS count\n" +
            "FROM tblunconsolidation tc\n" +
            "WHERE tc.IS_DELETED = 0\n" +
            "AND tc.UNCONSOLIDATED = 1\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId))\n" +
            "GROUP BY tc.PARTNER_MASTER_AIRWAY_BILL, tc.LANG_ID, tc.C_ID", nativeQuery = true)
    List<Object[]> getUnconsolidatedCountByPMawb(@Param("languageId") List<String> languageId,
                                                 @Param("companyId") List<String> companyId);

}
