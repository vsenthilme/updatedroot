package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.movingshipmentpricelist.ReplicaMovingShipmentPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReplicaMovingShipmentPriceListRepository extends JpaRepository<ReplicaMovingShipmentPriceList, String>, JpaSpecificationExecutor<ReplicaMovingShipmentPriceList> {

    Optional<ReplicaMovingShipmentPriceList> findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
            String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator
    );

    @Query(value = "Select \n" +
            "CONCAT (ts.STATUS_ID, ' - ', ts.STATUS_TEXT) \n" +
            "From tblstatus ts \n" +
            "Where \n" +
            "ts.STATUS_ID IN (:statusId) and \n" +
            "ts.IS_DELETED = 0", nativeQuery = true)
    String getStatusDescription(@Param("statusId") String statusId);
}
