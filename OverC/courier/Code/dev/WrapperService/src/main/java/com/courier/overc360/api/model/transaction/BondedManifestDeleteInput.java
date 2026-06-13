package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class BondedManifestDeleteInput {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String partnerMasterAirwayBill;

    private String bondedId;

    private String partnerHouseAirwayBill;

//    private String pieceId;
//
//    private String pieceItemId;

}
