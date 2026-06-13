package com.courier.overc360.api.midmile.primary.model.imagereference;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageReferenceCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID`, `C_ID`, `PARTNER_ID`, `MASTER_AIRWAY_BILL`, `HOUSE_AIRWAY_BILL`, `PIECE_ID`, `PIECE_ITEM_ID`, `IMAGE_REF_ID`
     */
    private String languageId;
    private String companyId;
    private String partnerId;
    private String masterAirwayBill;
    private String houseAirwayBill;
    private String imageRefId;
}
