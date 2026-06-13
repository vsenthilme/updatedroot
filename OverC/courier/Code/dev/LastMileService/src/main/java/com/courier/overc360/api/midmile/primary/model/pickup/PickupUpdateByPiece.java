package com.courier.overc360.api.midmile.primary.model.pickup;

import lombok.Data;

@Data
public class PickupUpdateByPiece {

    private String languageId;
    private String companyId;
    private String houseAirwayBill;
    private String pieceId;
    private String statusId;
    private String pickupId;

}
