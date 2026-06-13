package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.hsCode.FindHSCode;
import com.courier.overc360.api.idmaster.replica.model.hsCode.ReplicaHSCode;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaHSCodeSpecification implements Specification<ReplicaHSCode> {

    FindHSCode findHSCode;

    public ReplicaHSCodeSpecification(FindHSCode inputSearchParams) {
        this.findHSCode = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaHSCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findHSCode.getLanguageId() != null && !findHSCode.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findHSCode.getLanguageId()));
        }
        if (findHSCode.getCompanyId() != null && !findHSCode.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findHSCode.getCompanyId()));
        }
        if (findHSCode.getHsCode() != null && !findHSCode.getHsCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hsCode");
            predicates.add(group.in(findHSCode.getHsCode()));
        }
        if (findHSCode.getStatusId() != null && !findHSCode.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findHSCode.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}


