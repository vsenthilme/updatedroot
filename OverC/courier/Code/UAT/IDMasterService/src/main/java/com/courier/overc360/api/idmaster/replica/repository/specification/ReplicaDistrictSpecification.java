package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.district.FindDistrict;
import com.courier.overc360.api.idmaster.replica.model.district.ReplicaDistrict;
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
public class ReplicaDistrictSpecification implements Specification<ReplicaDistrict> {

    FindDistrict findDistrict;

    public ReplicaDistrictSpecification(FindDistrict inputSearchParams) {
        this.findDistrict = inputSearchParams;
    }


    @Override
    public Predicate toPredicate(Root<ReplicaDistrict> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findDistrict.getLanguageId() != null && !findDistrict.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findDistrict.getLanguageId()));
        }
        if (findDistrict.getCompanyId() != null && !findDistrict.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findDistrict.getCompanyId()));
        }
        if (findDistrict.getCountryId() != null && !findDistrict.getCountryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("countryId");
            predicates.add(group.in(findDistrict.getCountryId()));
        }
        if (findDistrict.getProvinceId() != null && !findDistrict.getProvinceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("provinceId");
            predicates.add(group.in(findDistrict.getProvinceId()));
        }
        if (findDistrict.getDistrictId() != null && !findDistrict.getDistrictId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("districtId");
            predicates.add(group.in(findDistrict.getDistrictId()));
        }
        if (findDistrict.getStatusId() != null && !findDistrict.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findDistrict.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
