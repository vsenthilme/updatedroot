package com.courier.overc360.api.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class RiderAssignmentDestinationDetails {

    private Long destinationDetailId;

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

    private String imageRefList;

    private String isExchange;

    private String reverseReason;

    private Long deletionIndicator = 0L;

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();

}
