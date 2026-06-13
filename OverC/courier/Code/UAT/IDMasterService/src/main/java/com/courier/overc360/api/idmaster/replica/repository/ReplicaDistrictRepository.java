package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.district.ReplicaDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaDistrictRepository extends JpaRepository<ReplicaDistrict, String>, JpaSpecificationExecutor<ReplicaDistrict> {

    Optional<ReplicaDistrict> findByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String provinceId, String districtId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndCountryIdAndProvinceIdAndDistrictIdAndDeletionIndicator(
            String languageId, String companyId, String countryId, String provinceId, String districtId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndDistrictIdAndDeletionIndicator(
            String languageId, String companyId, String districtId, Long deletionIndicator);

}
