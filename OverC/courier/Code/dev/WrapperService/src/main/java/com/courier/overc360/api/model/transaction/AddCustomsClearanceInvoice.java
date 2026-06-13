package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AddCustomsClearanceInvoice {

    private String languageId;

    private String companyId;

    private String partnerHouseAirwayBill;

    private String houseAirwayBill;

    private String invoiceNo;

    private Date invoiceDate;

    private String partnerId;

    private String partnerName;

    private String destinationName;

    private String destinationAddress;

    private Double clearanceFee;

    private Double customsDuty;

    private Double specialApprovalValue;

    private Double totalFee;

    private String paymentType;

    private String statusId;

    private Long deletionIndicator;

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

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;
}
