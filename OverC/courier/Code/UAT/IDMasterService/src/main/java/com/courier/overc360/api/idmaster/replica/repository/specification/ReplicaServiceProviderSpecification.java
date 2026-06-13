package com.courier.overc360.api.idmaster.replica.repository.specification;


import com.courier.overc360.api.idmaster.replica.model.serviceprovider.FindServiceProvider;
import com.courier.overc360.api.idmaster.replica.model.serviceprovider.ReplicaServiceProvider;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")


public class ReplicaServiceProviderSpecification implements Specification<ReplicaServiceProvider> {

    FindServiceProvider findServiceProvider;

    public ReplicaServiceProviderSpecification(FindServiceProvider inputSearchParams) {
        this.findServiceProvider = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaServiceProvider> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findServiceProvider.getLanguageId() != null && !findServiceProvider.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findServiceProvider.getLanguageId()));
        }
        if (findServiceProvider.getCompanyId() != null && !findServiceProvider.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findServiceProvider.getCompanyId()));
        }
        if (findServiceProvider.getServiceProvidersId() != null && !findServiceProvider.getServiceProvidersId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("serviceProvidersId");
            predicates.add(group.in(findServiceProvider.getServiceProvidersId()));
        }
        if (findServiceProvider.getStatusId() != null && !findServiceProvider.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findServiceProvider.getStatusId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}


