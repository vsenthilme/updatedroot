package com.courier.overc360.api.midmile.primary.model.pickup;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PickupDeleteInput {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

//    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

//    @NotBlank(message = "House Airway Bill is mandatory")
    private String houseAirwayBill;

//    @NotBlank(message = "PickUpId is mandatory")
    private String pickupId;

}
