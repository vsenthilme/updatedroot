package com.courier.overc360.api.idmaster.primary.model.module;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddModule {

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "Module Description is mandatory")
    private String moduleDescription;

    @NotNull(message = "MenuId is mandatory")
    private Long menuId;

    @NotNull(message = "SubMenuId is mandatory")
    private Long subMenuId;

    //    @NotBlank(message = "ModuleId is mandatory")
    private String moduleId;

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

    private Boolean createUpdate;

    private Boolean delete;

    private Boolean view;

    private Boolean addModule;
}
