package com.iweb2b.core.model.integration.asyad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PieceItemDetails {

    private String description;
    private int quantity;

    @JsonProperty("declared_value")
    private double declaredValue;

    private String currency;

    private double weight;

    @JsonProperty("weight_unit")
    private String weightUnit;

    private String sku;

}
