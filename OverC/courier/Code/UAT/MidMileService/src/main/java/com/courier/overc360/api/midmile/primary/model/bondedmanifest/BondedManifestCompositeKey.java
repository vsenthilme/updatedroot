package com.courier.overc360.api.midmile.primary.model.bondedmanifest;

import lombok.Data;

import java.io.Serializable;

@Data
public class BondedManifestCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID`, 'C_ID`, PARTNER_ID`, `BONDED_ID`, `PARTNER_MASTER_AIRWAY_BILL`, `PARTNER_HOUSE_AIRWAY_BILL`
     */

    private String languageId;
    private String companyId;
    private String partnerId;
    private String bondedId;
    private String partnerMasterAirwayBill;
    private String partnerHouseAirwayBill;

}
