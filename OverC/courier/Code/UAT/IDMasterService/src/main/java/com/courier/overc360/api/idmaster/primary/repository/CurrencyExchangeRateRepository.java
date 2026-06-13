package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.currencyExchangeRate.CurrenyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrenyExchangeRate,String>, JpaSpecificationExecutor<CurrenyExchangeRate> {

    Optional<CurrenyExchangeRate> findByLanguageIdAndCompanyIdAndFromCurrencyIdAndToCurrencyIdAndDeletionIndicator
            (String languageId, String companyId, String fromCurrencyId, String toCurrencyId,Long deletionIndicator);
}
