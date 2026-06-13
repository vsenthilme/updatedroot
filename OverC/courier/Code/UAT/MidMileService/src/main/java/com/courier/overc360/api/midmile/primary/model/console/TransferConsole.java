package com.courier.overc360.api.midmile.primary.model.console;

import lombok.Data;


@Data
public class TransferConsole {

    private String partnerHouseAirwayBill;
    private String fromConsoleId;
    private String toConsoleId;
    private String pieceId;
//    private String pieceItemId;
}
