package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaLMDInvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaLMDInvoiceLineRepository extends JpaRepository<ReplicaLMDInvoiceLine, Long>, JpaSpecificationExecutor<ReplicaLMDInvoiceLine> {


    Optional<ReplicaLMDInvoiceLine> findByCompanyIdAndLanguageIdAndCustomerIdAndInvoiceNoAndLineNumberAndDeletionIndicator(
            String companyId, String languageId, String customerId, String invoiceNo, Long lineNumber, Long deletionIndicator);

}
