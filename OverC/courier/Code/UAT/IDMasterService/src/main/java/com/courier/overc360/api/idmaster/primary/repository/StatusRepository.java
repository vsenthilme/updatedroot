package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StatusRepository extends JpaRepository<Status, String>, JpaSpecificationExecutor<Status> {

    Optional<Status> findByLanguageIdAndStatusIdAndDeletionIndicator(
            String languageId, String statusId, Long deletionIndicator);

    List<Status> findByLanguageIdAndDeletionIndicator(String languageId, Long deletionIndicator);

}
