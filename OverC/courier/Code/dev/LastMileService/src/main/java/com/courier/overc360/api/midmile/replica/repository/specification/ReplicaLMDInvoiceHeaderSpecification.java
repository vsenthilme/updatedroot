package com.courier.overc360.api.midmile.replica.repository.specification;

import com.courier.overc360.api.midmile.replica.model.invoice.FindLMDInvoiceHeader;
import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaLMDInvoiceHeader;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReplicaLMDInvoiceHeaderSpecification implements Specification<ReplicaLMDInvoiceHeader> {
    FindLMDInvoiceHeader findLMDInvoiceHeader;

    public ReplicaLMDInvoiceHeaderSpecification(FindLMDInvoiceHeader inputSearchParams) {
        this.findLMDInvoiceHeader = inputSearchParams;
    }

    public Predicate toPredicate(Root<ReplicaLMDInvoiceHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (findLMDInvoiceHeader.getLanguageId() != null && !findLMDInvoiceHeader.getLanguageId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("languageId");
            predicates.add(group.in(findLMDInvoiceHeader.getLanguageId()));
        }
        if (findLMDInvoiceHeader.getCompanyId() != null && !findLMDInvoiceHeader.getCompanyId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("companyId");
            predicates.add(group.in(findLMDInvoiceHeader.getCompanyId()));
        }
        if (findLMDInvoiceHeader.getCustomerId() != null && !findLMDInvoiceHeader.getCustomerId().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("customerId");
            predicates.add(group.in(findLMDInvoiceHeader.getCustomerId()));
        }
        if (findLMDInvoiceHeader.getInvoiceNo() != null && !findLMDInvoiceHeader.getInvoiceNo().isEmpty()) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("invoiceNo");
            predicates.add(group.in(findLMDInvoiceHeader.getInvoiceNo()));
        }
        if (findLMDInvoiceHeader.getInvoiceStatus() != null) {
            final Path<DeferredImportSelector.Group> group = root.<DeferredImportSelector.Group>get("invoiceStatus");
            predicates.add(group.in(findLMDInvoiceHeader.getInvoiceStatus()));
        }

        predicates.add(cb.equal(root.get("deletionIndicator"), 0L));
        return cb.and(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
    }
}
