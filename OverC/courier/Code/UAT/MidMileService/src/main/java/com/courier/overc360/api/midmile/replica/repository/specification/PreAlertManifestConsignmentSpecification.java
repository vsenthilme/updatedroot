package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.consignment.ReplicaConsignmentEntity;
import com.courier.overc360.api.midmile.replica.model.dto.FindPreAlertManifest;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PreAlertManifestConsignmentSpecification implements Specification<ReplicaConsignmentEntity> {

    FindPreAlertManifest findPreAlertManifest;

    public PreAlertManifestConsignmentSpecification(FindPreAlertManifest inputSearchParams) {
        this.findPreAlertManifest = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaConsignmentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findPreAlertManifest.getCompanyId() != null && !findPreAlertManifest.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findPreAlertManifest.getCompanyId()));
        }
        if (findPreAlertManifest.getLanguageId() != null && !findPreAlertManifest.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findPreAlertManifest.getLanguageId()));
        }
        if (findPreAlertManifest.getPartnerId() != null && !findPreAlertManifest.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findPreAlertManifest.getPartnerId()));
        }
        if (findPreAlertManifest.getStatusId() != null && !findPreAlertManifest.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findPreAlertManifest.getStatusId()));
        }
        if (findPreAlertManifest.getConsoleIndicator() != null && !findPreAlertManifest.getConsoleIndicator().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("consoleIndicator");
            predicates.add(group.in(findPreAlertManifest.getConsoleIndicator()));
        }
        if (findPreAlertManifest.getManifestIndicator() != null && !findPreAlertManifest.getManifestIndicator().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("manifestIndicator");
            predicates.add(group.in(findPreAlertManifest.getManifestIndicator()));
        }
        if (findPreAlertManifest.getConsignmentId() != null && !findPreAlertManifest.getConsignmentId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("consignmentId");
            predicates.add(group.in(findPreAlertManifest.getConsignmentId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
