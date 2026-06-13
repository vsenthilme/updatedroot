package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class BondedManifest {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String masterAirwayBill;

    private String houseAirwayBill;

    private String  bondedId;

    private String pieceId;

    private String pieceItemId;

    private String languageDescription;

    private String companyName;

    private String partnerType;

    private String partnerName;

    private String statusId;

    private String statusText;

    private String noOfPackageMawb;

    private String partnerMasterAirwayBill;

    private String partnerHouseAirwayBill;

    private String primaryDo;

    private String secondaryDo;

    private String description;

    private String netWeight;

    private String manifestedGrossWeight;

    private String grossWeight;

    private String tareWeight;

    private String manifestedQuantity;

    private String landedQuantity;

    private String totalQuantity;

    private String volume;

    private String finalDestination;

    private String notifyParty;

    private String consigneeName;

    private String productId;

    private String productName;

    private String subProductId;

    private String subProductName;

    private String serviceTypeId;

    private String serviceTypeName;

    private String shipperId;

    private String shipperName;

    private String remarks;

    private String isConsolidatedShipment;

    private String isSplitBillOfLading;

    private String airportOriginCode;

    private String isPendingShipment;

    private String bwhInvestor;

    private String kind;

    private String goodsType;

    private String fclLcl;

    private String containerNo;

    private String containerType;

    private String containerSize;

    private String markId;

    private String markType;

    private String sealNo;

    private String vehicleModel;

    private String vehicleType;

    private String chasisNo;

    private String engineNo;

    private String yearOfManufacture;

    private String vehicleBodyColor;

    private String vehicleBrand;

    private String vehicleNationality;

    private String load;

    private String passenger;

    private String enginePower;

    private String numberOfCylinders;

    private String countryOfOrigin;

    private String paymentType;

    private String eventCode;

    private String eventText;

    private Date eventTimestamp;

    private Date statusTimestamp;

    private String consolidatedBillNo;

    private Long deletionIndicator = 0L;

    private String lineNo;

    private String noOfPieceHawb;

    private String consigneeCivilId;

    private String incoTerms;

    private String invoiceNumber;

    private String invoiceDate;

    private String invoiceType;

    private String invoiceSupplierName;

    private String hsCode;

    private String goodsDescription;

    private String quantity;

    private String freightCurrency;

    private String freightCharges;

    private String consignmentCurrency;

    private String consignmentValue;

    private String actualCurrency;

    private String totalDuty;

    private String specialApprovalValue;

    private String declaredValue;

    private String currency;

    private String billOfLadingFor;

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

    private String createdBy;

    private Date createdOn ;

    private String updatedBy;

    private Date updatedOn;


}
