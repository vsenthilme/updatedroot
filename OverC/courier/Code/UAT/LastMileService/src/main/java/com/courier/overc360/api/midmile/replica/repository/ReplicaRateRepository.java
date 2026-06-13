package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.delivery.ReplicaRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReplicaRateRepository extends JpaRepository<ReplicaRate, String>, JpaSpecificationExecutor<ReplicaRate> {

    List<ReplicaRate> findByCompanyIdAndLanguageIdAndPartnerIdAndDeletionIndicator(
            String companyId, String languageId, String partnerId, Long deletionIndicator);
}