package com.courier.overc360.api.idmaster.primary.model.courierpartner;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data

public class CourierPartnerCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `COURIER_PARTNER_ID`
     */

    private String companyId;
    private String languageId;
    private String courierPartnerId;
    private String partnerId;
}
