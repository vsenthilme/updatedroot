package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.route.ReplicaRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface ReplicaRouteRepository extends JpaRepository<ReplicaRoute, String>, JpaSpecificationExecutor<ReplicaRoute> {

    Optional<ReplicaRoute> findByCompanyIdAndLanguageIdAndRouteIdAndDeletionIndicator
            (String companyId, String languageId, String routeId, Long deletionIndicator);
}
