package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.console.unconsolidation.FindUnconsolidation;
import com.courier.overc360.api.midmile.replica.model.console.unconsolidation.ReplicaUnconsolidation;
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
public class UnconsolidationSpecification implements Specification<ReplicaUnconsolidation> {

    FindUnconsolidation findUnconsolidation;

    public UnconsolidationSpecification(FindUnconsolidation inputSearchParams) {
        this.findUnconsolidation = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaUnconsolidation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findUnconsolidation.getLanguageId() != null && !findUnconsolidation.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findUnconsolidation.getLanguageId()));
        }
        if (findUnconsolidation.getCompanyId() != null && !findUnconsolidation.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findUnconsolidation.getCompanyId()));
        }
        if (findUnconsolidation.getPartnerId() != null && !findUnconsolidation.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findUnconsolidation.getPartnerId()));
        }
        if (findUnconsolidation.getPartnerMasterAirwayBill() != null && !findUnconsolidation.getPartnerMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerMasterAirwayBill");
            predicates.add(group.in(findUnconsolidation.getPartnerMasterAirwayBill()));
        }
        if (findUnconsolidation.getPartnerHouseAirwayBill() != null && !findUnconsolidation.getPartnerHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerHouseAirwayBill");
            predicates.add(group.in(findUnconsolidation.getPartnerHouseAirwayBill()));
        }
        if (findUnconsolidation.getUnconsolidatedFlag() != null && !findUnconsolidation.getUnconsolidatedFlag().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("unconsolidatedFlag");
            predicates.add(group.in(findUnconsolidation.getUnconsolidatedFlag()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
