package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.riderassignment.ReplicaRiderAssignmentEntity;
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
public interface ReplicaRiderAssignmentEntityRepository extends JpaRepository<ReplicaRiderAssignmentEntity, Long>,
        JpaSpecificationExecutor<ReplicaRiderAssignmentEntity> {

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId, Long deletionIndicator);

    Optional<ReplicaRiderAssignmentEntity> findByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId, Long deletionIndicator);


    // Get ALl Non-Deleted RiderAssignment Details
    @Query(value = "Select * From tblriderassignment_entity\n" +
            "Where IS_DELETED=0", nativeQuery = true)
    List<ReplicaRiderAssignmentEntity> getAllNonDeletedRiderAssignments();


    // Find RiderAssignments with optional Params
    @Query(value = "SELECT * FROM tblriderassignment_entity tr\n" +
            "WHERE tr.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tr.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tr.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR tr.PARTNER_ID IN (:partnerId))\n" +
            "AND (COALESCE(:houseAirwayBill, NULL) IS NULL OR tr.HOUSE_AIRWAY_BILL IN (:houseAirwayBill))\n" +
            "AND (COALESCE(:pickupId, NULL) IS NULL OR tr.PICKUP_ID IN (:pickupId))\n" +
            "And (COALESCE(CONVERT(VARCHAR(255), :fromCreatedOn), NULL) IS NULL OR (tr.CTD_ON between COALESCE(CONVERT(VARCHAR(255), :fromCreatedOn), NULL) And COALESCE(CONVERT(VARCHAR(255), :toCreatedOn), NULL)))"
            , nativeQuery = true)
    List<ReplicaRiderAssignmentEntity> findRiderAssignmentsWithOptionalParams(
            @Param("languageId") List<String> languageId,
            @Param("companyId") List<String> companyId,
            @Param("partnerId") List<String> partnerId,
            @Param("houseAirwayBill") List<String> houseAirwayBill,
            @Param("pickupId") List<String> pickupId,
            @Param("fromCreatedOn") Date fromCreatedOn,
            @Param("toCreatedOn") Date toCreatedOn);


}
