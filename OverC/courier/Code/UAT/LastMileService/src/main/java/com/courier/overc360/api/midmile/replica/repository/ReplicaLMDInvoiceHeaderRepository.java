package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaLMDInvoiceHeader;
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
public interface ReplicaLMDInvoiceHeaderRepository extends JpaRepository<ReplicaLMDInvoiceHeader, Long>, JpaSpecificationExecutor<ReplicaLMDInvoiceHeader> {


    boolean existsByCompanyIdAndLanguageIdAndInvoiceNoAndCustomerIdAndDeletionIndicator(
            String companyId, String languageId, String invoiceNo, String customerId, Long deletionIndicator);

    Optional<ReplicaLMDInvoiceHeader> findByCompanyIdAndLanguageIdAndInvoiceNoAndCustomerIdAndDeletionIndicator(
            String companyId, String languageId, String invoiceNo, String customerId, Long deletionIndicator);

    @Query(value = "SELECT MAX(C_ID) companyId, MAX(LANG_ID) langId, SUM(CEILING_VALUE) ceilingValue, SUM(CHARGEABLE_WEIGHT) chargeableWeight, SUM(FRIGHT_CHARGE) frightCharge, " +
            "SUM(COD_CHARGE) codCharge, SUM(FULFILMENT_CHARGE) fulfilmentCharge, SUM(RTO_CHARGE) rtoCharge, SUM(ASR_CHARGE) asrCharge, SUM(MOVEMENT_CHARGE) movementCharge, SUM(TRUCK_CHARGE) truckCharge " +
            "FROM tblconsignment_entity " +
            "WHERE PARTNER_ID = :partnerId AND " +
            "(COALESCE(:startDate, NULL) IS NULL OR CAST(CTD_ON AS datetime2) BETWEEN CAST(:startDate AS datetime2) AND CAST(:endDate AS datetime2))" +
//            "AND CTD_ON BETWEEN :startDate AND :endDate " +
            "GROUP BY PARTNER_ID", nativeQuery = true)
    IKeyValuePair getChargeValueForDateWise(@Param("partnerId") String partnerId,
                                            @Param("startDate") Date startDate,
                                            @Param("endDate") Date endDate);

    @Query(value = "SELECT MAX(C_ID) companyId, " +
            "MAX(LANG_ID) langId, " +
            "MAX(C_NAME) companyDesc, " +
            "MAX(LANG_TEXT) langDesc, " +
            "MAX(PARTNER_NAME) customerName, " +
            "SUM(CEILING_VALUE) ceilingValue, " +
            "SUM(CHARGEABLE_WEIGHT) chargeableWeight, " +
            "SUM(FRIGHT_CHARGE) frightCharge, " +
            "SUM(COD_CHARGE) codCharge, " +
            "SUM(FULFILMENT_CHARGE) fulfilmentCharge, " +
            "SUM(RTO_CHARGE) rtoCharge, " +
            "SUM(ASR_CHARGE) asrCharge, " +
            "SUM(MOVEMENT_CHARGE) movementCharge, " +
            "SUM(TRUCK_CHARGE) truckCharge, " +
            "SUM(CASE WHEN CEILING_VALUE IS NOT NULL AND CEILING_VALUE != 0 THEN 1 ELSE 0 END) AS ceilingValueCount," +
            "SUM(CASE WHEN CHARGEABLE_WEIGHT IS NOT NULL AND CHARGEABLE_WEIGHT != 0 THEN 1 ELSE 0 END) AS chargeableWeightCount, " +
            "SUM(CASE WHEN FRIGHT_CHARGE IS NOT NULL AND FRIGHT_CHARGE != 0 THEN 1 ELSE 0 END) AS frightChargeCount, " +
            "SUM(CASE WHEN COD_CHARGE IS NOT NULL AND COD_CHARGE != 0 THEN 1 ELSE 0 END) AS codChargeCount, " +
            "SUM(CASE WHEN FULFILMENT_CHARGE IS NOT NULL AND FULFILMENT_CHARGE != 0 THEN 1 ELSE 0 END) AS fulfilmentChargeCount, " +
            "SUM(CASE WHEN RTO_CHARGE IS NOT NULL AND RTO_CHARGE != 0 THEN 1 ELSE 0 END) AS rtoChargeCount, " +
            "SUM(CASE WHEN ASR_CHARGE IS NOT NULL AND ASR_CHARGE != 0 THEN 1 ELSE 0 END) AS asrChargeCount, " +
            "SUM(CASE WHEN MOVEMENT_CHARGE IS NOT NULL AND MOVEMENT_CHARGE != 0 THEN 1 ELSE 0 END) AS movementChargeCount, " +
            "SUM(CASE WHEN TRUCK_CHARGE IS NOT NULL AND TRUCK_CHARGE != 0 THEN 1 ELSE 0 END) AS truckChargeCount " +
            "FROM tblconsignment_entity " +
            "WHERE PARTNER_ID = :partnerId " +
            "AND HAWB_TYP_ID != 37 " +
            "AND CTD_ON BETWEEN :startDate AND :endDate " +
            "AND IS_DELETED = 0 " +
            "GROUP BY PARTNER_ID", nativeQuery = true)
    IKeyValuePair getChargeValue(@Param("partnerId") String partnerId,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);
}
