package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrierCustomAttributes {
    @JsonProperty("carrier_account_number")
    private String carrierAccountNumber;

    @JsonProperty("service_type")
    private String serviceType;

}