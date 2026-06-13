package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.district.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface DistrictRepository extends JpaRepository<District, String>, JpaSpecificationExecutor<District> {

    Optional<District> findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndDeletionIndicator
            (String languageId, String companyId, String countryId, String provinceId, String districtId, Long deletionIndicator);


}
