package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class UpdateOriginationDetails {

    private String addressHubCode;

    private String accountId;

    private String email;

    private String companyName;

    private String name;

    private String phone;

    private String alternatePhone;

    private String addressLine1;

    private String addressLine2;

    private String pinCode;

    private String district;

    private String city;

    private String state;

    private String country;

    private String latitude;

    private String longitude;
}
