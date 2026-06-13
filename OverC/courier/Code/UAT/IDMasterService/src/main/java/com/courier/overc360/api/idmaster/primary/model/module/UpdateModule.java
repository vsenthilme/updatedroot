package com.courier.overc360.api.idmaster.primary.model.module;

import lombok.Data;

@Data
public class UpdateModule {

    private String moduleDescription;

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

    private Boolean createUpdate;

    private Boolean delete;

    private Boolean view;

    private Boolean addModule;

    private Long menuId;

    private Long subMenuId;

}
