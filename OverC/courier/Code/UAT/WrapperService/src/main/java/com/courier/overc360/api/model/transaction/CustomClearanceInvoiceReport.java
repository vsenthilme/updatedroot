package com.courier.overc360.api.model.transaction;


import lombok.Data;

@Data
public class CustomClearanceInvoiceReport{

    private String partnerMasterAirwayBill;

    private Long noOfShipments;

    private Double clearanceCharge;

    private Double otherApproval;

    private Double foodApproval;

    private Double approvals;

    private Double handlingFees;

    private Double customDuty;

    private Double totalValue;

    private Double specialApproval;

    private String costPerShipment;

    private Double totalApproval;

}
