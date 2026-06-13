package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.drs.FindDrs;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaDrsSpecification implements Specification<ReplicaDrs> {

    FindDrs findDrs;

    public ReplicaDrsSpecification(FindDrs inputSearchParams) {
        this.findDrs = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaDrs> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findDrs.getLanguageId() != null && !findDrs.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findDrs.getLanguageId()));
        }
        if (findDrs.getCompanyId() != null && !findDrs.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findDrs.getCompanyId()));
        }
        if (findDrs.getCustomerId() != null && !findDrs.getCustomerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("customerId");
            predicates.add(group.in(findDrs.getCustomerId()));
        }
        if (findDrs.getDeliveryId() != null && !findDrs.getDeliveryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("deliveryId");
            predicates.add(group.in(findDrs.getDeliveryId()));
        }
        if (findDrs.getHouseAirwayBill() != null && !findDrs.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findDrs.getHouseAirwayBill()));
        }
        if (findDrs.getPieceId() != null && !findDrs.getPieceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceId");
            predicates.add(group.in(findDrs.getPieceId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
