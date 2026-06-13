package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.opStatus.FindOpStatus;
import com.courier.overc360.api.idmaster.replica.model.opStatus.ReplicaOpStatus;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ReplicaOpStatusSpecification implements Specification<ReplicaOpStatus> {

    FindOpStatus findOpStatus;

    public ReplicaOpStatusSpecification(FindOpStatus inputSearchParams) {
        this.findOpStatus = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaOpStatus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findOpStatus.getLanguageId() != null && !findOpStatus.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findOpStatus.getLanguageId()));
        }
        if (findOpStatus.getCompanyId() != null && !findOpStatus.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findOpStatus.getCompanyId()));
        }
        if(findOpStatus.getStatusCode() != null && !findOpStatus.getStatusCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusCode");
            predicates.add(group.in(findOpStatus.getStatusCode()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
