package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.replica.model.consignment.ReplicaOriginDetails;
import com.courier.overc360.api.midmile.replica.model.dto.OriginDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReplicaOriginDetailsRepository extends JpaRepository<ReplicaOriginDetails, Long> {

    @Query(value = "Select \n" +
            "NAME name,\n" +
            "PHONE phone,\n" +
            "ADDRESS_LINE_1 addressLine1,\n" +
            "ADDRESS_LINE_2 addressLine2,\n" +
            "CITY city,\n" +
            "COUNTRY country from tblorigindetails where \n" +
            "ORIGIN_ID = :consignmentId", nativeQuery = true)
    OriginDetailsImpl getOriginDetailsImpl(@Param(value = "consignmentId") Long consignmentId);

    @Query(value = "SELECT r FROM ReplicaOriginDetails r WHERE r.originId = ?1")
    ReplicaOriginDetails findOriginId(Long consignmentId);
}
