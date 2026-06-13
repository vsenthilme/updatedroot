package com.courier.overc360.api.idmaster.primary.model.statusevent;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data

public class AddStatusEvent {

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

//    @NotBlank(message = "TypeId is mandatory")
    private String typeId;

    @NotBlank(message = "Type is mandatory")
    private String type;

    private String typeText;

    @NotBlank(message = "Action is mandatory")
    private String action;

    private String trigger;

    @NotBlank(message = "PreRequisite is mandatory")
    private String preRequisite;

    @NotBlank(message = "Level is mandatory")
    private String level;

    private String conclusive;

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
