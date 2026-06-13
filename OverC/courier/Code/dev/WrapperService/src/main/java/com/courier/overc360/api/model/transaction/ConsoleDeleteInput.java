package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class ConsoleDeleteInput {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String partnerMasterAirwayBill;

    private String ConsoleId;

    private String partnerHouseAirwayBill;

    private String pieceId;

//    private String pieceItemId;

}
