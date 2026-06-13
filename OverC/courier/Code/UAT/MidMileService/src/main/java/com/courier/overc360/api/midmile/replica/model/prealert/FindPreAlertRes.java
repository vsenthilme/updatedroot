package com.courier.overc360.api.midmile.replica.model.prealert;

import lombok.Data;

@Data
public class FindPreAlertRes {

    private String companyId;
    private String languageId;
    private String subCustomerId;
    private String subCustomerName;
    private String partnerId;
    private String partnerName;
    private String partnerHouseAirwayBill;
    private String partnerMasterAirwayBill;
    private Long invoice;
    private String ddpInvoiceNo;

}
