package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.vehicle.FindVehicle;
import com.courier.overc360.api.idmaster.replica.model.vehicle.ReplicaVehicle;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaVehicleSpecification implements Specification<ReplicaVehicle> {

    FindVehicle findVehicle;

    public ReplicaVehicleSpecification(FindVehicle inputSearchParams) {
        this.findVehicle = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaVehicle> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findVehicle.getVehicleRegNumber() != null && !findVehicle.getVehicleRegNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("vehicleRegNumber");
            predicates.add(group.in(findVehicle.getVehicleRegNumber()));
        }
        if (findVehicle.getLanguageId() != null && !findVehicle.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findVehicle.getLanguageId()));
        }
        if (findVehicle.getCompanyId() != null && !findVehicle.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findVehicle.getCompanyId()));
        }
        if (findVehicle.getStatusId() != null && !findVehicle.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findVehicle.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
