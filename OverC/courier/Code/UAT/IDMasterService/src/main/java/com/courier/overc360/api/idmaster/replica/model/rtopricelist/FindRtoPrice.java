package com.courier.overc360.api.idmaster.replica.model.rtopricelist;

import lombok.Data;

import java.util.List;

@Data
public class FindRtoPrice {

    List<String> languageId;
    List<String> companyId;
    List<String> partnerId;
    List<Long> lineNo;
}
