package com.courier.overc360.api.idmaster.primary.model.timeslot;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class AddTimeSlot {

    private String languageId;

    private String languageDescription;

    private String companyId;

    private String companyName;

    private String timeSlotId;

    private String timeSlotStart;

    private String timeSlotEnd;

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

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();


}
