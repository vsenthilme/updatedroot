package com.courier.overc360.api.model.lastmile;


import lombok.Data;

@Data
public class MobileDashboardResponse {

    private Long pickupTotal;
    private Long pickupAssignedCount;
    private Long pickupInProgressCount;
    private Long deliveryTotal;
    private Long deliveryAssignedCount;
    private Long deliveryInProgressCount;
}
