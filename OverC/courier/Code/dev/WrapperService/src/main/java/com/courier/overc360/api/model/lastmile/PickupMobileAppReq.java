package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.List;

@Data
public class PickupMobileAppReq {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> houseAirwayBill;
    private List<String> pickupId;
    private List<String> courierId;
}
