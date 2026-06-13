package com.courier.overc360.api.model.transaction;
import lombok.Data;

import java.util.Date;

@Data
public class ConsignmentDetailsImpl {
    private String consignmentId;
    private String languageId;
    private String languageDescription;
    private String companyId;
    private String companyName;
    private String partnerId;
    private String partnerName;
    private String masterAirwayBill;
    private String houseAirwayBill;
    private String noOfPieceHawb;
    private String partnerMasterAirwayBill;
    private String partnerHouseAirwayBill;
    private String productId;
    private String productName;
    private String subProductId;
    private String subProductName;
    private String serviceTypeId;
    private String serviceTypeText;
    private String incoTerms;
    private String customerReferenceNumber;
    private String hawbType;
    private String hawbTypeId;
    private Date hawbTimeStamp;
    private String hawbTypeDescription;
    private String addOriginDetails;
    private String addDestinationDetails;
    private String paymentType;
    private String createdBy;
    private String createdOn;
}