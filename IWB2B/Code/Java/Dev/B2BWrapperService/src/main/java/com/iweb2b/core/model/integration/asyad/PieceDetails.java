package com.iweb2b.core.model.integration.asyad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PieceDetails {


    private String description;
    private Double declared_value;
    private String volume;
    private Double weight;
    private Double height;
    private Long length;
    private Long width;

    @JsonProperty("weight_unit")
    private String weightUnit;

    @JsonProperty("dimension_unit")
    private String dimensionUnit;

    @JsonProperty("dimension_depth")
    private int dimensionDepth;

    private String volume_unit;
    private String piece_product_code;
    private Long chargeable_weight;
    private Long volumetric_weight;
    private Long denormalized_width;
    private Long denormalized_height;
    private Long denormalized_length;
    private Long denormalized_volume;
    private Double denormalized_weight;
    private String denormalized_volume_unit;
    private String denormalized_weight_unit;
    private String denormalized_dimension_unit;

    private String hsn_code;

    private int quantity;
    private String currency;

    @JsonProperty("partner_parcel_reference")
    private String partnerParcelReference;
}
