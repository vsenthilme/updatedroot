package com.courier.overc360.api.batch.dto;


import lombok.Data;

@Data
public class ConsignmentDto {

    private String houseAirwayBill;
//    private Double netWeight;
//    private Double grossWeight;
    private Double length;
    private Double width;
    private Double height;
//    private Double weight;
    private Double actualWeight;
    private String dimensionUnit;
    private String weightUnit;

}
