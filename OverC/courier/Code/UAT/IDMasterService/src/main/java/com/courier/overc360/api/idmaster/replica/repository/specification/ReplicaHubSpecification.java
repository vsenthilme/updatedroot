package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.hub.FindHub;
import com.courier.overc360.api.idmaster.replica.model.hub.ReplicaHub;
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
public class ReplicaHubSpecification implements Specification<ReplicaHub> {

    FindHub findHub;

    public ReplicaHubSpecification(FindHub inputSearchParams) {
        this.findHub = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaHub> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findHub.getLanguageId() != null && !findHub.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findHub.getLanguageId()));
        }
        if (findHub.getCompanyId() != null && !findHub.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findHub.getCompanyId()));
        }
        if (findHub.getHubCode() != null && !findHub.getHubCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hubCode");
            predicates.add(group.in(findHub.getHubCode()));
        }
        if (findHub.getStatusId() != null && !findHub.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findHub.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
