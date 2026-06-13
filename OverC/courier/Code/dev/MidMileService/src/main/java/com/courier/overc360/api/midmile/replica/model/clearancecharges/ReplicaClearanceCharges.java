package com.courier.overc360.api.midmile.replica.model.clearancecharges;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblclearancecharges")
public class ReplicaClearanceCharges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLEARANCE_CHARGES_ID")
    private Long clearanceChargesId;

    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
    private String partnerName;

    @Column(name = "NO_OF_SHIPMENTS_FROM", columnDefinition = "nvarchar(50)")
    private String noOfShipmentsFrom;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "NO_OF_SHIPMENTS_TO", columnDefinition = "nvarchar(50)")
    private String noOfShipmentsTo;

    @Column(name = "PARAMETER", columnDefinition = "nvarchar(50)")
    private String parameter;

    @Column(name = "MULTIPLIER")
    private Double multiplier;

    @Column(name = "ADD_MIN_CHARGE", columnDefinition = "nvarchar(50)")
    private String addMinCharge;

    @Column(name = "CLEARANCE_CHARGES", columnDefinition = "nvarchar(50)")
    private String clearanceCharges;

    @Column(name = "CURRENCY", columnDefinition = "nvarchar(50)")
    private String currency;

    @Column(name = "REMARK", columnDefinition = "nvarchar(50)")
    private String remark;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
    private String statusDescription;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "FORMULA_FIELD_1", columnDefinition = "nvarchar(50)")
    private String formulaField1;

    @Column(name = "FORMULA_FIELD_2", columnDefinition = "nvarchar(50)")
    private String formulaField2;

    @Column(name = "FORMULA_FIELD_3", columnDefinition = "nvarchar(50)")
    private String formulaField3;

    @Column(name = "FORMULA_FIELD_4", columnDefinition = "nvarchar(50)")
    private String formulaField4;

    @Column(name = "FORMULA_FIELD_5", columnDefinition = "nvarchar(50)")
    private String formulaField5;

    @Column(name = "FORMULA_FIELD_6", columnDefinition = "nvarchar(50)")
    private String formulaField6;

    @Column(name = "FORMULA_FIELD_7", columnDefinition = "nvarchar(50)")
    private String formulaField7;

    @Column(name = "FORMULA_FIELD_8", columnDefinition = "nvarchar(50)")
    private String formulaField8;

    @Column(name = "FORMULA_FIELD_9", columnDefinition = "nvarchar(50)")
    private String formulaField9;

    @Column(name = "FORMULA_FIELD_10", columnDefinition = "nvarchar(50)")
    private String formulaField10;

    @Column(name = "VALIDITY_DATE_FROM")
    private Date validityDateFrom = new Date();

    @Column(name = "VALIDITY_DATE_TO")
    private Date validityDateTo = new Date();

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

    @Column(name = "SUB_CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String subCustomerId;

    @Column(name = "SUB_CUSTOMER_NAME", columnDefinition = "nvarchar(50)")
    private String subCustomerName;

}

