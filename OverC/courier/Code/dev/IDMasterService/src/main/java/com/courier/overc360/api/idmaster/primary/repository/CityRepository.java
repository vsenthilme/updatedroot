package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.city.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CityRepository extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {

    Optional<City> findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndCityIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId, Long deletionIndicator);


    // Updating CityName in CityMapping table
    @Transactional
    @Procedure(procedureName = "city_desc_update_proc")
    void cityDescUpdateProc(
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId,
            @Param(value = "cityId") String cityId,
            @Param(value = "oldCityDesc") String oldCityDesc,
            @Param(value = "newCityDesc") String newCityDesc);

}
