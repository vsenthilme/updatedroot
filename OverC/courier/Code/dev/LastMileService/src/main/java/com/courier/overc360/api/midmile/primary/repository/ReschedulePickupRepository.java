package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.npr.Npr;
import com.courier.overc360.api.midmile.primary.model.reschedulepickup.ReSchedulePickUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ReschedulePickupRepository extends JpaRepository<ReSchedulePickUp,Long> , JpaSpecificationExecutor<ReSchedulePickUp> {
    boolean existsByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(String languageId, String companyId, String pickupId, long l);

    Optional<ReSchedulePickUp> findByLanguageIdAndCompanyIdAndPickupIdAndRescheduleNoAndDeletionIndicator(String languageId, String companyId, String pickupId,Long rescheduleNo, long l);
}
