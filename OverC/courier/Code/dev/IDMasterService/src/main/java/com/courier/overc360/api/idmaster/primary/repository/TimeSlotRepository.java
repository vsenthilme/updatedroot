package com.courier.overc360.api.idmaster.primary.repository;


import com.courier.overc360.api.idmaster.primary.model.timeslot.TimeSlot;
import com.courier.overc360.api.idmaster.primary.model.uom.Uom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TimeSlotRepository extends JpaRepository<TimeSlot, String>, JpaSpecificationExecutor<TimeSlot> {
    boolean existsByLanguageIdAndCompanyIdAndTimeSlotIdAndDeletionIndicator(String languageId, String companyId, String timeSlotId, long l);

    Optional<TimeSlot> findByLanguageIdAndCompanyIdAndTimeSlotIdAndDeletionIndicator(String languageId, String companyId, String timeSlotId, long l);
}
