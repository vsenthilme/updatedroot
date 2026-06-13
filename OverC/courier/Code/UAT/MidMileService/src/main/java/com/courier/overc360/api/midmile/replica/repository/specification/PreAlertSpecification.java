package com.courier.overc360.api.midmile.replica.repository.specification;


import com.courier.overc360.api.midmile.replica.model.prealert.FindPreAlert;
import com.courier.overc360.api.midmile.replica.model.prealert.ReplicaPreAlert;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PreAlertSpecification implements Specification<ReplicaPreAlert> {

    FindPreAlert findPreAlert;

    public PreAlertSpecification(FindPreAlert inputSearchParams) {
        this.findPreAlert = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaPreAlert> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findPreAlert.getLanguageId() != null && !findPreAlert.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findPreAlert.getLanguageId()));
        }
        if (findPreAlert.getCompanyId() != null && !findPreAlert.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findPreAlert.getCompanyId()));
        }
        if (findPreAlert.getPartnerId() != null && !findPreAlert.getPartnerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerId");
            predicates.add(group.in(findPreAlert.getPartnerId()));
        }
        if (findPreAlert.getPartnerMasterAirwayBill() != null && !findPreAlert.getPartnerMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerMasterAirwayBill");
            predicates.add(group.in(findPreAlert.getPartnerMasterAirwayBill()));
        }
        if (findPreAlert.getPartnerHouseAirwayBill() != null && !findPreAlert.getPartnerHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerHouseAirwayBill");
            predicates.add(group.in(findPreAlert.getPartnerHouseAirwayBill()));
        }
        if (findPreAlert.getHsCode() != null && !findPreAlert.getHsCode().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("hsCode");
            predicates.add(group.in(findPreAlert.getHsCode()));
        }
        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
