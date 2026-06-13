package com.courier.overc360.api.midmile.replica.model.prealert;

public interface CustomsCalculationReport {

    String getPartnerMasterAirwayBill();
    Long getNoOfShipments();
    String getShipper();
    Double getTotalConsignmentValue();
    String getCurrency();
    Double getTotalCustomsValue();
    Double getIata();
    Double getCustomsInsurance();
    Double getTotalDutyValue();
}
