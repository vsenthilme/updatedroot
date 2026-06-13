package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.zonemaster.ZoneMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ZoneMasterRepository extends JpaRepository<ZoneMaster, String>, JpaSpecificationExecutor<ZoneMaster> {

    Optional<ZoneMaster> findByCompanyIdAndLanguageIdAndZoneIdAndZoneTypeAndHubCodeAndDeletionIndicator
            (String companyId, String languageId, String zoneId, String zoneType, String hubCode, Long deletionIndicator);

}
