package com.courier.overc360.api.idmaster.primary.model.menu;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddMenu {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    //    @NotNull(message = "MenuId is mandatory")
    private Long menuId;

    //    @NotNull(message = "SubMenuId is mandatory")
    private Long subMenuId;

//    @NotNull(message = "Authorization Object Id is mandatory")
//    private Long authorizationObjectId;

//    private String authorizationObjectValue;

    @NotBlank(message = "Menu Name is mandatory")
    private String menuName;

    @NotBlank(message = "SubMenu Name is mandatory")
    private String subMenuName;

//    private String authorizationObject;

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
