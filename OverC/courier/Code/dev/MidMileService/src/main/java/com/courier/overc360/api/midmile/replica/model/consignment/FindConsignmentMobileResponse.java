package com.courier.overc360.api.midmile.replica.model.consignment;

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
