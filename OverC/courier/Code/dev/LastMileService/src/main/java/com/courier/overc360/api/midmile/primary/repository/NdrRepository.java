package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.ndr.Ndr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface NdrRepository extends JpaRepository<Ndr, Long>, JpaSpecificationExecutor<Ndr> {

    Optional<Ndr> findByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
            String languageId, String companyId, String deliveryId, long deletionIndicator);
}
