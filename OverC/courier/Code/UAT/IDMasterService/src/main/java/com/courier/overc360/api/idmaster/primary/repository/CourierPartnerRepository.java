package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.courierpartner.CourierPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CourierPartnerRepository extends JpaRepository<CourierPartner, String>, JpaSpecificationExecutor<CourierPartner> {

    Optional<CourierPartner> findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
            (String companyId, String languageId, String courierPartnerId, String partnerId, Long deletionIndicator);
}
