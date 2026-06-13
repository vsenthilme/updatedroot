package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.country.FindCountry;
import com.courier.overc360.api.idmaster.replica.model.country.ReplicaCountry;
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
public class ReplicaCountrySpecification implements Specification<ReplicaCountry> {

    FindCountry findCountry;

    public ReplicaCountrySpecification(FindCountry inputSearchParams) {
        this.findCountry = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCountry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCountry.getLanguageId() != null && !findCountry.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCountry.getLanguageId()));
        }
        if (findCountry.getCompanyId() != null && !findCountry.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCountry.getCompanyId()));
        }
        if (findCountry.getCountryId() != null && !findCountry.getCountryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("countryId");
            predicates.add(group.in(findCountry.getCountryId()));
        }
        if (findCountry.getStatusId() != null && !findCountry.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCountry.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
