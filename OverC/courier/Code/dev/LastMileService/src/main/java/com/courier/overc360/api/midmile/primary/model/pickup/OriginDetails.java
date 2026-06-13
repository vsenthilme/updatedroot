package com.courier.overc360.api.midmile.primary.model.pickup;


import lombok.Data;

import java.util.Date;

@Data
public class OriginDetails {

    private Long originId;

//    private Long consignmentId = 0L;

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

    private Double latitude;

    private Double longitude;

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();

}
