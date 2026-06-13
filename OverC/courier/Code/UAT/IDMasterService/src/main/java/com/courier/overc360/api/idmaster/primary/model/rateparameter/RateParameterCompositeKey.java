package com.courier.overc360.api.idmaster.primary.model.rateparameter;

import lombok.Data;

import java.io.Serializable;

@Data
public class RateParameterCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`,`C_ID`,`RATE_PARAMETER_ID`
     */

    private String languageId;
    private String companyId;
    private String rateParameterId;

}

