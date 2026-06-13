package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.primary.model.consignment.FindConsignment;
import com.courier.overc360.api.midmile.replica.model.consignment.ReplicaConsignmentEntity;
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
public class ReplicaConsignmentSpecification implements Specification<ReplicaConsignmentEntity> {

    FindConsignment findConsignment;

    public ReplicaConsignmentSpecification(FindConsignment inputSearchParams) {
        this.findConsignment = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaConsignmentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findConsignment.getCompanyId() != null && !findConsignment.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findConsignment.getCompanyId()));
        }
        if (findConsignment.getLanguageId() != null && !findConsignment.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findConsignment.getLanguageId()));
        }
        if (findConsignment.getPartnerId() != null && !findConsignment.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findConsignment.getPartnerId()));
        }
        if (findConsignment.getHouseAirwayBill() != null && !findConsignment.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findConsignment.getHouseAirwayBill()));
        }
        if (findConsignment.getMasterAirwayBill() != null && !findConsignment.getMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("masterAirwayBill");
            predicates.add(group.in(findConsignment.getMasterAirwayBill()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
