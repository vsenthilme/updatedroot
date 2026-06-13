package com.courier.overc360.api.idmaster.replica.repository.specification;


import com.courier.overc360.api.idmaster.replica.model.billmode.FindBillMode;
import com.courier.overc360.api.idmaster.replica.model.billmode.ReplicaBillMode;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

public class ReplicaBillModeSpecification implements Specification<ReplicaBillMode> {

    FindBillMode findBillMode;

    public ReplicaBillModeSpecification(FindBillMode inputSearchParams) {
        this.findBillMode = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaBillMode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findBillMode.getLanguageId() != null && !findBillMode.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findBillMode.getLanguageId()));
        }
        if (findBillMode.getCompanyId() != null && !findBillMode.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findBillMode.getCompanyId()));
        }
        if (findBillMode.getBillModeId() != null && !findBillMode.getBillModeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("billModeId");
            predicates.add(group.in(findBillMode.getBillModeId()));
        }
        if (findBillMode.getStatusId() != null && !findBillMode.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findBillMode.getStatusId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}



