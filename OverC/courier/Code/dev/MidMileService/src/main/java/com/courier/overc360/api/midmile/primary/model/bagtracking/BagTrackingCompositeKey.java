package com.courier.overc360.api.midmile.primary.model.bagtracking;

import lombok.Data;

import java.io.Serializable;

@Data
public class BagTrackingCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;
    /*
     * `C_ID`, `LANG_ID`,'PARTNER_ID','BAG_ID', 'HOUSE_AIRWAY_BILL'
     */
    private String companyId;
    private String languageId;
    private String partnerId;
    private String houseAirwayBill;
    private Long consignmentBagId;
}
