package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.citymapping.CityMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CityMappingRepository extends JpaRepository<CityMapping, String>, JpaSpecificationExecutor<CityMapping> {

    Optional<CityMapping> findByLanguageIdAndCompanyIdAndCityIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String cityId, String partnerId, Long deletionIndicator);

}
