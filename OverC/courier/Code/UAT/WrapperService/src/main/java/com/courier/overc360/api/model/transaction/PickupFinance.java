package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class PickupFinance {

    private String companyId;
    private String languageId;
    private String customerId;
    private String partnerType;
    private Double weight;

}