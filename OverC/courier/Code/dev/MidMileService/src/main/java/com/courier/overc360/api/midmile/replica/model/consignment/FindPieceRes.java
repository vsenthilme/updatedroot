package com.courier.overc360.api.midmile.replica.model.consignment;

import lombok.Data;

@Data
public class FindPieceRes {

    private String houseAirwayBill;
    private String pickupId;
    private String pieceId;
    private String statusId;
    private String statusDescription;

}
