package com.courier.overc360.api.midmile.primary.model.ccr;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BayanUpdate {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

    @NotBlank(message = "PartnerHouseAirwayBill is mandatory")
    private String partnerHouseAirwayBill;

    @NotBlank(message = "PartnerMasterAirwayBill is mandatory")
    private String partnerMasterAirwayBill;

    @NotNull(message = "BayanHv is mandatory")
    private Double bayanHv;

}
