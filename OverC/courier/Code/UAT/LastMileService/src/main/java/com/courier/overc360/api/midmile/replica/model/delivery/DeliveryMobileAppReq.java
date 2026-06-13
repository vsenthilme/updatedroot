package com.courier.overc360.api.midmile.replica.model.delivery;

import antlr.collections.impl.LList;
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
