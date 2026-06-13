package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class UpdateConsignmentStatus {

    private String languageId;

    private String companyId;

    private String pieceId;

    private String houseAirwayBill;

//    private String statusId;

    private String bagId;

//    private String eventCode;

    private String masterAirwayBill;

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
//    private Date pieceEventTimestamp;
//
//    private Date eventTimestamp;
//
//    private Date statusTimestamp;

    private String partnerHouseAirwayBill;

    private String partnerMasterAirwayBill;

    private String hawbType;

    private String hawbTypeId;

    private String hawbTypeDescription;

    private Date hawbTimeStamp;

    private String pieceType;

    private String pieceTypeId;

    private String pieceTypeDescription;

    private Date pieceTimeStamp;

    private String hubCode;

    private String hubName;

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

}
