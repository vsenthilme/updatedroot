package com.courier.overc360.api.model.lastmile;

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
