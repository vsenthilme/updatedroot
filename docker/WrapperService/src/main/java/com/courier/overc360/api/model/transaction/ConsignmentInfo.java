package com.courier.overc360.api.model.transaction;


import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ConsignmentInfo {
        private Long consignmentInfoId;
        private String loadTypeId;
        private String loadType;
        private String description;
        private String notes;
        private String codAmount;
        private String codFavorOf;
        private String codCollectionMode;
        private String declaredValue;
        private String declaredValueWithoutTax;
        private String length;
        private String dimensionUnit;
        private String width;
        private String height;
        private String weight;
        private String weightUnit;
        private String volume;
        private String volumeUnit;
        private String upstreamCreationTime;
        private String upstreamCreationSource;
        private String allocationTime;
        private String autoAllocate;
        private String priority;
        private String courierPartner;
        private String courierAccount;
        private String courierPartnerReferenceNumber;
        private String pickupOtp;
        private String deliveryOtp;
        private String rtoOtp;
        private String tags;
        private String serviceTime;
        private String pickupServiceTime;
        private String deliveryServiceTime;
        private String pickupTimeSlotStart;
        private String pickupTimeSlotEnd;
        private String deliveryTimeSlotStart;
        private String deliveryTimeSlotEnd;
        private String scheduledAt;
        private String workerTipAmount;
        private String workerEligiblePayout;
        private String constraintTags;
        private String ewayBill;
        private String invoiceAmount;

//    @Column(name = "INVOICE_NUMBER", columnDefinition = "nvarchar(50)")
//    private String invoiceNumber;

//    @Column(name = "INVOICE_DATE", columnDefinition = "nvarchar(50)")
//    private String invoiceDate;
        private String invoiceUrl;
        private String productCode;
        private String incoTerms;
        private String customsValue;
        private String amount;
        private String currency;
        private String isCustomsDecalreable;
        private String packDetails;
        private String phone;
        private String createdBy;
        private Date createdOn = new Date();
        private String updatedBy;
        private Date updatedOn = new Date();
}
