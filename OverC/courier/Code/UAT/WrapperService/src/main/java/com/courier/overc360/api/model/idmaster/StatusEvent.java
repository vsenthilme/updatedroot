package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.Date;

@Data

public class StatusEvent {

    private String companyId;

    private String languageId;

    private String typeId;

    private String type;

    private String languageDescription;

    private String companyName;

    private String statusId;

    private String statusDescription;

    private String typeText;

    private String action;

    private String trigger;

    private String preRequisite;

    private String level;

    private String conclusive;

    private String remark;

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

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;
}

