package com.courier.overc360.api.idmaster.primary.model.partnerhubmapping;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
public class AddPartnerHubMapping {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "HubCode is mandatory")
    private String hubCode;

    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

    @NotBlank(message = "Partner Name is mandatory")
    private String partnerName;

    @NotBlank(message = "Partner Type is mandatory")
    private String partnerType;

    private String productCode;

    private String defaultHubCode;

    private String transitDC;

    private String finalDC;

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
