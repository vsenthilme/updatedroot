package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.storagetypemaster.ReplicaStorageTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReplicaStorageTypeMasterRepository extends JpaRepository<ReplicaStorageTypeMaster, String>,
        JpaSpecificationExecutor<ReplicaStorageTypeMaster> {

    Optional<ReplicaStorageTypeMaster> findByCompanyIdAndLanguageIdAndStorageTypeIdAndDeletionIndicator
            (String companyId, String languageId, String storageTypeId, Long deletionIndicator);

}
