package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.riderassignment.ReplicaRiderAssignmentImageReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaRiderAssignImageReferenceRepository extends JpaRepository<ReplicaRiderAssignmentImageReference, String>,
        JpaSpecificationExecutor<ReplicaRiderAssignmentImageReference> {

    Optional<ReplicaRiderAssignmentImageReference> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndImageRefIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
            String pieceId, String pieceItemId, String imageRefId, Long deletionIndicator);


}
