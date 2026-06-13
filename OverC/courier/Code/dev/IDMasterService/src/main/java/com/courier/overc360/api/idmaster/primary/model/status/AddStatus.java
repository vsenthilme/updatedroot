package com.courier.overc360.api.idmaster.primary.model.status;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddStatus {

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

//    @NotBlank(message = "Status Id is mandatory")
    private String statusId;

    @NotBlank(message = "Status Description is mandatory")
    private String statusDescription;

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
