package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto {

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("currency")
    private String currency;
}
