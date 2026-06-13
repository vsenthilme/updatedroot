package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class ConsoleDeleteInput {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String masterAirwayBill;

    private String ConsoleId;

    private String houseAirwayBill;

    private String pieceId;

    private String pieceItemId;

}
