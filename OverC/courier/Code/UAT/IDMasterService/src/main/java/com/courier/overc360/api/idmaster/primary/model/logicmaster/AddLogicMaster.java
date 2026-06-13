package com.courier.overc360.api.idmaster.primary.model.logicmaster;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddLogicMaster {

    @NotBlank(message = "Company Id is mandatory")
    private String companyId;

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

    @NotBlank(message = "Console Count Id is mandatory")
    private String consoleCountId;

    private Long noOfShipments;

    private Double consignmentValue;

    @NotBlank(message = "Status Id is mandatory")
    private String statusId;

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
