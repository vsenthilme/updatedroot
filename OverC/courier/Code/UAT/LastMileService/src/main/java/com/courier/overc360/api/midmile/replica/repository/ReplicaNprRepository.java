package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.replica.model.npr.ReplicaNpr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaNprRepository extends JpaRepository<ReplicaNpr,Long> , JpaSpecificationExecutor<ReplicaNpr> {


    Optional<ReplicaNpr> findByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(String languageId, String companyId, String pickupId, long l);
}
