package com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AddCustomsClearanceInvoice {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "PartnerHouseAirwayBill is mandatory")
    private String partnerHouseAirwayBill;

    @NotBlank(message = "HouseAirwayBill is mandatory")
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

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;
}
