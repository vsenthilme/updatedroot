package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.consignmentType.ReplicaConsignmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaConsignmentTypeRepository extends JpaRepository<ReplicaConsignmentType, String>, JpaSpecificationExecutor<ReplicaConsignmentType> {

    Optional<ReplicaConsignmentType> findByCompanyIdAndLanguageIdAndConsignmentTypeIdAndDeletionIndicator(
            String companyId, String languageId, String consignmentTypeId, Long deletionIndicator);

}