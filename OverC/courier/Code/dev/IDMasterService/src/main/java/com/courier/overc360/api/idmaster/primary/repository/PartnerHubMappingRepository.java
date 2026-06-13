package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.partnerhubmapping.PartnerHubMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PartnerHubMappingRepository extends JpaRepository<PartnerHubMapping, String>, JpaSpecificationExecutor<PartnerHubMapping> {

    Optional<PartnerHubMapping> findByLanguageIdAndCompanyIdAndHubCodeAndPartnerTypeAndPartnerIdAndProductCodeAndDeletionIndicator(
            String languageId, String companyId, String hubCode, String partnerType, String partnerId, String productCode, Long deletionIndicator);

}
