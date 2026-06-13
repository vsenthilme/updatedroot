package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.currencyExchangeRate.ReplicaCurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCurrencyExchangeRateRepository extends JpaRepository<ReplicaCurrencyExchangeRate, String>, JpaSpecificationExecutor<ReplicaCurrencyExchangeRate> {

    Optional<ReplicaCurrencyExchangeRate> findByLanguageIdAndCompanyIdAndFromCurrencyIdAndToCurrencyIdAndDeletionIndicator
            (String languageId, String companyId, String fromCurrencyId, String toCurrencyId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndFromCurrencyIdAndToCurrencyIdAndDeletionIndicator(
            String languageId, String companyId, String fromCurrencyId, String toCurrencyId, Long deletionIndicator);

}
