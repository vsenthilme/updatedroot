package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class UnconsolidationDeleteInput {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String partnerMasterAirwayBill;

//    private String consoleId;

    private String partnerHouseAirwayBill;

    private String pieceId;

//    private String pieceItemId;

}
