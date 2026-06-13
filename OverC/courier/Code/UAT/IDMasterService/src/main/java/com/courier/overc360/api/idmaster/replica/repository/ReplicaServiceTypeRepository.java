package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.serviceType.ReplicaServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaServiceTypeRepository extends JpaRepository<ReplicaServiceType, String>, JpaSpecificationExecutor<ReplicaServiceType> {

    Optional<ReplicaServiceType> findByCompanyIdAndLanguageIdAndServiceTypeIdAndDeletionIndicator(
            String companyId, String languageId, String serviceTypeId, Long deletionIndicator);

}
