package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.partnerhubmapping.ReplicaPartnerHubMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaPartnerHubMappingRepository extends JpaRepository<ReplicaPartnerHubMapping, String>,
        JpaSpecificationExecutor<ReplicaPartnerHubMapping> {

    Optional<ReplicaPartnerHubMapping> findByLanguageIdAndCompanyIdAndHubCodeAndPartnerTypeAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String hubCode, String partnerType, String partnerId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndHubCodeAndPartnerTypeAndPartnerIdAndProductCodeAndDeletionIndicator(
            String languageId, String companyId, String hubCode, String partnerType, String partnerId, String productCode, Long deletionIndicator);


    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tc.C_ID, ' - ', tc.C_NAME) As companyDesc, \n" +
            "CONCAT (th.HUB_CODE, ' - ', th.hub_name) As hubDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.LANG_ID = tc.LANG_ID \n" +
            "Join tblhub th on tl.LANG_ID = th.LANG_ID and tc.C_ID = th.C_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tc.C_ID IN (:companyId) and \n" +
            "th.HUB_CODE IN (:hubCode) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tc.IS_DELETED = 0 and \n" +
            "th.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "hubCode") String hubCode);

    // Get Hub Category
    @Query(value = "Select th.HUB_CATEGORY \n" +
            "From tblhub th \n" +
            "Where\n" +
            "th.LANG_ID IN (:languageId) and \n" +
            "th.C_ID IN (:companyId) and \n" +
            "th.HUB_CODE IN (:hubCode) and \n" +
            "th.IS_DELETED = 0", nativeQuery = true)
    Optional<String> getHubCategory(@Param(value = "languageId") String languageId,
                                    @Param(value = "companyId") String companyId,
                                    @Param(value = "hubCode") String hubCode);

}
