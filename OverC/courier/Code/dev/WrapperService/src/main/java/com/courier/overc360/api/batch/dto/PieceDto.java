package com.courier.overc360.api.batch.dto;

import lombok.Data;

@Data
public class PieceDto {
    private String houseAirwayBill;
    private String pieceId;
    private String pieceType;
    private String pieceTypeId;
    private String partnerHouseAirwayBill;
    private String packReferenceNumber;
    private String pieceTypeDescription;
    private Double declaredValue;
    private String codAmount;
    private Double length;
    private String dimensionUnit;
    private Double width;
    private Double height;
    private Double weight;
    private String weight_unit;

}
