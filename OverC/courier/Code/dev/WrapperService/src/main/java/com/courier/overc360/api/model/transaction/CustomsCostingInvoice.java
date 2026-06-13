package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.xml.transform.sax.SAXResult;
import java.util.Date;

@Data
public class CustomsCostingInvoice {

    private String costCenter;
    private String languageId;
    private String companyId;
    private String languageName;
    private String companyName;
    private String partnerId;
    private String partnerName;
    private Long lineNumber;
    private Boolean finance;
    private Long cashNumber;
    private Long noOfShipments;
    private String invoiceNumber;
    private String statusId;
    private String statusDescription;
    private String supplierName;
    private String subCustomerId;
    private String subCustomerName;
    private Date invoiceDate;
    private Date createdOn;
    private Date date;
    private String department;
    private Double totalCostAmount;
    private String approvedBy;
    private String referenceField1;
    private String referenceField2;
    private String referenceField3;
    private String referenceField4;
    private String referenceField5;
    private String costDescription;
    private Double costAmount;
    private String createdBy;

}
