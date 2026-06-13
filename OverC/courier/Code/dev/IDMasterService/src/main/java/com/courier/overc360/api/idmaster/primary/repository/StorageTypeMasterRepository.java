package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.storagetypemaster.StorageTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface StorageTypeMasterRepository extends JpaRepository<StorageTypeMaster, String>, JpaSpecificationExecutor<StorageTypeMaster> {

    Optional<StorageTypeMaster> findByCompanyIdAndLanguageIdAndStorageTypeIdAndDeletionIndicator
            (String companyId, String languageId, String storageTypeId, Long deletionIndicator);


}
