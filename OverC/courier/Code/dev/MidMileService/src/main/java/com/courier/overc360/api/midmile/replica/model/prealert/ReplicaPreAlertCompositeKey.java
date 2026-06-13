package com.courier.overc360.api.midmile.replica.model.prealert;


import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaPreAlertCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String companyId;

    private String languageId;

    private String partnerId;

    private String partnerHouseAirwayBill;

    private String partnerMasterAirwayBill;

}
