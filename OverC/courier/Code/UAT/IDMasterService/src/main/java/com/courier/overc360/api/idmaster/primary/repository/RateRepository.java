package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.rate.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RateRepository extends JpaRepository<Rate, String>, JpaSpecificationExecutor<Rate> {

    Optional<Rate> findByLanguageIdAndCompanyIdAndPartnerIdAndRateParameterIdAndLineNoAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String rateParameterId, Long lineNo, Long deletionIndicator);

}
