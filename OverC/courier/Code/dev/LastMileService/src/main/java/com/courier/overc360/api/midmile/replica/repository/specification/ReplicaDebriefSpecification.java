package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.debrief.FindDebrief;
import com.courier.overc360.api.midmile.replica.model.debrief.ReplicaDebrief;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaDebriefSpecification implements Specification<ReplicaDebrief> {

    FindDebrief findDebrief;

    public ReplicaDebriefSpecification(FindDebrief inputSearchParams) {
        this.findDebrief = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaDebrief> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (findDebrief.getLanguageId() != null && !findDebrief.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findDebrief.getLanguageId()));
        }
        if (findDebrief.getCompanyId() != null && !findDebrief.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findDebrief.getCompanyId()));
        }
        if (findDebrief.getCourierId() != null && !findDebrief.getCourierId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("courierId");
            predicates.add(group.in(findDebrief.getCourierId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
