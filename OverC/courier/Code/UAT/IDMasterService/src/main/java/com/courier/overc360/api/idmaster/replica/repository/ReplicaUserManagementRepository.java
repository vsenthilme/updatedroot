package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.user.ReplicaUserManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaUserManagementRepository extends JpaRepository<ReplicaUserManagement, Long>, JpaSpecificationExecutor<ReplicaUserManagement> {


    public List<ReplicaUserManagement> findByUserIdAndDeletionIndicator(String userId, Long deletionIndicator);

    Optional<ReplicaUserManagement> findByLanguageIdAndCompanyIdAndUserIdAndDeletionIndicator(
            String languageId, String companyId, String userId, Long deletionIndicator);
}