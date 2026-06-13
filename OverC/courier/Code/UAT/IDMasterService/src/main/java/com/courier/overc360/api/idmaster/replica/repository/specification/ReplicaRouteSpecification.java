package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.route.FindRoute;
import com.courier.overc360.api.idmaster.replica.model.route.ReplicaRoute;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaRouteSpecification implements Specification<ReplicaRoute> {

    FindRoute findRoute;

    public ReplicaRouteSpecification(FindRoute inputSearchParams) {
        this.findRoute = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaRoute> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findRoute.getLanguageId() != null && !findRoute.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findRoute.getLanguageId()));
        }
        if (findRoute.getCompanyId() != null && !findRoute.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findRoute.getCompanyId()));
        }
        if (findRoute.getRouteId() != null && !findRoute.getRouteId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("routeId");
            predicates.add(group.in(findRoute.getRouteId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}

