package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.finance.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RateRepository extends JpaRepository<Rate, String>, JpaSpecificationExecutor<Rate> {

    Optional<Rate> findByLanguageIdAndCompanyIdAndPartnerIdAndRateParameterIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String rateParameterId, Long deletionIndicator);


    List<Rate> findByCompanyIdAndLanguageIdAndPartnerIdAndDeletionIndicator(
            String companyId, String languageId, String partnerId, Long deletionIndicator);


}
