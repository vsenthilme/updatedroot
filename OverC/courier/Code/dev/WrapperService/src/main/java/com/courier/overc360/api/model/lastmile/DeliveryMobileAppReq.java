package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.List;

@Data
public class DeliveryMobileAppReq {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> pieceId;
    private List<String> houseAirwayBill;
    private List<String> deliveryId;
    private List<String> courierId;
}
