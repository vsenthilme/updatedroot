package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.statusevent.StatusEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface StatusEventRepository extends JpaRepository<StatusEvent,String>, JpaSpecificationExecutor<StatusEvent> {

    Optional<StatusEvent> findByCompanyIdAndLanguageIdAndTypeIdAndDeletionIndicator
            (String companyId, String languageId, String typeId, Long deletionIndicator);
}
