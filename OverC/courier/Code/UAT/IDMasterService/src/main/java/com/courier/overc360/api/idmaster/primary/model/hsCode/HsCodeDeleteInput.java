package com.courier.overc360.api.idmaster.primary.model.hsCode;


import lombok.Data;

@Data
public class HsCodeDeleteInput {

    private String hsCode;
    private String specialApprovalId;
    private String languageId;
    private String companyId;
}
