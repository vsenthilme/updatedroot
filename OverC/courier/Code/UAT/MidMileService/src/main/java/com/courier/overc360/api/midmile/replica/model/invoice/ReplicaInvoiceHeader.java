package com.courier.overc360.api.midmile.replica.model.invoice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblinvoiceheader")
public class ReplicaInvoiceHeader {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_HEADER_ID")
    private Long invoiceHeaderId;

    @Column(name = "LANG_ID", columnDefinition = "nvarchar(10)")
    private String languageId;

    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "INVOICE_NO", columnDefinition = "nvarchar(50)")
    private String invoiceNo;

    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
    private String partnerName;

    @Column(name = "TEXT", columnDefinition = "nvarchar(200)")
    private String description;

    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "STATUS_ID")
    private Long statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
    private String statusDescription;

    @Column(name = "SUB_CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String subCustomerId;

    @Column(name = "SUB_CUSTOMER_NAME", columnDefinition = "nvarchar(50)")
    private String subCustomerName;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "INVOICE_HEADER_ID")
    private List<ReplicaInvoiceLine> invoiceLines;

}
