package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.country.ReplicaCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCountryRepository extends JpaRepository<ReplicaCountry, String>, JpaSpecificationExecutor<ReplicaCountry> {

    Optional<ReplicaCountry> findByLanguageIdAndCompanyIdAndCountryIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndCountryIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, Long deletionIndicator);


    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tcm.C_ID, ' - ', tcm.C_NAME) As companyDesc, \n" +
            "CONCAT (tcn.COUNTRY_ID, ' - ', tcn.COUNTRY_NAME) As countryDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tcm on tl.LANG_ID=tcm.LANG_ID \n" +
            "Join tblcountry tcn on tcn.LANG_ID=tl.LANG_ID and tcn.C_ID=tcm.C_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tcm.C_ID IN (:companyId) and \n" +
            "tcn.COUNTRY_ID IN (:countryId) and \n" +
            "tl.is_deleted = 0 and \n" +
            "tcm.is_deleted = 0 and \n" +
            "tcn.is_deleted = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "countryId") String countryId);


    // Get Country Desc
    @Query(value = "Select \n" +
            "CONCAT (tc.COUNTRY_ID, ' - ', tc.COUNTRY_NAME) \n" +
            "From tblcountry tc \n" +
            "Where \n" +
            "tc.LANG_ID IN (:languageId) and \n" +
            "tc.C_ID IN (:companyId) and \n" +
            "tc.COUNTRY_ID IN (:countryId) and \n" +
            "tc.IS_DELETED = 0", nativeQuery = true)
    String getCountryDesc(@Param(value = "countryId") String countryId,
                          @Param(value = "languageId") String languageId,
                          @Param(value = "companyId") String companyId);

}
