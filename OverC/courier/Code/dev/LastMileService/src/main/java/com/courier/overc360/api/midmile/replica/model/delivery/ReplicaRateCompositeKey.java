package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaRateCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID`, `C_ID`, `PARTNER_ID`, `RATE_PARAMETER_ID`,'LINE_NO'
     */

    private String languageId;
    private String companyId;
    private String partnerId;
    private String rateParameterId;
    private Long lineNo;
}
