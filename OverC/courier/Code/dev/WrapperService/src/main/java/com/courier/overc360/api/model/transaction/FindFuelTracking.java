package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindFuelTracking {

    List<String> companyId;
    List<String> languageId;
    List<String> vehicleRegNumber;
    List<String> statusId;
}
