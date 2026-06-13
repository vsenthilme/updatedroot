package com.courier.overc360.api.idmaster.replica.model.billmode;

import lombok.Data;

import java.util.List;

@Data
public class FindBillMode {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> billModeId;
    private List<String> statusId;
}
