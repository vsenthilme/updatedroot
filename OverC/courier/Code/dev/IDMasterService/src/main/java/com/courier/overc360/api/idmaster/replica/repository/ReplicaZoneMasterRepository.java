package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.zonemaster.ReplicaZoneMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaZoneMasterRepository extends JpaRepository<ReplicaZoneMaster, String>, JpaSpecificationExecutor<ReplicaZoneMaster> {

    Optional<ReplicaZoneMaster> findByCompanyIdAndLanguageIdAndZoneIdAndZoneTypeAndHubCodeAndDeletionIndicator
    (String companyId, String languageId, String zoneId,String zoneType, String hubCode, Long deletionIndicator);
}
