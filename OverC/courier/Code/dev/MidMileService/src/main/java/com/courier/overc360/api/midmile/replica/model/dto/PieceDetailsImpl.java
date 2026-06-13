package com.courier.overc360.api.midmile.replica.model.dto;

public interface PieceDetailsImpl {

//    @Column(name = "PARTNER_HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
//    private String partnerHouseAirwayBill;
//
//    @Column(name = "DESCRIPTION", columnDefinition = "nvarchar(500)")
//    private String description;
//
//    @Column(name = "DECLARED_VALUE", columnDefinition = "nvarchar(50)")
//    private String declaredValue;
//
//    @Column(name = "WEIGHT", columnDefinition = "nvarchar(50)")
//    private String weight;
//
//    @Column(name = "HS_CODE", columnDefinition = "nvarchar(50)")
//    private String hsCode;
//
//    @Column(name = "CONSIGNMENT_ID")
//    private Long consignmentId;

    String partnerHouseAirwayBill();
    String description();
    String declaredValue();
    String weight();
    String hsCode();
    Long consignmentId();

}




















