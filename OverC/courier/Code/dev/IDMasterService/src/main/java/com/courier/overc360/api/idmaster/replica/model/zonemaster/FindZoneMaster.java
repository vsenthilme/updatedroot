package com.courier.overc360.api.idmaster.replica.model.zonemaster;

import lombok.Data;

import java.util.List;

@Data
public class FindZoneMaster {

    List<String> companyId;
    List<String> languageId;
    List<String> zoneId;
    List<String> zoneType;
    List<String> statusId;
    List<String> hubCode;
}
