package com.courier.overc360.api.idmaster.replica.model.vehicle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaVehicleCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;
    /*
     * `C_ID`, `LANG_ID`,'VEHICLE_REG_NUMBER'
     */
    private String companyId;
    private String languageId;
    private String vehicleRegNumber;
}
