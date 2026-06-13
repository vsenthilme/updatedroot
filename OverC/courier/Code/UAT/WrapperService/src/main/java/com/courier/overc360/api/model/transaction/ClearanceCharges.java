package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class ClearanceCharges {

    private String companyId;

    private String languageId;

    private String partnerId;

//    private String subCustomerId;

    private  String partnerName;

    private Long clearanceChargesId;

    private String noOfShipmentsFrom;

    private String languageDescription;

    private String companyName;

    private String noOfShipmentsTo;

    private String parameter;

    private Double multiplier;

    private String addMinCharge;

    private String clearanceCharges;

    private String currency;

    private String remark;

    private String statusDescription;

    private String statusId;

    private String formulaField1;

    private String formulaField2;

    private String formulaField3;

    private String formulaField4;

    private String formulaField5;

    private String formulaField6;

    private String formulaField7;

    private String formulaField8;

    private String formulaField9;

    private String formulaField10;

    private Date validityDateFrom = new Date();

    private Date validityDateTo = new Date();

    private Long deletionIndicator = 0L;

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

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();

    private String subCustomerId;

    private String subCustomerName;

}
