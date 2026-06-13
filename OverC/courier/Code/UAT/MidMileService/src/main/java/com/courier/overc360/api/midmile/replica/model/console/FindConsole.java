package com.courier.overc360.api.midmile.replica.model.console;

import lombok.Data;

import java.util.List;

@Data
public class FindConsole {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> partnerMasterAirwayBill;
    private List<String> partnerHouseAirwayBill;
    private List<String> consoleId;
    private List<Long> unconsolidatedFlag;
    private List<String> hawbTypeId;
    private List<String> incoTerm;
    private List<String> subCustomerId;

    // for mobile App
    private List<String> shippingLabelNo;

}
