package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.invoice.LMDInvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface LMDInvoiceLineRepository extends JpaRepository<LMDInvoiceLine, Long>, JpaSpecificationExecutor<LMDInvoiceLine> {


    Optional<LMDInvoiceLine> findByCompanyIdAndLanguageIdAndCustomerIdAndInvoiceNoAndLineNumberAndDeletionIndicator(
            String companyId, String languageId, String customerId, String invoiceNo, Long lineNumber, Long deletionIndicator);

}
