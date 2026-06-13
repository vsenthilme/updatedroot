package com.courier.overc360.api.midmile.primary.model.rescheduledelivery;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalTime;
import java.util.Date;

@Data
public class UpdateRescheduleDelivery {

    private Long rescheduleNo;

    private String languageId;

    private String companyId;

    private String deliveryId;

    private String languageDescription;

    private String companyName;

    private String partnerType;

    private String partnerId;

    private String partnerName;

    private String consignmentBagId;

    private String houseAirwayBill;

    private String consignmentId;

    private String reasonId;

    private String reasonDescription;

    private Date rescheduleDate;

    private LocalTime rescheduleStartTime;

    private LocalTime rescheduleEndTime;

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
