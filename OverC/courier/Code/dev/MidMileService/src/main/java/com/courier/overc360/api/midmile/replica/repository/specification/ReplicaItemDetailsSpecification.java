package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.primary.model.consignment.FindConsignment;
import com.courier.overc360.api.midmile.replica.model.itemdetails.ReplicaItemDetails;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ReplicaItemDetailsSpecification implements Specification<ReplicaItemDetails> {

    FindConsignment findItemDetails;

    public ReplicaItemDetailsSpecification(FindConsignment inputSearchParams) {
        this.findItemDetails = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaItemDetails> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findItemDetails.getLanguageId() != null && !findItemDetails.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findItemDetails.getLanguageId()));
        }
        if (findItemDetails.getCompanyId() != null && !findItemDetails.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findItemDetails.getCompanyId()));
        }
        if (findItemDetails.getMasterAirwayBill() != null && !findItemDetails.getMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("masterAirwayBill");
            predicates.add(group.in(findItemDetails.getMasterAirwayBill()));
        }
        if (findItemDetails.getHouseAirwayBill() != null && !findItemDetails.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findItemDetails.getHouseAirwayBill()));
        }
        if (findItemDetails.getPartnerId() != null && !findItemDetails.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findItemDetails.getPartnerId()));
        }
        if (findItemDetails.getPieceId() != null && !findItemDetails.getPieceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceId");
            predicates.add(group.in(findItemDetails.getPieceId()));
        }
        if (findItemDetails.getPieceItemId() != null && !findItemDetails.getPieceItemId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceItemId");
            predicates.add(group.in(findItemDetails.getPieceItemId()));
        }
        if (findItemDetails.getPartnerHouseAirwayBill() != null && !findItemDetails.getPartnerHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerHouseAirwayBill");
            predicates.add(group.in(findItemDetails.getPartnerHouseAirwayBill()));
        }
        if (findItemDetails.getPartnerMasterAirwayBill() != null && !findItemDetails.getPartnerMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerMasterAirwayBill");
            predicates.add(group.in(findItemDetails.getPartnerMasterAirwayBill()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}

