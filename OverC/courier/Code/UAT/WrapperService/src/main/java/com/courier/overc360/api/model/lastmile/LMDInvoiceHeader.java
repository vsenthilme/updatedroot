package com.courier.overc360.api.model.lastmile;


import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class LMDInvoiceHeader {

    private Long invoiceHeaderId;

    private String languageId;

    private String companyId;

    private String invoiceNo;

    private String customerId;

    private Double invoiceAmount;

    private String currencyUnit;

    private String customerName;

    private String companyName;

    private String languageDescription;

    private String statusId;

    private String statusText;

    private Long invoiceStatus;

    private Date statusTimestamp;

    private Date invoiceDate;

    private String invoiceDescription;

    private Long deletionIndicator = 0L;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;

    private List<LMDInvoiceLine> invoiceLines;

}
