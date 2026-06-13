package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.invoice.FindInvoiceHeader;
import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaInvoiceHeader;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceHeaderSpecification implements Specification<ReplicaInvoiceHeader> {

    FindInvoiceHeader findInvoiceHeader;

    public InvoiceHeaderSpecification(FindInvoiceHeader inputSearchParams) {
        this.findInvoiceHeader = inputSearchParams;
    }

    @Override
    public Predicate toPredicate(Root<ReplicaInvoiceHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (findInvoiceHeader.getInvoiceNo() != null && !findInvoiceHeader.getInvoiceNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("invoiceNo");
            predicates.add(group.in(findInvoiceHeader.getInvoiceNo()));
        }
        if (findInvoiceHeader.getCompanyId() != null && !findInvoiceHeader.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findInvoiceHeader.getCompanyId()));
        }
        if (findInvoiceHeader.getLanguageId() != null && !findInvoiceHeader.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findInvoiceHeader.getLanguageId()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
