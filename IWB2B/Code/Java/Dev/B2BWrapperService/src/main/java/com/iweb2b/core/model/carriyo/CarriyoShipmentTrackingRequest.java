package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CarriyoShipmentTrackingRequest {
    @JsonProperty("tracking_numbers")
    private List<String> trackingNumbers;
}