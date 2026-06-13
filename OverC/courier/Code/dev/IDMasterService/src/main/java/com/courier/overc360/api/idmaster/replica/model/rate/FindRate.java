package com.courier.overc360.api.idmaster.replica.model.rate;

import lombok.Data;

import java.util.List;

@Data
public class FindRate {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> rateParameterId;
    private List<Long> lineNo;
}
