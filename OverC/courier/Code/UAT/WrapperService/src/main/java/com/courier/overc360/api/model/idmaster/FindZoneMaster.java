package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindZoneMaster {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> zoneId;
    private List<String> zoneType;
    private List<String> statusId;
    private List<String> hubCode;

}
