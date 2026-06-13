package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.currencyExchangeRate.FindCurrencyExchangeRate;
import com.courier.overc360.api.idmaster.replica.model.currencyExchangeRate.ReplicaCurrencyExchangeRate;
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
public class ReplicaCurrencyExchangeRateSpecification implements Specification<ReplicaCurrencyExchangeRate> {

    FindCurrencyExchangeRate findCurrencyExchangeRate;

    public ReplicaCurrencyExchangeRateSpecification(FindCurrencyExchangeRate inputSearchParams) {
        this.findCurrencyExchangeRate = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCurrencyExchangeRate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCurrencyExchangeRate.getLanguageId() != null && !findCurrencyExchangeRate.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCurrencyExchangeRate.getLanguageId()));
        }
        if (findCurrencyExchangeRate.getCompanyId() != null && !findCurrencyExchangeRate.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCurrencyExchangeRate.getCompanyId()));
        }
        if (findCurrencyExchangeRate.getFromCurrencyId() != null && !findCurrencyExchangeRate.getFromCurrencyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("fromCurrencyId");
            predicates.add(group.in(findCurrencyExchangeRate.getFromCurrencyId()));
        }
        if (findCurrencyExchangeRate.getToCurrencyId() != null && !findCurrencyExchangeRate.getToCurrencyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("toCurrencyId");
            predicates.add(group.in(findCurrencyExchangeRate.getToCurrencyId()));
        }
        if (findCurrencyExchangeRate.getStatusId() != null && !findCurrencyExchangeRate.getStatusId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("statusId");
            predicates.add(group.in(findCurrencyExchangeRate.getStatusId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
