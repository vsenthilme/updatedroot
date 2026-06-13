package com.courier.overc360.api.model.transaction;


import lombok.Data;

import java.util.Date;

@Data
public class CustomCostingTotalResult {

    private String invoiceNumber;

    private Date invoiceDate;

    private String supplierName;

    private String costDescription;

    private Double nasDeliveryOrder;

    private Double global;

    private Double labours;

    private Double approval;

    private Double handlingForkCharges;

    private Double customDuty;

    private Double stamp;

    private Double otherCharges;

    private Double specialApprovals;

    private Double otherApprovals;

    private String createdBy;

    private String approvedBy;

    private Double others;

    private Double foodApprovals;

    private Double total;

    private Long cashNumber;

    private Date date;

    private String department;

    private String cashHolder;

    private String partnerId;

    private String partnerName;

    private String costCenter;

    private Long noOfShipments;

    private String remark;

    private Double costAmount;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;



}
