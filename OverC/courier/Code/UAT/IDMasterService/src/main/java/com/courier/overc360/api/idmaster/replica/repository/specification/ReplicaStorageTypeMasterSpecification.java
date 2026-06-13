package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.storagetypemaster.FindStorageTypeMaster;
import com.courier.overc360.api.idmaster.replica.model.storagetypemaster.ReplicaStorageTypeMaster;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaStorageTypeMasterSpecification implements Specification<ReplicaStorageTypeMaster> {

    FindStorageTypeMaster findStorageTypeMaster;

    public ReplicaStorageTypeMasterSpecification(FindStorageTypeMaster inputSearchParams) {
        this.findStorageTypeMaster = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaStorageTypeMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findStorageTypeMaster.getLanguageId() != null && !findStorageTypeMaster.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findStorageTypeMaster.getLanguageId()));
        }
        if (findStorageTypeMaster.getCompanyId() != null && !findStorageTypeMaster.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findStorageTypeMaster.getCompanyId()));
        }
        if (findStorageTypeMaster.getStorageTypeId() != null && !findStorageTypeMaster.getStorageTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("storageTypeId");
            predicates.add(group.in(findStorageTypeMaster.getStorageTypeId()));
        }
        if (findStorageTypeMaster.getStatusId() != null && !findStorageTypeMaster.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findStorageTypeMaster.getStatusId()));
        }
        if (findStorageTypeMaster.getHubCode() != null && !findStorageTypeMaster.getHubCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hubCode");
            predicates.add(group.in(findStorageTypeMaster.getHubCode()));
        }
        if (findStorageTypeMaster.getZoneTypeId() != null && !findStorageTypeMaster.getZoneTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("zoneTypeId");
            predicates.add(group.in(findStorageTypeMaster.getZoneTypeId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
