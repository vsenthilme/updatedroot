package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.rate.FindRate;
import com.courier.overc360.api.idmaster.replica.model.rate.ReplicaRate;
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
public class ReplicaRateSpecification implements Specification<ReplicaRate> {

    FindRate findRate;

    public ReplicaRateSpecification(FindRate inputSearchParams) {
        this.findRate = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaRate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findRate.getLanguageId() != null && !findRate.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findRate.getLanguageId()));
        }
        if (findRate.getCompanyId() != null && !findRate.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findRate.getCompanyId()));
        }
        if (findRate.getPartnerId() != null && !findRate.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findRate.getPartnerId()));
        }
        if (findRate.getRateParameterId() != null && !findRate.getRateParameterId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("rateParameterId");
            predicates.add(group.in(findRate.getRateParameterId()));
        }
        if (findRate.getLineNo() != null && !findRate.getLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lineNo");
            predicates.add(group.in(findRate.getLineNo()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
