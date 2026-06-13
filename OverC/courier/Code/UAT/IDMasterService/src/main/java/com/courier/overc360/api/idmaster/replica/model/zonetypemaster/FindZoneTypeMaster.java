package com.courier.overc360.api.idmaster.replica.model.zonetypemaster;

import lombok.Data;

import java.util.List;
@Data
public class FindZoneTypeMaster {

    List<String> companyId;
    List<String> languageId;
    List<String> zoneTypeId;
    List<String> statusId;
}
