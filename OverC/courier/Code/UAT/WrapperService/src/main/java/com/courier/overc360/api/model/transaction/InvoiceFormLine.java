package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class InvoiceFormLine {

    private String hsCode;

    private String goodsDescription;

    private String itemWeight;

    private Double unitValue;

    private String totalValue;

}