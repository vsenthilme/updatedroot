package com.courier.overc360.api.model.idmaster;


import lombok.Data;

import java.util.List;

@Data
public class FindFulfillmentPrice {

    List<String> languageId;
    List<String> companyId;
    List<String> partnerId;
    List<Long> lineNo;
}
