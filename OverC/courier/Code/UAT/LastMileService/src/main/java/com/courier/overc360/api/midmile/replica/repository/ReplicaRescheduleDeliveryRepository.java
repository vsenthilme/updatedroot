package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.RescheduleDelivery;
import com.courier.overc360.api.midmile.replica.model.rescheduledelivery.ReplicaRescheduleDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaRescheduleDeliveryRepository extends JpaRepository<ReplicaRescheduleDelivery, Long>, JpaSpecificationExecutor<ReplicaRescheduleDelivery> {

    Optional<ReplicaRescheduleDelivery> findByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
            String languageId, String companyId, String deliveryId, Long deletionIndicator
    );

    boolean existsByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
            String languageId, String companyId, String deliveryId, Long deletionIndicator
    );
}
