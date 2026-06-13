package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateUnconsolidation {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String partnerMasterAirwayBill;

    private String partnerHouseAirwayBill;

    private String masterAirwayBill;

    private String houseAirwayBill;

    private String partnerType;

    private String consignmentCurrency;

    private String consoleName;

    private String consoleGroupName;

    private String consignmentValue;

    private String exchangeRate;

    private String iata;

    private String customsInsurance;

    private String duty;

    private String consignmentValueLocal;

    private String addIata;

    private String addInsurance;

    private String customsValue;

    private String calculatedTotalDuty;

    private String expectedDuty;

    private String customsCurrency;

    private String partnerName;

    private String description;

    private String dutyPercentage;

    private String iataCharge;

    private String noOfPieces;

    private String dduCharge;

    private Double specialApprovalCharge;

    private String netWeight;

    private String primaryDo;

    private String secondaryDo;

    private String manifestedGrossWeight;

    private String grossWeight;

    private String tareWeight;

    private String manifestedQuantity;

    private String landedQuantity;

    private String totalQuantity;

    private Double volume;

    private String finalDestination;

    private String notifyParty;

    private String consigneeName;

    private String shipperId;

    private String shipperName;

    private String remarks;

    private String paymentType;

//    private String eventCode;
//
//    private String eventText;
//
//    private Date eventTimestamp;
//
//    private Date statusTimestamp;

    private String hawbType;

    private String hawbTypeId;

    private String hawbTypeDescription;

    private Date hawbTimeStamp;

    private String pieceType;

    private String pieceTypeId;

    private String pieceTypeDescription;

    private Date pieceTimeStamp;

    private String isConsolidatedShipment;

    private String isSplitBillOfLading;

    private String isPendingShipment;

    private Double totalDuty;

    private String goodsType;

    private String countryOfOrigin;

    private String noOfPieceHawb;

    private String airportOriginCode;

    private String customsKd;

    private String noOfPackageMawb;

    private String pieceId;

//    private String pieceItemId;

//    private String statusId;
//
//    private String statusText;

    private String bondedId;

    private Long shipmentBagId;

    private String actualCurrency;

    private String actualValue;

    private String specialApprovalValue;

    private String productId;

    private String productName;

    private String subProductId;

    private String subProductName;

    private String serviceTypeId;

    private String serviceTypeName;

    private String consigneeCivilId;

    private String incoTerms;

    private String invoiceNumber;

    private Date invoiceDate;

    private String invoiceType;

    private String invoiceSupplierName;

    private String hsCode;

    private String goodsDescription;

    private String quantity;

    private String freightCurrency;

    private String freightCharges;

    private String declaredValue;

    private String currency;

    private String consignmentLocalId;

    private String isExempted;

    private String exemptionFor;

    private String hubCode;

    private String exemptionBeneficiary;

    private String customsCcrNo;

    private String exemptionReference;

    private String hubName;

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

    // consolidation check
//    private Long unconsolidatedFlag;

}
