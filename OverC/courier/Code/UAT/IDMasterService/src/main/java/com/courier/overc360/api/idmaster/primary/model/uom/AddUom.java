package com.courier.overc360.api.idmaster.primary.model.uom;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddUom {

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "UOMId is mandatory")
    private String uomId;

    @NotBlank(message = "UOM Type is mandatory")
    private String uomType;

    @NotBlank(message = "UOM Description is mandatory")
    private String uomDescription;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

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
