package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.citymapping.FindCityMapping;
import com.courier.overc360.api.idmaster.replica.model.citymapping.ReplicaCityMapping;
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
public class ReplicaCityMappingSpecification implements Specification<ReplicaCityMapping> {

    FindCityMapping findCityMapping;

    public ReplicaCityMappingSpecification(FindCityMapping inputSearchParams) {
        this.findCityMapping = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCityMapping> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCityMapping.getLanguageId() != null && !findCityMapping.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCityMapping.getLanguageId()));
        }
        if (findCityMapping.getCompanyId() != null && !findCityMapping.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCityMapping.getCompanyId()));
        }
        if (findCityMapping.getCityId() != null && !findCityMapping.getCityId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("cityId");
            predicates.add(group.in(findCityMapping.getCityId()));
        }
        if (findCityMapping.getPartnerId() != null && !findCityMapping.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findCityMapping.getPartnerId()));
        }
        if (findCityMapping.getStatusId() != null && !findCityMapping.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCityMapping.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));

    }
}
