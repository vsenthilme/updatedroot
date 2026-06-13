package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ConsignmentStatus {

    private String languageId;

    private String companyId;

    private String pieceId;

    private String bagId;

    private String houseAirwayBill;

//    private String statusId;
//
//    private String eventCode;

    private String partnerHouseAirwayBill;

    private String partnerMasterAirwayBill;

    private String masterAirwayBill;

    private String languageDescription;

    private String companyName;

    private String hubCode;

    private String hubName;

//    private String statusText;
//
//    private String pieceStatusId;
//
//    private String pieceStatusText;
//
//    private String eventText;
//
//    private String pieceEventCode;
//
//    private String pieceEventText;
//
//    private Date pieceEventTimestamp = new Date();
//
//    private Date eventTimestamp = new Date();
//
//    private Date statusTimestamp = new Date();

    private String hawbType;

    private String hawbTypeId;

    private String hawbTypeDescription;

    private Date hawbTimeStamp = new Date();

    private String pieceType;

    private String pieceTypeId;

    private String pieceTypeDescription;

    private Date pieceTimeStamp = new Date();

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
