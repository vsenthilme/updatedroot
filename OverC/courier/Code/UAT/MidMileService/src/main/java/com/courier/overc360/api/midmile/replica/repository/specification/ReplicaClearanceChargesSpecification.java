package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.clearancecharges.FindClearanceCharges;
import com.courier.overc360.api.midmile.replica.model.clearancecharges.ReplicaClearanceCharges;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")

public class ReplicaClearanceChargesSpecification implements Specification<ReplicaClearanceCharges> {

    FindClearanceCharges findClearanceCharges;

    public ReplicaClearanceChargesSpecification(FindClearanceCharges inputSearchParams) {
        this.findClearanceCharges = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaClearanceCharges> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (findClearanceCharges.getClearanceChargesId() != null && !findClearanceCharges.getClearanceChargesId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("clearanceChargesId");
            predicates.add(group.in(findClearanceCharges.getClearanceChargesId()));
        }
        if (findClearanceCharges.getPartnerId() != null && !findClearanceCharges.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findClearanceCharges.getPartnerId()));
        }
        if (findClearanceCharges.getCompanyId() != null && !findClearanceCharges.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findClearanceCharges.getCompanyId()));
        }
        if (findClearanceCharges.getLanguageId() != null && !findClearanceCharges.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findClearanceCharges.getLanguageId()));
        }
        if (findClearanceCharges.getSubCustomerId() != null && !findClearanceCharges.getSubCustomerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subCustomerId");
            predicates.add(group.in(findClearanceCharges.getSubCustomerId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}

