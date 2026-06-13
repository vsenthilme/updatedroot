package com.courier.overc360.api.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomsCosting {

    private String partnerId;

    private String costCenter;

    private Long lineNumber;

    private String companyId;

    private String languageId;

    private Long cashNumber;

    private String partnerName;

    private String languageDescription;

    private String companyName;

    private String department;

    private Date date;

    private String cashHolder;

    private Long noOfShipments;

    private String invoiceNumber;

    private String statusId;

    private Date invoiceDate;

    private String supplierName;

    private String costDescription;

    private Double costAmount;

    private String approvedBy;

    private BigDecimal checkField;

    private String statusDescription;

    private String remark;

    private Boolean finance;

    private Long deletionIndicator;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;

}
