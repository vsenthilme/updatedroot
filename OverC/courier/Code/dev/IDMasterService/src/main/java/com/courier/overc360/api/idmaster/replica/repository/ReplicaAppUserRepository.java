package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.airportcode.ReplicaAirportCode;
import com.courier.overc360.api.idmaster.replica.model.appuser.ReplicaAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
public interface ReplicaAppUserRepository extends JpaRepository<ReplicaAppUser, String>, JpaSpecificationExecutor<ReplicaAppUser> {

    Optional<ReplicaAppUser> findByCompanyIdAndLanguageIdAndAppUserIdAndDeletionIndicator(
            String companyId, String languageId, String appUserId, Long deletionIndicator);

    boolean existsByCompanyIdAndLanguageIdAndAppUserIdAndDeletionIndicator(
            String companyId, String languageId, String appUserId, Long deletionIndicator);


}
