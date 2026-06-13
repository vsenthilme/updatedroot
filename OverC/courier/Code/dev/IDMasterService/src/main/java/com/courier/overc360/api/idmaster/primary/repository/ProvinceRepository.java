package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.province.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ProvinceRepository extends JpaRepository<Province, String>, JpaSpecificationExecutor<Province> {

    Optional<Province> findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String provinceId, Long deletionIndicator);

    Optional<Province> findByLanguageIdAndCompanyIdAndProvinceIdAndDeletionIndicator(
            String languageId, String companyId, String provinceId, Long deletionIndicator);

    // Updating ProvinceName in District, City, ProvinceMapping and DistrictMapping tables
    @Transactional
    @Procedure(procedureName = "province_desc_update_proc")
    void provinceDescUpdateProc(
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId,
            @Param(value = "countryId") String countryId,
            @Param(value = "provinceId") String provinceId,
            @Param(value = "oldProvinceDesc") String oldProvinceDesc,
            @Param(value = "newProvinceDesc") String newProvinceDesc);

}
