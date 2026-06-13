package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.invoice.FindInvoiceLine;
import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaInvoiceLine;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceLineSpecification implements Specification<ReplicaInvoiceLine> {

    FindInvoiceLine findInvoiceLine;

    public InvoiceLineSpecification(FindInvoiceLine inputSearchParams) {
        this.findInvoiceLine = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaInvoiceLine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (findInvoiceLine.getInvoiceNo() != null && !findInvoiceLine.getInvoiceNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("invoiceNo");
            predicates.add(group.in(findInvoiceLine.getInvoiceNo()));
        }
        if (findInvoiceLine.getCompanyId() != null && !findInvoiceLine.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findInvoiceLine.getCompanyId()));
        }
        if (findInvoiceLine.getLanguageId() != null && !findInvoiceLine.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findInvoiceLine.getLanguageId()));
        }
        if (findInvoiceLine.getPartnerMasterAirwayBill() != null && !findInvoiceLine.getPartnerMasterAirwayBill().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("partnerMasterAirwayBill");
            predicates.add(group.in(findInvoiceLine.getPartnerMasterAirwayBill()));
        }


        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
