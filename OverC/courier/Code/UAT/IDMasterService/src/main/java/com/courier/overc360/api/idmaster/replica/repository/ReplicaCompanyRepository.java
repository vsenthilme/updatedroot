package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.company.ReplicaCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCompanyRepository extends JpaRepository<ReplicaCompany, String>, JpaSpecificationExecutor<ReplicaCompany> {

    Optional<ReplicaCompany> findByCompanyIdAndLanguageIdAndDeletionIndicator(
            String companyId, String languageId, Long deletionIndicator);

    boolean existsByCompanyIdAndLanguageIdAndDeletionIndicator(
            String companyId, String languageId, Long deletionIndicator);

    // Get Description
    @Query(value = "Select \n" +
            "CONCAT (tl.LANG_ID, ' - ', tl.LANG_TEXT) As langDesc, \n" +
            "CONCAT (tc.C_ID, ' - ', tc.C_NAME) As companyDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.LANG_ID = tc.LANG_ID \n" +
            "Where \n" +
            "tl.LANG_ID IN (:languageId) and \n" +
            "tc.C_ID IN (:companyId) and \n" +
            "tl.IS_DELETED = 0 and \n" +
            "tc.is_deleted = 0", nativeQuery = true)
    IKeyValuePair getDescription(@Param(value = "languageId") String languageId,
                                 @Param(value = "companyId") String companyId);

    // Delete Validation Query for Company delete
    @Query(value = "Select COUNT (*) From ( \n" +
            "Select 1 As col From tblsubproduct Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblservicetype Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblconsignmenttype Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblloadtype Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblcountry Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblrateparameter Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblhub Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblopstatus Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tbliata Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblspecialapproval Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblhscode Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblairportcode Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblevent Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            "Union All \n" +
            "Select 1 As col From tblcurrencyexchangerate Where LANG_ID IN (:languageId) and C_ID IN (:companyId) and IS_DELETED = 0 \n" +
            ") AS temp", nativeQuery = true)
    Long getCompanyCount(@Param(value = "languageId") String languageId,
                         @Param(value = "companyId") String companyId);

}