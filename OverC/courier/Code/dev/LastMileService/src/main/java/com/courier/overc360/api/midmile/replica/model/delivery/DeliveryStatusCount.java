package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

@Data
public class DeliveryStatusCount {
    private Long statusCount;
    private Long statusId;
    private String statusText;
}
