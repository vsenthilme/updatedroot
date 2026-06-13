package com.courier.overc360.api.midmile.primary.model.drs;

import lombok.Data;

import java.io.Serializable;

@Data
public class DrsCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID`, `C_ID`, `CUSTOMER_ID`, `HOUSE_AIRWAY_BILL`
     */

    private String languageId;
    private String companyId;
    private String customerId;
    private String houseAirwayBill;
    private String pieceId;
}
