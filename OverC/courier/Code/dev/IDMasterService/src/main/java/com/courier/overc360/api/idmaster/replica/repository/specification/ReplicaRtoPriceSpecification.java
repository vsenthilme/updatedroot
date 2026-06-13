package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.retailPriceList.FindRetailPrice;
import com.courier.overc360.api.idmaster.replica.model.retailPriceList.ReplicaRetailPrice;
import com.courier.overc360.api.idmaster.replica.model.rtopricelist.FindRtoPrice;
import com.courier.overc360.api.idmaster.replica.model.rtopricelist.ReplicaRtoPrice;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaRtoPriceSpecification implements Specification<ReplicaRtoPrice> {

    FindRtoPrice findRtoPrice;

    public ReplicaRtoPriceSpecification(FindRtoPrice inputSearchParams) {
        this.findRtoPrice = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaRtoPrice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findRtoPrice.getLanguageId() != null && !findRtoPrice.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findRtoPrice.getLanguageId()));
        }
        if (findRtoPrice.getCompanyId() != null && !findRtoPrice.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findRtoPrice.getCompanyId()));
        }
        if (findRtoPrice.getPartnerId() != null && !findRtoPrice.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findRtoPrice.getPartnerId()));
        }
        if (findRtoPrice.getLineNo() != null && !findRtoPrice.getLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lineNo");
            predicates.add(group.in(findRtoPrice.getLineNo()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
