package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.consignment.DestinationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface DestinationDetailsRepository extends JpaRepository<DestinationDetails, Long> {

    Optional<DestinationDetails> findByDestinationDetailId(Long destinationDetailId);
}
