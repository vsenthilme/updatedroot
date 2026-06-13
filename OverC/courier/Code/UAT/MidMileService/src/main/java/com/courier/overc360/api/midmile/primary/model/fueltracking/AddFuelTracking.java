package com.courier.overc360.api.midmile.primary.model.fueltracking;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddFuelTracking {

    @NotBlank(message = "Company Id is mandatory")
    private String companyId;

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

    @NotBlank(message = "Vehicle Reg Number Id is mandatory")
    private String vehicleRegNumber;

    private String riderId;

    private String pickupId;

    private String deliveryTimeSlotStart;

    private String deliveryTimeSlotEnd;

    private String totalKmInput;

    private String imageRefList;

    private String assignedHubCode;

    private String routeId;

    private String riderType;

    private String remark;

    private String statusId;

    private String statusDescription;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;

    private String referenceField11;

    private String referenceField12;

    private String referenceField13;

    private String referenceField14;

    private String referenceField15;

    private String referenceField16;

    private String referenceField17;

    private String referenceField18;

    private String referenceField19;

    private String referenceField20;

}
