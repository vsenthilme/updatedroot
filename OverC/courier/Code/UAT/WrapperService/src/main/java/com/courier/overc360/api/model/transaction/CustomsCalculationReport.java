package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class CustomsCalculationReport {

    private String partnerMasterAirwayBill;
    private Long noOfShipments;
    private String shipper;
    private Double totalConsignmentValue;
    private String currency;
    private Double totalCustomsValue;
    private Double iata;
    private Double customsInsurance;
    private Double totalDutyValue;
}