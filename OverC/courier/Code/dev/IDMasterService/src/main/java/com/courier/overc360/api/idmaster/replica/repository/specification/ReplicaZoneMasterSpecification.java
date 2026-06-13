package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.zonemaster.FindZoneMaster;
import com.courier.overc360.api.idmaster.replica.model.zonemaster.ReplicaZoneMaster;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

public class ReplicaZoneMasterSpecification implements Specification<ReplicaZoneMaster> {

    FindZoneMaster findZoneMaster;

    public ReplicaZoneMasterSpecification(FindZoneMaster inputSearchParams) {
        this.findZoneMaster = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaZoneMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findZoneMaster.getLanguageId() != null && !findZoneMaster.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findZoneMaster.getLanguageId()));
        }
        if (findZoneMaster.getCompanyId() != null && !findZoneMaster.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findZoneMaster.getCompanyId()));
        }
        if (findZoneMaster.getZoneId() != null && !findZoneMaster.getZoneId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("zoneId");
            predicates.add(group.in(findZoneMaster.getZoneId()));
        }
        if (findZoneMaster.getZoneType() != null && !findZoneMaster.getZoneType().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("zoneType");
            predicates.add(group.in(findZoneMaster.getZoneType()));
        }
        if (findZoneMaster.getStatusId() != null && !findZoneMaster.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findZoneMaster.getStatusId()));
        }
        if (findZoneMaster.getHubCode() != null && !findZoneMaster.getHubCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hubCode");
            predicates.add(group.in(findZoneMaster.getHubCode()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}

