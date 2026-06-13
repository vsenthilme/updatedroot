package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.consignmentstatus.ReplicaConsignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

public interface ReplicaConsignmentStatusRepository extends JpaRepository<ReplicaConsignmentStatus, String>, JpaSpecificationExecutor<ReplicaConsignmentStatus> {

    Optional<ReplicaConsignmentStatus> findByLanguageIdAndCompanyIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String houseAirwayBill, String pieceId, Long deletionIndicator);

    List<ReplicaConsignmentStatus> findByCompanyIdAndLanguageIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String companyId, String languageId, String houseAirwayBill, String pieceId, Long deletionIndicator);


    @Query(value = "SELECT * FROM tblconsignmentstatus ORDER BY CON_STATUS_ID OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<ReplicaConsignmentStatus> findByBatch(@Param("offset") int offset, @Param("limit") int limit);


}
