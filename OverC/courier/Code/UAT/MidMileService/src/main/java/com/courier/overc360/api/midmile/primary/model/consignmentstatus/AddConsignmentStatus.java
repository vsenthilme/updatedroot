package com.courier.overc360.api.midmile.primary.model.consignmentstatus;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AddConsignmentStatus {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "PieceId is mandatory")
    private String pieceId;

    @NotBlank(message = "HouseAirwayBill is mandatory")
    private String houseAirwayBill;

//    @NotBlank(message = "StatusId is mandatory")
//    private String statusId;
//
//    @NotBlank(message = "EventCode is mandatory")
//    private String eventCode;

    private String masterAirwayBill;

    private String hubCode;

    private String hubName;

//    private String statusText;

    private String bagId;

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
