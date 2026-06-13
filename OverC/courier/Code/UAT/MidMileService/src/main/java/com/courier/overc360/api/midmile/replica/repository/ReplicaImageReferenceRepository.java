package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.imagereference.ReplicaImageReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReplicaImageReferenceRepository extends JpaRepository<ReplicaImageReference, String>,
        JpaSpecificationExecutor<ReplicaImageReference> {

    Optional<ReplicaImageReference> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndImageRefIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
            String pieceId, String pieceItemId, String imageRefId, Long DeletionIndicator);

}
