package com.courier.overc360.api.idmaster.replica.repository.specification;


import com.courier.overc360.api.idmaster.replica.model.uom.FindUom;
import com.courier.overc360.api.idmaster.replica.model.uom.ReplicaUom;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

public class ReplicaUomSpecification implements Specification<ReplicaUom> {

    FindUom findUom;

    public ReplicaUomSpecification(FindUom inputSearchParams) {
        this.findUom = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaUom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findUom.getLanguageId() != null && !findUom.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findUom.getLanguageId()));
        }
        if (findUom.getCompanyId() != null && !findUom.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findUom.getCompanyId()));
        }
        if (findUom.getUomId() != null && !findUom.getUomId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("uomId");
            predicates.add(group.in(findUom.getUomId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}

