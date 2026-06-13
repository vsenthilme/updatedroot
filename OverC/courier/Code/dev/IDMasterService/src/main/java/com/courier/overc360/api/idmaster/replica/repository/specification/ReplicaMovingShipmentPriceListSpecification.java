package com.courier.overc360.api.idmaster.replica.repository.specification;

import com.courier.overc360.api.idmaster.replica.model.movingshipmentpricelist.FindMovingShipmentPriceList;
import com.courier.overc360.api.idmaster.replica.model.movingshipmentpricelist.ReplicaMovingShipmentPriceList;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("serial")
public class ReplicaMovingShipmentPriceListSpecification implements Specification<ReplicaMovingShipmentPriceList> {

    FindMovingShipmentPriceList findMovingShipmentPriceList;

    public ReplicaMovingShipmentPriceListSpecification(FindMovingShipmentPriceList inputSearchParams) {
        this.findMovingShipmentPriceList = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaMovingShipmentPriceList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findMovingShipmentPriceList.getLanguageId() != null && !findMovingShipmentPriceList.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findMovingShipmentPriceList.getLanguageId()));
        }
        if (findMovingShipmentPriceList.getCompanyId() != null && !findMovingShipmentPriceList.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findMovingShipmentPriceList.getCompanyId()));
        }
        if (findMovingShipmentPriceList.getPartnerId() != null && !findMovingShipmentPriceList.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findMovingShipmentPriceList.getPartnerId()));
        }
        if (findMovingShipmentPriceList.getLineNo() != null && !findMovingShipmentPriceList.getLineNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lineNo");
            predicates.add(group.in(findMovingShipmentPriceList.getLineNo()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
