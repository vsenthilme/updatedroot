package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.reasonslistpickup.FindReasonsListPickup;
import com.courier.overc360.api.idmaster.replica.model.reasonslistpickup.ReplicaReasonsListPickup;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

public class ReplicaReasonsListPickupSpecification implements Specification<ReplicaReasonsListPickup> {

    FindReasonsListPickup findReasonsListPickup;

    public ReplicaReasonsListPickupSpecification(FindReasonsListPickup inputSearchParams) {
        this.findReasonsListPickup = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaReasonsListPickup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findReasonsListPickup.getReasonsId() != null && !findReasonsListPickup.getReasonsId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("reasonsId");
            predicates.add(group.in(findReasonsListPickup.getReasonsId()));
        }
        if (findReasonsListPickup.getLanguageId() != null && !findReasonsListPickup.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findReasonsListPickup.getLanguageId()));
        }
        if (findReasonsListPickup.getCompanyId() != null && !findReasonsListPickup.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findReasonsListPickup.getCompanyId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
