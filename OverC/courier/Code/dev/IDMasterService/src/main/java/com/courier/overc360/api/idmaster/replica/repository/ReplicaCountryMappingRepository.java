package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.countryMapping.ReplicaCountryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCountryMappingRepository extends JpaRepository<ReplicaCountryMapping, String>, JpaSpecificationExecutor<ReplicaCountryMapping> {

    Optional<ReplicaCountryMapping> findByLanguageIdAndCompanyIdAndCountryIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String partnerId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndCountryIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String partnerId, Long deletionIndicator);


}
