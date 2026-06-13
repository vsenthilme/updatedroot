package com.courier.overc360.api.idmaster.primary.model.storagetypemaster;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
@Data
public class AddStorageTypeMaster {


    @NotBlank(message = "Company Id is mandatory")
    private String companyId;

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

    @NotBlank(message = "StorageType Id is mandatory")
    private String storageTypeId;

//    private String cityId;
//
//    private String provinceId;
//
//    private String districtId;

    @NotBlank(message = "Status Id is mandatory")
    private String statusId;

    private String zoneTypeId;

    private String hubCode;

    private String zoneId;

    private String storageType;

    private String storageTypeText;

    private String remark;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;
}
