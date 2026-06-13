package com.courier.overc360.api.midmile.replica.model.prealert;

public interface PreAlertResponse {

    String getCompanyId();
    String getLanguageId();
    String getPartnerMasterAirwayBill();
    String getPartnerHouseAirwayBill();
    String getPartnerId();
    String getPartnerName();
    String getSubCustomerName();
    String getSubCustomerId();
    Long getInvoice();
    String getDdpInvoiceNo();
}
