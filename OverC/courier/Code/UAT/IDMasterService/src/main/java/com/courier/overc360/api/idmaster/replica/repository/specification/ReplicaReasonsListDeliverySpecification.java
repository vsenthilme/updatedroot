package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.reasonslistdelivery.FindReasonsListDelivery;
import com.courier.overc360.api.idmaster.replica.model.reasonslistdelivery.ReplicaReasonsListDelivery;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

public class ReplicaReasonsListDeliverySpecification implements Specification<ReplicaReasonsListDelivery> {

    FindReasonsListDelivery findReasonsListDelivery;

    public ReplicaReasonsListDeliverySpecification(FindReasonsListDelivery inputSearchParams) {
        this.findReasonsListDelivery = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaReasonsListDelivery> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findReasonsListDelivery.getReasonsId() != null && !findReasonsListDelivery.getReasonsId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("reasonsId");
            predicates.add(group.in(findReasonsListDelivery.getReasonsId()));
        }
        if (findReasonsListDelivery.getLanguageId() != null && !findReasonsListDelivery.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findReasonsListDelivery.getLanguageId()));
        }
        if (findReasonsListDelivery.getCompanyId() != null && !findReasonsListDelivery.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findReasonsListDelivery.getCompanyId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
