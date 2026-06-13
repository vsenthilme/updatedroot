package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class PdfLabelFormOutput {

    private Long consignmentId;
    private String customerReferenceNumber;
    private String houseAirwayBill;
    private String countryOfOrigin;
    private String partnerName;
    private String incoTerms;
    private String serviceTypeText;
    private String totalDuty;
    private String noOfPieceHawb;
    private String consignmentCurrency;
    private String partnerHouseAirwayBill;
    private String grossWeight;
    private String modeOfTransport;
    private String insurance;
    private String loadType;
    private String cod;
    private String destinationName;
    private String destinationPhone;
    private String destinationAlternatePhone;
    private String destinationAddress;
    private String destinationCity;
    private String destinationState;
    private String destinationCountry;
    private String originName;
    private String originPhone;
    private String originAlternatePhone;
    private String originAddress;
    private String originCity;
    private String originState;
    private String originCountry;
    private String pieceId;
    private String pieceProductCode;
    private String pieceValue;
    private String tags;
    private String goodsType;
    private Date createdOn;
}
