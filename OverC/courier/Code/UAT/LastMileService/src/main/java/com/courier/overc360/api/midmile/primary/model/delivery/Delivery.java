package com.courier.overc360.api.midmile.primary.model.delivery;

import com.courier.overc360.api.midmile.primary.model.pickup.RiderAssignmentImageReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbldelivery")
public class Delivery  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ENTITY_ID")
    private Long deliveryEntityId;

    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "PIECE_ID", columnDefinition = "nvarchar(50)")
    private String pieceId;

    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(50)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

    @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
    private String partnerName;

    @Column(name = "CONSIGNMENT_BAG_ID", columnDefinition = "nvarchar(50)")
    private String consignmentBagId;

    @Column(name = "PARTNER_HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerHouseAirwayBill;

    @Column(name = "INCO_TERMS", columnDefinition = "nvarchar(50)")
    private String incoTerms;

    @Column(name = "DELIVERY_ID", columnDefinition = "nvarchar(50)")
    private String deliveryId;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
    private String statusDescription;

    @Column(name = "STATUS_TIMESTAMP")
    private Date statusTimestamp;

    @Column(name = "PAYMENT_TYPE", columnDefinition = "nvarchar(50)")
    private String paymentType;

    @Column(name = "PAYMENT_MODE", columnDefinition = "nvarchar(50)")
    private String paymentMode;

    @Column(name = "TOTAL_SHIPMENT_WEIGHT",columnDefinition = "nvarchar(50)")
    private String totalShipmentWeight;

    @Column(name = "PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String productId;

    @Column(name = "PRODUCT_NAME" , columnDefinition = "nvarchar(50)")
    private String productName;

    @Column(name = "SERVICE_TYPE_ID", columnDefinition = "nvarchar(50)")
    private String serviceTypeId;

    @Column(name = "SERVICE_TYPE_TEXT" , columnDefinition = "nvarchar(50)")
    private String serviceTypeName;

    @Column(name = "REMARK", columnDefinition = "nvarchar(500)")
    private String remark;

    @Column(name = "PIECE_COUNT", columnDefinition = "nvarchar(50)")
    private String pieceCount;

    @Column(name = "PACKAGE_TYPE" , columnDefinition = "nvarchar(50)")
    private String packageType;

    @Column(name = "CUSTOMER_CODE", columnDefinition = "nvarchar(50)")
    private String customerCode;

    @Column(name = "CUSTOMER_REFERENCE_NUMBER", columnDefinition = "nvarchar(50)")
    private String customerReferenceNumber;

    @Column(name = "CONSIGNMENT_TYP", columnDefinition = "nvarchar(50)")
    private String consignmentType;

    @Column(name = "MOVEMENT_TYPE", columnDefinition = "nvarchar(50)")
    private String movementType;

    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Column(name = "HUB_NAME", columnDefinition = "nvarchar(50)")
    private String hubName;

    @Column(name = "LOAD_TYPE", columnDefinition = "nvarchar(50)")
    private String loadType;

    @Column(name = "DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "COD_AMOUNT")
    private Double codAmount;

    @Column(name = "COD_FAVOR_OF", columnDefinition = "nvarchar(50)")
    private String codFavorOf;

    @Column(name = "COD_COLLECTION_MODE", columnDefinition = "nvarchar(50)")
    private String codCollectionMode;

    @Column(name = "CURRENCY", columnDefinition = "nvarchar(50)")
    private String currency;

    @Column(name = "PRIORITY", columnDefinition = "nvarchar(50)")
    private String priority;

    @Column(name = "DELIVERY_DATE_TIME")
    private Date deliveryDateTime;

    @Column(name = "DELIVERY_TYPE", columnDefinition = "nvarchar(50)")
    private String deliveryType;

    @Column(name = "DELIVERY_OTP", columnDefinition = "nvarchar(50)")
    private String deliveryOtp;

    @Column(name = "RTO_OTP", columnDefinition = "nvarchar(50)")
    private String rtoOtp;

    @Column(name = "DELIVERY_SERVICE_TIME")
    private Date deliveryServiceTime;

    @Column(name = "CUSTOMER_DELIVERY_DATE")
    private Date customerDeliveryDate;

    @Column(name = "COURIER_TYPE", columnDefinition = "nvarchar(50)")
    private String courierType;

    @Column(name = "COURIER_ID", columnDefinition = "nvarchar(50)")
    private String courierId;

    @Column(name = "MANIFEST_NUMBER", columnDefinition = "nvarchar(50)")
    private String manifestNumber;

    @Column(name = "ASSIGNED_HUB_CODE", columnDefinition = "nvarchar(50)")
    private String assignedHubCode;

    @Column(name = "VEHICLE_REG_NUMBER", columnDefinition = "nvarchar(50)")
    private String vehicleRegNumber;

    @Column(name = "ROUTE_ID", columnDefinition = "nvarchar(50)")
    private String routeId;

    @Column(name = "RIDER_TYPE", columnDefinition = "nvarchar(50)")
    private String riderType;

    @Column(name = "DELIVERY_ATTEMPT_COUNT")
    private Long deliveryAttemptCount;

    @Column(name = "NDR_ID",columnDefinition = "nvarchar(50)")
    private String ndrId;

    @Column(name = "NDR_TEXT",columnDefinition = "nvarchar(50)")
    private String ndrText;

    @Column(name = "DELIVERY_TIME_SLOT_START")
    private Date deliveryTimeSlotStart;

    @Column(name = "DELIVERY_TIME_SLOT_END")
    private Date deliveryTimeSlotEnd;

    @Column(name = "RE_SCHEDULED_SLOT_START")
    private Date reScheduledSlotStart;

    @Column(name = "RE_SCHEDULED_SLOT_END")
    private Date reScheduledSlotEnd;

    @Column(name = "INVOICE_AMOUNT")
    private Double invoiceAmount;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @Column(name = "INVOICE_URL", columnDefinition = "nvarchar(500)")
    private String invoiceUrl;

    @Column(name = "INVOICE_NAME", columnDefinition = "nvarchar(500)")
    private String invoiceName;

    @Column(name = "PAYMENT_LINK", columnDefinition = "nvarchar(50)")
    private String paymentLink;

    @Column(name = "PRODUCT_CODE", columnDefinition = "nvarchar(50)")
    private String productCode;

    @Column(name = "SEQUENCE_NO", columnDefinition = "nvarchar(50)")
    private String sequenceNo;

    @Column(name = "ACTUAL_SEQUENCE_NO", columnDefinition = "nvarchar(50)")
    private String actualSequenceNo;

    @Column(name = "IS_CUSTOMS_DECLARABLE", columnDefinition = "nvarchar(50)")
    private String isCustomsDeclarable;

    @Column(name = "SOURCE_HUB_CODE", columnDefinition = "nvarchar(50)")
    private String sourceHubCode;

    @Column(name = "DESTINATION_HUB_CODE", columnDefinition = "nvarchar(50)")
    private String destinationHubCode;

    @Column(name = "DESTINATION_ADDRESS", columnDefinition = "nvarchar(500)")
    private String destinationAddress;

    @Column(name = "DESTINATION_ADDRESS_1", columnDefinition = "nvarchar(500)")
    private String destinationAddress1;

    @Column(name = "DELIVERY_ADDRESS", columnDefinition = "nvarchar(500)")
    private String address;

    @Column(name = "DELIVERY_DESTINATIONS", columnDefinition = "nvarchar(500)")
    private String destinations;

    @Column(name = "EMAIL_ID",columnDefinition = "nvarchar(50)")
    private String emailId;

    @Column(name = "COMPANY_NAME", columnDefinition = "nvarchar(50)")
    private String destCompanyName;

    @Column(name = "NAME", columnDefinition = "nvarchar(50)")
    private String name;

    @Column(name = "PHONE", columnDefinition = "nvarchar(50)")
    private String phone;

    @Column(name = "ALTERNATE_PHONE", columnDefinition = "nvarchar(50)")
    private String alternatePhone;

    @Column(name = "ADDRESS_LINE_1", columnDefinition = "nvarchar(150)")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2", columnDefinition = "nvarchar(150)")
    private String addressLine2;

    @Column(name = "PINCODE", columnDefinition = "nvarchar(50)")
    private String pinCode;

    @Column(name = "DISTRICT", columnDefinition = "nvarchar(50)")
    private String district;

    @Column(name = "CITY", columnDefinition = "nvarchar(50)")
    private String city;

    @Column(name = "STATE", columnDefinition = "nvarchar(50)")
    private String state;

    @Column(name = "COUNTRY", columnDefinition = "nvarchar(50)")
    private String country;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "IMAGE_REF_LIST", columnDefinition = "nvarchar(50)")
    private String imageRefList;

    @Column(name = "RECEIVER_NAME", columnDefinition = "nvarchar(50)")
    private String receiverName;

    @Column(name = "RECEIVER_RELATIONSHIP", columnDefinition = "nvarchar(50)")
    private String receiverRelationShip;

    @Column(name = "RECEIVER_PHONE", columnDefinition = "nvarchar(50)")
    private String receiverPhone;

    @Column(name = "PAYMENT_COLLECTED", columnDefinition = "nvarchar(50)")
    private String paymentCollected;

    @Column(name = "SIGNATURE_REF", columnDefinition = "nvarchar(50)")
    private String signatureRef;

    @Column(name = "CHEQUE_NO", columnDefinition = "nvarchar(50)")
    private String chequeNo;

    @Column(name = "ETA_DATE_TIME", columnDefinition = "nvarchar(50)")
    private String etaDateTime;

    @Column(name = "CASH_COLLECTED")
    private Double cashCollected = 0.0;

    @Column(name = "ONLINE_COLLECTED")
    private Double onlineCollected = 0.0;

    @Column(name = "CHEQUE_COLLECTED")
    private Double chequeCollected = 0.0;

    @Column(name = "COLLECTED_AMOUNT")
    private Double collectedAmount = 0.0;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "DELIVERY_ENTITY_ID", referencedColumnName = "DELIVERY_ENTITY_ID")
    private Set<RiderAssignmentImageReference> imageReferenceList = new HashSet<>();






}
