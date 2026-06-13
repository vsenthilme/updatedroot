package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.invoice.InvoiceHeader;
import com.courier.overc360.api.midmile.primary.model.invoice.InvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long>, JpaSpecificationExecutor<InvoiceLine> {
    Optional<InvoiceLine> findByCompanyIdAndLanguageIdAndInvoiceNoAndPartnerMasterAirwayBillAndDeletionIndicator(
            String companyId, String languageId, String invoiceNo, String partnerMasterAirwayBill, Long deletionIndicator);
}
