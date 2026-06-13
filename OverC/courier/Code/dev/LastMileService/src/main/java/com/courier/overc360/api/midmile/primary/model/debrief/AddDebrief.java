package com.courier.overc360.api.midmile.primary.model.debrief;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class AddDebrief {

    private String languageId;

    private String companyId;

    private String courierId;

    private String noOfAssigned;

    private Date date;

    private Date timeOfDeparture;

    private String noOfDelivered;

    private String noOfReturned;

    private String noOfAttempted;

    private String noOfPaidCash;

    private Date timeOfFirstStop;

    private Date timeOfLastStop;

    private Date timeOfArrival;

    private String noOfPaidLink;

    private String total;

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
