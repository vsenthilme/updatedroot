package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.RetailPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface RetailPriceRepository extends JpaRepository<RetailPrice, String>, JpaSpecificationExecutor<RetailPrice> {

    List<RetailPrice> findByCompanyIdAndLanguageIdAndPartnerIdAndDeletionIndicator(String companyId, String languageId, String partnerId, Long deletionIndicator);
}
