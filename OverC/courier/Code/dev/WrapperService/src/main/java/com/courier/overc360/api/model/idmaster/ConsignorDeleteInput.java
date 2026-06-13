package com.courier.overc360.api.model.idmaster;

import lombok.Data;

@Data
public class ConsignorDeleteInput {

    private String languageId;

    private String companyId;

    private String subProductId;

    private String subProductValue;

    private String productId;

    private String customerId;

    private String consignorId;

}
