package com.courier.overc360.api.midmile.replica.model.customscosting;

import lombok.Data;

import java.util.List;

@Data
public class FindCustomsCosting {

    List<String> companyId;
    List<String> languageId;
    List<String> costCenter;
    List<Long> lineNumber;
    List<String> partnerId;
    List<Long> cashNumber;
}
