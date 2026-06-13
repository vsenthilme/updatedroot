package com.courier.overc360.api.midmile.replica.model.bondedmanifest;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaBondedManifestCompositeKey implements Serializable {

    /**
     * `LANG_ID `, `C_ID`, `PARTNER_ID`, `BONDED_ID`, `PARTNER_HOUSE_AIRWAY_BILL`, `PARTNER_MASTER_AIRWAY_BILL`
     */

    private String languageId;
    private String companyId;
    private String partnerId;
    private String bondedId;
    private String partnerMasterAirwayBill;
    private String partnerHouseAirwayBill;

}
