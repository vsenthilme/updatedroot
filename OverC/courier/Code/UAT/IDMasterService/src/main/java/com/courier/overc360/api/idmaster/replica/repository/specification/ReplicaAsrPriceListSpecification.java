package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.asrpriceList.FindAsrPriceList;
import com.courier.overc360.api.idmaster.replica.model.asrpriceList.ReplicaAsrPriceList;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaAsrPriceListSpecification implements Specification<ReplicaAsrPriceList> {

    FindAsrPriceList findAsrPriceList;

    public ReplicaAsrPriceListSpecification(FindAsrPriceList inputSearchParams) {
        this.findAsrPriceList = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaAsrPriceList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findAsrPriceList.getLanguageId() != null && !findAsrPriceList.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findAsrPriceList.getLanguageId()));
        }
        if (findAsrPriceList.getCompanyId() != null && !findAsrPriceList.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findAsrPriceList.getCompanyId()));
        }
        if (findAsrPriceList.getPartnerId() != null && !findAsrPriceList.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findAsrPriceList.getPartnerId()));
        }
        if (findAsrPriceList.getLineNo() != null && !findAsrPriceList.getLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lineNo");
            predicates.add(group.in(findAsrPriceList.getLineNo()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
