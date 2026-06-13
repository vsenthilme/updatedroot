package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.loadtype.FindLoadType;
import com.courier.overc360.api.idmaster.replica.model.loadtype.ReplicaLoadType;
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
public class ReplicaLoadTypeSpecification implements Specification<ReplicaLoadType> {

    FindLoadType findLoadType;

    public ReplicaLoadTypeSpecification(FindLoadType inputSearchParams) {
        this.findLoadType = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaLoadType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findLoadType.getLanguageId() != null && !findLoadType.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findLoadType.getLanguageId()));
        }
        if (findLoadType.getCompanyId() != null && !findLoadType.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findLoadType.getCompanyId()));
        }
        if (findLoadType.getLoadTypeId() != null && !findLoadType.getLoadTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("loadTypeId");
            predicates.add(group.in(findLoadType.getLoadTypeId()));
        }
        if (findLoadType.getStatusId() != null && !findLoadType.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findLoadType.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
