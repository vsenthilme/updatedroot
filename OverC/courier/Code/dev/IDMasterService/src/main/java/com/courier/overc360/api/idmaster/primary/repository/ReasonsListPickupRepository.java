package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.reasonslistpickup.ReasonsListPickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReasonsListPickupRepository extends JpaRepository<ReasonsListPickup, String>, JpaSpecificationExecutor<ReasonsListPickup> {

    Optional<ReasonsListPickup> findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
            String companyId, String languageId, String reasonsId, Long deletionIndicator);

}
