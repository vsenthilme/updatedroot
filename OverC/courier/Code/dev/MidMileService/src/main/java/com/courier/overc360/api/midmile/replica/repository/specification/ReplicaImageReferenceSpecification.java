package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.primary.model.consignment.FindConsignment;
import com.courier.overc360.api.midmile.replica.model.imagereference.ReplicaImageReference;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaImageReferenceSpecification implements Specification<ReplicaImageReference> {

    FindConsignment findImageReference;

    public ReplicaImageReferenceSpecification(FindConsignment inputSearchParams) {
        this.findImageReference = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaImageReference> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findImageReference.getLanguageId() != null && !findImageReference.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findImageReference.getLanguageId()));
        }
        if (findImageReference.getCompanyId() != null && !findImageReference.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findImageReference.getCompanyId()));
        }
        if (findImageReference.getPartnerId() != null && !findImageReference.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findImageReference.getPartnerId()));
        }
        if (findImageReference.getMasterAirwayBill() != null && !findImageReference.getMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("masterAirwayBill");
            predicates.add(group.in(findImageReference.getMasterAirwayBill()));
        }
        if (findImageReference.getHouseAirwayBill() != null && !findImageReference.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findImageReference.getHouseAirwayBill()));
        }
        if (findImageReference.getPieceId() != null && !findImageReference.getPieceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceId");
            predicates.add(group.in(findImageReference.getPieceId()));
        }
        if (findImageReference.getPieceItemId() != null && !findImageReference.getPieceItemId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceItemId");
            predicates.add(group.in(findImageReference.getPieceItemId()));
        }
        if (findImageReference.getPartnerHouseAirwayBill() != null && !findImageReference.getPartnerHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerHouseAirwayBill");
            predicates.add(group.in(findImageReference.getPartnerHouseAirwayBill()));
        }
        if (findImageReference.getPartnerMasterAirwayBill() != null && !findImageReference.getPartnerMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerMasterAirwayBill");
            predicates.add(group.in(findImageReference.getPartnerMasterAirwayBill()));
        }
        if (findImageReference.getImageRefId() != null && !findImageReference.getImageRefId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("imageRefId");
            predicates.add(group.in(findImageReference.getImageRefId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}