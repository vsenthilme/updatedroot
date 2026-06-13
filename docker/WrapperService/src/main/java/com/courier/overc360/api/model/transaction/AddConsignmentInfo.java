package com.courier.overc360.api.model.transaction;


import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class AddConsignmentInfo {

    private String loadTypeId;

    private String loadType;

    private String description;

    private String notes;

    @NotBlank(message = "Cod Amount is mandatory")
    private String codAmount;

    @NotBlank(message = "CodeFavor Of is mandatory")
    private String codFavorOf;

    @NotBlank(message = "Cod CollectionMode is mandatory")
    private String codCollectionMode;

    @NotBlank(message = "DeclaredValue is mandatory")
    private String declaredValue;

    @NotBlank(message = "DeclaredValueWithout Tax is mandatory")
    private String declaredValueWithoutTax;

    @NotBlank(message = "Length is mandatory")
    private String length;

    @NotBlank(message = "DimensionUnit is mandatory")
    private String dimensionUnit;

    @NotBlank(message = "Width is mandatory")
    private String width;

    @NotBlank(message = "Height is mandatory")
    private String height;

    @NotBlank(message = "Weight is mandatory")
    private String weight;

    @NotBlank(message = "Weight is mandatory")
    private String weightUnit;

    @NotBlank(message = "Volume is mandatory")
    private String volume;

    @NotBlank(message = "Volume Unit is mandatory")
    private String volumeUnit;

    @NotBlank(message = "UpStream CreationTime is mandatory")
    private String upstreamCreationTime;

    @NotBlank(message = "UpStream Creation is mandatory")
    private String upstreamCreationSource;

    @NotBlank(message = "Allocation Time is mandatory")
    private String allocationTime;

    @NotBlank(message = "Auto Allocate is mandatory")
    private String autoAllocate;

    @NotBlank(message = "Priority is mandatory")
    private String priority;

    @NotBlank(message = "Courier Partner is mandatory")
    private String courierPartner;

    @NotBlank(message = "Courier Account is mandatory")
    private String courierAccount;

    @NotBlank(message = "Courier Partner Reference Number is mandatory")
    private String courierPartnerReferenceNumber;

    private String pickupOtp;

    private String deliveryOtp;

    private String rtoOtp;

    private String tags;

    @NotBlank(message = "Service Time is mandatory")
    private String serviceTime;

    @NotBlank(message = "PickupService Time is mandatory")
    private String pickupServiceTime;

    @NotBlank(message = "Delivery Service Time is mandatory")
    private String deliveryServiceTime;

    @NotBlank(message = "PickupTimeSlot Start is mandatory")
    private String pickupTimeSlotStart;

    @NotBlank(message = "PickupTimeSlot End is mandatory ")
    private String pickupTimeSlotEnd;

    @NotBlank(message = "DeliveryTimeSlot Start is mandatory")
    private String deliveryTimeSlotStart;

    @NotBlank(message = "DeliveryTimeSlot End is mandatory")
    private String deliveryTimeSlotEnd;

    @NotBlank(message = "Scheduled At is mandatory")
    private String scheduledAt;

    private String workerTipAmount;

    private String workerEligiblePayout;

    private String constraintTags;

    @NotBlank(message = "EWayBill is mandatory")
    private String ewayBill;

    @NotBlank(message = "InvoiceAmount is mandatory")
    private String invoiceAmount;

    @NotBlank(message = "Invoice Number is mandatory")
    private String invoiceNumber;

    @NotBlank(message = "Invoice Date is mandatory")
    private String invoiceDate;

    @NotBlank(message = "InvoiceUrl is mandatory")
    private String invoiceUrl;

    @NotBlank(message = "Product Code is mandatory")
    private String productCode;

    @NotBlank(message = "IncoTerms is mandatory")
    private String incoTerms;

    @NotBlank(message = "Customs Value is mandatory")
    private String customsValue;

    @NotBlank(message = "Amount is mandatory")
    private String amount;

    @NotBlank(message = "Currency is mandatory")
    private String currency;

    @NotBlank(message = "IsCustoms Declarable is mandatory")
    private String isCustomsDeclarable;

//    private String packDetails;

//    private String phone;

}
