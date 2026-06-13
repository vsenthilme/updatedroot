package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.paymenttype.FindPaymentType;
import com.courier.overc360.api.idmaster.replica.model.paymenttype.ReplicaPaymentType;
import com.courier.overc360.api.idmaster.replica.model.timeslot.FindTimeSlot;
import com.courier.overc360.api.idmaster.replica.model.timeslot.ReplicaTimeSlot;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaPaymentTypeSpecification implements Specification<ReplicaPaymentType> {

    FindPaymentType findPaymentType;

    public ReplicaPaymentTypeSpecification(FindPaymentType inputSearchParams) {
        this.findPaymentType = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaPaymentType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findPaymentType.getLanguageId() != null && !findPaymentType.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findPaymentType.getLanguageId()));
        }
        if (findPaymentType.getCompanyId() != null && !findPaymentType.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findPaymentType.getCompanyId()));
        }
        if (findPaymentType.getPaymentTypeId() != null && !findPaymentType.getPaymentTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("timeSlotId");
            predicates.add(group.in(findPaymentType.getPaymentTypeId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
