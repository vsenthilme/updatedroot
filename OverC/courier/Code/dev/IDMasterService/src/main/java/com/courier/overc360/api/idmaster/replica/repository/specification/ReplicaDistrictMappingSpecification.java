package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.districtMapping.FindDistrictMapping;
import com.courier.overc360.api.idmaster.replica.model.districtMapping.ReplicaDistrictMapping;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaDistrictMappingSpecification implements Specification<ReplicaDistrictMapping> {

    FindDistrictMapping findDistrictMapping;
    public ReplicaDistrictMappingSpecification(FindDistrictMapping inputSearchParams) {
        this.findDistrictMapping = inputSearchParams;
    }
    @Override
    public Predicate toPredicate(Root<ReplicaDistrictMapping> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findDistrictMapping.getDistrictId() != null && !findDistrictMapping.getDistrictId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("districtId");
            predicates.add(group.in(findDistrictMapping.getDistrictId()));
        }
        if (findDistrictMapping.getLanguageId() != null && !findDistrictMapping.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findDistrictMapping.getLanguageId()));
        }
        if (findDistrictMapping.getCompanyId() != null && !findDistrictMapping.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findDistrictMapping.getCompanyId()));
        }
        if (findDistrictMapping.getPartnerId() != null && !findDistrictMapping.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findDistrictMapping.getPartnerId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}
