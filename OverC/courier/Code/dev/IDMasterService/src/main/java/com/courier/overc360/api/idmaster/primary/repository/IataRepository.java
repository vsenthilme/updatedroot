package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.iata.Iata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface IataRepository extends JpaRepository<Iata, String>, JpaSpecificationExecutor<Iata> {
    Optional<Iata> findByLanguageIdAndCompanyIdAndOriginAndOriginCodeAndDeletionIndicator(
            String languageId, String companyId, String origin, String originCode,Long deletionIndicator);

}
