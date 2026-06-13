package com.courier.overc360.api.model.lastmile;

import lombok.Data;

@Data
public class DeliveryCountResponse {

    private Long deliveryAssignedCount;

    private Long deliveryInProgressCount;
}
