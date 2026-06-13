package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class CarriyoTrackingInfo {
    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("status_events")
    private List<CarriyoStatusEvent> statusEvents;

}
