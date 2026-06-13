package com.courier.overc360.api.midmile.replica.model.invoice;

import lombok.Data;

import java.util.List;

@Data
public class FindInvoiceLine {
    List<String> companyId;
    List<String> languageId;
    List<String> invoiceNo;
    List<String> partnerMasterAirwayBill;
}
