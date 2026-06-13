package com.courier.overc360.api.idmaster.primary.model.currencyExchangeRate;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddCurrencyExchangeRate {

    @NotBlank(message = "Language id is mandatory")
    private String languageId;

    @NotBlank(message = "Company id is mandatory")
    private String companyId;

    private String fromCurrencyId;

    @NotBlank(message = "ToCurrency id is mandatory")
    private String toCurrencyId;

    @NotBlank(message = "FromCurrency description  is mandatory")
    private String fromCurrencyDescription;

    @NotBlank(message = "FromCurrency value  is mandatory")
    private String fromCurrencyValue;

    @NotBlank(message = "ToCurrency description  is mandatory")
    private String toCurrencyDescription;

    @NotBlank(message = "ToCurrency value  is mandatory")
    private String toCurrencyValue;

    @NotBlank(message = "Status Id  is mandatory")
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


