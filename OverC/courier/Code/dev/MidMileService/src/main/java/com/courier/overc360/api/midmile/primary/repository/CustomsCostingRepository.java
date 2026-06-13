package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.customscosting.CustomsCosting;
import com.courier.overc360.api.midmile.replica.model.customscosting.ReplicaCustomsCosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CustomsCostingRepository extends JpaRepository<CustomsCosting, String>, JpaSpecificationExecutor<CustomsCosting> {

    Optional<CustomsCosting> findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndLineNumberAndCashNumberAndDeletionIndicator
            (String companyId, String languageId, String partnerId, String costCenter, Long lineNumber, Long cashNumber, Long deletionIndicator);

    List<CustomsCosting> findByCostCenterAndDeletionIndicator(String costCenter, Long deletionIndicator);

    @Query(value = "select top 1 cash_number from tblcustomscosting where cost_center in (:costCenter) and " +
            " partner_id in (:partnerId) and is_deleted = 0 order by CASH_NUMBER desc ", nativeQuery = true)
    public Long getCashNumber(@Param("costCenter") String costCenter,
                              @Param("partnerId") String partnerId);

    @Query(value = "select coalesce(max(line_no) + 1, 1) from tblcustomscosting where cost_center in (:costCenter) and " +
            " partner_id in (:partnerId) and is_deleted = 0 ", nativeQuery = true)
    public Long getLineNumber(@Param("costCenter") String costCenter,
                              @Param("partnerId") String partnerId);


    @Query(value = "SELECT PARTNER_MASTER_AIRWAY_BILL AS partnerMasterAirwayBill, MAX(partner_name) partnerName, MAX(partner_id) partnerId, \n" +
            " COUNT(HOUSE_AIRWAY_BILL) AS countHawb from tblprealert\n" +
            " where PARTNER_MASTER_AIRWAY_BILL =:partnerMAB AND \n " +
            " IS_DELETED = 0 GROUP BY PARTNER_MASTER_AIRWAY_BILL", nativeQuery = true)
    public IKeyValuePair getPartner(@Param(value = "partnerMAB") String partnerMAB);

    Optional<CustomsCosting> findByCostCenterAndCompanyIdAndLanguageIdAndCashNumberAndCostDescriptionAndDeletionIndicator(
            String costCenter, String companyId, String languageId, Long cashNumber, String costDescription, Long deletionIndicator);

    @Query(value = "SELECT * FROM tblcustomscosting " +
            "WHERE IS_DELETED = 0 AND " +
            "COST_CENTER =:costCenter AND " +
            "COST_DESCRIPTION =:costText", nativeQuery = true)
    public CustomsCosting getCustomCosting(@Param("costCenter") String costCenter,
                                           @Param("costText") String costText);

    @Modifying
    @Transactional
    @Query(value = "update tblcustomscosting set is_deleted = 1, utd_by = :loginUserID, utd_on = GETDATE() " +
            " where cost_center in (:costCenter) and is_deleted = 0 ", nativeQuery = true)
    public void deleteCustomCosting(@Param("costCenter") String costCenter,
                                    @Param("loginUserID") String loginUserID);

    @Modifying(clearAutomatically = true)
    @Query(value =
            "CREATE TABLE #CC \n" +
                    "(C_ID NVARCHAR(10), \n" +
                    "LANG_ID NVARCHAR(10), \n" +
                    "PMAWB NVARCHAR(50), \n" +
                    "COST_DESCRIPTION NVARCHAR(100), \n" +
                    "COST FLOAT DEFAULT 0, \n" +
                    "NO_OF_SHIPMENT FLOAT DEFAULT 0, \n" +
                    "PRIMARY KEY (C_ID,LANG_ID,PMAWB,COST_DESCRIPTION)); \n" +

                    "insert into #CC(C_ID,LANG_ID,PMAWB,COST,COST_DESCRIPTION) \n" +
                    "select C_ID, LANG_ID, COST_CENTER PMAWB, isnull(sum(COST_AMOUNT),0) cost, COST_DESCRIPTION  \n" +
                    "from tblcustomscosting  \n" +
                    "where COST_DESCRIPTION is not null and is_deleted = 0 and C_ID = :companyId and LANG_ID = :languageId \n" +
                    "and COST_CENTER = :partnerMasterAirwayBill \n" +
                    "group by COST_DESCRIPTION,COST_CENTER,C_ID,LANG_ID \n" +

                    "UPDATE TH SET TH.NO_OF_SHIPMENT = X.VALUE FROM #CC TH INNER JOIN \n" +
                    "(SELECT COUNT(house_airway_bill) VALUE, C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL FROM tblprealert \n" +
                    "WHERE IS_DELETED = 0 \n" +
                    "GROUP BY partner_master_airway_bill,C_ID,LANG_ID) X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND X.PARTNER_MASTER_AIRWAY_BILL=TH.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill \n" +

                    "select (case when NO_OF_SHIPMENT > 0 then cost/No_of_shipment else 0 end) costPerShipment,PMAWB,COST_DESCRIPTION,C_ID,LANG_ID,NO_OF_SHIPMENT into #tcc from #cc \n" +

                    //-------------------------------Special Approval--------------------------------------//
                    "CREATE TABLE #SACC \n" +
                    "(C_ID NVARCHAR(10), \n" +
                    "LANG_ID NVARCHAR(10), \n" +
                    "PMAWB NVARCHAR(50), \n" +
                    "PHAWB NVARCHAR(50), \n" +
                    "COST_DESCRIPTION NVARCHAR(100), \n" +
                    "COST FLOAT DEFAULT 0, \n" +
                    "NO_OF_SHIPMENT FLOAT DEFAULT 0, \n" +
                    "PRIMARY KEY (C_ID,LANG_ID,PMAWB,PHAWB,COST_DESCRIPTION)); \n" +

                    "insert into #SACC(C_ID,LANG_ID,PMAWB,PHAWB,COST,COST_DESCRIPTION) \n" +
                    "select C_ID, LANG_ID, COST_CENTER PMAWB,INVOICE_NUMBER PHAWB, isnull(sum(COST_AMOUNT),0) cost, COST_DESCRIPTION  \n" +
                    "from tblcustomscosting  \n" +
                    "where COST_DESCRIPTION is not null and is_deleted = 0 and INVOICE_NUMBER is not null and C_ID in :companyId and LANG_ID in :languageId \n" +
                    "and COST_CENTER in :partnerMasterAirwayBill \n" +
                    "group by COST_DESCRIPTION,INVOICE_NUMBER,COST_CENTER,C_ID,LANG_ID \n" +

                    "UPDATE TH SET TH.SPECIAL_APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT COST VALUE, C_ID, LANG_ID, PMAWB, PHAWB, COST_DESCRIPTION FROM #SACC where COST_DESCRIPTION = 'SpecialApprovals') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB AND TH.PARTNER_HOUSE_AIRWAY_BILL=x.PHAWB \n" +
                    "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

                    //-------------------------------Special Approval--------------------------------------//

                    "UPDATE TH SET TH.NAS_DELIVERY = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc \n" +
                    "where COST_DESCRIPTION = 'NAS - Delivery' or COST_DESCRIPTION = 'NAS-Delivery') X ON \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.GLOBAL = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Global') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.APPROVAL = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Approval') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.HANDLING_FORK = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Handling&Fork') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.FOOD_APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'FoodApprovals') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.OTHER_APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'OtherApprovals') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.SPECIAL_APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'SpecialApprovals') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.LABOURS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Labours') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.OTHERS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Others') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.OTHER_CHARGES = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'OtherCharges') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PMAWB = :partnerMasterAirwayBill \n" +

//                    "UPDATE tblprealert SET CUSTOM_DUTY = CALCULATED_TOTAL_DUTY \n" +
//                    "WHERE IS_DELETED = 0 AND C_ID in :companyId AND LANG_ID in :languageId AND PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT (ISNULL(FOOD_APPROVALS,0) + ISNULL(OTHER_APPROVALS,0) + ISNULL(APPROVALS,0)) VALUE,  \n" +
                    "C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL,PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert where IS_DELETED = 0) X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
                    "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.STAMP_CHARGES = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB, COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'StampChrgs') X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
                    "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.HANDLING_FEES = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT (case  \n" +
                    "when APPROVALS is not null and APPROVALS > 0 then (5/NO_OF_SHIPMENT) else 0 end) VALUE, \n" +
                    "tpa.C_ID, tpa.LANG_ID, tpa.PARTNER_MASTER_AIRWAY_BILL,tpa.PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert tpa \n"+
                    "Join #cc cc on cc.C_ID = tpa.C_ID AND cc.LANG_ID = tpa.LANG_ID AND cc.PMAWB = tpa.PARTNER_MASTER_AIRWAY_BILL where tpa.IS_DELETED = 0) X ON  \n"+
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
                    "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.CUSTOM_DUTY = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT (case when ISNUMERIC(BAYAN_HV) = 1 then cast(BAYAN_HV as decimal(10,3)) else 0 end) VALUE,  \n" +
                    "C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL,PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert where IS_DELETED = 0) X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
                    "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.TOTAL_COST_PER_SHIPMENT = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT (ISNULL(NAS_DELIVERY,0) + ISNULL(GLOBAL,0) + ISNULL(HANDLING_FORK,0) + ISNULL(STAMP_CHARGES,0) + ISNULL(LABOURS,0) +  \n" +
                    "ISNULL(OTHER_CHARGES,0) + ISNULL(OTHERS,0) + ISNULL(CUSTOM_DUTY,0) + ISNULL(SPECIAL_APPROVALS,0) + ISNULL(APPROVALS,0)) VALUE,  \n" +
                    "C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL,PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert where IS_DELETED = 0) X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill \n" +

                    "update tblprealert set UTD_BY = :loginUserID, UTD_ON = :updatedOn where IS_DELETED = 0 \n" +
                    "AND C_ID = :companyId AND LANG_ID = :languageId AND PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill \n" +

                    "update tblcustomscosting set STATUS_ID = :statusId, STATUS_TEXT = :statusDescription where IS_DELETED = 0 \n" +
                    "AND C_ID in :companyId AND LANG_ID in :languageId AND COST_CENTER in :partnerMasterAirwayBill \n" +

                    "UPDATE TH SET TH.TOTAL_VALUE_SHIPMENT = X.VALUE FROM tblprealert TH INNER JOIN \n" +
                    "(SELECT (ISNULL(CLEARANCE_CHARGE,0) + ISNULL(APPROVALS,0) + ISNULL(SPECIAL_APPROVALS,0) + ISNULL(HANDLING_FEES,0) + ISNULL(CUSTOM_DUTY,0)) VALUE, \n" +
                    "C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL,PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert where IS_DELETED = 0) X ON  \n" +
                    "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
                    "AND X.C_ID = :companyId AND X.LANG_ID = :languageId AND X.PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill ", nativeQuery = true)
    void updatePreAlertReportValues(@Param("languageId") String languageId,
                                    @Param("companyId") String companyId,
                                    @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
                                    @Param("loginUserID") String loginUserID,
                                    @Param("updatedOn") Date updatedOn,
                                    @Param("statusId") Long statusId,
                                    @Param("statusDescription") String statusDescription);

    @Modifying(clearAutomatically = true)
    @Query(value =
            "CREATE TABLE #CC \n" +
            "(C_ID NVARCHAR(10), \n" +
            "LANG_ID NVARCHAR(10), \n" +
            "PMAWB NVARCHAR(50), \n" +
            "COST_DESCRIPTION NVARCHAR(100), \n" +
            "COST FLOAT DEFAULT 0, \n" +
            "NO_OF_SHIPMENT FLOAT DEFAULT 0, \n" +
            "PRIMARY KEY (C_ID,LANG_ID,PMAWB,COST_DESCRIPTION)); \n" +

            "insert into #CC(C_ID,LANG_ID,PMAWB,COST,COST_DESCRIPTION) \n" +
            "select C_ID, LANG_ID, COST_CENTER PMAWB, isnull(sum(COST_AMOUNT),0) cost, COST_DESCRIPTION  \n" +
            "from tblcustomscosting  \n" +
            "where COST_DESCRIPTION is not null and is_deleted = 0 and C_ID in :companyId and LANG_ID in :languageId \n" +
            "and COST_CENTER in :partnerMasterAirwayBill \n" +
            "group by COST_DESCRIPTION,COST_CENTER,C_ID,LANG_ID \n" +

            "UPDATE TH SET TH.NO_OF_SHIPMENT = X.VALUE FROM #CC TH INNER JOIN \n" +
            "(SELECT COUNT(house_airway_bill) VALUE, C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL FROM tblprealert \n" +
            "WHERE IS_DELETED = 0 \n" +
            "GROUP BY partner_master_airway_bill,C_ID,LANG_ID) X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND X.PARTNER_MASTER_AIRWAY_BILL=TH.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

            "select (case when NO_OF_SHIPMENT > 0 then cost/No_of_shipment else 0 end) costPerShipment,PMAWB,COST_DESCRIPTION,C_ID,LANG_ID,NO_OF_SHIPMENT into #tcc from #cc \n" +

            //-------------------------------Special Approval--------------------------------------//
            "CREATE TABLE #SACC \n" +
            "(C_ID NVARCHAR(10), \n" +
            "LANG_ID NVARCHAR(10), \n" +
            "PMAWB NVARCHAR(50), \n" +
            "PHAWB NVARCHAR(50), \n" +
            "COST_DESCRIPTION NVARCHAR(100), \n" +
            "COST FLOAT DEFAULT 0, \n" +
            "NO_OF_SHIPMENT FLOAT DEFAULT 0, \n" +
            "PRIMARY KEY (C_ID,LANG_ID,PMAWB,PHAWB,COST_DESCRIPTION)); \n" +

            "insert into #SACC(C_ID,LANG_ID,PMAWB,PHAWB,COST,COST_DESCRIPTION) \n" +
            "select C_ID, LANG_ID, COST_CENTER PMAWB,INVOICE_NUMBER PHAWB, isnull(sum(COST_AMOUNT),0) cost, COST_DESCRIPTION  \n" +
            "from tblcustomscosting  \n" +
            "where COST_DESCRIPTION is not null and is_deleted = 0 and INVOICE_NUMBER is not null and C_ID in :companyId and LANG_ID in :languageId \n" +
            "and COST_CENTER in :partnerMasterAirwayBill \n" +
            "group by COST_DESCRIPTION,INVOICE_NUMBER,COST_CENTER,C_ID,LANG_ID \n" +

            "UPDATE TH SET TH.SPECIAL_APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT COST VALUE, C_ID, LANG_ID, PMAWB, PHAWB, COST_DESCRIPTION FROM #SACC where COST_DESCRIPTION = 'SpecialApprovals') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB AND TH.PARTNER_HOUSE_AIRWAY_BILL=x.PHAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            //-------------------------------Special Approval--------------------------------------//

            "UPDATE TH SET TH.NAS_DELIVERY = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc \n" +
            "where COST_DESCRIPTION = 'NAS - Delivery' or COST_DESCRIPTION = 'NAS-Delivery') X ON \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.GLOBAL = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Global') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.APPROVAL = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Approval') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.HANDLING_FORK = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Handling&Fork') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.FOOD_APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'FoodApprovals') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.OTHER_APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'OtherApprovals') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.LABOURS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Labours') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.OTHERS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'Others') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.OTHER_CHARGES = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB,COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'OtherCharges') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

//            "UPDATE tblprealert SET CUSTOM_DUTY = CALCULATED_TOTAL_DUTY \n" +
//            "WHERE IS_DELETED = 0 AND C_ID in :companyId AND LANG_ID in :languageId AND PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.APPROVALS = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT (ISNULL(FOOD_APPROVALS,0) + ISNULL(OTHER_APPROVALS,0) + ISNULL(APPROVAL,0)) VALUE,  \n" +
            "C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL,PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert where IS_DELETED = 0) X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.STAMP_CHARGES = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT costPerShipment VALUE, C_ID, LANG_ID, PMAWB, COST_DESCRIPTION FROM #tcc where COST_DESCRIPTION = 'StampChrgs') X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PMAWB \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PMAWB in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.HANDLING_FEES = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT (case  \n" +
            "when APPROVALS is not null and APPROVALS > 0 then (5/NO_OF_SHIPMENT) else 0 end) VALUE, \n" +
            "tpa.C_ID, tpa.LANG_ID, tpa.PARTNER_MASTER_AIRWAY_BILL,tpa.PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert tpa \n"+
            "Join #cc cc on cc.C_ID = tpa.C_ID AND cc.LANG_ID = tpa.LANG_ID AND cc.PMAWB = tpa.PARTNER_MASTER_AIRWAY_BILL where tpa.IS_DELETED = 0) X ON  \n"+
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.CUSTOM_DUTY = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT (case when ISNUMERIC(BAYAN_HV) = 1 then cast(BAYAN_HV as decimal(10,3)) else 0 end) VALUE,  \n" +
            "C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL,PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert where IS_DELETED = 0) X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.TOTAL_COST_PER_SHIPMENT = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT (ISNULL(NAS_DELIVERY,0) + ISNULL(GLOBAL,0) + ISNULL(HANDLING_FORK,0) + ISNULL(STAMP_CHARGES,0) + ISNULL(LABOURS,0) +  \n" +
            "ISNULL(OTHER_CHARGES,0) + ISNULL(OTHERS,0) + ISNULL(CUSTOM_DUTY,0) + ISNULL(SPECIAL_APPROVALS,0) + ISNULL(APPROVALS,0)) VALUE,  \n" +
            "C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL,PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert where IS_DELETED = 0) X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

            "update tblprealert set UTD_BY = :loginUserID, UTD_ON = :updatedOn where IS_DELETED = 0 \n" +
            "AND C_ID in :companyId AND LANG_ID in :languageId AND PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill \n" +

            "update tblcustomscosting set STATUS_ID = :statusId, STATUS_TEXT = :statusDescription where IS_DELETED = 0 \n" +
            "AND C_ID in :companyId AND LANG_ID in :languageId AND COST_CENTER in :partnerMasterAirwayBill \n" +

            "UPDATE TH SET TH.TOTAL_VALUE_SHIPMENT = X.VALUE FROM tblprealert TH INNER JOIN \n" +
            "(SELECT (ISNULL(CLEARANCE_CHARGE,0) + ISNULL(APPROVALS,0) + ISNULL(HANDLING_FEES,0) + ISNULL(SPECIAL_APPROVALS,0) + ISNULL(CUSTOM_DUTY,0)) VALUE, \n" +
            "C_ID, LANG_ID, PARTNER_MASTER_AIRWAY_BILL,PARTNER_HOUSE_AIRWAY_BILL FROM tblprealert where IS_DELETED = 0) X ON  \n" +
            "X.C_ID = TH.C_ID AND X.LANG_ID = TH.LANG_ID AND TH.PARTNER_MASTER_AIRWAY_BILL=x.PARTNER_MASTER_AIRWAY_BILL AND TH.PARTNER_HOUSE_AIRWAY_BILL = X.PARTNER_HOUSE_AIRWAY_BILL \n" +
            "AND X.C_ID in :companyId AND X.LANG_ID in :languageId AND X.PARTNER_MASTER_AIRWAY_BILL in :partnerMasterAirwayBill ", nativeQuery = true)
    void batchUpdatePreAlertReportValues(@Param("languageId") List<String> languageId,
                                         @Param("companyId") List<String> companyId,
                                         @Param("partnerMasterAirwayBill") List<String> partnerMasterAirwayBill,
                                         @Param("loginUserID") String loginUserID,
                                         @Param("updatedOn") Date updatedOn,
                                         @Param("statusId") Long statusId,
                                         @Param("statusDescription") String statusDescription);

    @Query(value = "Select \n" +
            "ts.STATUS_TEXT \n" +
            "From tblstatus ts \n" +
            "Where \n" +
            "ts.STATUS_ID IN (:statusId) and \n" +
            "ts.IS_DELETED = 0", nativeQuery = true)
    String getStatusDescription(@Param(value = "statusId") String statusId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tblprealert SET CLEARANCE_CHARGE = :clearanceCharge \n" +
            "where is_deleted = 0 AND PARTNER_MASTER_AIRWAY_BILL = :partnerMasterAirwayBill \n" +
            "AND C_ID = :companyId AND LANG_ID = :languageId \n", nativeQuery = true)
    void UpdateClearanceChargeReportValues(@Param("languageId") String languageId,
                                           @Param("companyId") String companyId,
                                           @Param("partnerMasterAirwayBill") String partnerMasterAirwayBill,
                                           @Param("clearanceCharge") Double clearanceCharge);


    @Modifying
    @Query(value = "update tblcustomscosting " +
            "set COST_AMOUNT = coalesce(:amount, 0.0) " +
            " where c_id in (:companyId) and " +
            " lang_id in (:languageId) and " +
            " COST_CENTER in (:costCenter) and " +
            " COST_DESCRIPTION in (:text) and " +
            " is_deleted = 0", nativeQuery = true)
    void updateAmount(@Param("companyId") String companyId,
                      @Param("languageId") String languageId,
                      @Param("costCenter") String costCenter,
                      @Param("amount") Double amount,
                      @Param("text") String text);


    @Modifying
    @Query(value = "UPDATE tblcustomscosting " +
            "SET IS_DELETED = 1," +
            "UTD_BY = :loginUserID," +
            "UTD_ON = GETDATE()" +
            "WHERE " +
            "INVOICE_NUMBER = :invoiceNo AND " +
            "IS_DELETED = 0", nativeQuery = true)
    public void deleteCCWHConsole(@Param("loginUserID") String loginUserID,
                                    @Param("invoiceNo") String invoiceNo);


//===============================================================================================================================
    @Query(value = "select TOP 1 * from tblcustomscosting where is_deleted = 0 AND COST_DESCRIPTION ='CustomDuty' \n" +
            "AND (COALESCE(:cashNumber,NULL) IS NULL OR CASH_NUMBER IN (:cashNumber)) \n" +
            "AND (:date IS NULL OR date = CAST(:date AS datetime2)) " +
            "AND (COALESCE(:department,NULL) IS NULL OR DEPARTMENT IN (:department)) \n" +
            "AND (COALESCE(:cashHolder,NULL) IS NULL OR CASH_HOLDER IN (:cashHolder)) \n" +
            "AND (COALESCE(:partnerId,NULL) IS NULL OR PARTNER_ID IN (:partnerId)) \n" +
            "AND (COALESCE(:costCenter,NULL) IS NULL OR COST_CENTER IN (:costCenter)) \n" +
            "AND (COALESCE(:noOfShipments,NULL) IS NULL OR NO_OF_SHIPMENTS IN (:noOfShipments)) \n" +
            "AND (COALESCE(:remark,NULL) IS NULL OR REMARK IN (:remark))", nativeQuery = true)
    CustomsCosting getCustomDutyData(@Param("cashNumber") Long cashNumber,
                                            @Param("date") Date date,
                                            @Param("department") String department,
                                            @Param("cashHolder") String cashHolder,
                                            @Param("partnerId") String partnerId,
                                            @Param("costCenter") String costCenter,
                                            @Param("noOfShipments") Long noOfShipments,
                                            @Param("remark") String remark);

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
    List<CustomsCosting> getCustomCostingTotal(@Param("cashNumber") Long cashNumber,
                                                      @Param("date") Date date,
                                                      @Param("department") String department,
                                                      @Param("cashHolder") String cashHolder,
                                                      @Param("partnerId") String partnerId,
                                                      @Param("costCenter") String costCenter,
                                                      @Param("noOfShipments") Long noOfShipments,
                                                      @Param("remark") String remark);
}