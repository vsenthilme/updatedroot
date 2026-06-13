package com.courier.overc360.api.model.transaction;


import lombok.Data;

import java.util.List;

@Data
public class FindCustomInvoice {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> costCenter;
    private List<String> partnerId;
    private List<Long> cashNumber;
    private List<Long> lineNumber;
    private List<String> subCustomerId;

}
