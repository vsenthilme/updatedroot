package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.Date;

@Data
public class Debrief {

    private String languageId;

    private String companyId;

    private String courierId;

    private Long noOfAssigned;

    private Date date;

    private Date timeOfDeparture;

    private Long noOfDelivered;

    private Long noOfReturned;

    private Long noOfAttempted;

    private Long noOfPaidCash;

    private Date timeOfFirstStop;

    private Date timeOfLastStop;

    private Date timeOfArrival;

    private Long noOfPaidLink;

    private Long total;

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

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();
}
