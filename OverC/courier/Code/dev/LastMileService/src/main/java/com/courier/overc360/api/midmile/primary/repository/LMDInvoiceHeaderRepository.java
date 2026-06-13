package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.invoice.LMDInvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
@Transactional
public interface LMDInvoiceHeaderRepository extends JpaRepository<LMDInvoiceHeader, Long>, JpaSpecificationExecutor<LMDInvoiceHeader> {


    boolean existsByCompanyIdAndLanguageIdAndCustomerIdAndDeletionIndicator(
            String companyId, String languageId, String customerId, Long deletionIndicator);

    Optional<LMDInvoiceHeader> findByCompanyIdAndLanguageIdAndInvoiceNoAndCustomerIdAndDeletionIndicator(
            String companyId, String languageId, String invoiceNo, String customerId, Long deletionIndicator);


    @Query(value = "SELECT MAX(CTD_ON) from tbllmdinvoiceheader where is_deleted = 0 and CUSTOMER_ID = :customerId", nativeQuery = true)
    public Date getDate(@Param("customerId") String customerId);

}
