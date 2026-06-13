package com.courier.overc360.api.model.lastmile;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteInvoice {

    @NotBlank(message = "companyId is mandatory")
    private String companyId;
    @NotBlank(message = "languageId is mandatory")
    private String languageId;
    @NotBlank(message = "customerId is mandatory")
    private String customerId;
    @NotBlank(message = "InvoiceNo is mandatory")
    private String invoiceNo;
    private Long lineNo;
}
