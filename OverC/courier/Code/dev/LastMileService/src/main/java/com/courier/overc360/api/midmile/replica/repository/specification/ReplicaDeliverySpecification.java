package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.delivery.FindDelivery;
import com.courier.overc360.api.midmile.replica.model.delivery.ReplicaDelivery;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaDeliverySpecification implements Specification<ReplicaDelivery> {

    FindDelivery findDelivery;

    public ReplicaDeliverySpecification(FindDelivery inputSearchParams) {
        this.findDelivery = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaDelivery> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findDelivery.getLanguageId() != null && !findDelivery.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findDelivery.getLanguageId()));
        }
        if (findDelivery.getCompanyId() != null && !findDelivery.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findDelivery.getCompanyId()));
        }
        if (findDelivery.getPieceId() != null && !findDelivery.getPieceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceId");
            predicates.add(group.in(findDelivery.getPieceId()));
        }
        if (findDelivery.getHouseAirwayBill() != null && !findDelivery.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findDelivery.getHouseAirwayBill()));
        }
        if (findDelivery.getDeliveryId() != null && !findDelivery.getDeliveryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("deliveryId");
            predicates.add(group.in(findDelivery.getDeliveryId()));
        }
        if (findDelivery.getCourierId() != null && !findDelivery.getCourierId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("courierId");
            predicates.add(group.in(findDelivery.getCourierId()));
        }
        if (findDelivery.getStatusId() != null && !findDelivery.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findDelivery.getStatusId()));
        }
        if (findDelivery.getFromDate() != null && findDelivery.getToDate() != null) {
            predicates.add(cb.between(root.get("createdOn"), findDelivery.getFromDate(), findDelivery.getToDate()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
