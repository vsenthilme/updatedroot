package com.courier.overc360.api.midmile.replica.model.clearancecharges;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaClearanceChargesCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID` , `C_ID`, `PARTNER_ID`, `SUB_CUSTOMER_ID`
     */

    private String languageId;
    private String companyId;
    private String partnerId;
    //    private String subCustomerId;
//    private Long clearanceChargesId;
}
