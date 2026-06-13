package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.Unconsolidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UnconsolidationRepository extends JpaRepository<Unconsolidation, String>, JpaSpecificationExecutor<Unconsolidation> {

    Optional<Unconsolidation> findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String partnerMasterAirwayBill,
            String partnerHouseAirwayBill, String pieceId, Long deletionIndicator);


    // Update unconsolidated flag in Consignment table
    @Transactional
    @Modifying
    @Query(value = "Update tblconsignment_entity\n" +
            "Set UNCONSOLIDATED = 1\n" +
            "Where IS_DELETED=0\n" +
            "AND LANG_ID = :languageId\n" +
            "And C_ID = :companyId\n" +
            "AND PARTNER_ID = :partnerId\n" +
            "AND PARTNER_MASTER_AB = :partnerMasterAirwayBill\n" +
            "AND PARTNER_HOUSE_AB = :partnerHouseAirwayBill", nativeQuery = true)
    void updateUnconsolidatedFlagInConsignmentTbl(
            @Param("languageId") String languageId,
            @Param("companyId") String companyId,
            @Param("partnerId") String partnerId,
            @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill,
            @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

}
