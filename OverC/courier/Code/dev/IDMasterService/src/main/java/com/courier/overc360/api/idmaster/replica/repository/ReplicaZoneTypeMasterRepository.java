package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.zonetypemaster.ReplicaZoneTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaZoneTypeMasterRepository extends JpaRepository<ReplicaZoneTypeMaster, String>, JpaSpecificationExecutor<ReplicaZoneTypeMaster> {

    Optional<ReplicaZoneTypeMaster> findByCompanyIdAndLanguageIdAndZoneTypeIdAndDeletionIndicator
            (String companyId, String languageId, String zoneTypeId, Long deletionIndicator);


    @Query(value = "Select CONCAT (ZONE_TYPE_ID, ' - ', ZONE_TYPE_TEXT) From tblzonetypemaster " +
            "Where ZONE_TYPE_ID IN (:zoneType) and IS_DELETED = 0", nativeQuery = true)
    String getZoneTypeText(@Param("zoneType") String zoneType);
}
