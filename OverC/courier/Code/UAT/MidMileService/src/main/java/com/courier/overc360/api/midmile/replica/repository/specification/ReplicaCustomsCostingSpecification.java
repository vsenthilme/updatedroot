package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.customscosting.FindCustomsCosting;
import com.courier.overc360.api.midmile.replica.model.customscosting.ReplicaCustomsCosting;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ReplicaCustomsCostingSpecification implements Specification<ReplicaCustomsCosting> {

    FindCustomsCosting findCustomsCosting;

    public ReplicaCustomsCostingSpecification(FindCustomsCosting inputSearchParams) {
        this.findCustomsCosting = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCustomsCosting> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCustomsCosting.getLanguageId() != null && !findCustomsCosting.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCustomsCosting.getLanguageId()));
        }
        if (findCustomsCosting.getCompanyId() != null && !findCustomsCosting.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCustomsCosting.getCompanyId()));
        }
        if (findCustomsCosting.getPartnerId() != null && !findCustomsCosting.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findCustomsCosting.getPartnerId()));
        }
        if (findCustomsCosting.getCostCenter() != null && !findCustomsCosting.getCostCenter().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("costCenter");
            predicates.add(group.in(findCustomsCosting.getCostCenter()));
        }
        if (findCustomsCosting.getLineNumber() != null && !findCustomsCosting.getLineNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lineNumber");
            predicates.add(group.in(findCustomsCosting.getLineNumber()));
        }
        if (findCustomsCosting.getCashNumber() != null && !findCustomsCosting.getCashNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCustomsCosting.getCashNumber()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}

