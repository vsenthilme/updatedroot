package com.courier.overc360.api.idmaster.replica.model.courierpartner;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaCourierPartnerCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `COURIER_PARTNER_ID`
     */

    private String companyId;
    private String languageId;
    private String courierPartnerId;
    private String partnerId;

}
