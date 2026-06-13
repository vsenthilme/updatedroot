package com.courier.overc360.api.idmaster.primary.model.subproject;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubProductCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `SUB_PRODUCT_ID`
     */

    private String companyId;
    private String languageId;
    private String subProductId;
    private String subProductValue;

}
