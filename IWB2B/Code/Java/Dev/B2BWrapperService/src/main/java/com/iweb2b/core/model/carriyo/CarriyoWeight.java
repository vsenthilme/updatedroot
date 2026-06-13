package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarriyoWeight {

    @JsonProperty("value")
    private double value;

    @JsonProperty("unit")
    private String unit;

    private String sku;
}
