package com.iweb2b.core.model.carriyo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CarriyoPickupDto {

    @JsonProperty("contact_name")
    private String contactName;

    @JsonProperty("contact_phone")
    private String contactPhone;

    @JsonProperty("contact_email")
    private String contactEmail;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("city_name_en")
    private String cityNameEn;

    @JsonProperty("state")
    private String state;

    @JsonProperty("coords")
    private List<Double> coords;

    @JsonProperty("postcode")
    private String postcode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("what3words")
    private String what3words;

    @JsonProperty("po_box")
    private String poBox;

    private String type;
    private String notes;
}

