package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.iata.ReplicaIata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaIataRepository extends JpaRepository<ReplicaIata, String>, JpaSpecificationExecutor<ReplicaIata> {

    Optional<ReplicaIata> findByLanguageIdAndCompanyIdAndOriginAndOriginCodeAndIataKdAndDeletionIndicator(
            String languageId, String companyId, String origin, String originCode, String iataKd, Long deletionIndicator);

    Optional<ReplicaIata> findByLanguageIdAndCompanyIdAndOriginAndOriginCodeAndDeletionIndicator(
            String languageId, String companyId, String origin, String originCode, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndOriginAndOriginCodeAndDeletionIndicator(
            String languageId, String companyId, String origin, String originCode, Long deletionIndicator);

}
