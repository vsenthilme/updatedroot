package com.courier.overc360.api.idmaster.replica.model.province;

import lombok.Data;

import java.util.List;

@Data
public class FindProvince {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> countryId;
    private List<String> provinceId;
    private List<String> statusId;

}
