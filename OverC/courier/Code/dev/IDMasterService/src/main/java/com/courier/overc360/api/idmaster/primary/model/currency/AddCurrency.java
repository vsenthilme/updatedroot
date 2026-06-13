package com.courier.overc360.api.idmaster.primary.model.currency;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddCurrency {

    @NotBlank(message = "CurrencyId is mandatory")
    private String currencyId;

    @NotBlank(message = "Currency Description is mandatory")
    private String currencyDescription;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

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
