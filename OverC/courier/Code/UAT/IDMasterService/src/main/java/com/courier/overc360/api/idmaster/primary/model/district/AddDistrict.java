package com.courier.overc360.api.idmaster.primary.model.district;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddDistrict {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "ProvinceId is mandatory")
    private String provinceId;

    @NotBlank(message = "CountryId is mandatory")
    private String countryId;

    //    @NotBlank(message = "DistrictId is mandatory")
    private String districtId;

    @NotBlank(message = "DistrictName is mandatory")
    private String districtName;

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
