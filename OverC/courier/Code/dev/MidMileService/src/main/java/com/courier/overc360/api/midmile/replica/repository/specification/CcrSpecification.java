package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.ccr.FindCcr;
import com.courier.overc360.api.midmile.replica.model.ccr.ReplicaCcr;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CcrSpecification implements Specification<ReplicaCcr> {

    FindCcr findCcrHeader;

    public CcrSpecification(FindCcr inputSearchParams) {
        this.findCcrHeader = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCcr> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCcrHeader.getLanguageId() != null && !findCcrHeader.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCcrHeader.getLanguageId()));
        }
        if (findCcrHeader.getCompanyId() != null && !findCcrHeader.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCcrHeader.getCompanyId()));
        }
        if (findCcrHeader.getPartnerId() != null && !findCcrHeader.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findCcrHeader.getPartnerId()));
        }
        if (findCcrHeader.getPartnerMasterAirwayBill() != null && !findCcrHeader.getPartnerMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerMasterAirwayBill");
            predicates.add(group.in(findCcrHeader.getPartnerMasterAirwayBill()));
        }
        if (findCcrHeader.getPartnerHouseAirwayBill() != null && !findCcrHeader.getPartnerHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerHouseAirwayBill");
            predicates.add(group.in(findCcrHeader.getPartnerHouseAirwayBill()));
        }
        if (findCcrHeader.getConsoleId() != null && !findCcrHeader.getConsoleId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("consoleId");
            predicates.add(group.in(findCcrHeader.getConsoleId()));
        }
        if (findCcrHeader.getCcrId() != null && !findCcrHeader.getCcrId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("ccrId");
            predicates.add(group.in(findCcrHeader.getCcrId()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
