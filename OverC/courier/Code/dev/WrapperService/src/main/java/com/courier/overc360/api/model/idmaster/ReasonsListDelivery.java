package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.Date;

@Data
public class ReasonsListDelivery {

    private String languageId;

    private String companyId;

    private String reasonsId;

    private String languageDescription;

    private String companyName;

    private String reasonsText;

    private String remark;

    private String statusId;

    private String statusDescription;

    private Long deletionIndicator = 0L;

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
