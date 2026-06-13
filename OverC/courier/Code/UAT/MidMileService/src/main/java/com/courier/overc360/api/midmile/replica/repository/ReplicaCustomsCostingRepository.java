package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.customscosting.CustomCostingInvoice;
import com.courier.overc360.api.midmile.replica.model.customscosting.ReplicaCustomsCosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaCustomsCostingRepository extends JpaRepository<ReplicaCustomsCosting, String>,
        JpaSpecificationExecutor<ReplicaCustomsCosting> {

    Optional<ReplicaCustomsCosting> findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndLineNumberAndCashNumberAndDeletionIndicator
            (String companyId, String languageId, String partnerId, String costCenter, Long lineNumber, Long cashNumber, Long deletionIndicator);

    Optional<ReplicaCustomsCosting> findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndInvoiceNumberAndDeletionIndicator
            (String companyId, String languageId, String partnerId, String costCenter, String invoiceNo, Long deletionIndicator);


    Optional<ReplicaCustomsCosting> findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndCostDescriptionAndDeletionIndicator
            (String companyId, String languageId, String partnerId, String costCenter, String costDescription, Long deletionIndicator);

    @Query(value = "select c.cost_center costCenter, c.COST_DESCRIPTION costDescription, " +
            "MAX(c.PARTNER_NAME) partnerName, " +
            "ROUND(SUM(c.COST_AMOUNT), 3) costAmount, " +
            "MAX(t.totalAmount) AS totalCostAmount, " +
            "MAX(c.CASH_NUMBER) cashNumber, " +
            "MAX(c.LANG_ID) languageId, " +
            "MAX(c.LANG_TEXT) languageName, " +
            "MAX(c.C_ID) companyId, " +
            "MAX(c.C_NAME) companyName, " +
            "MAX(c.PARTNER_ID) partnerId, " +
            "MAX(c.DATE) date, " +
            "MAX(c.DEPARTMENT) department, " +
            "MAX(c.CTD_BY) createdBy, " +
            "MAX(c.CTD_ON) createdOn, " +
            "MAX(c.CASH_HOLDER) cashHolder, " +
            "CASE WHEN MAX(CAST(c.FINANCE AS INT)) = 1 THEN 'true' ELSE 'false' END AS finance, " +
            "MAX(c.NO_OF_SHIPMENTS) noOfShipments, " +
            "MAX(c.INVOICE_NUMBER) invoiceNumber, " +
            "MAX(c.STATUS_ID) statusId, " +
            "MAX(c.INVOICE_DATE) invoiceDate, " +
            "MAX(c.SUPPLIER_NAME) supplierName, " +
            "MAX(c.STATUS_TEXT) statusDescription, " +
            "MAX(c.LINE_NO) lineNumber, " +
            "MAX(c.APPROVED_BY) approvedBy, " +
            "MAX(c.SUB_CUSTOMER_ID) subCustomerId, " +
            "MAX(c.SUB_CUSTOMER_NAME) subCustomerName, " +
            "MAX(c.REF_FIELD_1) referenceField1, " +
            "MAX(c.REF_FIELD_2) referenceField2, " +
            "MAX(c.REF_FIELD_3) referenceField3, " +
            "MAX(c.REF_FIELD_4) referenceField4, " +
            "MAX(c.REF_FIELD_5) referenceField5 " +
            "from tblcustomscosting c " +
            "LEFT JOIN (SELECT COST_CENTER, ROUND(SUM(COST_AMOUNT), 3) AS totalAmount " +
            "FROM tblcustomscosting " +
            "WHERE IS_DELETED = 0 " +
            "GROUP BY COST_CENTER) t " +
            "ON c.COST_CENTER = t.COST_CENTER " +
            "WHERE c.IS_DELETED = 0 " +
            "AND (COALESCE(:languageId, NULL) IS NULL OR c.LANG_ID IN (:languageId)) " +
            "AND (COALESCE(:companyId, NULL) IS NULL OR c.C_ID IN (:companyId)) " +
            "AND (COALESCE(:costCenter, NULL) IS NULL OR c.COST_CENTER IN (:costCenter)) " +
            "AND (COALESCE(:cashNumber, NULL) IS NULL OR c.CASH_NUMBER IN (:cashNumber)) " +
            "AND (COALESCE(:partnerId, NULL) IS NULL OR c.PARTNER_ID IN (:partnerId)) " +
            "AND (COALESCE(:lineNo, NULL) IS NULL OR c.LINE_NO IN (:lineNo)) " +
            "AND (COALESCE(:subCustomerId, NULL) IS NULL OR c.SUB_CUSTOMER_ID IN (:subCustomerId)) " +
            "GROUP BY c.COST_CENTER, c.COST_DESCRIPTION", nativeQuery = true)
    public List<CustomCostingInvoice> getCustomCosting(@Param("companyId") List<String> companyId,
                                                       @Param("languageId") List<String> languageId,
                                                       @Param("costCenter") List<String> costCenter,
                                                       @Param("partnerId") List<String> partnerId,
                                                       @Param("cashNumber") List<Long> cashNumber,
                                                       @Param("lineNo") List<Long> lineNo,
                                                       @Param("subCustomerId") List<String> subCustomerId);


    @Query(value = "select cost_center costCenter, cost_description costDescription, \n " +
            " sum(COST_AMOUNT) costAmount " +
            " from tblcustomscosting " +
            " where is_deleted = 0 and " +
            " cost_center in (:costCenter) " +
            " group by cost_center, cost_description", nativeQuery = true)
    public List<CustomCostingInvoice> getCustom(@Param("costCenter") String costCenter);

    @Query(value = "select * from tblcustomscosting where is_deleted = 0 AND COST_DESCRIPTION NOT IN ('CustomDuty') \n" +
            "AND (COALESCE(:cashNumber,NULL) IS NULL OR CASH_NUMBER IN (:cashNumber)) \n" +
//            "AND (COALESCE(:date,NULL) IS NULL OR DATE IN (:date)) \n" +
            " AND (:date IS NULL OR date = CAST(:date AS datetime2)) " +
            "AND (COALESCE(:department,NULL) IS NULL OR DEPARTMENT IN (:department)) \n" +
            "AND (COALESCE(:cashHolder,NULL) IS NULL OR CASH_HOLDER IN (:cashHolder)) \n" +
            "AND (COALESCE(:partnerId,NULL) IS NULL OR PARTNER_ID IN (:partnerId)) \n" +
            "AND (COALESCE(:costCenter,NULL) IS NULL OR COST_CENTER IN (:costCenter)) \n" +
            "AND (COALESCE(:noOfShipments,NULL) IS NULL OR NO_OF_SHIPMENTS IN (:noOfShipments)) \n" +
            "AND (COALESCE(:remark,NULL) IS NULL OR REMARK IN (:remark))", nativeQuery = true)
    List<ReplicaCustomsCosting> getCustomCostingTotal(@Param("cashNumber") Long cashNumber,
                                                      @Param("date") Date date,
                                                      @Param("department") String department,
                                                      @Param("cashHolder") String cashHolder,
                                                      @Param("partnerId") String partnerId,
                                                      @Param("costCenter") String costCenter,
                                                      @Param("noOfShipments") Long noOfShipments,
                                                      @Param("remark") String remark);


    @Query(value = "select CLEARANCE_FEE from tblcustomcharges where LANG_ID = :languageId AND " +
            "C_ID = :companyId AND IS_DELETED = 0", nativeQuery = true)
    String clearanceCharges(@Param("languageId") String languageId,
                            @Param("companyId") String companyId);

    @Query(value = "SELECT SUM(c.cost_Amount) FROM tblcustomscosting c WHERE c.cost_Description LIKE '%Special%Approvals%' AND" +
            " c.lang_id = :languageId AND c.c_id = :companyId AND c.INVOICE_NUMBER = :partnerHouseAirwayBill " +
            "AND c.IS_DELETED = 0 ", nativeQuery = true)
    Double costDescriptionAmount(@Param("languageId") String languageId,
                                 @Param("companyId") String companyId,
                                 @Param("partnerHouseAirwayBill") String partnerHouseAirwayBill);

    @Query(value = "select SUM(COST_AMOUNT) from tblcustomscosting where is_deleted = 0 AND COST_DESCRIPTION ='CustomDuty' \n" +
            "AND (COALESCE(:cashNumber,NULL) IS NULL OR CASH_NUMBER IN (:cashNumber)) \n" +
            "AND (:date IS NULL OR date = CAST(:date AS datetime2)) " +
            "AND (COALESCE(:department,NULL) IS NULL OR DEPARTMENT IN (:department)) \n" +
            "AND (COALESCE(:cashHolder,NULL) IS NULL OR CASH_HOLDER IN (:cashHolder)) \n" +
            "AND (COALESCE(:partnerId,NULL) IS NULL OR PARTNER_ID IN (:partnerId)) \n" +
            "AND (COALESCE(:costCenter,NULL) IS NULL OR COST_CENTER IN (:costCenter)) \n" +
            "AND (COALESCE(:noOfShipments,NULL) IS NULL OR NO_OF_SHIPMENTS IN (:noOfShipments)) \n" +
            "AND (COALESCE(:remark,NULL) IS NULL OR REMARK IN (:remark))", nativeQuery = true)
    Double getCustomDutySum(@Param("cashNumber") Long cashNumber,
                            @Param("date") Date date,
                            @Param("department") String department,
                            @Param("cashHolder") String cashHolder,
                            @Param("partnerId") String partnerId,
                            @Param("costCenter") String costCenter,
                            @Param("noOfShipments") Long noOfShipments,
                            @Param("remark") String remark);


    @Query(value = "select TOP 1 * from tblcustomscosting where is_deleted = 0 AND COST_DESCRIPTION ='CustomDuty' \n" +
            "AND (COALESCE(:cashNumber,NULL) IS NULL OR CASH_NUMBER IN (:cashNumber)) \n" +
            "AND (:date IS NULL OR date = CAST(:date AS datetime2)) " +
            "AND (COALESCE(:department,NULL) IS NULL OR DEPARTMENT IN (:department)) \n" +
            "AND (COALESCE(:cashHolder,NULL) IS NULL OR CASH_HOLDER IN (:cashHolder)) \n" +
            "AND (COALESCE(:partnerId,NULL) IS NULL OR PARTNER_ID IN (:partnerId)) \n" +
            "AND (COALESCE(:costCenter,NULL) IS NULL OR COST_CENTER IN (:costCenter)) \n" +
            "AND (COALESCE(:noOfShipments,NULL) IS NULL OR NO_OF_SHIPMENTS IN (:noOfShipments)) \n" +
            "AND (COALESCE(:remark,NULL) IS NULL OR REMARK IN (:remark))", nativeQuery = true)
    ReplicaCustomsCosting getCustomDutyData(@Param("cashNumber") Long cashNumber,
                                            @Param("date") Date date,
                                            @Param("department") String department,
                                            @Param("cashHolder") String cashHolder,
                                            @Param("partnerId") String partnerId,
                                            @Param("costCenter") String costCenter,
                                            @Param("noOfShipments") Long noOfShipments,
                                            @Param("remark") String remark);

    @Query(value = "select C_NAME,LANG_TEXT from tblcompany where is_deleted = 0 \n" +
            "AND C_ID = :companyId AND LANG_ID = :languageId ", nativeQuery = true)
    String[] getDescription(@Param("companyId") String companyId,
                            @Param("languageId") String languageId);


}