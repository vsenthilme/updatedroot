package com.courier.overc360.api.idmaster.primary.model.driverRouteAssignment;

import lombok.Data;

import java.io.Serializable;
@Data
public class DriverRouteAssignmentCompositeKey implements Serializable {
    private static final long serialVersionUID = -7617672247680004647L;
    /*
     * `C_ID`, `LANG_ID`,'COURIER_ID','ROUTE_ID','VEHICLE_REG_NUMBER','ASSIGNED_HUB_CODE'
     */
    private String companyId;
    private String languageId;
    private String courierId;
    private String routeId;
    private String vehicleRegNumber;
    private String assignedHubCode;
}
