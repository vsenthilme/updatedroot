package com.courier.overc360.api.idmaster.primary.model.rate;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddRate {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

    @NotBlank(message = "PartnerName is mandatory")
    private String partnerName;

    @NotBlank(message = "PartnerType is mandatory")
    private String partnerType;

    @NotBlank(message = "RateParameterId is mandatory")
    private String rateParameterId;

    private Long lineNo;

    @NotNull(message = "Rate is mandatory")
    private Double rate;

    @NotBlank(message = "RateUnit is mandatory")
    private String rateUnit;

    @NotBlank(message = "RateParameterUnit is mandatory")
    private String rateParameterUnit;

    @NotNull(message = "RangeFrom is mandatory")
    private Double rangeFrom;

    @NotNull(message = "RangeTo is mandatory")
    private Double rangeTo;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

    private String subPartnerId;

    private String subPartnerName;

    private String remark;

    private Double ceilingValue = 0.0;

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
