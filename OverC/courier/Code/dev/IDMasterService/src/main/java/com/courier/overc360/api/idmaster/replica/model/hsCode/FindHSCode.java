package com.courier.overc360.api.idmaster.replica.model.hsCode;

import lombok.Data;

import java.util.List;

@Data
public class FindHSCode {


    private List<String> languageId;
    private List<String> companyId;
    private List<String> hsCode;
    private List<String> specialApprovalId;
    private List<String> statusId;

}
