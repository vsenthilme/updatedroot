package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.debrief.Debrief;
import com.courier.overc360.api.midmile.primary.model.delivery.Delivery;
import com.courier.overc360.api.midmile.replica.model.debrief.ReplicaDebrief;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaDebriefRepository extends JpaRepository<ReplicaDebrief, String>, JpaSpecificationExecutor<ReplicaDebrief> {

    Optional<Debrief> findByLanguageIdAndCompanyIdAndCourierIdAndDeletionIndicator(
            String languageId, String companyId, String courierId, Long deletionIndicator
    );

    @Query(value = "SELECT COUNT(COURIER_ID) courier FROM tbldelivery " +
            "WHERE COURIER_ID = :courierId AND " +
            "STATUS_ID = (:statusId) " +
            "AND IS_DELETED = 0 ", nativeQuery = true)
    public Long pickupDeliveryCount(@Param("courierId") String courierId,
                                    @Param("statusId") String statusId);

    @Query(value = "SELECT COUNT(CASH_COLLECTED) FROM tbldelivery " +
            "WHERE COURIER_ID = :courierId " +
            "AND IS_DELETED = 0 " +
            "AND ONLINE_COLLECTED > 0", nativeQuery = true)
    public Long pickupDeliveryAmountSum(@Param("courierId") String courierId);

    @Query(value = "SELECT COUNT(ONLINE_COLLECTED) FROM tbldelivery " +
            "WHERE COURIER_ID = :courierId " +
            "AND IS_DELETED = 0 " +
            "AND ONLINE_COLLECTED > 0", nativeQuery = true)
    public Long noOfPainLink(@Param("courierId") String courierId);

    @Query(value = "SELECT MAX(d.STATUS_TIMESTAMP) " +
            "FROM tbldelivery d " +
            "WHERE d.MANIFEST_NUMBER = :manifestNumber " +
            "AND d.STATUS_TEXT LIKE %:statusText% " +
            "AND d.IS_DELETED = 0", nativeQuery = true)
    Date departureTime(@Param("manifestNumber") String manifestNumber,
                       @Param("statusText") String statusText);

    @Query(value = "SELECT top 1 d.STATUS_TIMESTAMP " +
            "FROM tbldelivery d " +
            "WHERE d.MANIFEST_NUMBER = :manifestNumber " +
            "AND d.STATUS_TEXT LIKE %:statusText% " +
            "AND d.IS_DELETED = 0 " +
            "ORDER BY d.STATUS_TIMESTAMP ASC", nativeQuery = true)
    Date firstStop(@Param("manifestNumber") String manifestNumber,
                   @Param("statusText") String statusText);

    @Query(value = "SELECT top 1 d.STATUS_TIMESTAMP " +
            "FROM tbldelivery d " +
            "WHERE d.MANIFEST_NUMBER = :manifestNumber " +
            "AND d.STATUS_TEXT LIKE %:statusText% " +
            "AND d.IS_DELETED = 0 " +
            "ORDER BY d.STATUS_TIMESTAMP DESC", nativeQuery = true)
    Date lastStop(@Param("manifestNumber") String manifestNumber,
                  @Param("statusText") String statusText);

}
