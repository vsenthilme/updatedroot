package com.courier.overc360.api.midmile.primary.model.invoice;


import com.courier.overc360.api.midmile.primary.model.pickup.RiderAssignmentImageReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbllmdinvoiceheader")
public class LMDInvoiceHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_HEADER_ID")
    private Long invoiceHeaderId;

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

    @Column(name = "C_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
    private String statusText;

    @Column(name = "STATUS_TIMESTAMP")
    private Date statusTimestamp;

    @Column(name = "INVOICE_STATUS")
    private Long invoiceStatus = 0L;

    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "INVOICE_TEXT", columnDefinition = "nvarchar(500)")
    private String invoiceDescription;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "INVOICE_HEADER_ID", referencedColumnName = "INVOICE_HEADER_ID")
    private List<LMDInvoiceLine> invoiceLines = new ArrayList<>();

}
