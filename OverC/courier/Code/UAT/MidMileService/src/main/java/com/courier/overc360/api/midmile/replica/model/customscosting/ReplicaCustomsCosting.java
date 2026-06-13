package com.courier.overc360.api.midmile.replica.model.customscosting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
/*
 * `LANG_ID`, `C_ID`, `COST_CENTER`, `LINE_NO`, `CUSTOMER_ID`
 */
@Table(
        name = "tblcustomscosting",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_customscosting",
                        columnNames = {"LANG_ID", "C_ID", "COST_CENTER", "LINE_NO", "PARTNER_ID", "CASH_NUMBER"}
                )
        }
)
@IdClass(ReplicaCustomsCostingCompositeKey.class)
public class ReplicaCustomsCosting {

    @Id
    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Id
    @Column(name = "COST_CENTER", columnDefinition = "nvarchar(50)")
    private String costCenter;

    @Id
    @Column(name = "LINE_NO")
    private Long lineNumber;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "CASH_NUMBER")
    private Long cashNumber;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
    private String partnerName;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "DEPARTMENT", columnDefinition = "nvarchar(100)")
    private String department;

    @Column(name = "DATE")
    private Date date = new Date();

    @Column(name = "CASH_HOLDER", columnDefinition = "nvarchar(100)")
    private String cashHolder;

    @Column(name = "APPROVED_BY", columnDefinition = "nvarchar(50)")
    private String approvedBy;

    @Column(name = "NO_OF_SHIPMENTS")
    private Long noOfShipments;

    @Column(name = "INVOICE_NUMBER", columnDefinition = "nvarchar(100)")
    private String invoiceNumber;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "SUPPLIER_NAME", columnDefinition = "nvarchar(50)")
    private String supplierName;

    @Column(name = "COST_DESCRIPTION", columnDefinition = "nvarchar(50)")
    private String costDescription;

    @Column(name = "COST_AMOUNT")
    private Double costAmount;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(100)")
    private String statusDescription;

    @Column(name = "REMARK", columnDefinition = "nvarchar(2000)")
    private String remark;
    
    @Column(name = "FINANCE")
    private Boolean finance;
    
    @Column(name = "SUB_CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String subCustomerId;

    @Column(name = "SUB_CUSTOMER_NAME", columnDefinition = "nvarchar(50)")
    private String subCustomerName;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(500)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(500)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(500)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(500)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(500)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(500)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(500)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(500)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(500)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(500)")
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
