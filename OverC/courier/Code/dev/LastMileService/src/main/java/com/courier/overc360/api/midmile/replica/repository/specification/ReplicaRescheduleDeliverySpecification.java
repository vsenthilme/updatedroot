package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.replica.model.rescheduledelivery.FindRescheduleDelivery;
import com.courier.overc360.api.midmile.replica.model.rescheduledelivery.ReplicaRescheduleDelivery;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaRescheduleDeliverySpecification implements Specification<ReplicaRescheduleDelivery> {

    FindRescheduleDelivery findRescheduleDelivery;

    public ReplicaRescheduleDeliverySpecification(FindRescheduleDelivery inputSearchParams) {
        this.findRescheduleDelivery = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaRescheduleDelivery> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findRescheduleDelivery.getLanguageId() != null && !findRescheduleDelivery.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findRescheduleDelivery.getLanguageId()));
        }
        if (findRescheduleDelivery.getCompanyId() != null && !findRescheduleDelivery.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findRescheduleDelivery.getCompanyId()));
        }
        if (findRescheduleDelivery.getDeliveryId() != null && !findRescheduleDelivery.getDeliveryId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("deliveryId");
            predicates.add(group.in(findRescheduleDelivery.getDeliveryId()));
        }
        if (findRescheduleDelivery.getRescheduleNo() != null && !findRescheduleDelivery.getRescheduleNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("rescheduleNo");
            predicates.add(group.in(findRescheduleDelivery.getRescheduleNo()));
        }
        if (findRescheduleDelivery.getHouseAirwayBill() != null && !findRescheduleDelivery.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findRescheduleDelivery.getHouseAirwayBill()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
