package com.iweb2b.api.portal.repository.Specification;

import com.iweb2b.api.portal.model.consignment.dto.shopini.FindShopini;
import com.iweb2b.api.portal.model.consignment.dto.shopini.ShopiniWebhook;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ShopiniSpecification implements Specification<ShopiniWebhook> {

    FindShopini findShopini;

    public ShopiniSpecification(FindShopini inputSearchParams) {
        this.findShopini = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ShopiniWebhook> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if (findShopini.getReferenceNumber() != null && !findShopini.getReferenceNumber().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("referenceNumber");
            predicates.add(group.in(findShopini.getReferenceNumber()));
        }
        if (findShopini.getTrackingNo() != null && !findShopini.getTrackingNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("trackingNo");
            predicates.add(group.in(findShopini.getTrackingNo()));
        }
        if (findShopini.getShipmentStatus() != null && !findShopini.getShipmentStatus().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("shipmentStatus");
            predicates.add(group.in(findShopini.getShipmentStatus()));
        }
        if (findShopini.getItemActionName() != null && !findShopini.getItemActionName().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("itemActionName");
            predicates.add(group.in(findShopini.getItemActionName()));
        }
        if (findShopini.getLmdStatus() != null && !findShopini.getLmdStatus().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("lmdStatus");
            predicates.add(group.in(findShopini.getLmdStatus()));
        }
//        if (findShopini.getStartDate() != null && findShopini.getEndDate() != null) {
//            predicates.add(cb.between(root.get("actionDate"), findShopini.getStartDate(), findShopini.getEndDate()));
//        }

        return cb.and(predicates.toArray(new Predicate[] {}));
    }
}
