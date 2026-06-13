package com.courier.overc360.api.idmaster.primary.repository;


import com.courier.overc360.api.idmaster.primary.model.fulFillmentPrice.FulfillmentPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface FulfillmentPriceRepository extends JpaRepository<FulfillmentPrice,String>, JpaSpecificationExecutor<FulfillmentPrice> {
    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);

    Optional<FulfillmentPrice> findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);
}
