package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.airportcode.FindAirportCode;
import com.courier.overc360.api.idmaster.replica.model.airportcode.ReplicaAirportCode;
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
public class ReplicaAirportCodeSpecification implements Specification<ReplicaAirportCode> {

    FindAirportCode findAirportCode;

    public ReplicaAirportCodeSpecification(FindAirportCode inputSearchParams) {
        this.findAirportCode = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaAirportCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findAirportCode.getAirportCode() != null && !findAirportCode.getAirportCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("airportCode");
            predicates.add(group.in(findAirportCode.getAirportCode()));
        }
        if (findAirportCode.getLanguageId() != null && !findAirportCode.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findAirportCode.getLanguageId()));
        }
        if (findAirportCode.getCompanyId() != null && !findAirportCode.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findAirportCode.getCompanyId()));
        }
        if (findAirportCode.getStatusId() != null && !findAirportCode.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findAirportCode.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
