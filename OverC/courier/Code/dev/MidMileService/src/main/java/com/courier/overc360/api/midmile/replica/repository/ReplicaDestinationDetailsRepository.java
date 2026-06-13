package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.replica.model.consignment.ReplicaDestinationDetails;
import com.courier.overc360.api.midmile.replica.model.dto.DestinationDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReplicaDestinationDetailsRepository extends JpaRepository<ReplicaDestinationDetails, Long> {

    @Query(value = "Select \n" +
            "NAME name,\n" +
            "PHONE phone,\n" +
            "ADDRESS_LINE_1 addressLine1,\n" +
            "ADDRESS_LINE_2 addressLine2,\n" +
            "CITY city,\n" +
            "COUNTRY country from tbldestdetails where \n" +
            "DEST_DETAIL_ID = :consignmentId", nativeQuery = true)
    DestinationDetailsImpl getDestinationDetailsImpl(@Param(value = "consignmentId") Long consignmentId);

    @Query(value = "SELECT r FROM ReplicaDestinationDetails r WHERE r.destinationDetailId = ?1")
    ReplicaDestinationDetails findDestId(Long consignmentId);
}
