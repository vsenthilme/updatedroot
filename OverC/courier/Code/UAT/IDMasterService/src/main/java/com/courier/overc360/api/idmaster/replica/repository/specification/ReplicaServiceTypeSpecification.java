package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.serviceType.FindServiceType;
import com.courier.overc360.api.idmaster.replica.model.serviceType.ReplicaServiceType;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaServiceTypeSpecification implements Specification<ReplicaServiceType> {

    FindServiceType findServiceType;

    public ReplicaServiceTypeSpecification(FindServiceType inputSearchParams) {
        this.findServiceType = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaServiceType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findServiceType.getServiceTypeId() != null && !findServiceType.getServiceTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("serviceTypeId");
            predicates.add(group.in(findServiceType.getServiceTypeId()));
        }
        if (findServiceType.getLanguageId() != null && !findServiceType.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findServiceType.getLanguageId()));
        }
        if (findServiceType.getCompanyId() != null && !findServiceType.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findServiceType.getCompanyId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
