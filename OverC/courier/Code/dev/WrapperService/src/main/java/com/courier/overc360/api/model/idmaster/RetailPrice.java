package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.Date;

@Data
public class RetailPrice {

    private String languageId;

    private String languageDescription;

    private String companyId;

    private String companyName;

    private String partnerId;

    private Long lineNo;

    private String partnerType;

    private String partnerName;

    private String subPartnerId;

    private String subPartnerName;

    private String rateParameterId;

    private String rangeFrom;

    private String rangeTo;

    private String rate;

    private Double ceilingValue;

    private String rateParameterUnit;

    private String rateUnit;

    private Double roundOff;

    private String statusId;

    private String statusDescription;

    private String remark;

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
