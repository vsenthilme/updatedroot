package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindStorageTypeMaster {

    List<String> companyId;
    List<String> languageId;
    List<String> storageTypeId;
    List<String> statusId;
    List<String> zoneTypeId;
    List<String> hubCode;
}
