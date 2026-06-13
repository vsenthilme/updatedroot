package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.specialapproval.ReplicaSpecialApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaSpecialApprovalRepository extends JpaRepository<ReplicaSpecialApproval, String>, JpaSpecificationExecutor<ReplicaSpecialApproval> {

    Optional<ReplicaSpecialApproval> findByCompanyIdAndLanguageIdAndSpecialApprovalIdAndDeletionIndicator(
            String companyId, String languageId, String specialApprovalId, Long deletionIndicator);

}
