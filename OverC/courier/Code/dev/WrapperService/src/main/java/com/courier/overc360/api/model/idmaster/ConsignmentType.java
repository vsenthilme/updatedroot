package com.courier.overc360.api.model.idmaster;


import lombok.Data;

import java.util.Date;

@Data
public class ConsignmentType {

    private String languageId;

    private String languageDescription;

    private String companyId;

    private String companyName;

    private String consignmentTypeId;

    private String consignmentTypeText;

    private String remark;

    private String statusId;

    private String statusDescription;

    private Long deletionIndicator;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;

    private String createdBy;

    private Date createdOn;

    private Date updatedOn;

    private String updatedBy;

}
