package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CountryRepository extends JpaRepository<Country, String>, JpaSpecificationExecutor<Country> {

    Optional<Country> findByLanguageIdAndCompanyIdAndCountryIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, Long deletionIndicator);

    // Updating CountryName in Province, District, City, Hub, CountryMapping and DistrictMapping tables
    @Transactional
    @Procedure(procedureName = "country_desc_update_proc")
    void countryDescUpdateProc(
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId,
            @Param(value = "countryId") String countryId,
            @Param(value = "oldCountryDesc") String oldCountryDesc,
            @Param(value = "newCountryDesc") String newCountryDesc);

}
