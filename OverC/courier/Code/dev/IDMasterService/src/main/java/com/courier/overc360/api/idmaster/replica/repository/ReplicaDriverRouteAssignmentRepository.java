package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.driverrouteassignment.ReplicaDriverRouteAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
public interface ReplicaDriverRouteAssignmentRepository extends JpaRepository<ReplicaDriverRouteAssignment, String>, JpaSpecificationExecutor<ReplicaDriverRouteAssignment> {

    Optional<ReplicaDriverRouteAssignment> findByCompanyIdAndLanguageIdAndCourierIdAndRouteIdAndVehicleRegNumberAndAssignedHubCodeAndDeletionIndicator(
            String companyId, String languageId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, Long deletionIndicator);


    boolean existsByCompanyIdAndLanguageIdAndCourierIdAndRouteIdAndVehicleRegNumberAndAssignedHubCodeAndDeletionIndicator(
            String companyId, String languageId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, Long deletionIndicator);



}
