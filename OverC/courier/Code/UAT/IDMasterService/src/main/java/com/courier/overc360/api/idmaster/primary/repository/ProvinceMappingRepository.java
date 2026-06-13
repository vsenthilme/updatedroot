package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.provincemapping.ProvinceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ProvinceMappingRepository extends JpaRepository<ProvinceMapping, String>, JpaSpecificationExecutor<ProvinceMapping> {

    Optional<ProvinceMapping> findByLanguageIdAndCompanyIdAndProvinceIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String provinceId, String partnerId, Long deletionIndicator);

}
