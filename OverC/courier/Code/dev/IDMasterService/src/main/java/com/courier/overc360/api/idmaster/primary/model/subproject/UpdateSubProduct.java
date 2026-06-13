package com.courier.overc360.api.idmaster.primary.model.subproject;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateSubProduct {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "SubProductId is mandatory")
    private String subProductId;

    @NotBlank(message = "SubProduct Value is mandatory")
    private String subProductValue;

    @NotBlank(message = "SubProduct Value is mandatory")
    private String subProductName;

    private String statusId;

    private String remark;

    private Long deletionIndicator;

    @NotBlank(message = "SubProduct Value Description is mandatory")
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
