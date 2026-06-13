package com.courier.overc360.api.model.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class InvoiceLine {

    private Long invoiceLineId;

    private String languageId;

    private String companyId;

    private String invoiceNo;

    private String partnerMasterAirwayBill;

    private Long noOfShipments;

    private String clearanceCharge;

    private String otherApproval;

    private String foodApproval;

    private Double customDuty;

    private Double totalValue;

    private Double handlingFees;

    private Double approvals;

    private Double totalApproval;

    private String costPerShipment;

    private Long deletionIndicator = 0L;

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

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();


}
