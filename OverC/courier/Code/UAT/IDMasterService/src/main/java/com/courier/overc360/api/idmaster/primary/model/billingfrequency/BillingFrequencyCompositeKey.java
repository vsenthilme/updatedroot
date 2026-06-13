package com.courier.overc360.api.idmaster.primary.model.billingfrequency;

import lombok.Data;

import java.io.Serializable;
@Data
public class BillingFrequencyCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;
    /*
     * `C_ID`, `LANG_ID`,'BILLING_FREQUENCY_ID
     */
    private String companyId;
    private String languageId;
    private String billingFrequencyId;
}
