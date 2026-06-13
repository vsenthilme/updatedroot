package com.courier.overc360.api.idmaster.replica.model.billingfrequency;

import lombok.Data;

import java.util.List;
@Data
public class FindBillingFrequency {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> billingFrequencyId;
}
