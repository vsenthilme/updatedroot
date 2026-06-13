package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.fueltracking.ReplicaFuelTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReplicaFuelTrackingRepository extends JpaRepository<ReplicaFuelTracking, String>,
        JpaSpecificationExecutor<ReplicaFuelTracking> {

    Optional<ReplicaFuelTracking> findByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator
            (String companyId, String languageId, String vehicleRegNumber, Long deletionIndicator);
}
