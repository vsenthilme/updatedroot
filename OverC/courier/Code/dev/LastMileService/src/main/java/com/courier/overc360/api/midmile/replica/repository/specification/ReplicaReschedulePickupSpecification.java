package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.npr.FindNpr;
import com.courier.overc360.api.midmile.replica.model.npr.ReplicaNpr;
import com.courier.overc360.api.midmile.replica.model.reschedulepickup.FindReschedulePickup;
import com.courier.overc360.api.midmile.replica.model.reschedulepickup.ReplicaReSchedulePickUp;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class ReplicaReschedulePickupSpecification implements Specification<ReplicaReSchedulePickUp> {

    FindReschedulePickup findReschedulePickup;

    public ReplicaReschedulePickupSpecification(FindReschedulePickup inputSearchParams) {
        this.findReschedulePickup = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaReSchedulePickUp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findReschedulePickup.getLanguageId() != null && !findReschedulePickup.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findReschedulePickup.getLanguageId()));
        }
        if (findReschedulePickup.getCompanyId() != null && !findReschedulePickup.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findReschedulePickup.getCompanyId()));
        }
        if (findReschedulePickup.getPickupId() != null && !findReschedulePickup.getPickupId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pickupId");
            predicates.add(group.in(findReschedulePickup.getPickupId()));
        }
        if (findReschedulePickup.getPickupEntityId() != null && !findReschedulePickup.getPickupEntityId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pickupEntityId");
            predicates.add(group.in(findReschedulePickup.getPickupEntityId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
