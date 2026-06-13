package com.courier.overc360.api.idmaster.primary.repository;


import com.courier.overc360.api.idmaster.primary.model.retailPriceList.RetailPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional
public interface RetailPriceRepository extends JpaRepository<RetailPrice, String>, JpaSpecificationExecutor<RetailPrice> {
    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);

    Optional<RetailPrice> findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);
}
