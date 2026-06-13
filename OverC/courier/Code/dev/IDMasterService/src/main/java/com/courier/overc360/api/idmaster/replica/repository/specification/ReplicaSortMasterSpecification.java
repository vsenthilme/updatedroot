package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.sortingmaster.FindSortMaster;
import com.courier.overc360.api.idmaster.replica.model.sortingmaster.ReplicaSortingMaster;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class ReplicaSortMasterSpecification implements Specification<ReplicaSortingMaster> {

    FindSortMaster findSortMaster;

    public ReplicaSortMasterSpecification(FindSortMaster inputSearchParams) {
        this.findSortMaster = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaSortingMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findSortMaster.getLanguageId() != null && !findSortMaster.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findSortMaster.getLanguageId()));
        }
        if (findSortMaster.getCompanyId() != null && !findSortMaster.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findSortMaster.getCompanyId()));
        }
        if (findSortMaster.getSortingId() != null && !findSortMaster.getSortingId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("subProductId");
            predicates.add(group.in(findSortMaster.getSortingId()));
        }
        if (findSortMaster.getZoneType() != null && !findSortMaster.getZoneType().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("productId");
            predicates.add(group.in(findSortMaster.getZoneType()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }


}
