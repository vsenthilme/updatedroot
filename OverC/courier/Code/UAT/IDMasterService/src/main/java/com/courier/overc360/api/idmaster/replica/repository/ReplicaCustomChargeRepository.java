package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.customcharges.ReplicaCustomCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface ReplicaCustomChargeRepository extends JpaRepository<ReplicaCustomCharges,String>, JpaSpecificationExecutor<ReplicaCustomCharges> {


    boolean existsByLanguageIdAndCompanyIdAndDeletionIndicator(String languageId, String companyId, long l);
}
