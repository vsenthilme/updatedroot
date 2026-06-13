package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.loadtype.LoadType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoadTypeRepository extends JpaRepository<LoadType, String>, JpaSpecificationExecutor<LoadType> {

    Optional<LoadType> findByLoadTypeIdAndLanguageIdAndCompanyIdAndDeletionIndicator
            (String loadTypeId, String languageId, String companyId, Long DeletionIndicator);
}
