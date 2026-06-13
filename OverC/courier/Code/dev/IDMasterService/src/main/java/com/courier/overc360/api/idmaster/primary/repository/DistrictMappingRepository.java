package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.districtMapping.DistrictMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface DistrictMappingRepository extends JpaRepository<DistrictMapping, String>, JpaSpecificationExecutor<DistrictMapping> {

    Optional<DistrictMapping> findByLanguageIdAndCompanyIdAndPartnerIdAndDistrictIdAndDeletionIndicator
            (String languageId, String companyId, String partnerId, String districtId, Long deletionIndicator);


}
