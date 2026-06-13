package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.billingfrequency.BillingFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface BillingFrequencyRepository extends JpaRepository<BillingFrequency, String>, JpaSpecificationExecutor<BillingFrequency> {

    boolean existsByLanguageIdAndCompanyIdAndBillingFrequencyIdAndDeletionIndicator(String languageId, String companyId, String billingFrequencyId, Long deletionIndicator);

    Optional<BillingFrequency> findByLanguageIdAndCompanyIdAndBillingFrequencyIdAndDeletionIndicator(String languageId, String companyId, String billingFrequencyId, Long deletionIndicator);

}
