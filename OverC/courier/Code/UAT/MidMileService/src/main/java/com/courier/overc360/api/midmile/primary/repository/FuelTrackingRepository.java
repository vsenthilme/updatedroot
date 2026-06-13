package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.fueltracking.FuelTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FuelTrackingRepository extends JpaRepository<FuelTracking, String>, JpaSpecificationExecutor<FuelTracking> {

    Optional<FuelTracking> findByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator
            (String companyId, String languageId, String vehicleRegNumber, Long deletionIndicator);

}
