package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.invoice.InvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface InvoiceHeaderRepository extends JpaRepository<InvoiceHeader, Long>, JpaSpecificationExecutor<InvoiceHeader> {
    Optional<InvoiceHeader> findByCompanyIdAndLanguageIdAndInvoiceNoAndDeletionIndicator(
            String companyId, String languageId, String invoiceNo, Long deletionIndicator);
}
