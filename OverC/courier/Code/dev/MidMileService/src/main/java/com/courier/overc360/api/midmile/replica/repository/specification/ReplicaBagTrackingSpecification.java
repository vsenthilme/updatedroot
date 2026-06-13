package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.bagtracking.FindBagTracking;
import com.courier.overc360.api.midmile.replica.model.bagtracking.ReplicaBagTracking;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaBagTrackingSpecification implements Specification<ReplicaBagTracking> {

    FindBagTracking findBagTracking;

    public ReplicaBagTrackingSpecification(FindBagTracking inputSearchParams) {
        this.findBagTracking = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaBagTracking> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findBagTracking.getConsignmentBagId() != null && !findBagTracking.getConsignmentBagId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("consignmentBagId");
            predicates.add(group.in(findBagTracking.getConsignmentBagId()));
        }
        if (findBagTracking.getLanguageId() != null && !findBagTracking.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findBagTracking.getLanguageId()));
        }
        if (findBagTracking.getCompanyId() != null && !findBagTracking.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findBagTracking.getCompanyId()));
        }
        if (findBagTracking.getPartnerId() != null && !findBagTracking.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findBagTracking.getPartnerId()));
        }
        if (findBagTracking.getHouseAirwayBill() != null && !findBagTracking.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findBagTracking.getHouseAirwayBill()));
        }
        if (findBagTracking.getStatusId() != null && !findBagTracking.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findBagTracking.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
