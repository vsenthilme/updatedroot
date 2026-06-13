package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.city.ReplicaCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCityRepository extends JpaRepository<ReplicaCity, String>, JpaSpecificationExecutor<ReplicaCity> {

    Optional<ReplicaCity> findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndCityIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndCityIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String provinceId, String districtId, String cityId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndCityIdAndDeletionIndicator(
            String languageId, String companyId, String cityId, Long deletionIndicator);


    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tc.C_ID, ' - ', tc.C_NAME) As companyDesc, \n" +
            "CONCAT (tcn.COUNTRY_ID, ' - ',  tcn.COUNTRY_NAME) As countryDesc, \n" +
            "CONCAT (tp.PROVINCE_ID, ' - ', tp.PROVINCE_NAME) As provinceDesc, \n" +
            "CONCAT (td.DISTRICT_ID, ' - ', td.DISTRICT_NAME) As districtDesc, \n" +
            "td.DISTRICT_NAME As districtText, \n" +
            "tcn.COUNTRY_NAME As countryText, \n" +
            "tp.PROVINCE_NAME As provinceText \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.LANG_ID=tc.LANG_ID \n" +
            "Join tblcountry tcn on tl.LANG_ID=tcn.LANG_ID and tc.C_ID=tcn.C_ID \n" +
            "Join tblprovince tp on tl.LANG_ID=tp.LANG_ID and tc.C_ID=tp.C_ID and tcn.COUNTRY_ID=tp.COUNTRY_ID \n" +
            "Join tbldistrict td on tl.LANG_ID=td.LANG_ID and tc.C_ID=td.C_ID and tcn.COUNTRY_ID=td.COUNTRY_ID and tp.PROVINCE_ID=td.PROVINCE_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tc.C_ID IN (:companyId) and \n" +
            "tcn.COUNTRY_ID IN (:countryId) and \n" +
            "tp.PROVINCE_ID IN (:provinceId) and \n" +
            "td.DISTRICT_ID IN (:districtId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tc.IS_DELETED = 0 and \n" +
            "tcn.IS_DELETED = 0 and \n" +
            "tp.IS_DELETED = 0 and \n" +
            "td.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "countryId") String countryId,
                                 @Param(value = "provinceId") String provinceId,
                                 @Param(value = "districtId") String districtId);

    // Company Queries
    @Query(value = "select top 1 lang_text from tbllanguage where (lang_id = :languageId) and is_deleted =0", nativeQuery = true)
    String getLanguageDesc(@Param("languageId") String languageId);

    @Query(value = "select top 1 city_name from tblcity where (city_id = :cityId) and is_deleted = 0", nativeQuery = true)
    String getCityDesc(@Param("cityId") String cityId);

    @Query(value = "select top 1 country_name from tblcountry where (country_id = :countryId) and is_deleted = 0", nativeQuery = true)
    String getCountryDesc(@Param("countryId") String countryId);

    @Query(value = "select top 1 province_name from tblprovince where ( province_id = :provinceId) and is_deleted = 0", nativeQuery = true)
    String getProvinceDesc(@Param("provinceId") String provinceId);

    @Query(value = "select top 1 district_name from tbldistrict where (district_id = :districtId) and is_deleted = 0", nativeQuery = true)
    String getDistrictDesc(@Param("districtId") String districtId);

}
