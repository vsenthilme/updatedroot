package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CcrDeleteInput {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

    @NotBlank(message = "PartnerMaster Airway Bill is mandatory")
    private String partnerMasterAirwayBill;

    @NotBlank(message = "Ccr Id is mandatory")
    private String ccrId;

    @NotBlank(message = "CustomsCcrNo is mandatory")
    private String customsCcrNo;

    @NotBlank(message = "Partner House Airway Bill is mandatory")
    private String partnerHouseAirwayBill;

    @NotBlank(message = "ConsoleId is mandatory")
    private String consoleId;

    @NotBlank(message = "pieceId is mandatory")
    private String pieceId;

//    @NotBlank(message = "pieceItemId is mandatory")
//    private String pieceItemId;

}
