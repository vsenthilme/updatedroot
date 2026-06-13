package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.airportcode.ReplicaAirportCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaAirportCodeRepository extends JpaRepository<ReplicaAirportCode, String>, JpaSpecificationExecutor<ReplicaAirportCode> {

    Optional<ReplicaAirportCode> findByCompanyIdAndLanguageIdAndAirportCodeAndDeletionIndicator(
            String companyId, String languageId, String airportCode, Long deletionIndicator);

    boolean existsByCompanyIdAndLanguageIdAndAirportCodeAndDeletionIndicator(
            String companyId, String languageId, String airportCode, Long deletionIndicator);


}
