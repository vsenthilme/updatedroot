package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

@Data
public class PaymentCollected {

    private String method;
    private Double collectAmount;
}
