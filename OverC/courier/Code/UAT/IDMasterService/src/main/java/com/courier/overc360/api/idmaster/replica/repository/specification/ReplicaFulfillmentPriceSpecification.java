package com.courier.overc360.api.idmaster.replica.repository.specification;


import com.courier.overc360.api.idmaster.replica.model.fulFillmentPrice.FindFulfillmentPrice;
import com.courier.overc360.api.idmaster.replica.model.fulFillmentPrice.ReplicaFulfillmentPrice;
import com.courier.overc360.api.idmaster.replica.model.paymenttype.FindPaymentType;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaFulfillmentPriceSpecification implements Specification<ReplicaFulfillmentPrice> {

    FindFulfillmentPrice findFulfillmentPrice;

    public ReplicaFulfillmentPriceSpecification(FindFulfillmentPrice inputSearchParams) {
        this.findFulfillmentPrice = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaFulfillmentPrice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findFulfillmentPrice.getLanguageId() != null && !findFulfillmentPrice.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findFulfillmentPrice.getLanguageId()));
        }
        if (findFulfillmentPrice.getCompanyId() != null && !findFulfillmentPrice.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findFulfillmentPrice.getCompanyId()));
        }
        if (findFulfillmentPrice.getPartnerId() != null && !findFulfillmentPrice.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findFulfillmentPrice.getPartnerId()));
        }
        if (findFulfillmentPrice.getLineNo() != null && !findFulfillmentPrice.getLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lineNo");
            predicates.add(group.in(findFulfillmentPrice.getLineNo()));
        }

        predicates.add(criteriaBuilder.equal(root.get("deletionIndicator"), 0L));
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
