package com.courier.overc360.api.midmile.primary.model.console;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConsoleStatus {

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;
    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;
    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;
    @NotBlank(message = "PartnerHouseAirwayBill is mandatory")
    private String partnerHouseAirwayBill;
    @NotBlank(message = "PartnerMasterAirwayBill is mandatory")
    private String partnerMasterAirwayBill;
    @NotBlank(message = "PieceId is mandatory")
    private String pieceId;
//    @NotBlank(message = "HawbType is mandatory")
//    private String hawbType;
    @NotBlank(message = "HawbId is mandatory")
    private String hawbTypeId;
    @NotBlank(message = "ConsoleId is mandatory")
    private String consoleId;

    private String hubCode;
    private Long bagId;

    private Long unconsolidatedFlag;



}
