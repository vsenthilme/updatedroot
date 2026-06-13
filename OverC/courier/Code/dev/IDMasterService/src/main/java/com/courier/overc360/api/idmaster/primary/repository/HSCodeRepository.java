package com.courier.overc360.api.idmaster.primary.repository;


import com.courier.overc360.api.idmaster.primary.model.hsCode.HSCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface HSCodeRepository extends JpaRepository<HSCode, String>, JpaSpecificationExecutor<HSCode> {

    Optional<HSCode> findByLanguageIdAndCompanyIdAndHsCodeAndSpecialApprovalIdAndDeletionIndicator(
            String languageId, String companyId, String hsCode, String SpecialApprovalId, Long deletionIndicator);

}
