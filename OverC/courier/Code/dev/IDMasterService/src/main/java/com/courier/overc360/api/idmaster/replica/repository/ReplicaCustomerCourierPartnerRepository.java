package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.customercourierpartner.ReplicaCustomerCourierPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
@Repository
public interface ReplicaCustomerCourierPartnerRepository extends JpaRepository<ReplicaCustomerCourierPartner, String>,
        JpaSpecificationExecutor<ReplicaCustomerCourierPartner> {

    Optional<ReplicaCustomerCourierPartner> findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
            (String companyId, String languageId, String courierPartnerId, String partnerId, Long deletionIndicator);
}
