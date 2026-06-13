package com.courier.overc360.api.midmile.primary.model.customscosting;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomsCostingCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `C_ID`, `COST_CENTER`, `LINE_NO`, `CUSTOMER_ID`
     */

    private String companyId;
    private String languageId;
    private String costCenter;
    private Long lineNumber;
    private String partnerId;
    private Long cashNumber;
}
