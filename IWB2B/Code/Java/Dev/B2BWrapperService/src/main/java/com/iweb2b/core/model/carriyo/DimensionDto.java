package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DimensionDto {

    @JsonProperty("width")
    private long width;

    @JsonProperty("height")
    private double height;

    @JsonProperty("depth")
    private int depth;

    @JsonProperty("unit")
    private String unit;
}
