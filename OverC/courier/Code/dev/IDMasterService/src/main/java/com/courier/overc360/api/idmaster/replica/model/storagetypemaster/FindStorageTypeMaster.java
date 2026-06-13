package com.courier.overc360.api.idmaster.replica.model.storagetypemaster;

import lombok.Data;

import java.util.List;
@Data
public class FindStorageTypeMaster {

    List<String> companyId;
    List<String> languageId;
    List<String> storageTypeId;
    List<String> statusId;
    List<String> hubCode;
    List<String> zoneTypeId;

}
