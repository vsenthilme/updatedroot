package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.serviceType.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ServiceTypeRepository extends JpaRepository<ServiceType, String>, JpaSpecificationExecutor<ServiceType> {

    Optional<ServiceType> findByCompanyIdAndLanguageIdAndServiceTypeIdAndDeletionIndicator(
            String companyId, String languageId, String serviceTypeId, Long deletionIndicator);

}
