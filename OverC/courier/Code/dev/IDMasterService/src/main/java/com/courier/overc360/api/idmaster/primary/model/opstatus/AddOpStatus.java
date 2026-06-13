package com.courier.overc360.api.idmaster.primary.model.opstatus;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddOpStatus {

    @NotBlank(message = "Language Id is Mandatory")
    private String languageId;

    @NotBlank(message = "Company Id is Mandatory")
    private String companyId;

    private String statusCode;

    @NotBlank(message = "OpStatus Description is Mandatory")
    private String opStatusDescription;

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
