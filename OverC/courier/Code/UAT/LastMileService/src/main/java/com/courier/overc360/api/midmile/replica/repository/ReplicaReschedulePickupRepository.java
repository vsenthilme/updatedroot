package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.replica.model.reschedulepickup.ReplicaReSchedulePickUp;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
@Transactional
public interface ReplicaReschedulePickupRepository extends JpaRepository<ReplicaReSchedulePickUp, Long>,
        JpaSpecificationExecutor<ReplicaReSchedulePickUp> {
    Optional<ReplicaReSchedulePickUp> findByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(String languageId, String companyId, String pickupId, long l);
}
