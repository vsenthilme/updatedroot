package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.hub.Hub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface HubRepository extends JpaRepository<Hub, String>, JpaSpecificationExecutor<Hub> {

    Optional<Hub> findByLanguageIdAndCompanyIdAndHubCodeAndDeletionIndicator(
            String languageId, String companyId, String hubCode, Long deletionIndicator);

}
