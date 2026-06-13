package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.city.FindCity;
import com.courier.overc360.api.idmaster.replica.model.codpricelist.FindCodPriceList;
import com.courier.overc360.api.idmaster.replica.model.codpricelist.ReplicaCodPriceList;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaCodPriceListSpecification implements Specification<ReplicaCodPriceList> {

    FindCodPriceList findCodPriceList;

    public ReplicaCodPriceListSpecification(FindCodPriceList inputSearchParams) {
        this.findCodPriceList = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCodPriceList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCodPriceList.getPartnerId() != null && !findCodPriceList.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findCodPriceList.getPartnerId()));
        }
        if (findCodPriceList.getLanguageId() != null && !findCodPriceList.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCodPriceList.getLanguageId()));
        }
        if (findCodPriceList.getCompanyId() != null && !findCodPriceList.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCodPriceList.getCompanyId()));
        }
        if (findCodPriceList.getLineNo() != null && !findCodPriceList.getLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lineNo");
            predicates.add(group.in(findCodPriceList.getLineNo()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
