package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.ndr.FindNdr;
import com.courier.overc360.api.midmile.replica.model.ndr.ReplicaNdr;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ReplicaNdrSpecification implements Specification<ReplicaNdr> {

    FindNdr findNdr;

    public ReplicaNdrSpecification(FindNdr inputSearchParams) {
        this.findNdr = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaNdr> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findNdr.getLanguageId() != null && !findNdr.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findNdr.getLanguageId()));
        }
        if (findNdr.getCompanyId() != null && !findNdr.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findNdr.getCompanyId()));
        }
        if (findNdr.getDeliveryId() != null && !findNdr.getDeliveryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("deliveryId");
            predicates.add(group.in(findNdr.getDeliveryId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
