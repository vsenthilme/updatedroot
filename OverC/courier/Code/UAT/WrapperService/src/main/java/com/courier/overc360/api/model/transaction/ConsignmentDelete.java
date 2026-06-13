package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConsignmentDelete {

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;
    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;
    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;
    @NotBlank(message = "MasterAirwayBill is mandatory")
    private String masterAirwayBill;
    @NotBlank(message = "HouseAirwayBill is mandatory")
    private String houseAirwayBill;

    private String pieceId;
    private String pieceItemId;
    private String imageRefId;
}
