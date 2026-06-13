package com.courier.overc360.api.model.lastmile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class PickupDestinationDetails {


    private String emailId;

    private String companyName;

    private String name;

    private String phone;

    private String alternatePhone;

    private String destinationAddress;

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
