package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;
@Data
public class FindConsole {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> masterAirwayBill;
    private List<String> houseAirwayBill;
    private List<String> consoleId;

}
