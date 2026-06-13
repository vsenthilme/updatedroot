package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.airportcode.AirportCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AirportCodeRepository extends JpaRepository<AirportCode, String>, JpaSpecificationExecutor<AirportCode> {

    Optional<AirportCode> findByCompanyIdAndLanguageIdAndAirportCodeAndDeletionIndicator(
            String companyId, String languageId, String airportCode, Long deletionIndicator);

}
