package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindDriverRouteAssignment {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> CourierId;
    private List<String> routeId;
    private List<String> vehicleRegNumber;
    private List<String> assignedHubCode;
    private List<String> statusId;
}
