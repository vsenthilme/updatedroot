package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.vehicle.ReplicaVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaVehicleRepository extends JpaRepository<ReplicaVehicle, String>, JpaSpecificationExecutor<ReplicaVehicle> {

    Optional<ReplicaVehicle> findByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator(
            String companyId, String languageId, String vehicleRegNumber, Long deletionIndicator);

    boolean existsByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator(
            String companyId, String languageId, String vehicleRegNumber, Long deletionIndicator);

}
