package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class SortMasterDeleteInput {

    private String languageId;

    private String companyId;

    @NotBlank(message = "Sorting Id is mandatory")
    private String sortingId;

    private String zoneType;
}
