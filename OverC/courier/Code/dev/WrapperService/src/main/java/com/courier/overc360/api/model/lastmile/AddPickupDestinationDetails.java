package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.Date;

@Data
public class AddPickupDestinationDetails {

    private Long destinationDetailId;
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

    private String destinationAddress;

    private Long deletionIndicator = 0L;

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();

}
