package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.timeslot.FindTimeSlot;
import com.courier.overc360.api.idmaster.replica.model.timeslot.ReplicaTimeSlot;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaTimeslotSpecification implements Specification<ReplicaTimeSlot> {

    FindTimeSlot findTimeSlot;

    public ReplicaTimeslotSpecification(FindTimeSlot inputSearchParams) {
        this.findTimeSlot = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaTimeSlot> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findTimeSlot.getLanguageId() != null && !findTimeSlot.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findTimeSlot.getLanguageId()));
        }
        if (findTimeSlot.getCompanyId() != null && !findTimeSlot.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findTimeSlot.getCompanyId()));
        }
        if (findTimeSlot.getTimeSlotId() != null && !findTimeSlot.getTimeSlotId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("timeSlotId");
            predicates.add(group.in(findTimeSlot.getTimeSlotId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
