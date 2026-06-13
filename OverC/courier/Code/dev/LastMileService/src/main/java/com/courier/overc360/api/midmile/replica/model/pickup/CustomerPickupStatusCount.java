package com.courier.overc360.api.midmile.replica.model.pickup;

import lombok.Data;

@Data
public class CustomerPickupStatusCount {

    private Long statusCount;
    private String statusId;
    private String statusText;
}
