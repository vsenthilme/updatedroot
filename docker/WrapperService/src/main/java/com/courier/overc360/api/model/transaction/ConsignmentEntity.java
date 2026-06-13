package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ConsignmentEntity {

    private String companyId;

    private String languageId;

    private String partnerId;

    private String partnerType;

    private String partnerName;

    private Long consignmentId;

    private String masterAirwayBill;

    private String houseAirwayBill;

    private String statusId;

    private String paymentType;

    private String eventCode;

    private String eventText;

    private Date eventTimestamp;

    private Date statusTimestamp;

    private String languageDescription;

    private String statusDescription;

    private String productId;

    private String productName;

    private String subProductId;

    private String subProductName;

    private String serviceTypeId;

    private String serviceTypeText;

    private String shipperId;

    private String shipperName;

    private String noOfPackageMawb;

    private String noOfPackageHawb;

    private String noOfPieceHawb;

    private String airportDestinationCode;

    private String partnerMasterAirwayBill;

    private String partnerHouseAirwayBill;

    private String flightDetails;

    private String flightNo;

    private String flightName;

    private String primaryDo;

    private String secondaryDo;

    private String modeOfTransport;

    private String insurance;

    private Long consoleIndicator;

    private Long manifestIndicator;

    private Date flightArrivalTime;

    private String consigneeName;

    private String consigneeCivilId;

    private String invoiceSupplierName;

    private String freightCurrency;

    private String freightCharges;

    private String countryOfSupply;

    private String remark;

    private String invoiceNumber;

    private String invoiceDate;

    private String invoiceType;

    private String hsCode;

    private String goodsDescription;

    private String countryOfOrigin;

    private String manufacturer;

    private String noOfPackages;

    private String itemTotalPrice;

    private String packageType;

    private String quantity;

    private String netWeight;

    private String grossWeight;

    private String notifyParty;

    private String isExempted;

    private String exemptionFor;

    private String exemptionBeneficiary;

    private String exemptionReference;

    private String airportOriginCode;

    private String referenceNumber;

    private String customerCode;

    private String customerReferenceNumber;

    private String hubCode;

    private String consignmentType;

    private String actionType;

    private String movementType;

    private String forwardReferenceNumber;

    private String lineNo;

    private String workerCode;

    private String loadTypeId;

    private String loadType;

    private String description;

    private String notes;

    private String codAmount;

    private String codFavorOf;

    private String codCollectionMode;

    private String declaredValue;

    private String consignmentCurrency;

    private String consignmentValue;

    private String actualCurrency;

    private String totalDuty;

    private String specialApprovalValue;

    private String declaredValueWithoutTax;

    private String length;

    private String dimensionUnit;

    private String exchangeRate;

    private String customsCurrency;

    private String dutyPercentage;

    private String iataCharge;

    private String dduCharge;

    private String specialApprovalCharge;

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

    private String consignmentValueLocal;

    private String addIata;

    private String addInsurance;

    private String customsValue;

    private String calculatedTotalDuty;

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

    private String invoiceUrl;

    private String productCode;

    private String incoTerms;

    private String amount;

    private String currency;

    private String isCustomsDeclarable;

    private String packDetails;

    private String storageLocation;

    private List<ReferenceImageList> referenceImageList;

    private String isExchange;

    private String reverseReason;

    private String countryOfDestination;

    private String noOfCrt;

    private String totalShipmentWeight;

    private String totalWeightHawb;

    private Date estimatedDepartureTime;

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
    private String referenceField21;
    private String referenceField22;
    private String referenceField23;
    private String referenceField24;
    private String referenceField25;
    private String referenceField26;
    private String referenceField27;
    private String referenceField28;
    private String referenceField29;
    private String referenceField30;
    private String referenceField31;
    private String referenceField32;
    private String referenceField33;
    private String referenceField34;
    private String referenceField35;
    private String referenceField36;
    private String referenceField37;
    private String referenceField38;
    private String referenceField39;
    private String referenceField40;
    private String referenceField41;
    private String referenceField42;
    private String referenceField43;
    private String referenceField44;
    private String referenceField45;
    private String referenceField46;
    private String referenceField47;
    private String referenceField48;
    private String referenceField49;
    private String referenceField50;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private Long preAlertValidationIndicator;
    private DestinationDetails destinationDetails;
    private OriginationDetails originDetails;
    private ReturnDetails returnDetails;
    private List<PieceDetails> pieceDetails = new ArrayList<>();
}
