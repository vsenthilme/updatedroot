package com.courier.overc360.api.model.idmaster;

import lombok.Data;

@Data
public class ProductDeleteInput {

    private String languageId;

    private String companyId;

    private String subProductId;

    private String productId;

    private String subProductValue;

}
