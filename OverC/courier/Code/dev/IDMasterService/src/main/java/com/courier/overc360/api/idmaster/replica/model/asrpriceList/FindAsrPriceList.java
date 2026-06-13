package com.courier.overc360.api.idmaster.replica.model.asrpriceList;

import lombok.Data;

import java.util.List;

@Data
public class FindAsrPriceList {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<Long> lineNo;
}
