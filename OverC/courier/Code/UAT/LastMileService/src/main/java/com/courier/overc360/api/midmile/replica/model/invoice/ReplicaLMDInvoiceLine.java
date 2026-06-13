package com.courier.overc360.api.midmile.replica.model.invoice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbllmdinvoiceline")
public class ReplicaLMDInvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_LINE_ID")
    private Long invoiceLineId;

    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "INVOICE_NO", columnDefinition = "nvarchar(100)")
    private String invoiceNo;

    @Column(name = "CUSTOMER_ID", columnDefinition = "nvarchar(100)")
    private String customerId;

    @Column(name = "INVOICE_AMOUNT")
    private Double invoiceAmount;

    @Column(name = "CURRENCY_UNIT", columnDefinition = "nvarchar(100)")
    private String currencyUnit;

    @Column(name = "CUSTOMER_NAME", columnDefinition = "nvarchar(100)")
    private String customerName;

    @Column(name = "LINE_NUMBER")
    private Long lineNumber;

    @Column(name = "TO_DATE")
    private Date fromDate;

    @Column(name = "FROM_DATE")
    private Date toDate;

    @Column(name = "NO_OF_SHIPMENT", columnDefinition = "nvarchar(100)")
    private String noOfShipment;

    @Column(name = "CHARGE_DESCRIPTION", columnDefinition = "nvarchar(100)")
    private String chargeDescription;

    @Column(name = "CHARGE_AMOUNT")
    private Double chargeAmount;

    @Column(name = "REMARKS", columnDefinition = "nvarchar(100)")
    private String remarks;

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

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

}
