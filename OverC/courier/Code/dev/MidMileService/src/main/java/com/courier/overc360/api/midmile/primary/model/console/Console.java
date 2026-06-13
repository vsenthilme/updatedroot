package com.courier.overc360.api.midmile.primary.model.console;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
/*
 * `LANG_ID`, `C_ID`, `PARTNER_ID`, `MASTER_AIRWAY_BILL`, `HOUSE_AIRWAY_BILL`,`CONSOLE_ID`
 */
@Table(name = "tblconsole",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_console",
                        columnNames = {"LANG_ID", "C_ID", "PARTNER_ID", "PARTNER_MASTER_AIRWAY_BILL", "PARTNER_HOUSE_AIRWAY_BILL", "PIECE_ID"}
                )
        }
)
@IdClass(ConsoleCompositeKey.class)
public class Console {

//    @Id
    @Column(name = "CONSOLE_ID")
    private String consoleId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Id
    @Column(name = "PARTNER_HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerHouseAirwayBill;

    @Id
    @Column(name = "PARTNER_MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerMasterAirwayBill;

    @Id
    @Column(name = "PIECE_ID", columnDefinition = "nvarchar(50)")
    private String pieceId;

    //    @Id
    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    //    @Id
    @Column(name = "MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String masterAirwayBill;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "CCR_ID", columnDefinition = "nvarchar(50)")
    private String ccrId;

//    @Id
//    @Column(name = "PIECE_ITEM_ID", columnDefinition = "nvarchar(50)")
//    private String pieceItemId;

    @Column(name = "CONSIGNMENT_CURRENCY", columnDefinition = "nvarchar(50)")
    private String consignmentCurrency;

    @Column(name = "CONSIGNMENT_VALUE")
    private Double consignmentValue;

    @Column(name = "CONSOLE_NAME", columnDefinition = "nvarchar(50)")
    private String consoleName;

    @Column(name = "CONSOLE_GROUP_NAME", columnDefinition = "nvarchar(50)")
    private String consoleGroupName;

    @Column(name = "EXCHANGE_RATE")
    private Double exchangeRate;

    @Column(name = "IATA")
    private Double iata;

    @Column(name = "CUSTOMS_INSURANCE")
    private Double customsInsurance;

    @Column(name = "DUTY")
    private Double duty;

    @Column(name = "CONSIGNMENT_VALUE_LOCAL")
    private Double consignmentValueLocal;

    @Column(name = "ADD_IATA")
    private Double addIata;

    @Column(name = "ADD_INSURANCE")
    private Double addInsurance;

    @Column(name = "CUSTOMS_VALUE")
    private Double customsValue;

    @Column(name = "CALCULATED_TOTAL_DUTY")
    private Double calculatedTotalDuty;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(100)")
    private String partnerName;

//    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
//    private String statusId;
//
//    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
//    private String statusText;

    @Column(name = "PRIMARY_DO", columnDefinition = "nvarchar(50)")
    private String primaryDo;

    @Column(name = "SECONDARY_DO", columnDefinition = "nvarchar(50)")
    private String secondaryDo;

    @Column(name = "NO_OF_PACKAGE_MAWB", columnDefinition = "nvarchar(50)")
    private String noOfPackageMawb;

    @Column(name = "NO_OF_PIECE_HAWB", columnDefinition = "nvarchar(50)")
    private String noOfPieceHawb;

    @Column(name = "BONDED_ID", columnDefinition = "nvarchar(50)")
    private String bondedId;

    @Column(name = "EXPECTED_DUTY", columnDefinition = "nvarchar(50)")
    private String expectedDuty;

    @Column(name = "CUSTOMS_CURRENCY", columnDefinition = "nvarchar(50)")
    private String customsCurrency;

    @Column(name = "DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "NET_WEIGHT")
    private Double netWeight;

    @Column(name = "MANIFESTED_GROSS_WEIGHT")
    private Double manifestedGrossWeight;

    @Column(name = "GROSS_WEIGHT")
    private Double grossWeight;

    @Column(name = "TARE_WEIGHT")
    private Double tareWeight;

    @Column(name = "MANIFESTED_QUANTITY", columnDefinition = "nvarchar(50)")
    private String manifestedQuantity;

    @Column(name = "LANDED_QUANTITY", columnDefinition = "nvarchar(50)")
    private String landedQuantity;

    @Column(name = "TOTAL_QUANTITY", columnDefinition = "nvarchar(50)")
    private String totalQuantity;

    @Column(name = "VOLUME")
    private Double volume;

    @Column(name = "FINAL_DESTINATION", columnDefinition = "nvarchar(50)")
    private String finalDestination;

    @Column(name = "NOTIFY_PARTY", columnDefinition = "nvarchar(50)")
    private String notifyParty;

    @Column(name = "NO_OF_PIECES", columnDefinition = "nvarchar(50)")
    private String noOfPieces;

    @Column(name = "PAYMENT_TYPE", columnDefinition = "nvarchar(50)")
    private String paymentType;

    @Column(name = "IS_EXEMPTED", columnDefinition = "nvarchar(50)")
    private String isExempted;

    @Column(name = "EXEMPTION_FOR", columnDefinition = "nvarchar(50)")
    private String exemptionFor;

    @Column(name = "EXEMPTION_BENEFICIARY", columnDefinition = "nvarchar(50)")
    private String exemptionBeneficiary;

    @Column(name = "EXEMPTION_REFERENCE", columnDefinition = "nvarchar(50)")
    private String exemptionReference;

//    @Column(name = "EVENT_CODE", columnDefinition = "nvarchar(50)")
//    private String eventCode;
//
//    @Column(name = "EVENT_TEXT", columnDefinition = "nvarchar(50)")
//    private String eventText;
//
//    @Column(name = "EVENT_TIMESTAMP")
//    private Date eventTimestamp;
//
//    @Column(name = "STATUS_TIMESTAMP")
//    private Date statusTimestamp;

    @Column(name = "SHIPMENT_BAG_ID")
    private Long shipmentBagId;

    @Column(name = "DUTY_PERCENTAGE", columnDefinition = "nvarchar(50)")
    private String dutyPercentage;

    @Column(name = "IATA_CHARGE", columnDefinition = "nvarchar(50)")
    private String iataCharge;

    @Column(name = "DDU_CHARGE", columnDefinition = "nvarchar(50)")
    private String dduCharge;

    @Column(name = "SPECIAL_APPROVAL_CHARGE")
    private Double specialApprovalCharge;

    @Column(name = "CONSIGNEE_NAME", columnDefinition = "nvarchar(500)")
    private String consigneeName;

    @Column(name = "SHIPPER_NAME", columnDefinition = "nvarchar(50)")
    private String shipperName;

    @Column(name = "REMARKS", columnDefinition = "nvarchar(50)")
    private String remarks;

    @Column(name = "IS_CONSOLIDATED_SHIPMENT", columnDefinition = "nvarchar(50)")
    private String isConsolidatedShipment;

    @Column(name = "IS_SPLIT_BILL_OF_LADING", columnDefinition = "nvarchar(50)")
    private String isSplitBillOfLading;

    @Column(name = "IS_PENDING_SHIPMENT", columnDefinition = "nvarchar(50)")
    private String isPendingShipment;

    @Column(name = "GOODS_TYPE", columnDefinition = "nvarchar(50)")
    private String goodsType;

    @Column(name = "COUNTRY_OF_ORIGIN", columnDefinition = "nvarchar(50)")
    private String countryOfOrigin;

    @Column(name = "ACTUAL_CURRENCY", columnDefinition = "nvarchar(50)")
    private String actualCurrency;

    @Column(name = "ACTUAL_VALUE", columnDefinition = "nvarchar(50)")
    private String actualValue;

    @Column(name = "SPECIAL_APPROVAL_VALUE", columnDefinition = "nvarchar(50)")
    private String specialApprovalValue;

    @Column(name = "AIRPORT_ORIGIN_CODE", columnDefinition = "nvarchar(50)")
    private String airportOriginCode;

    @Column(name = "CUSTOMS_KD", columnDefinition = "nvarchar(50)")
    private String customsKd;

    @Column(name = "TOTAL_DUTY")
    private Double totalDuty;

    @Column(name = "PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String productId;

    @Column(name = "PRODUCT_NAME", columnDefinition = "nvarchar(50)")
    private String productName;

    @Column(name = "SUB_PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String subProductId;

    @Column(name = "SUB_PRODUCT_NAME", columnDefinition = "nvarchar(50)")
    private String subProductName;

    @Column(name = "SERVICE_TYPE_ID", columnDefinition = "nvarchar(50)")
    private String serviceTypeId;

    @Column(name = "SERVICE_TYPE_TEXT", columnDefinition = "nvarchar(50)")
    private String serviceTypeName;

    @Column(name = "SHIPPER_ID", columnDefinition = "nvarchar(50)")
    private String shipperId;

    @Column(name = "CONSIGNEE_CIVIL_ID", columnDefinition = "nvarchar(50)")
    private String consigneeCivilId;

    @Column(name = "INCO_TERMS", columnDefinition = "nvarchar(50)")
    private String incoTerm;

    @Column(name = "INVOICE_NUMBER", columnDefinition = "nvarchar(50)")
    private String invoiceNumber;

    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "INVOICE_TYPE", columnDefinition = "nvarchar(50)")
    private String invoiceType;

    @Column(name = "INVOICE_SUPPLIER_NAME", columnDefinition = "nvarchar(50)")
    private String invoiceSupplierName;

    @Column(name = "HS_CODE", columnDefinition = "nvarchar(50)")
    private String hsCode;

    @Column(name = "GOODS_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String goodsDescription;

    @Column(name = "QUANTITY", columnDefinition = "nvarchar(50)")
    private String quantity;

    @Column(name = "FREIGHT_CURRENCY", columnDefinition = "nvarchar(50)")
    private String freightCurrency;

    @Column(name = "FREIGHT_CHARGES", columnDefinition = "nvarchar(50)")
    private String freightCharges;

    @Column(name = "DECLARED_VALUE", columnDefinition = "nvarchar(50)")
    private String declaredValue;

    @Column(name = "CURRENCY", columnDefinition = "nvarchar(50)")
    private String currency;

    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Column(name = "HUB_NAME", columnDefinition = "nvarchar(50)")
    private String hubName;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "CON_LOCAL_ID", columnDefinition = "nvarchar(50)")
    private String consignmentLocalId;

    @Column(name = "HAWB_TYP", columnDefinition = "nvarchar(50)")
    private String hawbType;

    @Column(name = "HAWB_TYP_ID", columnDefinition = "nvarchar(50)")
    private String hawbTypeId;

    @Column(name = "HAWB_TYP_TXT", columnDefinition = "nvarchar(100)")
    private String hawbTypeDescription;

    @Column(name = "HAWB_TIMESTAMP")
    private Date hawbTimeStamp = new Date();

    @Column(name = "PIECE_TYP", columnDefinition = "nvarchar(50)")
    private String pieceType;

    @Column(name = "PIECE_TYP_ID", columnDefinition = "nvarchar(50)")
    private String pieceTypeId;

    @Column(name = "PIECE_TYP_TXT", columnDefinition = "nvarchar(100)")
    private String pieceTypeDescription;

    @Column(name = "PIECE_TIMESTAMP")
    private Date pieceTimeStamp = new Date();

    @Column(name = "CUSTOMS_CCR_NO", columnDefinition = "nvarchar(500)")
    private String customsCcrNo;

    @Column(name = "FLIGHT_NAME", columnDefinition = "nvarchar(50)")
    private String flightName;

    @Column(name = "FLIGHT_NO", columnDefinition = "nvarchar(50)")
    private String flightNo;

    @Column(name = "ADD_DESTINATION_DETAILS", columnDefinition = "nvarchar(500)")
    private String addDestinationDetails;

    @Column(name = "ADD_ORIGIN_DETAILS", columnDefinition = "nvarchar(500)")
    private String addOriginDetails;

    @Column(name = "SUB_CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String subCustomerId;

    @Column(name = "SUB_CUSTOMER_NAME", columnDefinition = "nvarchar(50)")
    private String subCustomerName;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(500)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(500)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(500)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(500)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(500)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(500)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(500)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(500)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(500)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(500)")
    private String referenceField10;

    @Column(name = "REF_FIELD_11", columnDefinition = "nvarchar(500)")
    private String referenceField11;

    @Column(name = "REF_FIELD_12", columnDefinition = "nvarchar(500)")
    private String referenceField12;

    @Column(name = "REF_FIELD_13", columnDefinition = "nvarchar(500)")
    private String referenceField13;

    @Column(name = "REF_FIELD_14", columnDefinition = "nvarchar(500)")
    private String referenceField14;

    @Column(name = "REF_FIELD_15", columnDefinition = "nvarchar(500)")
    private String referenceField15;

    @Column(name = "REF_FIELD_16", columnDefinition = "nvarchar(500)")
    private String referenceField16;

    @Column(name = "REF_FIELD_17", columnDefinition = "nvarchar(500)")
    private String referenceField17;

    @Column(name = "REF_FIELD_18", columnDefinition = "nvarchar(500)")
    private String referenceField18;

    @Column(name = "REF_FIELD_19", columnDefinition = "nvarchar(500)")
    private String referenceField19;

    @Column(name = "REF_FIELD_20", columnDefinition = "nvarchar(500)")
    private String referenceField20;

    @Column(name = "LINE_NUMBER")
    private Long lineNumber;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

    @Column(name = "SCANNED_BY", columnDefinition = "nvarchar(50)")
    private String scannedBy;

    @Column(name = "SCANNED_ON")
    private Date scannedOn;

    // unconsolidation
    @Column(name = "UNCONSOLIDATED")
    private Long unconsolidatedFlag = 0L;

    // Notification
    @Column(name = "NOTI_STATUS")
    private Long notificationStatus = 0L;

    @Column(name = "ORIGIN_FLIGHT_COUNTRY", columnDefinition = "nvarchar(100)")
    private String originFlightCountry;


//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CONSOLE_HEADER_ID")
//    private List<ConsoleLine> consoleLines;

}
