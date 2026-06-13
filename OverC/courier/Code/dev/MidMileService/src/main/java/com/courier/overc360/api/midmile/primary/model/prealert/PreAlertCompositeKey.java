package com.courier.overc360.api.midmile.primary.model.prealert;


import lombok.Data;

import java.io.Serializable;

@Data
public class PreAlertCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String companyId;

    private String languageId;

    private String partnerId;

    private String partnerHouseAirwayBill;

    private String partnerMasterAirwayBill;

}
