package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.currency.ReplicaCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCurrencyRepository extends JpaRepository<ReplicaCurrency, String>, JpaSpecificationExecutor<ReplicaCurrency> {

    Optional<ReplicaCurrency> findByCurrencyIdAndDeletionIndicator(String currencyId, Long deletionIndicator);

    @Query(value = "Select \n" +
            "tl.currency_text \n" +
            "From tblcurrency tl \n" +
            "Where \n" +
            "tl.currency_id IN (:currencyId) and \n" +
            "tl.is_deleted = 0", nativeQuery = true)
    String getCurrencyDesc(@Param("currencyId") String currencyId);

}

