package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BondedManifestDeleteInput {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String masterAirwayBill;

    private String bondedId;

    private String houseAirwayBill;

    private String pieceId;

    private String pieceItemId;

}
