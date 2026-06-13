package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.iata.FindIata;
import com.courier.overc360.api.idmaster.replica.model.iata.ReplicaIata;
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
public class ReplicaIataSpecification implements Specification<ReplicaIata> {
    FindIata findIata;

    public ReplicaIataSpecification(FindIata inputSearchParams) {
        this.findIata = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaIata> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (findIata.getLanguageId() != null && !findIata.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findIata.getLanguageId()));
        }
        if (findIata.getCompanyId() != null && !findIata.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findIata.getCompanyId()));
        }
        if (findIata.getOrigin() != null && !findIata.getOrigin().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("origin");
            predicates.add(group.in(findIata.getOrigin()));
        }
        if (findIata.getOriginCode() != null && !findIata.getOriginCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("originCode");
            predicates.add(group.in(findIata.getOriginCode()));
        }
        if (findIata.getStatusId() != null && !findIata.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findIata.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}


