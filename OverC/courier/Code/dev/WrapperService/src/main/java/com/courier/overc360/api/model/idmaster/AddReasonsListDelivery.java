package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddReasonsListDelivery {
    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    private String reasonsId;

    @NotBlank(message = "reasonsText is mandatory")
    private String reasonsText;

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
