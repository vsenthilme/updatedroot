package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.riderassignment.RiderAssignmentOriginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RiderAssignmentOriginDetailsRepository extends JpaRepository<RiderAssignmentOriginDetails, Long> {
}
