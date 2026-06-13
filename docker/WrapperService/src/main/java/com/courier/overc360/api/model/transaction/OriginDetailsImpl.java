package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class OriginDetailsImpl {

    private String name;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;

}