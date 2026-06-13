package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.consignmentType.ConsignmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ConsignmentTypeRepository extends JpaRepository<ConsignmentType, String>, JpaSpecificationExecutor<ConsignmentType> {

    Optional<ConsignmentType> findByCompanyIdAndLanguageIdAndConsignmentTypeIdAndDeletionIndicator(
            String companyId, String languageId, String consignmentTypeId, Long deletionIndicator);

}
