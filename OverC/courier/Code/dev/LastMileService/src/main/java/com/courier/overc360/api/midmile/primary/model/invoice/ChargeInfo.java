package com.courier.overc360.api.midmile.primary.model.invoice;


import lombok.Data;

@Data
public class ChargeInfo {
    private String chargeDescription;
    private Double chargeAmount;
    private Long noOfShipment;

    public ChargeInfo(String chargeDescription, Double chargeAmount, Long noOfShipment) {
        this.chargeDescription = chargeDescription;
        this.chargeAmount = chargeAmount;
        this.noOfShipment = noOfShipment;
    }
}
