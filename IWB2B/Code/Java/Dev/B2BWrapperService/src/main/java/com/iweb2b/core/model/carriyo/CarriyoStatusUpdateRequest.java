package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarriyoStatusUpdateRequest {

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("status_update_date")
    private Date statusUpdateDate;

    @JsonProperty("carriyo_status_code")
    private String carriyoStatusCode;

    @JsonProperty("carriyo_reason_code")
    private String carriyoReasonCode;

    @JsonProperty("carrier_status_code")
    private String carrierStatusCode;

    @JsonProperty("carrier_status_description")
    private String carrierStatusDescription;

    @JsonProperty("status_location")
    private String statusLocation;

    @JsonProperty("status_coordinates")
    private String statusCoordinates;

    @JsonProperty("proof_of_delivery")
    private String proofOfDelivery;

    @JsonProperty("driver_name")
    private String driverName;

    @JsonProperty("driver_phone")
    private String driverPhone;

    @JsonProperty("recipient_name")
    private String recipientName;

}
