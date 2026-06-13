package com.courier.overc360.api.idmaster.primary.model.roleaccess;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddRoleAccess {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    private Long userRoleId;

    @NotNull(message = " MenuId is mandatory")
    private Long menuId;

    @NotNull(message = " SubMenuId is mandatory")
    private Long subMenuId;

    private Long roleId;

    private String moduleId;

    private Long authorizationObjectId;

    private String authorizationObjectValue;

    private String userRoleName;

    private String description;

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

    private Long deletionIndicator;

    private Boolean createUpdate;

    private Boolean edit;

    private Boolean view;

    private Boolean delete;


}




