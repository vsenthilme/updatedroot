package com.courier.overc360.api.model.lastmile;

import lombok.Data;

@Data
public class CustomerPickupStatusCount {

    private Long statusCount;
    private String statusId;
    private String statusText;
}
