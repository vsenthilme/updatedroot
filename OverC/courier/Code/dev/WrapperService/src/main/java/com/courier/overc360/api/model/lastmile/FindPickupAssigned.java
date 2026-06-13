package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.Date;

@Data
public class FindPickupAssigned {

    private Long pickupEntityId;
    private String customerName;
    private Long actualSequenceNo;
    private String address;
    private String phone;
    private String pieceCount;
    private String currency;
    private String houseAirwayBill;
    private String pickupDateTime;
    private String pickupId;
    private String statusId;
    private String statusDescription;
    private String consignmentCreation;
    private String remarks;
    private Double codAmount;
    private String createdBy;
    private Date createdOn;
}
