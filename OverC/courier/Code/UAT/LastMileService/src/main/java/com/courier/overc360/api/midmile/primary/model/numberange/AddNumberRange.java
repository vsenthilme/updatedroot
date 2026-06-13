package com.courier.overc360.api.midmile.primary.model.numberange;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddNumberRange {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotNull(message = "NumberRangeCode is mandatory")
    private Long numberRangeCode;

    @NotBlank(message = "NumberRangeObject is mandatory")
    private String numberRangeObject;

    @NotBlank(message = "NumberRangeFrom is mandatory")
    private String numberRangeFrom;

    @NotBlank(message = "NumberRangeTo is mandatory")
    private String numberRangeTo;

    @NotBlank(message = "NumberRangeCurrent is mandatory")
    private String numberRangeCurrent;

    private String numberRangeStatus;

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
