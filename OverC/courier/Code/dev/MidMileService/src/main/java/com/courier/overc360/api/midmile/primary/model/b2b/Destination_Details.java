package com.courier.overc360.api.midmile.primary.model.b2b;

import lombok.Data;

@Data
public class Destination_Details {

    private String name;
    private String phone;
    private String alternate_phone;
    private String address_line_1;
    private String address_line_2;
    private String pincode;
    private String city;
    private String state;
    private String country;

    private String address_line_3;
    private String address_line_4;
}