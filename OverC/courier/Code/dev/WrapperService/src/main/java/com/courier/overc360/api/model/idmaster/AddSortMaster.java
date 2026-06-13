package com.courier.overc360.api.model.idmaster;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddSortMaster {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "SortingId is mandatory")
    private String sortingId;

    @NotBlank(message = "ZoneType is mandatory")
    private String zoneType;

    private String companyName;

    private String languageDescription;

    private String sortingText;

    private String zoneTypeText;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

    private String statusText;

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
