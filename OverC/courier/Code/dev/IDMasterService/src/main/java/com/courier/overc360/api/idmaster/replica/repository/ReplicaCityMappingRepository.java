package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.citymapping.ReplicaCityMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCityMappingRepository extends JpaRepository<ReplicaCityMapping, String>, JpaSpecificationExecutor<ReplicaCityMapping> {

    Optional<ReplicaCityMapping> findByLanguageIdAndCompanyIdAndCityIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String cityId, String partnerId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndCityIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String cityId, String partnerId, Long deletionIndicator);


    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tc.C_ID, ' - ', tc.C_NAME) As companyDesc, \n" +
            "CONCAT (tp.CITY_ID, ' - ', tp.CITY_NAME) As cityDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.LANG_ID = tc.LANG_ID \n" +
            "Join tblcity tp on tl.LANG_ID = tp.LANG_ID and tc.C_ID = tp.C_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tc.C_ID IN (:companyId) and \n" +
            "tp.CITY_ID IN (:cityId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tc.IS_DELETED = 0 and \n" +
            "tp.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "cityId") String cityId);

}
