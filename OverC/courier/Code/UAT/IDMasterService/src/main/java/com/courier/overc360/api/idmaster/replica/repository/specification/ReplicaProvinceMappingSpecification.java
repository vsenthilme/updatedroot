package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.provincemapping.FindProvinceMapping;
import com.courier.overc360.api.idmaster.replica.model.provincemapping.ReplicaProvinceMapping;
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
public class ReplicaProvinceMappingSpecification implements Specification<ReplicaProvinceMapping> {

    FindProvinceMapping findProvinceMapping;

    public ReplicaProvinceMappingSpecification(FindProvinceMapping inputSearchParams) {
        this.findProvinceMapping = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaProvinceMapping> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findProvinceMapping.getLanguageId() != null && !findProvinceMapping.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findProvinceMapping.getLanguageId()));
        }
        if (findProvinceMapping.getCompanyId() != null && !findProvinceMapping.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findProvinceMapping.getCompanyId()));
        }
        if (findProvinceMapping.getProvinceId() != null && !findProvinceMapping.getProvinceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("provinceId");
            predicates.add(group.in(findProvinceMapping.getProvinceId()));
        }
        if (findProvinceMapping.getPartnerId() != null && !findProvinceMapping.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findProvinceMapping.getPartnerId()));
        }
        if (findProvinceMapping.getStatusId() != null && !findProvinceMapping.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findProvinceMapping.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));

    }
}
