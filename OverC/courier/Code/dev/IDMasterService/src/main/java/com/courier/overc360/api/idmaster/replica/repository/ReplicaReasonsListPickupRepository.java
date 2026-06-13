package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.reasonslistpickup.ReplicaReasonsListPickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaReasonsListPickupRepository extends JpaRepository<ReplicaReasonsListPickup, String>, JpaSpecificationExecutor<ReplicaReasonsListPickup> {

    Optional<ReplicaReasonsListPickup> findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
            String companyId, String languageId, String reasonsId, Long deletionIndicator);

}
