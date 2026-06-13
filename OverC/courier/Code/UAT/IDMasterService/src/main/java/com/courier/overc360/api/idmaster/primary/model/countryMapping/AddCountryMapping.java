package com.courier.overc360.api.idmaster.primary.model.countryMapping;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddCountryMapping {

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

    @NotBlank(message = "Company Id is mandatory")
    private String companyId;

    @NotBlank(message = "Country Id is mandatory")
    private String countryId;

    @NotBlank(message = "Partner Id is mandatory")
    private String partnerId;

    @NotBlank(message = "Partner Name is mandatory")
    private String partnerName;

    @NotBlank(message = "Partner Type is mandatory")
    private String partnerType;

    private String partnerCountryId;

    private String partnerCountryName;

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
