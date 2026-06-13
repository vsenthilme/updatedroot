package com.courier.overc360.api.idmaster.primary.model.subproject;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddSubProduct {

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    private String subProductId;

    @NotBlank(message = "SubProduct Name is mandatory")
    private String subProductName;

    @NotBlank(message = "SubProduct Value is mandatory")
    private String subProductValue;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

    private String remark;

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
