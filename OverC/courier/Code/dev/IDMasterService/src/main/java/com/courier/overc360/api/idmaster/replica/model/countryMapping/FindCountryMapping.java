package com.courier.overc360.api.idmaster.replica.model.countryMapping;

import lombok.Data;

import java.util.List;

@Data
public class FindCountryMapping {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> countryId;
    private List<String> partnerId;
    private List<String> statusId;

}
