package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.customercourierpartner.CustomerCourierPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
@Repository
public interface CustomerCourierPartnerRepository extends JpaRepository<CustomerCourierPartner, String>, JpaSpecificationExecutor<CustomerCourierPartner> {

    Optional<CustomerCourierPartner> findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
            (String companyId, String languageId, String courierPartnerId, String partnerId, Long deletionIndicator);

}
