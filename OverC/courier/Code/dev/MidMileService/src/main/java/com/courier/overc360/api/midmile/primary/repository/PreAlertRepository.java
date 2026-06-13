package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PreAlertRepository extends JpaRepository<PreAlert, String> {

    Optional<PreAlert> findByCompanyIdAndLanguageIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
            String companyId, String languageId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill,Long deletionIndicator);


    @Modifying
    @Query(value = "UPDATE tblprealert SET BAYAN_HV = :totalDuty, CALCULATED_TOTAL_DUTY = :totalDuty " +
            "WHERE " +
            "C_ID = :companyId AND " +
            "LANG_ID = :languageId AND " +
            "PARTNER_ID = :partnerId AND " +
            "PARTNER_HOUSE_AIRWAY_BILL = :partnerHAB AND " +
            "PARTNER_MASTER_AIRWAY_BILL = :partnerMAB AND " +
            "IS_DELETED = 0 ", nativeQuery = true)
    public void updatePreAlert(@Param("companyId") String companyId,
                               @Param("languageId") String languageId,
                               @Param("partnerId") String partnerId,
                               @Param("partnerHAB") String partnerHAB,
                               @Param("partnerMAB") String partnerMAB,
                               @Param("totalDuty") Double totalDuty);

    @Modifying
    @Query(value = "update tblprealert set CUSTOM_DUTY = :customDuty where " +
            " c_id = :companyId and lang_id = :languageId and " +
            " partner_id = :partnerId and partner_house_airway_bill = :partnerHAB and " +
            " partner_master_airway_bill = :partnerMAB and is_deleted = 0 ", nativeQuery = true)
    public void updateCustomDuty(@Param("companyId") String companyId,
                               @Param("languageId") String languageId,
                               @Param("partnerId") String partnerId,
                               @Param("partnerHAB") String partnerHAB,
                               @Param("partnerMAB") String partnerMAB,
                               @Param("customDuty") Double customDuty);

    // Set SpecialApprovalValue in PreAlert Table for CustomCost Create
    @Modifying
    @Transactional
    @Query(value = "update tblprealert " +
            " set SPECIAL_APPROVAL_CHARGE = :specialApp " +
            " where PARTNER_MASTER_AIRWAY_BILL = :pmab and " +
            " c_id = :companyId and lang_id = :languageId and " +
            " partner_id = :partnerId and is_deleted = 0", nativeQuery = true)
    public void updateSpecialApproval(@Param("pmab") String pmab,
                                      @Param("companyId") String companyId,
                                      @Param("languageId") String languageId,
                                      @Param("specialApp") Double specialApp,
                                      @Param("partnerId") String partnerId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tblprealert " +
            "SET invoice = 6, ddp_invoice_no = :invoiceNo, " +
            "HANDLING_FEES = CASE WHEN :handlingFee IS NOT NULL AND :handlingFee != 0.0 THEN :handlingFee ELSE HANDLING_FEES END, " +
            "CLEARANCE_CHARGE = CASE WHEN :clearanceCh IS NOT NULL AND :clearanceCh != 0.0 THEN :clearanceCh ELSE CLEARANCE_CHARGE END, " +
            "TOTAL_APPROVAL = CASE WHEN :totalApproval IS NOT NULL AND :totalApproval != 0.0 THEN :totalApproval ELSE TOTAL_APPROVAL END " +
            "WHERE PARTNER_MASTER_AIRWAY_BILL = :partnerMAB " +
            "AND is_deleted = 0", nativeQuery = true)
     void invoiceCreatePreAlertUpdate(@Param("partnerMAB") String partnerMAB,
                                            @Param("handlingFee") Double handlingFee,
                                            @Param("clearanceCh") Double clearanceCh,
                                            @Param("totalApproval") Double totalApproval,
                                            @Param("invoiceNo") String invoiceNo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tblprealert " +
            "SET invoice = 0 " +
            "WHERE PARTNER_MASTER_AIRWAY_BILL = :partnerMAB " +
            "AND is_deleted = 0", nativeQuery = true)
    public void invoiceCreatePreAlertUpdate(@Param("partnerMAB") String partnerMAB);



    // Getting PreAlert for Customs Costing
    @Query(value = "SELECT * FROM tblprealert WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    List<PreAlert> preAlert(@Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update PreAlert for Customs Costing
    @Modifying
    @Query(value = "UPDATE tblprealert SET NAS_DELIVERY = COALESCE(NAS_DELIVERY, 0) + :nasDelivery " +
            "WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateNasDelivery(@Param("nasDelivery") Double nasDelivery, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing Global
    @Modifying
    @Query(value = "UPDATE tblprealert SET GLOBAL = COALESCE(GLOBAL, 0) + :global " +
            "WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateGlobal(@Param("global") Double global, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing Approval
    @Modifying
    @Query(value = "UPDATE tblprealert SET APPROVAL = COALESCE(APPROVAL, 0) + :approval" +
            " WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateApproval(@Param("approval") Double approval, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing Handling Fork
    @Modifying
    @Query(value = "UPDATE tblprealert SET HANDLING_FORK = COALESCE(HANDLING_FORK, 0) + :handlingFork " +
            "WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateHandlingFork(@Param("handlingFork") Double handlingFork, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing stampCharges
    @Modifying
    @Query(value = "UPDATE tblprealert SET STAMP_CHARGES = COALESCE(STAMP_CHARGES, 0) + :stampCharges " +
            "WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateStampCharges(@Param("stampCharges") Double stampCharges, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing clearanceCharge
    @Modifying
    @Query(value = "UPDATE tblprealert SET CLEARANCE_CHARGE = COALESCE(CLEARANCE_CHARGE, 0) + :clearanceCharge " +
            "WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateClearanceCharge(@Param("clearanceCharge") Double clearanceCharge, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing handlingFees
    @Modifying
    @Query(value = "UPDATE tblprealert SET HANDLING_FEES = COALESCE(HANDLING_FEES, 0) + :handlingFees " +
            "WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateHandlingFees(@Param("handlingFees") Double handlingFees, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing foodApprovals
    @Modifying
    @Query(value = "UPDATE tblprealert SET FOOD_APPROVALS = COALESCE(FOOD_APPROVALS, 0) + :foodApprovals " +
            "WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateFoodApprovals(@Param("foodApprovals") Double foodApprovals, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update Consignment for Customs Costing otherApprovals
    @Modifying
    @Query(value = "UPDATE tblprealert SET OTHER_APPROVALS = COALESCE(OTHER_APPROVALS, 0) + :otherApprovals" +
            " WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateOtherApprovals(@Param("otherApprovals") Double otherApprovals, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);

    // Bulk update PreAlert for Customs Costing SpecialApprovalCharge
    @Modifying
    @Query(value = "UPDATE tblprealert SET SPECIAL_APPROVAL_CHARGE = COALESCE(SPECIAL_APPROVAL_CHARGE, 0) + :specialApprovalCharge" +
            " WHERE partner_master_airway_bill = :partnerMasterAirwayBill AND is_deleted = 0", nativeQuery = true)
    void bulkUpdateSpecialApproval(@Param("specialApprovalCharge") Double specialApprovalCharge, @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);



    @Query(value = "Select top 1 PARTNER_ID as partnerId,PARTNER_NAME as partnerName from tblprealert where IS_DELETED = 0 AND \n" +
            "(COALESCE(:partnerMasterAirwayBill,NULL) IS NULL OR PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill)", nativeQuery = true)
    IKeyValuePair findPartnerId(@Param("partnerMasterAirwayBill") String partnerMasterAirwayBill);
}
