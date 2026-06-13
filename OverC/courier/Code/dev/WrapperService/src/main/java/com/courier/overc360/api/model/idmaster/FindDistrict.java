package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindDistrict {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> countryId;
    private List<String> provinceId;
    private List<String> districtId;
    private List<String> statusId;

}
