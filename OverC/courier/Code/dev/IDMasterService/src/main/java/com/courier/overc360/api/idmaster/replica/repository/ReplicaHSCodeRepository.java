package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.hsCode.ReplicaHSCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaHSCodeRepository extends JpaRepository<ReplicaHSCode, String>, JpaSpecificationExecutor<ReplicaHSCode> {

    Optional<ReplicaHSCode> findByLanguageIdAndCompanyIdAndHsCodeAndSpecialApprovalIdAndDeletionIndicator(
            String languageId, String companyId, String hsCode, String specialApprovalId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndHsCodeAndSpecialApprovalIdAndDeletionIndicator(
            String languageId, String companyId, String hsCode, String specialApprovalId, Long deletionIndicator);


    // Get Special Approval Desc
    @Query(value = "Select \n" +
            "CONCAT (ta.SPECIAL_APPROVAL_ID, ' - ', ta.SPECIAL_APPROVAL_TEXT) \n" +
            "From tblspecialapproval ta \n" +
            "Where \n" +
            "ta.LANG_ID IN (:languageId) and \n" +
            "ta.C_ID IN (:companyId) and \n" +
            "ta.SPECIAL_APPROVAL_ID IN (:specialApprovalId) and \n" +
            "ta.IS_DELETED = 0", nativeQuery = true)
    String getSpecialApprovalDesc(
            @Param(value = "specialApprovalId") String specialApprovalId,
            @Param(value = "languageId") String languageId,
            @Param(value = "companyId") String companyId);

}

