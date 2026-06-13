package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class RiderAssignmentEntity {

    private Long riderAssignmentId;

    private String consignmentBagId;

    private String languageId;

    private String languageDescription;

    private String companyId;

    private String companyName;

    private String statusId;

    private String statusDescription;

    private String partnerHouseAirwayBill;

    private String partnerMasterAirwayBill;

    private String partnerType;

    private String partnerId;

    private String partnerName;

    private String hawbType;

    private String hawbTypeId;

    private String hawbDescription;

    private Date hawbTimeStamp = new Date();

    private String pieceType;

    private String pieceTypeId;

    private String pieceTypeDescription;

    private Date pieceTimeStamp = new Date();

    private String paymentType;

//    private String countryOfDestination;
//
//    private String noOfCrt;
//
//    private String primaryDo;
//
//    private String secondaryDo;

    private Double insurance;

    private String noOfPackageMawb;

    private Double totalConsignmentValueMawb;

    private Double totalShipmentWeight;

    private String productId;

    private String productName;

    private String subProductId;

    private String subProductName;

    private String serviceTypeId;

    private String serviceTypeText;

    private String houseAirwayBill;

    private String totalWeightHawb;

    private String shipperId;

    private String shipperName;

    private String consigneeName;

    private String consigneeCivilId;

//    private String countryOfOrigin;

    private String incoTerms;

    private String remarks;

//    private String itemTotalPrice;

    private String hsCode;

    private String invoiceNumber;

    private Date invoiceDate;

    private String invoiceSupplierName;

    private String goodsDescription;

    private Double quantity;

    private Double netWeight;

    private Double grossWeight;

//    private String notifyParty;
//
//    private String customsCurrency;
//
//    private String freightCurrency;
//
//    private String freightCharges;
//
//    private String countryOfSupply;
//
//    private Double declaredValue;
//
//    private String consignmentCurrency;
//
//    private Double consignmentValue;
//
//    private String actualCurrency;
//
//    private String actualValue;
//
//    private Double totalDuty;
//
//    private String specialApprovalValue;
//
//    private String currency;
//
//    private String airportOriginCode;
//
//    private String flightNo;
//
//    private Date estimatedDepartureTime;
//
//    private Date flightArrivalTime;
//
//    private String noOfPackages;
//
//    private String flightName;

    private String packageType;

    private String customerCode;

    private String customerReferenceNumber;

    private String hubCode;

    private String hubName;

    private String manufacturer;

    private String consignmentType;

    private String actionType;

    private String movementType;

    private String forwardReferenceNumber;

    private String workerCode;

    private String loadType;

    private String description;

    private String notes;

    private Double codAmount;

    private String codFavorOf;

    private String codCollectionMode;

    private String declaredValueWithoutTax;

    private Double length;

    private String dimensionUnit;

    private Double width;

    private Double height;

    private Double weight;

    private String weightUnit;

    private Double volume;

    private String volumeUnit;

    private String upstreamCreationTime;

    private String upstreamCreationSource;

    private String allocationTime;

    private String autoAllocate;

    private String priority;

    private String courierPartner;

    private String courierAccount;

    private String courierPartnerReferenceNumber;

    private String pickupType;

    private String pickupOtp;

    private String deliveryOtp;

    private String rtoOtp;

    private String tags;

    private String serviceTime;

    private String pickupServiceTime;

    private String deliveryServiceTime;

    private Date customerPickupDate;

    private String pickupTimeSlotStart;

    private String pickupTimeSlotEnd;

    private String pickupId;

//    private String riderId;
//
//    private String riderName;

    private String assignedHubCode;

    private String vehicleRegNumber;

    private String routeId;

    private String riderType;

    private Long deliveryAttemptCount;

    private String deliveryFailedReason;

    private String deliveryTimeSlotStart;

    private String deliveryTimeSlotEnd;

    private String scheduledAt;

    private String workerTipAmount;

    private String workerEligiblePayout;

    private String constraintTags;

    private String invoiceAmount;

    private String invoiceUrl;

    private String productCode;

//    private Double customsValue;

    private String isCustomsDeclarable;

    private String isExchange;

    private String reverseReason;

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

    private String referenceField11;

    private String referenceField12;

    private String referenceField13;

    private String referenceField14;

    private String referenceField15;

    private String referenceField16;

    private String referenceField17;

    private String referenceField18;

    private String referenceField19;

    private String referenceField20;

    private Long deletionIndicator = 0L;

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();

    private RiderAssignmentDestinationDetails destinationDetails = new RiderAssignmentDestinationDetails();

    private RiderAssignmentOriginDetails originDetails = new RiderAssignmentOriginDetails();

    private RiderAssignmentPickupDetails pickupDetails = new RiderAssignmentPickupDetails();

    private Set<ImageReference> imageReferenceList = new HashSet<>();

}
