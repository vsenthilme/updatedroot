package com.courier.overc360.api.idmaster.primary.model.specialapproval;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddSpecialApproval {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    private String specialApprovalId;

    @NotBlank(message = "SpecialApprovalText is mandatory")
    private String specialApprovalText;

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
