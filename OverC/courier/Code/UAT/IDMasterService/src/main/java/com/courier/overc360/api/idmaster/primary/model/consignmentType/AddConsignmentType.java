package com.courier.overc360.api.idmaster.primary.model.consignmentType;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddConsignmentType {

    @NotBlank(message = "companyId is mandatory")
    private String companyId;

    @NotBlank(message = "languageId is mandatory")
    private String languageId;

    private String consignmentTypeId;

    @NotBlank(message = "ConsignmentTypeText is mandatory")
    private String consignmentTypeText;

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
