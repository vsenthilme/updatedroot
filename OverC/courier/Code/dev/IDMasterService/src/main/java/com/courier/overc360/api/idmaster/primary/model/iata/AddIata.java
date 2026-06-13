package com.courier.overc360.api.idmaster.primary.model.iata;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddIata {

    @NotBlank(message = "LanguageId is mandatory ")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory ")
    private String companyId;

    @NotBlank(message = "Origin is mandatory")
    private String origin;

    @NotBlank(message = "OriginCode is mandatory")
    private String originCode;

    @NotBlank(message = "CurrencyId is mandatory")
    private String currencyId;

//    @NotBlank(message = "IataKd is mandatory")
    private Double iataKd;

    private String iataCharge;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

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
