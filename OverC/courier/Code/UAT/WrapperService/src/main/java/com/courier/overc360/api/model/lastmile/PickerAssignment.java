package com.courier.overc360.api.model.lastmile;


import lombok.Data;

import java.util.Date;

@Data
public class PickerAssignment {

    private String userId;
    private String mobileNumber;
    private String address;
    private Double latitude;
    private Double longitude;
    private Long noOfShipmentsAssignment;
    private Long noOfShipmentsDelivered;
    private Long NoOfShipmentsPending;
    private String lastShipmentsETA;

}
