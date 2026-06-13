package com.courier.overc360.api.idmaster.primary.model.fulFillmentPrice;

import lombok.Data;


@Data
public class AddFulfillmentPrice {

    private String languageId;

    private String languageDescription;

    private String companyId;

    private String companyName;

    private String partnerType;

    private String partnerId;

    private Long lineNo;

    private String partnerName;

    private String subPartnerId;

    private String subPartnerName;

    private String rateParameterId;

    private String rangeFrom;

    private String rangeTo;

    private Double ceilingValue = 0.0;

    private String rate;

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

}
