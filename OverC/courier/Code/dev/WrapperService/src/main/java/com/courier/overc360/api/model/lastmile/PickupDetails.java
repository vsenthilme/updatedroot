package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class PickupDetails {


    private String pickupHubCode;

    private String emailId;

    private String companyName;

    private String name;

    private String phone;

    private String pickUpAddress;

    private String pickUpAddress1;

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

}
