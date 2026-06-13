package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.drs.FindDrs;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.replica.model.npr.FindNpr;
import com.courier.overc360.api.midmile.replica.model.npr.ReplicaNpr;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaNprSpecification implements Specification<ReplicaNpr> {

    FindNpr findNpr;

    public ReplicaNprSpecification(FindNpr inputSearchParams) {
        this.findNpr = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaNpr> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findNpr.getLanguageId() != null && !findNpr.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findNpr.getLanguageId()));
        }
        if (findNpr.getCompanyId() != null && !findNpr.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findNpr.getCompanyId()));
        }
        if (findNpr.getPickupId() != null && !findNpr.getPickupId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pickupId");
            predicates.add(group.in(findNpr.getPickupId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
