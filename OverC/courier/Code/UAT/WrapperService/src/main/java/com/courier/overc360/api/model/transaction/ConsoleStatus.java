package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class ConsoleStatus {

    private String companyId;
    private String languageId;
    private String partnerId;
    private String partnerHouseAirwayBill;
    private String partnerMasterAirwayBill;
    private String pieceId;
//    private String hawbType;
    private String hawbTypeId;
    private String consoleId;
    private String hubCode;

}
