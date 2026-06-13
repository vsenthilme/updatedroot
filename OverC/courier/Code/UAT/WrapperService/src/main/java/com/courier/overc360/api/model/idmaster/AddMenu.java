package com.courier.overc360.api.model.idmaster;

import lombok.Data;

@Data
public class AddMenu {

    private String languageId;

    private String companyId;

    private Long menuId;

    private Long subMenuId;

    //    private Long authorizationObjectId;
//
//    private String authorizationObjectValue;
//
    private String menuName;

    private String subMenuName;

//    private String authorizationObject;

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
