package com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class UpdateCustomsClearanceInvoice {

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

    private String statusId;

    private Long deletionIndicator;

    private String updatedBy;

    private Date updatedOn;
}
