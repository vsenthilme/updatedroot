package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindCcr {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> partnerMasterAirwayBill;
    private List<String> partnerHouseAirwayBill;
    private List<String> ccrId;
    private List<String> consoleId;
}
