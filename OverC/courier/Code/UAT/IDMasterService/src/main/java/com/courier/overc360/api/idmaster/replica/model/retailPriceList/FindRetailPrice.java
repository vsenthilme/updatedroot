package com.courier.overc360.api.idmaster.replica.model.retailPriceList;

import lombok.Data;

import java.util.List;


@Data
public class FindRetailPrice {


    List<String> languageId;
    List<String> companyId;
    List<String> partnerId;
    List<Long> lineNo;
}
