package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.logicmaster.ReplicaLogicMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaLogicMasterRepository extends JpaRepository<ReplicaLogicMaster, String>, JpaSpecificationExecutor<ReplicaLogicMaster> {

    Optional<ReplicaLogicMaster> findByCompanyIdAndLanguageIdAndConsoleCountIdAndDeletionIndicator
            (String companyId, String languageId, String consoleCountId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndConsoleCountIdAndDeletionIndicator(
            String languageId, String companyId, String consoleCountId, Long deletionIndicator);

}
