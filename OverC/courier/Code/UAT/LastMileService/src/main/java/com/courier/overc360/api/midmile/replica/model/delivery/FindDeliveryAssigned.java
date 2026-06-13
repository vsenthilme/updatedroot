package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

import java.util.Date;

@Data
public class FindDeliveryAssigned {

    private String actualSequenceNo;
    private String address;
    private String currency;
    private String customerName;
    private String houseAirwayBill;
    private String phone;
    private Date deliveryDateTime;
    private String pieceCount;
    private String deliveryId;
    private String statusId;
    private String createdBy;
    private Date createdOn;

//    private String consignmentBagId;
//
//    private Date createdOn;
//
//    private String sequenceNo;
//
//    private String name;
//
//    private String addressLine1;




}
