package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.customer.FindCustomer;
import com.courier.overc360.api.idmaster.replica.model.customer.ReplicaCustomer;
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
public class ReplicaCustomerSpecification implements Specification<ReplicaCustomer> {

    FindCustomer findCustomer;

    public ReplicaCustomerSpecification(FindCustomer inputSearchParams) {
        this.findCustomer = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCustomer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCustomer.getLanguageId() != null && !findCustomer.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCustomer.getLanguageId()));
        }
        if (findCustomer.getCompanyId() != null && !findCustomer.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCustomer.getCompanyId()));
        }
        if (findCustomer.getSubProductId() != null && !findCustomer.getSubProductId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subProductId");
            predicates.add(group.in(findCustomer.getSubProductId()));
        }
        if (findCustomer.getProductId() != null && !findCustomer.getProductId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("productId");
            predicates.add(group.in(findCustomer.getProductId()));
        }
        if (findCustomer.getCustomerId() != null && !findCustomer.getCustomerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("customerId");
            predicates.add(group.in(findCustomer.getCustomerId()));
        }
        if (findCustomer.getSubProductValue() != null && !findCustomer.getSubProductValue().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subProductValue");
            predicates.add(group.in(findCustomer.getSubProductValue()));
        }
        if (findCustomer.getStatusId() != null && !findCustomer.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCustomer.getStatusId()));
        }
        if (findCustomer.getHubCode() != null && !findCustomer.getHubCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hubCode");
            predicates.add(group.in(findCustomer.getHubCode()));
        }
        if (findCustomer.getReferenceField2() != null && !findCustomer.getReferenceField2().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("referenceField2");
            predicates.add(group.in(findCustomer.getReferenceField2()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
