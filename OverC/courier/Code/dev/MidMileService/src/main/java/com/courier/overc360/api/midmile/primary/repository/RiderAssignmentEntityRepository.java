package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.riderassignment.RiderAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RiderAssignmentEntityRepository extends JpaRepository<RiderAssignmentEntity, Long> {

    Optional<RiderAssignmentEntity> findByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId, Long deletionIndicator);

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId, Long deletionIndicator);

}
