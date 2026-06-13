package com.courier.overc360.api.idmaster.primary.model.zonemaster;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddZoneMaster {

    @NotBlank(message = "Company Id is mandatory")
    private String companyId;

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

//    @NotBlank(message = "Zone Id is mandatory")
    private String zoneId;

    @NotBlank(message = "Zone Type is mandatory")
    private String zoneType;

    @NotBlank(message = "Status Id is mandatory")
    private String statusId;

    @NotBlank(message = "Hub Code is mandatory")
    private String hubCode;

    private String zoneTypeText;

    private String zoneText;

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

