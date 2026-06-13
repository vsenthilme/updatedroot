package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.countryMapping.CountryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CountryMappingRepository extends JpaRepository<CountryMapping, String>, JpaSpecificationExecutor<CountryMapping> {

    Optional<CountryMapping> findByLanguageIdAndCompanyIdAndCountryIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String partnerId, Long deletionIndicator);


}
