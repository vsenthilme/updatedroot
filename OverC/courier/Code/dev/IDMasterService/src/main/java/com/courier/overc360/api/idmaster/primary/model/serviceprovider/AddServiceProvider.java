package com.courier.overc360.api.idmaster.primary.model.serviceprovider;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddServiceProvider {

    @NotBlank(message = "Company Id is mandatory")
    private String companyId;

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

    @NotBlank(message = "Service Provider Id is mandatory")
    private String serviceProvidersId;

    private String cityId;

    private String provinceId;

    private String districtId;

    @NotBlank(message = "Status Id is mandatory")
    private String statusId;

    private String routeId;

    private String assignedHubCode;

    private String serviceProvidersText;

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
