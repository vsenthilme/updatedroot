package com.courier.overc360.api.midmile.primary.model.invoice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblinvoiceline")
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_LINE_ID")
    private Long invoiceLineId;

    @Column(name = "LANG_ID", columnDefinition = "nvarchar(10)")
    private String languageId;

    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "INVOICE_NO", columnDefinition = "nvarchar(50)")
    private String invoiceNo;

    @Column(name = "PARTNER_MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerMasterAirwayBill;

    @Column(name = "NO_OF_SHIPMENT")
    private Long noOfShipments;

    @Column(name = "HANDLING_FEES")
    private Double handlingFees;

    @Column(name = "APPROVALS")
    private Double approvals;

    @Column(name = "COST_PER_SHIPMENT", columnDefinition = "nvarchar(50)")
    private String costPerShipment;

    @Column(name = "CLEARANCE_CHARGE", columnDefinition = "nvarchar(50)")
    private String clearanceCharge;

    @Column(name = "OTHER_APPROVAL", columnDefinition = "nvarchar(50)")
    private String otherApproval;

    @Column(name = "FOOD_APPROVAL", columnDefinition = "nvarchar(50)")
    private String foodApproval;

    @Column(name = "CUSTOM_DUTY")
    private Double customDuty;

    @Column(name = "TOTAL_VALUE")
    private Double totalValue;

    @Column(name = "TOTAL_APPROVAL")
    private Double totalApproval;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(200)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(200)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(200)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(200)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(200)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(200)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(200)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(200)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(200)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(200)")
    private String referenceField10;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();


}
