package com.courier.overc360.api.model.transaction;


import lombok.Data;

@Data
public class Finance {

    private String houseAirwayBill;
    private String companyId;
    private String languageId;
    private String partnerId;
    private String addOriginDetails;
    private String addDestinationDetails;
    private String goodsDescription;
    private String hsCode;
    private String noOfPieceHawb;
    private String incoTerms;
    private Double weight;
    private Double ceilingValue;
    private Double chargeableWeight;
    private Double frightCharge;
    private Double codCharge;
    private Double fulfilmentCharge;
    private Double rtoCharge;
    private Double asrCharge;
    private Double movementCharge;
    private Double truckCharge;
    private Double paymentCollected;
    private Double totalLmdCharges;
    private Double outboundClearance;

}