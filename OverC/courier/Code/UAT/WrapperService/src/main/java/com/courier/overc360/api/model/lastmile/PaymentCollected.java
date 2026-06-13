package com.courier.overc360.api.model.lastmile;

import lombok.Data;

@Data
public class PaymentCollected {

    private String method;
    private Double collectAmount;

}