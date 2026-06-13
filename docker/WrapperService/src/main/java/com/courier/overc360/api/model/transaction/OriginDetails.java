package com.courier.overc360.api.model.transaction;


import lombok.Data;

import java.util.Date;

@Data
public class OriginDetails {

    private Long originId;
    private String originDetails;
    private String addressHubCode;
    private String accountId;
    private String email;
    private String name;
    private String phone;
    private String alternatePhone;
    private String addressLine1;
    private String addressLine2;
    private String pincode;
    private String district;
    private String city;
    private String state;
    private String country;
    private String latitude;
    private String longitude;
    private String createdBy;
    private Date createdOn = new Date();
    private String updatedBy;
    private Date updatedOn = new Date();
}
