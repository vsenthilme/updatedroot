package com.courier.overc360.api.idmaster.replica.model.airportcode;

import lombok.Data;

import java.util.List;

@Data
public class FindAirportCode {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> airportCode;
    private List<String> statusId;

}
