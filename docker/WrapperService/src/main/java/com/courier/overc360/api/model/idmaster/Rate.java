package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.Date;

@Data
public class Rate {

    private String languageId;

    private String languageDescription;

    private String companyId;

    private String companyName;

    private String partnerType;

    private String partnerId;

    private String partnerName;

    private String rateParameterId;

    private String rateParameterDescription;

    private String rateParameterUnit;

    private String rangeFrom;

    private String rangeTo;

    private String rate;

    private String rateUnit;

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
