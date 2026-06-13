package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindRateParameter {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> rateParameterId;
    private List<String> statusId;

}
