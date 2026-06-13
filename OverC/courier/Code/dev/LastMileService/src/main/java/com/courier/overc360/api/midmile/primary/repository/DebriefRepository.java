package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.debrief.Debrief;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface DebriefRepository extends JpaRepository<Debrief, String>, JpaSpecificationExecutor<Debrief> {

    Optional<Debrief> findByLanguageIdAndCompanyIdAndCourierIdAndDeletionIndicator(
            String languageId, String companyId, String courierId, Long deletionIndicator
    );
}
