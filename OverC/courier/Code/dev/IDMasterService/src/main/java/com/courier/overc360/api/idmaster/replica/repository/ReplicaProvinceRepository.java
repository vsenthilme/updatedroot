package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.province.ReplicaProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaProvinceRepository extends JpaRepository<ReplicaProvince, String>, JpaSpecificationExecutor<ReplicaProvince> {

    Optional<ReplicaProvince> findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String provinceId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String provinceId, Long deletionIndicator);

    // Get Province Name
    @Query(value = "Select \n" +
            "CONCAT (tp.PROVINCE_ID, ' - ', tp.PROVINCE_NAME) \n" +
            "From tblprovince tp \n" +
            "Where \n" +
            "tp.LANG_ID IN (:languageId) and \n" +
            "tp.C_ID IN (:companyId) and \n" +
            "tp.COUNTRY_ID IN (:countryId) and \n" +
            "tp.PROVINCE_ID IN (:provinceId) and \n" +
            "tp.IS_DELETED = 0", nativeQuery = true)
    String getProvinceName(@Param(value = "languageId") String languageId,
                           @Param(value = "companyId") String companyId,
                           @Param(value = "countryId") String countryId,
                           @Param(value = "provinceId") String provinceId);


    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tc.C_ID, ' - ', tc.C_NAME) As companyDesc, \n" +
            "CONCAT (tcn.COUNTRY_ID, ' - ',  tcn.COUNTRY_NAME) As countryDesc, \n" +
            "CONCAT (tp.PROVINCE_ID, ' - ', tp.PROVINCE_NAME) As provinceDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.LANG_ID=tc.LANG_ID \n" +
            "Join tblcountry tcn on tl.LANG_ID=tcn.LANG_ID and tc.C_ID=tcn.C_ID \n" +
            "Join tblprovince tp on tl.LANG_ID=tp.LANG_ID and tc.C_ID=tp.C_ID and tcn.COUNTRY_ID=tp.COUNTRY_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tc.C_ID IN (:companyId) and \n" +
            "tcn.COUNTRY_ID IN (:countryId) and \n" +
            "tp.PROVINCE_ID IN (:provinceId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tc.IS_DELETED = 0 and \n" +
            "tcn.IS_DELETED = 0 and \n" +
            "tp.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "countryId") String countryId,
                                 @Param(value = "provinceId") String provinceId);


}
