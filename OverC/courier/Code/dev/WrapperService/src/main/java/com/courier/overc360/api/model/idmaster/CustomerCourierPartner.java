package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerCourierPartner {

    private String companyId;

    private String languageId;

    private String courierPartnerId;

    private String partnerId;

    private String statusId;

    private String partnerType;

    private String assignedHubCode;

    private String languageDescription;

    private String companyName;

    private String courierPartnerText;

    private Long deletionIndicator = 0L;

    private String remark;

    private String statusDescription;

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

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();

}
