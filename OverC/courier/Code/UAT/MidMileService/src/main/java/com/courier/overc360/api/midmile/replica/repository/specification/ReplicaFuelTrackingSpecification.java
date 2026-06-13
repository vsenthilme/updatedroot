package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.fueltracking.FindFuelTracking;
import com.courier.overc360.api.midmile.replica.model.fueltracking.ReplicaFuelTracking;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaFuelTrackingSpecification implements Specification<ReplicaFuelTracking> {

    FindFuelTracking findFuelTracking;

    public ReplicaFuelTrackingSpecification(FindFuelTracking inputSearchParams) {
        this.findFuelTracking = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaFuelTracking> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findFuelTracking.getLanguageId() != null && !findFuelTracking.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findFuelTracking.getLanguageId()));
        }
        if (findFuelTracking.getCompanyId() != null && !findFuelTracking.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findFuelTracking.getCompanyId()));
        }
        if (findFuelTracking.getVehicleRegNumber() != null && !findFuelTracking.getVehicleRegNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("vehicleRegNumber");
            predicates.add(group.in(findFuelTracking.getVehicleRegNumber()));
        }
        if (findFuelTracking.getStatusId() != null && !findFuelTracking.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findFuelTracking.getStatusId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
