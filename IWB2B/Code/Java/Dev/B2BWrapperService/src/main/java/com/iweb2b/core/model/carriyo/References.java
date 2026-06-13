package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class References {

    @JsonProperty("partner_order_reference")
    private String partnerOrderReference;

    @JsonProperty("partner_shipment_reference")
    private String partnerShipmentReference;
}