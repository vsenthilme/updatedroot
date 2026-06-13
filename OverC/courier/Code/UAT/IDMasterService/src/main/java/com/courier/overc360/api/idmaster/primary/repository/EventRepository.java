package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event,String>, JpaSpecificationExecutor<Event> {

    Optional<Event> findByLanguageIdAndCompanyIdAndEventCodeAndDeletionIndicator(
            String languageId, String companyId, String eventCode, Long deletionIndicator);
}
