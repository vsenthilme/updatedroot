package com.courier.overc360.api.idmaster.primary.model.consignor;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConsignorCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `C_ID`, `SUB_PRODUCT_ID`, `PRODUCT_ID`, `CUSTOMER_ID`, `CONSIGNOR_ID`
     */

    private String languageId;
    private String companyId;
    private String subProductId;
    private String productId;
    private String customerId;
    private String consignorId;
    private String subProductValue;

}
