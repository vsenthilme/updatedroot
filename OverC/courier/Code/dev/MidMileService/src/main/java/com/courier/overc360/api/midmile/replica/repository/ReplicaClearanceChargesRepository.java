package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.clearancecharges.ReplicaClearanceCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaClearanceChargesRepository extends JpaRepository<ReplicaClearanceCharges, String>, JpaSpecificationExecutor<ReplicaClearanceCharges> {

    Optional<ReplicaClearanceCharges> findByClearanceChargesIdAndDeletionIndicator
            (Long clearanceChargesId, Long deletionIndicator);

    @Query(value = "select * from tblclearancecharges " +
            " where is_deleted = 0 and c_id in (:companyId) " +
            "and lang_id in (:languageId) and SUB_CUSTOMER_ID in (:partnerId) ", nativeQuery = true)
    public List<ReplicaClearanceCharges> getClearance(@Param("companyId") String companyId,
                                                      @Param("languageId") String languageId,
                                                      @Param("partnerId") String partnerId);

    @Query(value = "Select \n" +
            "CONCAT (ts.STATUS_ID, ' - ', ts.STATUS_TEXT) \n" +
            "From tblstatus ts \n" +
            "Where \n" +
            "ts.STATUS_ID IN (:statusId) and \n" +
            "ts.IS_DELETED = 0", nativeQuery = true)
    String getStatusDescription(@Param(value = "statusId") String statusId);

    @Query(value = "select top 1 * from tblclearancecharges \n" +
            " where is_deleted = 0 and c_id in (:companyId) and NO_OF_SHIPMENTS_FROM <= :shipments AND NO_OF_SHIPMENTS_TO >= :shipments \n" +
            "and lang_id in (:languageId) and SUB_CUSTOMER_ID in (:subCustomerId) order by CTD_ON desc ", nativeQuery = true)
    public ReplicaClearanceCharges getClearanceCharge(@Param("companyId") String companyId,
                                                      @Param("languageId") String languageId,
                                                      @Param("subCustomerId") String subCustomerId,
                                                      @Param("shipments") Long shipments);

    @Query(value = "select count(sub_customer_id) from tblprealert " +
            "where sub_customer_id = :subCustomerId and is_deleted = 0", nativeQuery = true)
    Long countCustomerId(@Param("subCustomerId") String subCustomerId);

}