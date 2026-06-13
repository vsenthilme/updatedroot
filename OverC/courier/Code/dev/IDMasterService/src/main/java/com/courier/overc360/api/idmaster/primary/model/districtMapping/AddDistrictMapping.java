package com.courier.overc360.api.idmaster.primary.model.districtMapping;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddDistrictMapping {

    @NotBlank(message = "District id is mandatory")
    private String districtId;

    @NotBlank(message = "Language id is mandatory")
    private String languageId;

    @NotBlank(message = "Company id is mandatory")
    private String companyId;

    @NotBlank(message = "Partner id is mandatory")
    private String partnerId;

    @NotBlank(message = "Partner name is mandatory")
    private String partnerName;

    @NotBlank(message = "Partner type is mandatory")
    private String partnerType;

    private String partnerDistrictId;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

    private String remark;

    private String partnerDistrictName;

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
