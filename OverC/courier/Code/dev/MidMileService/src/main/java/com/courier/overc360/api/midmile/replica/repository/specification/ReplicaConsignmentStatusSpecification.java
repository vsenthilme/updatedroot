package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.consignmentstatus.FindConsignmentStatus;
import com.courier.overc360.api.midmile.replica.model.consignmentstatus.ReplicaConsignmentStatus;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaConsignmentStatusSpecification implements Specification<ReplicaConsignmentStatus> {

    FindConsignmentStatus findConsignmentStatus;

    public ReplicaConsignmentStatusSpecification(FindConsignmentStatus inputSearchParams) {
        this.findConsignmentStatus = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaConsignmentStatus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findConsignmentStatus.getLanguageId() != null && !findConsignmentStatus.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findConsignmentStatus.getLanguageId()));
        }
        if (findConsignmentStatus.getCompanyId() != null && !findConsignmentStatus.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findConsignmentStatus.getCompanyId()));
        }
        if (findConsignmentStatus.getHouseAirwayBill() != null && !findConsignmentStatus.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findConsignmentStatus.getHouseAirwayBill()));
        }
        if (findConsignmentStatus.getStatusId() != null && !findConsignmentStatus.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findConsignmentStatus.getStatusId()));
        }
        if (findConsignmentStatus.getPieceId() != null && !findConsignmentStatus.getPieceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceId");
            predicates.add(group.in(findConsignmentStatus.getPieceId()));
        }
        if (findConsignmentStatus.getEventCode() != null && !findConsignmentStatus.getEventCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("eventCode");
            predicates.add(group.in(findConsignmentStatus.getEventCode()));
        }
        if (findConsignmentStatus.getHawbTypeId() != null && !findConsignmentStatus.getHawbTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hawbTypeId");
            predicates.add(group.in(findConsignmentStatus.getHawbTypeId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
