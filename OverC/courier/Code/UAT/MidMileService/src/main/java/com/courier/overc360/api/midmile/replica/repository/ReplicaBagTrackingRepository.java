package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.bagtracking.ReplicaBagTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaBagTrackingRepository extends JpaRepository<ReplicaBagTracking, String>, JpaSpecificationExecutor<ReplicaBagTracking> {

    Optional<ReplicaBagTracking> findByLanguageIdAndCompanyIdAndPartnerIdAndConsignmentBagIdAndHouseAirwayBillAndDeletionIndicator
            (String languageId, String companyId, String partnerId, Long consignmentBagId, String houseAirwayBill, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndDeletionIndicator
            (String languageId, String companyId, String partnerId, String houseAirwayBill, Long deletionIndicator);

}
