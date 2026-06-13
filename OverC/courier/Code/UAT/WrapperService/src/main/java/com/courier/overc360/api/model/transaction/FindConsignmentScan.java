package com.courier.overc360.api.model.transaction;


import lombok.Data;

@Data
public class FindConsignmentScan {

    private String languageId;
    private String companyId;
    private String shippingLabelNo;
    private String statusId;
    private String hubCode;
    private String storageDescription;
}
