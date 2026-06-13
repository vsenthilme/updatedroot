package com.courier.overc360.api.midmile.primary.model.reports;

import lombok.Data;

@Data
public class DeliveryCountResponse {

    private Long deliveryAssignedCount;

    private Long deliveryInProgressCount;
}
