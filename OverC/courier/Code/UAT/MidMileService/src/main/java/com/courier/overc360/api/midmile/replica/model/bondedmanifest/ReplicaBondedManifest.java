package com.courier.overc360.api.midmile.replica.model.bondedmanifest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
/*
 * `LANG_ID`, `C_ID`, BONDED_ID`, `MASTER_AIRWAY_BILL`, `HOUSE_AIRWAY_BILL`
 */
@Table(name = "tblbondedmanifest",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_bondedmanifest",
                        columnNames = {"LANG_ID", "C_ID", "BONDED_ID", "PARTNER_MASTER_AIRWAY_BILL", "PARTNER_HOUSE_AIRWAY_BILL"}
                )
        }
)
@IdClass(ReplicaBondedManifestCompositeKey.class)
public class ReplicaBondedManifest {

    @Id
    @Column(name = "BONDED_ID", columnDefinition = "nvarchar(50)")
    private String bondedId;

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
    @Column(name = "PARTNER_MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerMasterAirwayBill;

    @Id
    @Column(name = "PARTNER_HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerHouseAirwayBill;

    //    @Id
    @Column(name = "MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String masterAirwayBill;
    //
//    @Id
    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    @Column(name = "PIECE_ID", columnDefinition = "nvarchar(50)")
    private String pieceId;

    @Column(name = "PIECE_ITEM_ID", columnDefinition = "nvarchar(50)")
    private String pieceItemId;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "ESTIMATED_TIME_OF_ARRIVAL")
    private Date estimatedTimeOfArrival;

    @Column(name = "PARTNER_TYP", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(100)")
    private String partnerName;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
    private String statusText;

    @Column(name = "NO_OF_PACKAGES_MAWB", columnDefinition = "nvarchar(50)")
    private String noOfPackageMawb;

    @Column(name = "PRIMARY_DO", columnDefinition = "nvarchar(50)")
    private String primaryDo;

    @Column(name = "SECONDARY_DO", columnDefinition = "nvarchar(50)")
    private String secondaryDo;

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

    @Column(name = "MANIFESTED_QTY", columnDefinition = "nvarchar(50)")
    private String manifestedQuantity;

    @Column(name = "LANDED_QTY", columnDefinition = "nvarchar(50)")
    private String landedQuantity;

    @Column(name = "TOTAL_QTY", columnDefinition = "nvarchar(50)")
    private String totalQuantity;

    @Column(name = "VOLUME")
    private Double volume;

    @Column(name = "FINAL_DESTINATION", columnDefinition = "nvarchar(50)")
    private String finalDestination;

    @Column(name = "NOTIFY_PARTY", columnDefinition = "nvarchar(50)")
    private String notifyParty;

    @Column(name = "CONSIGNEE_NAME", columnDefinition = "nvarchar(50)")
    private String consigneeName;

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

    @Column(name = "SHIPPER_NAME", columnDefinition = "nvarchar(100)")
    private String shipperName;

    @Column(name = "CONSIGNEE_FREE_TEXT", columnDefinition = "nvarchar(100)")
    private String consigneeFreeText;

    @Column(name = "REMARKS", columnDefinition = "nvarchar(100)")
    private String remarks;

    @Column(name = "IS_CONSOLIDATED_SHIPMENT", columnDefinition = "nvarchar(50)")
    private String isConsolidatedShipment;

    @Column(name = "IS_SPLIT_BILL_OF_LADING", columnDefinition = "nvarchar(50)")
    private String isSplitBillOfLading;

    @Column(name = "IS_PENDING_SHIPMENT", columnDefinition = "nvarchar(50)")
    private String isPendingShipment;

    @Column(name = "BWH_INVESTOR", columnDefinition = "nvarchar(50)")
    private String bwhInvestor;

    @Column(name = "KIND", columnDefinition = "nvarchar(50)")
    private String kind;

    @Column(name = "GOODS_TYP", columnDefinition = "nvarchar(50)")
    private String goodsType;

    @Column(name = "FCL_LCL", columnDefinition = "nvarchar(500)")
    private String fclLcl;

    @Column(name = "CONTAINER_NO", columnDefinition = "nvarchar(50)")
    private String containerNo;

    @Column(name = "CONTAINER_TYP", columnDefinition = "nvarchar(50)")
    private String containerType;

    @Column(name = "CONTAINER_SIZE", columnDefinition = "nvarchar(50)")
    private String containerSize;

    @Column(name = "MARK_ID", columnDefinition = "nvarchar(50)")
    private String markId;

    @Column(name = "MARK_TYP", columnDefinition = "nvarchar(50)")
    private String markType;

    @Column(name = "SEAL_NO", columnDefinition = "nvarchar(50)")
    private String sealNo;

    @Column(name = "VEHICLE_MODEL", columnDefinition = "nvarchar(500)")
    private String vehicleModel;

    @Column(name = "VEHICLE_TYP", columnDefinition = "nvarchar(50)")
    private String vehicleType;

    @Column(name = "CHASIS_NO", columnDefinition = "nvarchar(50)")
    private String chasisNo;

    @Column(name = "ENGINE_NO", columnDefinition = "nvarchar(50)")
    private String engineNo;

    @Column(name = "YR_OF_MANUFACTURE", columnDefinition = "nvarchar(50)")
    private String yearOfManufacture;

    @Column(name = "VEHICLE_BODY_COLOR", columnDefinition = "nvarchar(50)")
    private String vehicleBodyColor;

    @Column(name = "VEHICLE_BRAND", columnDefinition = "nvarchar(50)")
    private String vehicleBrand;

    @Column(name = "VEHICLE_NATIONALITY", columnDefinition = "nvarchar(50)")
    private String vehicleNationality;

    @Column(name = "LOAD", columnDefinition = "nvarchar(50)")
    private String load;

    @Column(name = "PASSENGER", columnDefinition = "nvarchar(50)")
    private String passenger;

    @Column(name = "ENGINE_POWER", columnDefinition = "nvarchar(50)")
    private String enginePower;

    @Column(name = "NO_OF_CYLINDERS", columnDefinition = "nvarchar(50)")
    private String numberOfCylinders;

    @Column(name = "COUNTRY_OF_ORIGIN", columnDefinition = "nvarchar(50)")
    private String countryOfOrigin;

    @Column(name = "PAYMENT_TYPE", columnDefinition = "nvarchar(50)")
    private String paymentType;

    @Column(name = "EVENT_CODE", columnDefinition = "nvarchar(50)")
    private String eventCode;

    @Column(name = "EVENT_TEXT", columnDefinition = "nvarchar(50)")
    private String eventText;

    @Column(name = "EVENT_TIMESTAMP")
    private Date eventTimestamp;

    @Column(name = "STATUS_TIMESTAMP")
    private Date statusTimestamp;

    @Column(name = "CONSOLIDATED_BILL_NO", columnDefinition = "nvarchar(50)")
    private String consolidatedBillNo;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "LINE_NO", columnDefinition = "nvarchar(50)")
    private String lineNo;

    @Column(name = "NO_OF_PIECES_HAWB", columnDefinition = "nvarchar(50)")
    private String noOfPieceHawb;

    @Column(name = "CONSIGNEE_CIVIL_ID", columnDefinition = "nvarchar(50)")
    private String consigneeCivilId;

    @Column(name = "INCO_TERMS", columnDefinition = "nvarchar(50)")
    private String incoTerms;

    @Column(name = "INVOICE_NO", columnDefinition = "nvarchar(50)")
    private String invoiceNumber;

    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "INVOICE_TYP", columnDefinition = "nvarchar(50)")
    private String invoiceType;

    @Column(name = "INVOICE_SUPPLIER_NAME", columnDefinition = "nvarchar(50)")
    private String invoiceSupplierName;

    @Column(name = "HS_CODE", columnDefinition = "nvarchar(50)")
    private String hsCode;

    @Column(name = "GOODS_DESC", columnDefinition = "nvarchar(500)")
    private String goodsDescription;

    @Column(name = "QTY", columnDefinition = "nvarchar(50)")
    private String quantity;

    @Column(name = "FREIGHT_CURRENCY", columnDefinition = "nvarchar(50)")
    private String freightCurrency;

    @Column(name = "FREIGHT_CHARGES", columnDefinition = "nvarchar(50)")
    private String freightCharges;

    @Column(name = "CONSIGNMENT_CURRENCY", columnDefinition = "nvarchar(50)")
    private String consignmentCurrency;

    @Column(name = "CONSIGNMENT_VALUE")
    private Double consignmentValue;

    @Column(name = "ACTUAL_CURRENCY", columnDefinition = "nvarchar(50)")
    private String actualCurrency;

    @Column(name = "TOTAL_DUTY")
    private Double totalDuty;

    @Column(name = "SPECIAL_APPROVAL_VALUE", columnDefinition = "nvarchar(50)")
    private String specialApprovalValue;

    @Column(name = "DECLARED_VALUE")
    private Double declaredValue;

    @Column(name = "CURRENCY", columnDefinition = "nvarchar(50)")
    private String currency;

    @Column(name = "BILL_OF_LADING_FOR", columnDefinition = "nvarchar(50)")
    private String billOfLadingFor;

    @Column(name = "AIRPORT_ORIGIN_CODE", columnDefinition = "nvarchar(50)")
    private String airportOriginCode;

    @Column(name = "BAYAN_HV", columnDefinition = "nvarchar(50)")
    private String bayanHV;

    @Column(name = "CON_LOCAL_ID", columnDefinition = "nvarchar(50)")
    private String consignmentLocalId;

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

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

}
