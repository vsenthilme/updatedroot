package com.courier.overc360.api.idmaster.replica.model.vehicle;

import lombok.Data;

import java.util.List;

@Data
public class FindVehicle {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> vehicleRegNumber;
    private List<String> statusId;
}
