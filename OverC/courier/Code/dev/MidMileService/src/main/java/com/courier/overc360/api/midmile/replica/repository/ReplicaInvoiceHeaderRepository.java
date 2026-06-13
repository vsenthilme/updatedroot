package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaInvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaInvoiceHeaderRepository extends JpaRepository<ReplicaInvoiceHeader, Long>, JpaSpecificationExecutor<ReplicaInvoiceHeader> {
    Optional<ReplicaInvoiceHeader> findByCompanyIdAndLanguageIdAndInvoiceNoAndDeletionIndicator(
            String companyId, String languageId, String invoiceNo, Long deletionIndicator);
}
