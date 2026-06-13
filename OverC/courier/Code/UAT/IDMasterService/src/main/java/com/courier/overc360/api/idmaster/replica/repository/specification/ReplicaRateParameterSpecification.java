package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.rateparameter.FindRateParameter;
import com.courier.overc360.api.idmaster.replica.model.rateparameter.ReplicaRateParameter;
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
public class ReplicaRateParameterSpecification implements Specification<ReplicaRateParameter> {

    FindRateParameter findRateParameter;

    public ReplicaRateParameterSpecification(FindRateParameter inputSearchParams) {
        this.findRateParameter = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaRateParameter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findRateParameter.getRateParameterId() != null && !findRateParameter.getRateParameterId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("rateParameterId");
            predicates.add(group.in(findRateParameter.getRateParameterId()));
        }
        if (findRateParameter.getLanguageId() != null && !findRateParameter.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findRateParameter.getLanguageId()));
        }
        if (findRateParameter.getCompanyId() != null && !findRateParameter.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findRateParameter.getCompanyId()));
        }
        if (findRateParameter.getStatusId() != null && !findRateParameter.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findRateParameter.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}


