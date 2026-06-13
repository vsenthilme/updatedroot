package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.driverRouteAssignment.DriverRouteAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface DriverRouteAssignmentRepository extends JpaRepository<DriverRouteAssignment, String>, JpaSpecificationExecutor<DriverRouteAssignment> {

    Optional<DriverRouteAssignment> findByCompanyIdAndLanguageIdAndCourierIdAndRouteIdAndVehicleRegNumberAndAssignedHubCodeAndDeletionIndicator(
            String companyId, String languageId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, Long deletionIndicator);

}
