package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.consignmentstatus.ReplicaConsignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

public interface ReplicaConsignmentStatusRepository extends JpaRepository<ReplicaConsignmentStatus, String>, JpaSpecificationExecutor<ReplicaConsignmentStatus> {

    Optional<ReplicaConsignmentStatus> findByLanguageIdAndCompanyIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String houseAirwayBill, String pieceId, Long deletionIndicator);

    List<ReplicaConsignmentStatus> findByCompanyIdAndLanguageIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String companyId, String languageId, String houseAirwayBill, String pieceId, Long deletionIndicator);
}
