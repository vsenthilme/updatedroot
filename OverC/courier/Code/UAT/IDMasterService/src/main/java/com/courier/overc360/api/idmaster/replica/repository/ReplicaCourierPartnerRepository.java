package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.courierpartner.ReplicaCourierPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReplicaCourierPartnerRepository extends JpaRepository<ReplicaCourierPartner, String>,
        JpaSpecificationExecutor<ReplicaCourierPartner> {

    Optional<ReplicaCourierPartner> findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
            (String companyId, String languageId, String courierPartnerId, String partnerId, Long deletionIndicator);
}
