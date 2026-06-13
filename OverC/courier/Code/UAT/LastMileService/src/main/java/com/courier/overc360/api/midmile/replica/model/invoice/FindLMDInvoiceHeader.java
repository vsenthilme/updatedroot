package com.courier.overc360.api.midmile.replica.model.invoice;


import lombok.Data;

import java.util.List;

@Data
public class FindLMDInvoiceHeader {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> customerId;
    private List<String> invoiceNo;
    private Long invoiceStatus;

}
