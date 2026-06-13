package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

import java.util.Date;

@Data
public class DeliveryManifestMobileAppRes {

    private String manifestNumber;

    private Long noOfShipment;

    private String rider;

    private Date date;
}
