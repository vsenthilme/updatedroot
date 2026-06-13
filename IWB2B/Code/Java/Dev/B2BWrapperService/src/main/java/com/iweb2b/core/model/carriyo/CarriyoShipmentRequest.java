package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CarriyoShipmentRequest {

    @JsonProperty("entity_type")
    private String entityType;

    private String tenant;
    private String merchant;

    private References references;

    private CarriyoPaymentDto payment;

    private SchedulerDto collection;

    private SchedulerDto delivery;

    private CarriyoPickupDto pickup;
    private CarriyoPickupDto dropoff;

    private List<CarriyoItemDto> items;

    private List<CarriyoParcelDto> parcels;

    @JsonProperty("carrier_custom_attributes")
    private CarrierCustomAttributes carrierCustomAttributes;
}
