package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.rate.ReplicaRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaRateRepository extends JpaRepository<ReplicaRate, String>, JpaSpecificationExecutor<ReplicaRate> {

    Optional<ReplicaRate> findByLanguageIdAndCompanyIdAndPartnerIdAndRateParameterIdAndLineNoAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String rateParameterId, Long lineNo, Long deletionIndicator);

    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tc.C_ID, ' - ', tc.C_NAME) As companyDesc, \n" +
            "CONCAT (trp.RATE_PARAMETER_ID, ' - ', trp.RATE_PARAMETER_TEXT) As rateParameterDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.LANG_ID = tc.LANG_ID \n" +
            "Join tblrateparameter trp on tl.LANG_ID = trp.LANG_ID and tc.C_ID = trp.C_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and\n" +
            "tc.C_ID IN (:companyId) and \n" +
            "trp.RATE_PARAMETER_ID IN (:rateParameterId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tc.IS_DELETED = 0 and \n" +
            "trp.IS_DELETED = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId,
                                 @Param(value = "rateParameterId") String rateParameterId);

    @Query(value = "Select \n" +
            "CONCAT (ts.STATUS_ID, ' - ', ts.STATUS_TEXT) \n" +
            "From tblstatus ts \n" +
            "Where \n" +
            "ts.STATUS_ID IN (:statusId) and \n" +
            "ts.IS_DELETED = 0", nativeQuery = true)
    String getStatusDescription(@Param("statusId") String statusId);

}
