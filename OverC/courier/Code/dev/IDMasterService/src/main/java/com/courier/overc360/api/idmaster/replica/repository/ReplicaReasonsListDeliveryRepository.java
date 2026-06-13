package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.reasonslistdelivery.ReplicaReasonsListDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaReasonsListDeliveryRepository extends JpaRepository<ReplicaReasonsListDelivery, String>, JpaSpecificationExecutor<ReplicaReasonsListDelivery> {

    Optional<ReplicaReasonsListDelivery> findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
            String companyId, String languageId, String reasonsId, Long deletionIndicator);
}
