package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class RiderAssignmentDeleteInput {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String houseAirwayBill;

    private String pickupId;

}
