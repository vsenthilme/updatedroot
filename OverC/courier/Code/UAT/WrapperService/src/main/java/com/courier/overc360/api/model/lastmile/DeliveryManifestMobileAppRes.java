package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.Date;

@Data
public class DeliveryManifestMobileAppRes {

    private String manifestNumber;

    private Long noOfShipment;

    private String rider;

    private Date date;
}
