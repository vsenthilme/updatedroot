package com.courier.overc360.api.idmaster.replica.model.product;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaProductCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `SUB_PRODUCT_ID`, `PRODUCT_ID`
     */

    private String companyId;
    private String languageId;
    private String subProductId;
    private String productId;
    private String subProductValue;

}
