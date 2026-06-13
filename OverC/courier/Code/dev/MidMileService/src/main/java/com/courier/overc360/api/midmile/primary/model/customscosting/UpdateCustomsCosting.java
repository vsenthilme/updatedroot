package com.courier.overc360.api.midmile.primary.model.customscosting;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class UpdateCustomsCosting {

    private String partnerName;

    private String department;

    private Date date;

    private String cashHolder;

    private Long noOfShipments;

    private String invoiceNumber;

    private Long deletionIndicator;

    private String statusId;

    private Date invoiceDate;

    private String supplierName;

    private String costDescription;

    private Double costAmount;

    private String remark;

    private Long cashNumber;

    private Boolean finance;

    private String subCustomerId;

    private String subCustomerName;

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
}
