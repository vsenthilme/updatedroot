package com.courier.overc360.api.midmile.primary.model.riderassignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblriderpickupdetails")
public class RiderAssignmentPickupDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RIDER_PICKUP_ID")
    private Long riderAssignmentPickupId;

    @Column(name = "ACCOUNT_ID", columnDefinition = "nvarchar(50)")
    private String accountId;

    @Column(name = "EMAIL", columnDefinition = "nvarchar(50)")
    private String email;

    @Column(name = "COMPANY_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

    @Column(name = "NAME", columnDefinition = "nvarchar(50)")
    private String name;

    @Column(name = "PHONE", columnDefinition = "nvarchar(50)")
    private String phone;

    @Column(name = "ALTERNATE_PHONE", columnDefinition = "nvarchar(50)")
    private String alternatePhone;

    @Column(name = "ADDRESS_LINE_1", columnDefinition = "nvarchar(50)")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2", columnDefinition = "nvarchar(50)")
    private String addressLine2;

    @Column(name = "PIN_CODE", columnDefinition = "nvarchar(50)")
    private String pinCode;

    @Column(name = "DISTRICT", columnDefinition = "nvarchar(50)")
    private String district;

    @Column(name = "CITY", columnDefinition = "nvarchar(50)")
    private String city;

    @Column(name = "STATE", columnDefinition = "nvarchar(50)")
    private String state;

    @Column(name = "COUNTRY", columnDefinition = "nvarchar(50)")
    private String country;

    @Column(name = "LATITUDE", columnDefinition = "nvarchar(50)")
    private String latitude;

    @Column(name = "LONGITUDE", columnDefinition = "nvarchar(50)")
    private String longitude;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();


}
