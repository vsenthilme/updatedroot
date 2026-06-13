package com.courier.overc360.api.idmaster.replica.model.specialapproval;

import lombok.Data;

import java.util.List;

@Data
public class FindSpecialApproval {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> specialApprovalId;
    private List<String> statusId;

}
