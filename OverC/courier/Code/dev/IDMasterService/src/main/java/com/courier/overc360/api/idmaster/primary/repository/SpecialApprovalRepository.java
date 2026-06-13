package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.specialapproval.SpecialApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface SpecialApprovalRepository extends JpaRepository<SpecialApproval, String>, JpaSpecificationExecutor<SpecialApproval> {

    Optional<SpecialApproval> findByCompanyIdAndLanguageIdAndSpecialApprovalIdAndDeletionIndicator(
            String companyId, String languageId, String specialApprovalId, Long deletionIndicator);

}
