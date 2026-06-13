package com.courier.overc360.api.midmile.replica.model.dto;

public interface OriginDetailsImpl {

//    @Column(name = "NAME", columnDefinition = "nvarchar(50)")
//    private String name;
//
//    @Column(name = "PHONE", columnDefinition = "nvarchar(50)")
//    private String phone;
//
//    @Column(name = "ADDRESS_LINE_1", columnDefinition = "nvarchar(50)")
//    private String addressLine1;
//
//    @Column(name = "ADDRESS_LINE_2", columnDefinition = "nvarchar(50)")
//    private String addressLine2;
//
//    @Column(name = "CITY", columnDefinition = "nvarchar(50)")
//    private String city;
//
//    @Column(name = "COUNTRY", columnDefinition = "nvarchar(50)")
//    private String country;

    String getName();
    String getPhone();
    String getAddressLine1();
    String getAddressLine2();
    String getCity();
    String getCountry();

}