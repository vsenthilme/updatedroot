package com.courier.overc360.api.idmaster.primary.model.usertype;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddUserType {

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotNull(message = "UserTypeId is mandatory")
    private Long userTypeId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    private String userTypeDescription;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

    private Long deletionIndicator;

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
