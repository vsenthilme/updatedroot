package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.consignor.FindConsignor;
import com.courier.overc360.api.idmaster.replica.model.consignor.ReplicaConsignor;
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
public class ReplicaConsignorSpecification implements Specification<ReplicaConsignor> {

    FindConsignor findConsignor;

    public ReplicaConsignorSpecification(FindConsignor inputSearchParams) {
        this.findConsignor = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaConsignor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findConsignor.getLanguageId() != null && !findConsignor.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findConsignor.getLanguageId()));
        }
        if (findConsignor.getCompanyId() != null && !findConsignor.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findConsignor.getCompanyId()));
        }
        if (findConsignor.getSubProductId() != null && !findConsignor.getSubProductId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subProductId");
            predicates.add(group.in(findConsignor.getSubProductId()));
        }
        if (findConsignor.getProductId() != null && !findConsignor.getProductId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("productId");
            predicates.add(group.in(findConsignor.getProductId()));
        }
        if (findConsignor.getCustomerId() != null && !findConsignor.getCustomerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("customerId");
            predicates.add(group.in(findConsignor.getCustomerId()));
        }
        if (findConsignor.getConsignorId() != null && !findConsignor.getConsignorId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("consignorId");
            predicates.add(group.in(findConsignor.getConsignorId()));
        }
        if (findConsignor.getSubProductValue() != null && !findConsignor.getSubProductValue().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subProductValue");
            predicates.add(group.in(findConsignor.getSubProductValue()));
        }
        if (findConsignor.getStatusId() != null && !findConsignor.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findConsignor.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
