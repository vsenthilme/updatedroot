package com.courier.overc360.api.midmile.primary.model.consignment;


import lombok.Data;

@Data
public class ConsignmentCommonValues {

    private String pieceId;
    private String companyId;
    private String languageId;
    private String masterAirwayBill;
    private String houseAirwayBill;
    private String partnerId;
    private String companyName;
    private String partnerName;
    private String languageDescription;
    private String partnerMasterAirwayBill;
    private String partnerHouseAirwayBill;
    private String hsCode;
    private Double length;
    private Double width;
    private Double height;
    private Double volume;
    private String weightUnit;
    private String codAmount;
    private String hawbTypeId;
    private String hawbType;
    private String hawbTypeDescription;
    private String country;
    private String hubCode;
    private String hubName;
    private String createdBy;

}
