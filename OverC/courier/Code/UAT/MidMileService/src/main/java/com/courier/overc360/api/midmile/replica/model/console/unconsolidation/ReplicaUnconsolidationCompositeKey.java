package com.courier.overc360.api.midmile.replica.model.console.unconsolidation;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaUnconsolidationCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID`, `C_ID`, `PARTNER_ID`, `MASTER_AIRWAY_BILL`, `HOUSE_AIRWAY_BILL`
     */

    private String languageId;
    private String companyId;
    private String partnerId;
    private String partnerHouseAirwayBill;
    private String partnerMasterAirwayBill;
    private String pieceId;
//    private String pieceItemId;

}
