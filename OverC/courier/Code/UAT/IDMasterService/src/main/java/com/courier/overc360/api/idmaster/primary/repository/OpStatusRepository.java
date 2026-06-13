package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.opstatus.OpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface OpStatusRepository extends JpaRepository<OpStatus, String>, JpaSpecificationExecutor<OpStatus> {

    Optional<OpStatus> findByLanguageIdAndCompanyIdAndStatusCodeAndDeletionIndicator(
            String languageId, String companyId, String statusCode, Long deletionIndicator);

}
