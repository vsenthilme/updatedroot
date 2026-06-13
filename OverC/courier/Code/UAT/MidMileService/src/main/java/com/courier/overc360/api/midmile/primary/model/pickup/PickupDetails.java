package com.courier.overc360.api.midmile.primary.model.pickup;

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

    private String alternatePhone;

    private String addressLine1;

    private String addressLine2;

    private String pinCode;

    private String district;

    private String city;

    private String state;

    private String country;

    private String pickupAddress;

    private String latitude;

    private String longitude;

}
