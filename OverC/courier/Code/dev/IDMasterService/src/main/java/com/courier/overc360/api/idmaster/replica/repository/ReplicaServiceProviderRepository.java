package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.serviceprovider.ReplicaServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReplicaServiceProviderRepository extends JpaRepository<ReplicaServiceProvider, String>,
        JpaSpecificationExecutor<ReplicaServiceProvider> {

    Optional<ReplicaServiceProvider> findByCompanyIdAndLanguageIdAndServiceProvidersIdAndDeletionIndicator
            (String companyId, String languageId, String serviceProvidersId, Long deletionIndicator);
}
