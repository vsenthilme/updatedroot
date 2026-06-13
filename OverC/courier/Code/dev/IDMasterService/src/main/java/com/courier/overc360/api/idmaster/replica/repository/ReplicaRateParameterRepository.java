package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.rateparameter.ReplicaRateParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaRateParameterRepository extends JpaRepository<ReplicaRateParameter, String>, JpaSpecificationExecutor<ReplicaRateParameter> {

    Optional<ReplicaRateParameter> findByLanguageIdAndCompanyIdAndRateParameterIdAndDeletionIndicator(
            String languageId, String companyId, String rateParameterId, Long DeletionIndicator);

//    Optional<Status> findByLanguageIdAndCompanyIdAndDeletionIndicator(
//            String languageId, String companyId,String StatusId, Long DeletionIndicator);

}
