package com.courier.overc360.api.model.transaction;

import lombok.Data;


@Data
public class PreAlertDeleteInput {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String partnerHouseAirwayBill;

    private String partnerMasterAirwayBill;
}
