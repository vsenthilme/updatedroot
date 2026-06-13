package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarriyoItemDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private PriceDto price;

    @JsonProperty("weight")
    private CarriyoWeight weight;

    @JsonProperty("sku")
    private String sku;

}