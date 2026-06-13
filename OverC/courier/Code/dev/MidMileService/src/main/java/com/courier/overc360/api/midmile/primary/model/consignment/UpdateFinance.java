package com.courier.overc360.api.midmile.primary.model.consignment;


import lombok.Data;

import java.util.List;

@Data
public class UpdateFinance {

    private List<String> houseAirwayBill;
    private Double ceilingValue;
    private Double chargeableWeight;
    private Double frightCharge;
    private Double codCharge;
    private Double fulfilmentCharge;
    private Double rtoCharge;
    private Double asrCharge;
    private Double movementCharge;
    private Double truckCharge;
    private Double outboundClearance;
}
