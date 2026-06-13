package com.courier.overc360.api.idmaster.replica.repository;


import com.courier.overc360.api.idmaster.replica.model.retailPriceList.ReplicaRetailPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional
public interface ReplicaRetailPriceRepository extends JpaRepository<ReplicaRetailPrice,String>, JpaSpecificationExecutor<ReplicaRetailPrice> {
    Optional<ReplicaRetailPrice> findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);
}
