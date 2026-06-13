package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.company.FindCompany;
import com.courier.overc360.api.idmaster.replica.model.company.ReplicaCompany;
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
public class ReplicaCompanySpecification implements Specification<ReplicaCompany> {

    FindCompany findCompany;

    public ReplicaCompanySpecification(FindCompany inputSearchParams) {
        this.findCompany = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCompany> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCompany.getLanguageId() != null && !findCompany.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCompany.getLanguageId()));
        }
        if (findCompany.getCompanyId() != null && !findCompany.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCompany.getCompanyId()));
        }
        if (findCompany.getCountryId() != null && !findCompany.getCountryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("countryId");
            predicates.add(group.in(findCompany.getCountryId()));
        }
        if (findCompany.getProvinceId() != null && !findCompany.getProvinceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("provinceId");
            predicates.add(group.in(findCompany.getProvinceId()));
        }
        if (findCompany.getDistrictId() != null && !findCompany.getDistrictId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("districtId");
            predicates.add(group.in(findCompany.getDistrictId()));
        }
        if (findCompany.getCityId() != null && !findCompany.getCityId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("cityId");
            predicates.add(group.in(findCompany.getCityId()));
        }
        if (findCompany.getStatusId() != null && !findCompany.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCompany.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}