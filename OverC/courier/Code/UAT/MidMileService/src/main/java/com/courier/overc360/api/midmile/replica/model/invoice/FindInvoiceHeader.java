package com.courier.overc360.api.midmile.replica.model.invoice;

import lombok.Data;

import java.util.List;

@Data
public class FindInvoiceHeader {
    List<String> companyId;
    List<String> languageId;
    List<String> invoiceNo;
}
