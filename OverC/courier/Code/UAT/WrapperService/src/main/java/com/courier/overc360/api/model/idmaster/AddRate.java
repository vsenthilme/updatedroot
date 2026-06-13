package com.courier.overc360.api.model.idmaster;

import lombok.Data;

@Data
public class AddRate {

    private String languageId;

    private String companyId;

    private String partnerId;

    private Long lineNo;

    private String partnerName;

    private String partnerType;

    private String rateParameterId;

    private String rateParameterDescription;

    private Double rate;

    private Double ceilingValue;

    private String rateUnit;

    private String rateParameterUnit;

    private Double rangeFrom;

    private Double rangeTo;

    private String statusId;

    private String remark;

    private String subPartnerId;

    private String subPartnerName;

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
