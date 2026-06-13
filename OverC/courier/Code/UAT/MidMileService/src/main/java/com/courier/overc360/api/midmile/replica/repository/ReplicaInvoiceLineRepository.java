package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaInvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaInvoiceLineRepository extends JpaRepository<ReplicaInvoiceLine, Long>, JpaSpecificationExecutor<ReplicaInvoiceLine> {
    Optional<ReplicaInvoiceLine> findByCompanyIdAndLanguageIdAndInvoiceNoAndPartnerMasterAirwayBillAndDeletionIndicator(
            String companyId, String languageId, String invoiceNo, String partnerMasterAirwayBill, Long deletionIndicator);
}
