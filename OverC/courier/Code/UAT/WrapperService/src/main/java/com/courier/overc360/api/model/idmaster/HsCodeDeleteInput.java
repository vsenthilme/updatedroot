package com.courier.overc360.api.model.idmaster;


import lombok.Data;

@Data
public class HsCodeDeleteInput {

    private String hsCode;
    private String specialApprovalId;
    private String languageId;
    private String companyId;
}
