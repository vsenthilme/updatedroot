package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DeliveryManifestMobAppInput {

    private List<String> hubCode;

    private Date fromDate;

    private Date toDate;

    private List<String> statusId;
}
