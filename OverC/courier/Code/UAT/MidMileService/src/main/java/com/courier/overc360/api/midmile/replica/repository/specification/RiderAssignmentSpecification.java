package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.riderassignment.FindRiderAssignment;
import com.courier.overc360.api.midmile.replica.model.riderassignment.ReplicaRiderAssignmentEntity;
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
public class RiderAssignmentSpecification implements Specification<ReplicaRiderAssignmentEntity> {

    FindRiderAssignment findRiderAssignment;

    public RiderAssignmentSpecification(FindRiderAssignment inputSearchParams) {
        this.findRiderAssignment = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaRiderAssignmentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findRiderAssignment.getLanguageId() != null && !findRiderAssignment.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findRiderAssignment.getLanguageId()));
        }
        if (findRiderAssignment.getCompanyId() != null && !findRiderAssignment.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findRiderAssignment.getCompanyId()));
        }
        if (findRiderAssignment.getPartnerId() != null && !findRiderAssignment.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findRiderAssignment.getPartnerId()));
        }
        if (findRiderAssignment.getMasterAirwayBill() != null && !findRiderAssignment.getMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("masterAirwayBill");
            predicates.add(group.in(findRiderAssignment.getMasterAirwayBill()));
        }
        if (findRiderAssignment.getHouseAirwayBill() != null && !findRiderAssignment.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findRiderAssignment.getHouseAirwayBill()));
        }
        if (findRiderAssignment.getRiderId() != null && !findRiderAssignment.getRiderId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("riderId");
            predicates.add(group.in(findRiderAssignment.getRiderId()));
        }
        if (findRiderAssignment.getPickupId() != null && !findRiderAssignment.getPickupId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pickupId");
            predicates.add(group.in(findRiderAssignment.getPickupId()));
        }
        if (findRiderAssignment.getServiceTypeId() != null && !findRiderAssignment.getServiceTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("serviceTypeId");
            predicates.add(group.in(findRiderAssignment.getServiceTypeId()));
        }
        if (findRiderAssignment.getFromCreatedOn() != null && findRiderAssignment.getToCreatedOn() != null) {
            predicates.add(cb.between(root.get("createdOn"), findRiderAssignment.getFromCreatedOn(), findRiderAssignment.getToCreatedOn()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
