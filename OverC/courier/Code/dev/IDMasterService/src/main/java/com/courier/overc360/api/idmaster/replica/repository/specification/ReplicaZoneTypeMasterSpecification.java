package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.zonetypemaster.FindZoneTypeMaster;
import com.courier.overc360.api.idmaster.replica.model.zonetypemaster.ReplicaZoneTypeMaster;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaZoneTypeMasterSpecification implements Specification<ReplicaZoneTypeMaster> {

    FindZoneTypeMaster findZoneTypeMaster;

    public ReplicaZoneTypeMasterSpecification(FindZoneTypeMaster inputSearchParams) {
        this.findZoneTypeMaster = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaZoneTypeMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findZoneTypeMaster.getLanguageId() != null && !findZoneTypeMaster.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findZoneTypeMaster.getLanguageId()));
        }
        if (findZoneTypeMaster.getCompanyId() != null && !findZoneTypeMaster.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findZoneTypeMaster.getCompanyId()));
        }
        if (findZoneTypeMaster.getZoneTypeId() != null && !findZoneTypeMaster.getZoneTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("zoneTypeId");
            predicates.add(group.in(findZoneTypeMaster.getZoneTypeId()));
        }
        if (findZoneTypeMaster.getStatusId() != null && !findZoneTypeMaster.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findZoneTypeMaster.getStatusId()));
        }


        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
