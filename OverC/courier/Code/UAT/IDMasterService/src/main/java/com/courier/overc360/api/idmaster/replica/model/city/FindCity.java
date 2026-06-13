package com.courier.overc360.api.idmaster.replica.model.city;

import lombok.Data;

import java.util.List;

@Data
public class FindCity {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> countryId;
    private List<String> provinceId;
    private List<String> districtId;
    private List<String> cityId;
    private List<String> statusId;

}
