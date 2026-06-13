package com.courier.overc360.api.idmaster.primary.model.hub;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddHub {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    private String hubCode;

    @NotBlank(message = "Hub Name is mandatory")
    private String hubName;

    private String hubCategory;

    @NotBlank(message = "CountryId is mandatory")
    private String countryId;

    private String provinceId;

    @NotBlank(message = "CityId is mandatory")
    private String cityId;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String addressLine4;

    private String latitude;

    private String longitude;

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
