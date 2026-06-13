package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.replica.model.pickup.ReplicaPickupDestinationDetails;
import com.courier.overc360.api.midmile.replica.model.pickup.ReplicaPickupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReplicaPickupDestinationDetailsRepository extends JpaRepository<ReplicaPickupDestinationDetails, Long> {


    @Query(value = "SELECT DESTINATION_ADDRESS FROM tblpickupdestdetails WHERE " +
            "(COALESCE(:destId, NULL) IS NULL OR DESTINATION_DETAIL_ID = :destId)",nativeQuery = true)
    String findPickupId(Long destId);
}
