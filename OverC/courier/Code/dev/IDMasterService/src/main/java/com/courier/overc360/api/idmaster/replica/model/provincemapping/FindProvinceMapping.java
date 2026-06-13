package com.courier.overc360.api.idmaster.replica.model.provincemapping;

import lombok.Data;

import java.util.List;

@Data
public class FindProvinceMapping {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> provinceId;
    private List<String> partnerId;
    private List<String> statusId;

}
