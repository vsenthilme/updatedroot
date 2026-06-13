package com.courier.overc360.api.idmaster.replica.model.citymapping;

import lombok.Data;

import java.util.List;

@Data
public class FindCityMapping {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> cityId;
    private List<String> partnerId;
    private List<String> statusId;

}
