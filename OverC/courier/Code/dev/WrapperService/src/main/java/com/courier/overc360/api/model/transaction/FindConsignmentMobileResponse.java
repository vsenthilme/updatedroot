package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class FindConsignmentMobileResponse {

    private String houseAirwayBill;
    private String partnerName;
    private String pieceId;
    private String noOfPieceHawb;
    private String originAddress;
    private String destinationAddress;


}
