package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CurrencyRepository extends JpaRepository<Currency, String>, JpaSpecificationExecutor<Currency> {

    Optional<Currency> findByCurrencyIdAndDeletionIndicator(
            String currencyId, Long deletionIndicator);


}

