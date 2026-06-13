package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.uom.ReplicaUom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaUomRepository extends JpaRepository<ReplicaUom, String>, JpaSpecificationExecutor<ReplicaUom> {

    Optional<ReplicaUom> findByCompanyIdAndLanguageIdAndUomIdAndDeletionIndicator
            (String companyId, String languageId, String uomId, Long deletionIndicator);
}

