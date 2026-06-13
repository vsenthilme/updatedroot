package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.npr.Npr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface NprRepository extends JpaRepository<Npr,Long>, JpaSpecificationExecutor<Npr> {
    boolean existsByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(String languageId, String companyId, String pickupId, long l);

    Optional<Npr> findByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(String languageId, String companyId, String pickupId, long l);
}
