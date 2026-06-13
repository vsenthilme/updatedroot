package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.console.FindConsole;
import com.courier.overc360.api.midmile.replica.model.console.ReplicaConsole;
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
public class ConsoleSpecification implements Specification<ReplicaConsole> {

    FindConsole findConsole;

    public ConsoleSpecification(FindConsole inputSearchParams) {
        this.findConsole = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaConsole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findConsole.getLanguageId() != null && !findConsole.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findConsole.getLanguageId()));
        }
        if (findConsole.getCompanyId() != null && !findConsole.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findConsole.getCompanyId()));
        }
        if (findConsole.getPartnerId() != null && !findConsole.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findConsole.getPartnerId()));
        }
        if (findConsole.getPartnerMasterAirwayBill() != null && !findConsole.getPartnerMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerMasterAirwayBill");
            predicates.add(group.in(findConsole.getPartnerMasterAirwayBill()));
        }
        if (findConsole.getPartnerHouseAirwayBill() != null && !findConsole.getPartnerHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerHouseAirwayBill");
            predicates.add(group.in(findConsole.getPartnerHouseAirwayBill()));
        }
        if (findConsole.getConsoleId() != null && !findConsole.getConsoleId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("consoleId");
            predicates.add(group.in(findConsole.getConsoleId()));
        }
        if (findConsole.getUnconsolidatedFlag() != null && !findConsole.getUnconsolidatedFlag().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("unconsolidatedFlag");
            predicates.add(group.in(findConsole.getUnconsolidatedFlag()));
        }
        if (findConsole.getHawbTypeId() != null && !findConsole.getHawbTypeId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hawbTypeId");
            predicates.add(group.in(findConsole.getHawbTypeId()));
        }
        if (findConsole.getIncoTerm() != null && !findConsole.getIncoTerm().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("incoTerm");
            predicates.add(group.in(findConsole.getIncoTerm()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
