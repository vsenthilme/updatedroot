package com.courier.overc360.api.midmile.primary.model.finance;

import lombok.Data;

import java.io.Serializable;

@Data
public class RateCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID`, `C_ID`, `PARTNER_ID`, `RATE_PARAMETER_ID`
     */

    private String languageId;
    private String companyId;
    private String partnerId;
    private String rateParameterId;
}
