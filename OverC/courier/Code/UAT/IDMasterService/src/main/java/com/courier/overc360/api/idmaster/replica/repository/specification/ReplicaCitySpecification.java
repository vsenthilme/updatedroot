package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.city.FindCity;
import com.courier.overc360.api.idmaster.replica.model.city.ReplicaCity;
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
public class ReplicaCitySpecification implements Specification<ReplicaCity> {

    FindCity findCity;

    public ReplicaCitySpecification(FindCity inputSearchParams) {
        this.findCity = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCity.getCityId() != null && !findCity.getCityId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("cityId");
            predicates.add(group.in(findCity.getCityId()));
        }
        if (findCity.getLanguageId() != null && !findCity.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCity.getLanguageId()));
        }
        if (findCity.getCompanyId() != null && !findCity.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCity.getCompanyId()));
        }
        if (findCity.getCountryId() != null && !findCity.getCountryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("countryId");
            predicates.add(group.in(findCity.getCountryId()));
        }
        if (findCity.getProvinceId() != null && !findCity.getProvinceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("provinceId");
            predicates.add(group.in(findCity.getProvinceId()));
        }
        if (findCity.getDistrictId() != null && !findCity.getDistrictId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("districtId");
            predicates.add(group.in(findCity.getDistrictId()));
        }
        if (findCity.getStatusId() != null && !findCity.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCity.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
