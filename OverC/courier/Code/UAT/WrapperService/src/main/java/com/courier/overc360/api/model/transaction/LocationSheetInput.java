package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class LocationSheetInput {

    private String languageId;
    private String companyId;
    private String partnerId;
    private String masterAirwayBill;
    private String partnerMasterAirwayBill;
    private String consoleId;
    private String location;

}
