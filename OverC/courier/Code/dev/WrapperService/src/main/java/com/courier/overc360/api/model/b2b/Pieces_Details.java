package com.courier.overc360.api.model.b2b;

import lombok.Data;

@Data
public class Pieces_Details {

    private String description;
    private Double declared_value;
    private String volume;
    private Double weight;
    private Double height;
    private Long length;
    private Long width;
    private String weight_unit;
    private String dimension_unit;
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
}
