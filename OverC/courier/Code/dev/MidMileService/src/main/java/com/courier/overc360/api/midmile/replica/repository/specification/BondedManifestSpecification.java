package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.bondedmanifest.FindBondedManifest;
import com.courier.overc360.api.midmile.replica.model.bondedmanifest.ReplicaBondedManifest;
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
public class BondedManifestSpecification implements Specification<ReplicaBondedManifest> {

    FindBondedManifest findBondedManifest;

    public BondedManifestSpecification(FindBondedManifest inputSearchParams) {
        this.findBondedManifest = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaBondedManifest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findBondedManifest.getLanguageId() != null && !findBondedManifest.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findBondedManifest.getLanguageId()));
        }
        if (findBondedManifest.getCompanyId() != null && !findBondedManifest.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findBondedManifest.getCompanyId()));
        }
        if (findBondedManifest.getPartnerId() != null && !findBondedManifest.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findBondedManifest.getPartnerId()));
        }
        if (findBondedManifest.getPartnerMasterAirwayBill() != null && !findBondedManifest.getPartnerMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerMasterAirwayBill");
            predicates.add(group.in(findBondedManifest.getPartnerMasterAirwayBill()));
        }
        if (findBondedManifest.getPartnerHouseAirwayBill() != null && !findBondedManifest.getPartnerHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerHouseAirwayBill");
            predicates.add(group.in(findBondedManifest.getPartnerHouseAirwayBill()));
        }
        if (findBondedManifest.getBondedId() != null && !findBondedManifest.getBondedId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("bondedId");
            predicates.add(group.in(findBondedManifest.getBondedId()));
        }
        if (findBondedManifest.getPieceId() != null && !findBondedManifest.getPieceId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceId");
            predicates.add(group.in(findBondedManifest.getPieceId()));
        }
        if (findBondedManifest.getPieceItemId() != null && !findBondedManifest.getPieceItemId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("pieceItemId");
            predicates.add(group.in(findBondedManifest.getPieceItemId()));
        }
        if (findBondedManifest.getHsCode() != null && !findBondedManifest.getHsCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hsCode");
            predicates.add(group.in(findBondedManifest.getHsCode()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
