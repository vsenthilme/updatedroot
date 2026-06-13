package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.consignmentType.FindConsignmentType;
import com.courier.overc360.api.idmaster.replica.model.consignmentType.ReplicaConsignmentType;
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
public class ReplicaConsignmentTypeSpecification implements Specification<ReplicaConsignmentType> {

    FindConsignmentType findConsignmentType;

    public ReplicaConsignmentTypeSpecification(FindConsignmentType inputSearchParams) {
        this.findConsignmentType = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaConsignmentType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findConsignmentType.getConsignmentTypeId() != null && !findConsignmentType.getConsignmentTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("consignmentTypeId");
            predicates.add(group.in(findConsignmentType.getConsignmentTypeId()));
        }
        if (findConsignmentType.getLanguageId() != null && !findConsignmentType.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findConsignmentType.getLanguageId()));
        }
        if (findConsignmentType.getCompanyId() != null && !findConsignmentType.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findConsignmentType.getCompanyId()));
        }
        if (findConsignmentType.getStatusId() != null && !findConsignmentType.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findConsignmentType.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
