package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.customsclearanceinvoice.FindCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.replica.model.customsclearanceinvoice.ReplicaCustomsClearanceInvoice;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaCustomsClearanceInvoiceSpecification implements Specification<ReplicaCustomsClearanceInvoice> {

    FindCustomsClearanceInvoice findCustomsClearanceInvoice;

    public ReplicaCustomsClearanceInvoiceSpecification(FindCustomsClearanceInvoice inputSearchParams) {
        this.findCustomsClearanceInvoice = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaCustomsClearanceInvoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (findCustomsClearanceInvoice.getLanguageId() != null && !findCustomsClearanceInvoice.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findCustomsClearanceInvoice.getLanguageId()));
        }
        if (findCustomsClearanceInvoice.getCompanyId() != null && !findCustomsClearanceInvoice.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findCustomsClearanceInvoice.getCompanyId()));
        }
        if (findCustomsClearanceInvoice.getPartnerHouseAirwayBill() != null && !findCustomsClearanceInvoice.getPartnerHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerHouseAirwayBill");
            predicates.add(group.in(findCustomsClearanceInvoice.getPartnerHouseAirwayBill()));
        }
        if (findCustomsClearanceInvoice.getHouseAirwayBill() != null && !findCustomsClearanceInvoice.getHouseAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("houseAirwayBill");
            predicates.add(group.in(findCustomsClearanceInvoice.getHouseAirwayBill()));
        }
        if (findCustomsClearanceInvoice.getInvoiceNo() != null && !findCustomsClearanceInvoice.getInvoiceNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("invoiceNo");
            predicates.add(group.in(findCustomsClearanceInvoice.getInvoiceNo()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
