package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.Date;

@Data

public class Module {

    private String companyId;

    private String moduleId;

    private String languageId;

    private Long menuId;

    private Long subMenuId;

    private String moduleDescription;

    private String companyIdAndDescription;

    private String languageIdAndDescription;

    private String menuName;

    private String subMenuName;

    private String statusId;

    private String statusDescription;

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

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;
}
