package com.courier.overc360.api.idmaster.primary.model.roleaccess;

import lombok.Data;

@Data
public class UpdateRoleAccess {

    private Long authorizationObjectId;

    private String authorizationObjectValue;

    private String description;

    private String userRoleName;

    private String moduleId;

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
