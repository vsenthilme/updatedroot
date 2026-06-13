package com.courier.overc360.api.midmile.primary.model.bondedmanifest;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BondedManifestDeleteInput {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

//    @NotBlank(message = "Master Airway Bill is mandatory")
//    private String masterAirwayBill;

    @NotBlank(message = "Bonded Id is mandatory")
    private String bondedId;

    @NotBlank(message = "Partner Master Airway Bill is mandatory")
    private String partnerMasterAirwayBill;

    @NotBlank(message = "Partner House Airway Bill is mandatory")
    private String partnerHouseAirwayBill;

//    @NotBlank(message = "House Airway Bill is mandatory")
//    private String houseAirwayBill;

//    @NotBlank(message = "Piece Id is mandatory")
//    private String pieceId;
//
//    @NotBlank(message = "PieceItem Id is mandatory")
//    private String pieceItemId;

}
