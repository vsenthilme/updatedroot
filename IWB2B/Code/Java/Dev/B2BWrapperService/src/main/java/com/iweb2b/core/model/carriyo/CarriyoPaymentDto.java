package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CarriyoPaymentDto {

    @JsonProperty("payment_mode")
    private String paymentMode;

    @JsonProperty("pending_amount")
    private double pendingAmount;

    @JsonProperty("total_amount")
    private double totalAmount;

    @JsonProperty("currency")
    private String currency;
}



