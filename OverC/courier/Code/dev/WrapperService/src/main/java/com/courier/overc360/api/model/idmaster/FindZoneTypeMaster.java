package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindZoneTypeMaster {

    List<String> companyId;
    List<String> languageId;
    List<String> zoneTypeId;
    List<String> statusId;
}
