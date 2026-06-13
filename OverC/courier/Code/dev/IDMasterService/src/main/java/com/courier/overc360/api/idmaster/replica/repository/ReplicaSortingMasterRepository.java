package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.sortingmaster.ReplicaSortingMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
@Transactional
public interface ReplicaSortingMasterRepository extends JpaRepository<ReplicaSortingMaster,String>, JpaSpecificationExecutor<ReplicaSortingMaster> {


    boolean existsByLanguageIdAndCompanyIdAndSortingIdAndZoneTypeAndDeletionIndicator(String languageId, String companyId, String sortingId, String zoneType, Long deletionIndicator);

    Optional<ReplicaSortingMaster> findByLanguageIdAndCompanyIdAndSortingIdAndZoneTypeAndDeletionIndicator(String languageId, String companyId, String sortingId, String zoneType, long l);



}
