package com.courier.overc360.api.idmaster.primary.model.route;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddRoute {

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    private String routeId;

    private String legId;

    @NotBlank(message = "Route Name is mandatory")
    private String routeName;

    private String legName;

    private String sequenceNo;

    @NotBlank(message = "StatusId is mandatory")
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
