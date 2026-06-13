package com.courier.overc360.api.midmile.primary.model.ccr;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AddCcr {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

    @NotBlank(message = "Partner Master Airway Bill is mandatory")
    private String partnerHouseAirwayBill;

    @NotBlank(message = "Partner House Airway Bill is mandatory")
    private String partnerMasterAirwayBill;

    private String masterAirwayBill;
    private String houseAirwayBill;

    @NotBlank(message = "ConsoleId is mandatory")
    private String consoleId;

    private String ccrId;

    private String consignmentCurrency;

    private Double consignmentValue;

    private Double exchangeRate;

    private Double iata;

    private Double customsInsurance;

    private Double duty;

    private Double consignmentValueLocal;

    private Double addIata;

    private Double addInsurance;

    private Double customsValue;

    private Double calculatedTotalDuty;

//    private String statusText;

    private String dduCharge;

    private String customsCcrNo;

    private Double specialApprovalCharge;

    private String pieceId;

    private String companyName;

    private String languageDescription;

    private String partnerType;

    private String partnerName;

//    private String statusId;

    private String primaryDo;

    private String secondaryDo;

    private String paymentType;

//    private String eventCode;

//    private String eventText;

//    private Date eventTimestamp;

//    private Date statusTimestamp;

    private String noOfPackageMawb;

    private String bondedId;

    private String description;

    private Double netWeight;

    private String manifestedQuantity;

    private String landedQuantity;

    private String totalQuantity;

    private Double volume;

    private String finalDestination;

    private String notifyParty;

    private String productId;

    private String productName;

    private String subProductId;

    private String subProductName;

    private String serviceTypeId;

    private String serviceTypeName;

    private String shipperId;

    private String shipperName;

    private Double manifestedGrossWeight;

    private Double grossWeight;

    private Double tareWeight;

    private String remarks;

    private String isConsolidatedShipment;

    private String isSplitBillOfLading;

    private String isPendingShipment;

    private String goodsType;

    private String countryOfOrigin;

    private String manufacturer;

    private String packageType;

    private String noOfPieceHawb;

    private String airportOriginCode;

    private String actualCurrency;

    private Double totalDuty;

    private String specialApprovalValue;

    private String customsKd;

    private String consigneeName;

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

    private  String freightCharges;

    private String declaredValue;

    private String currency;

    private String isExempted;

    private String exemptionFor;

    private String exemptionBeneficiary;

    private String exemptionReference;

    private String flightNo;

    private String flightArrivalTime;

    private String consignmentLocalId;

    private Long deletionIndicator = 0L;

    private String consoleName;

    private String consoleGroupName;

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

}
