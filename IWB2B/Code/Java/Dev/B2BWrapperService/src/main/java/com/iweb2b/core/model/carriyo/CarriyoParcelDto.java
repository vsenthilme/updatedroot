package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarriyoParcelDto {

    @JsonProperty("partner_parcel_reference")
    private String partnerParcelReference;
    private CarriyoWeight weight;
    private DimensionDto dimension;
}
