package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.statusevent.ReplicaStatusEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaStatusEventRepository extends JpaRepository<ReplicaStatusEvent,String>, JpaSpecificationExecutor<ReplicaStatusEvent> {

    Optional<ReplicaStatusEvent> findByCompanyIdAndLanguageIdAndTypeIdAndDeletionIndicator
            (String companyId, String languageId, String typeId, Long deletionIndicator);
}
