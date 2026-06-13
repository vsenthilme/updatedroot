package com.courier.overc360.api.midmile.primary.model.pickup;

import lombok.Data;

@Data
public class PickupPriceList {
    private String customerId;
    private String partnerType;
    private Double weight;
    private Double chargeableWeight;
    private Double ceilingValue;
    private Double frightCharge;
}
