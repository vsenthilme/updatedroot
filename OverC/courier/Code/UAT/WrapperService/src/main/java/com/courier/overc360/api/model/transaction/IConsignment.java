package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class IConsignment {

    private Long consignmentId;
    private String noOfPieceHawb;
    private String partnerHouseAirwayBill;
    private String noOfPackageHawb;
    private String declaredValue;
    private String consignmentCurrency;
    private String incoTerms;
    private String countryOfOrigin;
    private String grossWeight;
    private String paymentType;
    private Date createdOn;

    OriginDetailsImpl originDetails;
    DestinationDetailsImpl destinationDetails;
    List<PieceDetailsImpl> pieceDetails;
}