package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindUnconsolidation {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> partnerMasterAirwayBill;
    private List<String> partnerHouseAirwayBill;
    private List<Long> unconsolidatedFlag;

//    private List<String> consoleId;

}
