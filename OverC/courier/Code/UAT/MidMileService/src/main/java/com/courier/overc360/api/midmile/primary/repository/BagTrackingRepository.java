package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.bagtracking.BagTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
public interface BagTrackingRepository extends JpaRepository<BagTracking, String>, JpaSpecificationExecutor<BagTracking> {

    Optional<BagTracking> findByLanguageIdAndCompanyIdAndPartnerIdAndConsignmentBagIdAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String partnerId, Long consignmentBagId, String houseAirwayBill, Long deletionIndicator);



}
