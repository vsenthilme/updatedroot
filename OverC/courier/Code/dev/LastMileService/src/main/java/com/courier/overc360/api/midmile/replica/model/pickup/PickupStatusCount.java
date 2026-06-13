package com.courier.overc360.api.midmile.replica.model.pickup;

import lombok.Data;

@Data
public class PickupStatusCount {
    private Long statusCount;
    private Long statusId;
    private String statusText;
}
