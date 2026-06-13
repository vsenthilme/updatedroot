package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class ConsignmentStatus {

    private String languageId;

    private String companyId;

    private String pieceId;

    private String bagId;

    private String houseAirwayBill;

    private String statusId;

    private String eventCode;

    private String masterAirwayBill;

    private String languageDescription;

    private String companyName;

    private String statusText;

    private String pieceStatusId;

    private String pieceStatusText;

    private String eventText;

    private String pieceEventCode;

    private String pieceEventText;

    private Date pieceEventTimestamp = new Date();

    private Date eventTimestamp = new Date();

    private Date statusTimestamp = new Date();

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
