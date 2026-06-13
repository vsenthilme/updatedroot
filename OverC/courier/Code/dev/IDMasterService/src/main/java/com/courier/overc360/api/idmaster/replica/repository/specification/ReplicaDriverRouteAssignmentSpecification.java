package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.appuser.FindAppUser;
import com.courier.overc360.api.idmaster.replica.model.driverrouteassignment.FindDriverRouteAssignment;
import com.courier.overc360.api.idmaster.replica.model.driverrouteassignment.ReplicaDriverRouteAssignment;
import com.courier.overc360.api.idmaster.replica.model.vehicle.FindVehicle;
import com.courier.overc360.api.idmaster.replica.model.vehicle.ReplicaVehicle;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("serial")
public class ReplicaDriverRouteAssignmentSpecification implements Specification<ReplicaDriverRouteAssignment> {

    FindDriverRouteAssignment findDriverRouteAssignment;

    public ReplicaDriverRouteAssignmentSpecification(FindDriverRouteAssignment inputSearchParams) {
        this.findDriverRouteAssignment = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaDriverRouteAssignment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findDriverRouteAssignment.getCourierId() != null && !findDriverRouteAssignment.getCourierId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("courierId");
            predicates.add(group.in(findDriverRouteAssignment.getCourierId()));
        }
        if (findDriverRouteAssignment.getLanguageId() != null && !findDriverRouteAssignment.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findDriverRouteAssignment.getLanguageId()));
        }
        if (findDriverRouteAssignment.getCompanyId() != null && !findDriverRouteAssignment.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findDriverRouteAssignment.getCompanyId()));
        }
        if (findDriverRouteAssignment.getStatusId() != null && !findDriverRouteAssignment.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findDriverRouteAssignment.getStatusId()));
        }
        if (findDriverRouteAssignment.getVehicleRegNumber() != null && !findDriverRouteAssignment.getVehicleRegNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("vehicleRegNumber");
            predicates.add(group.in(findDriverRouteAssignment.getVehicleRegNumber()));
        }
        if (findDriverRouteAssignment.getRouteId() != null && !findDriverRouteAssignment.getRouteId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("routeId");
            predicates.add(group.in(findDriverRouteAssignment.getRouteId()));
        }
        if (findDriverRouteAssignment.getAssignedHubCode() != null && !findDriverRouteAssignment.getAssignedHubCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("assignedHubCode");
            predicates.add(group.in(findDriverRouteAssignment.getAssignedHubCode()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
