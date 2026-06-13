package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.districtMapping.ReplicaDistrictMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaDistrictMappingRepository extends JpaRepository<ReplicaDistrictMapping, String>, JpaSpecificationExecutor<ReplicaDistrictMapping> {

    Optional<ReplicaDistrictMapping> findByLanguageIdAndCompanyIdAndPartnerIdAndDistrictIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String districtId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndDistrictIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String districtId, Long deletionIndicator);


    //Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tc.C_ID, ' - ', tc.C_NAME) As companyDesc, \n" +
            "CONCAT (td.DISTRICT_ID, ' - ', td.DISTRICT_NAME) As districtDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tc.LANG_ID = tl.LANG_ID \n" +
            "Join tbldistrict td on td.LANG_ID = tl.LANG_ID and td.C_ID = tc.C_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tc.C_ID IN (:companyId) and \n" +
            "td.DISTRICT_ID IN (:districtId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tc.IS_DELETED = 0 and \n" +
            "td.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "districtId") String districtId);

}
