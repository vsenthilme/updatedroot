package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.RescheduleDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RescheduleDeliveryRepository extends JpaRepository<RescheduleDelivery, Long>{

    Optional<RescheduleDelivery> findByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
            String languageId, String companyId, String deliveryId, Long deletionIndicator
    );

    boolean existsByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
            String languageId, String companyId, String deliveryId, Long deletionIndicator
    );
}
