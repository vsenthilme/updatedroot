package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.hub.ReplicaHub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaHubRepository extends JpaRepository<ReplicaHub, String>, JpaSpecificationExecutor<ReplicaHub> {

    Optional<ReplicaHub> findByLanguageIdAndCompanyIdAndHubCodeAndDeletionIndicator(
            String languageId, String companyId, String hubCode, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndHubCodeAndDeletionIndicator(
            String languageId, String companyId, String hubCode, Long deletionIndicator);


    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tcm.C_ID, ' - ', tcm.C_NAME) As companyDesc, \n" +
            "CONCAT (tcn.COUNTRY_ID, ' - ', tcn.COUNTRY_NAME) As countryDesc, \n" +
            "CONCAT (tp.CITY_ID, ' - ', tp.CITY_NAME) As cityDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tcm on tcm.LANG_ID = tl.LANG_ID \n" +
            "Join tblcountry tcn on tcn.LANG_ID = tl.LANG_ID and tcn.C_ID = tcm.C_ID \n" +
            "Join tblcity tp on tp.LANG_ID = tl.LANG_ID and tp.C_ID = tcm.C_ID and tp.COUNTRY_ID = tcn.COUNTRY_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tcm.C_ID IN (:companyId) and \n" +
            "tcn.COUNTRY_ID IN (:countryId) and \n" +
            "tp.CITY_ID IN (:cityId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tcm.IS_DELETED = 0 and \n" +
            "tcn.IS_DELETED = 0 and \n" +
            "tp.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "countryId") String countryId,
                                 @Param(value = "cityId") String cityId);


}
