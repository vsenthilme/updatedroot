package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.piecedetails.FindPieceDetails;
import com.courier.overc360.api.midmile.replica.model.piecedetails.ReplicaPieceDetails;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaPieceDetailsSpecification implements Specification<ReplicaPieceDetails> {

    FindPieceDetails findPieceDetails;

    public ReplicaPieceDetailsSpecification(FindPieceDetails inputSearchParams) {
        this.findPieceDetails = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaPieceDetails> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findPieceDetails.getLanguageId() != null && !findPieceDetails.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findPieceDetails.getLanguageId()));
        }
        if (findPieceDetails.getCompanyId() != null && !findPieceDetails.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findPieceDetails.getCompanyId()));
        }
        if (findPieceDetails.getPartnerId() != null && !findPieceDetails.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findPieceDetails.getPartnerId()));
        }
        if (findPieceDetails.getMasterAirwayBill() != null && !findPieceDetails.getMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("masterAirwayBill");
            predicates.add(group.in(findPieceDetails.getMasterAirwayBill()));
        }
        if (findPieceDetails.getHouseAirwayBill() != null && !findPieceDetails.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findPieceDetails.getHouseAirwayBill()));
        }
        if (findPieceDetails.getPieceId() != null && !findPieceDetails.getPieceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceId");
            predicates.add(group.in(findPieceDetails.getPieceId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}

