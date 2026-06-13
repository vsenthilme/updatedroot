package com.courier.overc360.api.idmaster.replica.repository.specification;


import com.courier.overc360.api.idmaster.replica.model.retailPriceList.FindRetailPrice;
import com.courier.overc360.api.idmaster.replica.model.retailPriceList.ReplicaRetailPrice;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class ReplicaRetailPriceSpecification implements Specification<ReplicaRetailPrice> {


    FindRetailPrice findRetailPrice;

    public ReplicaRetailPriceSpecification(FindRetailPrice inputSearchParams) {
        this.findRetailPrice = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaRetailPrice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findRetailPrice.getLanguageId() != null && !findRetailPrice.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findRetailPrice.getLanguageId()));
        }
        if (findRetailPrice.getCompanyId() != null && !findRetailPrice.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findRetailPrice.getCompanyId()));
        }
        if (findRetailPrice.getPartnerId() != null && !findRetailPrice.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findRetailPrice.getPartnerId()));
        }
        if (findRetailPrice.getLineNo() != null && !findRetailPrice.getLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lineNo");
            predicates.add(group.in(findRetailPrice.getLineNo()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
