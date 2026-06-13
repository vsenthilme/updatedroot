package com.courier.overc360.api.idmaster.primary.model.rate;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UpdateRate {

    private String partnerId;

    private String companyId;

    private String languageId;

    private String rateParameterId;

    private Long lineNo;

    private Double rate;

    private String rateUnit;

    private String rateParameterUnit;

    private Double ceilingValue;

    private Double rangeFrom;

    private Double rangeTo;

    private String partnerName;

    private String partnerType;

    private String statusId;

    private String subPartnerId;

    private String subPartnerName;

    private String deletionIndicator;

    private String remark;

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
