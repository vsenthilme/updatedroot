package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.subproduct.ReplicaSubProduct;
import com.courier.overc360.api.idmaster.replica.model.timeslot.ReplicaTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaTimeSlotRepository extends JpaRepository<ReplicaTimeSlot,String>, JpaSpecificationExecutor<ReplicaTimeSlot> {


    Optional<ReplicaTimeSlot> findByLanguageIdAndCompanyIdAndTimeSlotIdAndDeletionIndicator(String languageId, String companyId, String timeslotId, long l);
}
