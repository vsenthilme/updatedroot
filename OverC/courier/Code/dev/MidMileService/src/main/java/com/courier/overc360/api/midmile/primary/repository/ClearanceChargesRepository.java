package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.clearancecharges.ClearanceCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ClearanceChargesRepository extends JpaRepository<ClearanceCharges, String>, JpaSpecificationExecutor<ClearanceCharges> {

    Optional<ClearanceCharges> findByClearanceChargesIdAndDeletionIndicator
            (Long clearanceChargesId, Long deletionIndicator);
}
