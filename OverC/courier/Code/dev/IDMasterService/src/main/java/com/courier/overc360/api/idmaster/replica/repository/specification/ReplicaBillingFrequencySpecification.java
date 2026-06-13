package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.billingfrequency.FindBillingFrequency;
import com.courier.overc360.api.idmaster.replica.model.billingfrequency.ReplicaBillingFrequency;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaBillingFrequencySpecification implements Specification<ReplicaBillingFrequency> {

    FindBillingFrequency findBillingFrequency;

    public ReplicaBillingFrequencySpecification(FindBillingFrequency inputSearchParams) {
        this.findBillingFrequency = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaBillingFrequency> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findBillingFrequency.getLanguageId() != null && !findBillingFrequency.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findBillingFrequency.getLanguageId()));
        }
        if (findBillingFrequency.getCompanyId() != null && !findBillingFrequency.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findBillingFrequency.getCompanyId()));
        }
        if (findBillingFrequency.getBillingFrequencyId() != null && !findBillingFrequency.getBillingFrequencyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("billingFrequencyId");
            predicates.add(group.in(findBillingFrequency.getBillingFrequencyId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
