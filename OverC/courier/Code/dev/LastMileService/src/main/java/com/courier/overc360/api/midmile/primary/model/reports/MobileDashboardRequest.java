package com.courier.overc360.api.midmile.primary.model.reports;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MobileDashboardRequest {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

}
