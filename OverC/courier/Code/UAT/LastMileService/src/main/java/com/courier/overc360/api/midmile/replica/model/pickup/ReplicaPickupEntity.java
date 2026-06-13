package com.courier.overc360.api.midmile.replica.model.pickup;

import com.courier.overc360.api.midmile.primary.model.pickup.RiderAssignmentImageReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblpickup_entity")
public class ReplicaPickupEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PICKUP_ENTITY_ID")
    private Long pickupEntityId;

    @Column(name = "CONSIGNMENT_BAG_ID")
    private Long consignmentBagId;

    @Column(name = "PICKUP_ID")
    private String pickupId;

    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Column(name = "LANG_TXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
    private String partnerName;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(100)")
    private String statusDescription;

    @Column(name = "STATUS_TIMESTAMP")
    private Date statusTimeStamp = new Date();

    @Column(name = "PAYMENT_TYPE", columnDefinition = "nvarchar(50)")
    private String paymentType;

    @Column(name = "TOTAL_SHIPMENT_WEIGHT")
    private Double totalShipmentWeight;

    @Column(name = "PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String productId;

    @Column(name = "PRODUCT_NAME", columnDefinition = "nvarchar(50)")
    private String productName;

    @Column(name = "SERVICE_TYPE_ID", columnDefinition = "nvarchar(50)")
    private String serviceTypeId;

    @Column(name = "SERVICE_TYPE_TEXT", columnDefinition = "nvarchar(50)")
    private String serviceTypeText;

    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    @Column(name = "REMARKS", columnDefinition = "nvarchar(2000)")
    private String remarks;

    @Column(name = "PACKAGE_TYPE", columnDefinition = "nvarchar(50)")
    private String packageType;

    @Column(name = "CUSTOMER_CODE", columnDefinition = "nvarchar(50)")
    private String customerCode;

    @Column(name = "CUSTOMER_REFERENCE_NUMBER", columnDefinition = "nvarchar(50)")
    private String customerReferenceNumber;

    @Column(name = "CONSIGNMENT_TYPE", columnDefinition = "nvarchar(50)")
    private String consignmentType;

    @Column(name = "MOVEMENT_TYPE", columnDefinition = "nvarchar(50)")
    private String movementType;

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

    @Column(name = "PRIORITY", columnDefinition = "nvarchar(50)")
    private String priority;

    @Column(name = "PICKUP_TYPE", columnDefinition = "nvarchar(50)")
    private String pickupType;

    @Column(name = "PICKUP_OTP", columnDefinition = "nvarchar(50)")
    private String pickupOtp;

    @Column(name = "RTO_OTP", columnDefinition = "nvarchar(50)")
    private String rtoOtp;

    @Column(name = "ETA_DATE_TIME", columnDefinition = "nvarchar(50)")
    private String etaDateTime;

    @Column(name = "CONSIGNMENT_CREATION", columnDefinition = "nvarchar(50)")
    private String consignmentCreation;

    @Column(name = "CUSTOMER_PICKUP_DATE")
    private Date customerPickupDate = new Date();

    @Column(name = "PICKUP_SERVICE_TIME", columnDefinition = "nvarchar(50)")
    private String pickupServiceTime;

    @Column(name = "PICKUP_TIME_SLOT_START", columnDefinition = "nvarchar(50)")
    private String pickupTimeSlotStart;

    @Column(name = "PICKUP_TIME_SLOT_END", columnDefinition = "nvarchar(50)")
    private String pickupTimeSlotEnd;

    @Column(name = "COURIER_TYPE", columnDefinition = "nvarchar(50)")
    private String courierType;

    @Column(name = "COURIER_ID", columnDefinition = "nvarchar(50)")
    private String courierId;

    @Column(name = "ASSIGNED_HUB_CODE", columnDefinition = "nvarchar(50)")
    private String assignedHubCode;

    @Column(name = "VEHICLE_REG_NO", columnDefinition = "nvarchar(50)")
    private String vehicleRegNumber;

    @Column(name = "ROUTE_ID", columnDefinition = "nvarchar(50)")
    private String routeId;

    @Column(name = "PICKUP_ATTEMPT_COUNT")
    private Long pickupAttemptCount;

    @Column(name = "PICKUP_FAILED_REASON", columnDefinition = "nvarchar(50)")
    private String pickupFailedReason;

    @Column(name = "INVOICE_AMOUNT", columnDefinition = "nvarchar(50)")
    private String invoiceAmount;

    @Column(name = "INVOICE_URL", columnDefinition = "nvarchar(500)")
    private String invoiceUrl;

    @Column(name = "PRODUCT_CODE", columnDefinition = "nvarchar(50)")
    private String productCode;

    @Column(name = "PAYMENT_LINK", columnDefinition = "nvarchar(50)")
    private String paymentLink;

    @Column(name = "SEQUENCE_NO", columnDefinition = "nvarchar(50)")
    private String sequenceNo;

    @Column(name = "ACTUAL_SEQUENCE_NO")
    private Long actualSequenceNo;

    @Column(name = "CURRENCY", columnDefinition = "nvarchar(50)")
    private String currency;

    @Column(name = "IS_CUSTOMS_DECLARABLE", columnDefinition = "nvarchar(50)")
    private String isCustomsDeclarable;

    @Column(name = "PIECE_ID", columnDefinition = "nvarchar(500)")
    private String pieceId;

    @Column(name = "PIECE_COUNT", columnDefinition = "nvarchar(50)")
    private String pieceCount;

    @Column(name = "REVERSE_REASON", columnDefinition = "nvarchar(50)")
    private String reverseReason;

    @Column(name = "ADDRESS", columnDefinition = "nvarchar(200)")
    private String address;

    @Column(name = "DESTINATIONS", columnDefinition = "nvarchar(200)")
    private String destinations;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "PICKUP_DATE_TIME")
    private Date pickupDateTime;

    @Column(name = "PICKUP_ASSIGNED_DATE_TIME")
    private Date pickupAssignedDateTime;

    @Column(name = "RATE")
    private Double rate;

    @Column(name = "CHARGEABLE_WEIGHT")
    private Double chargeableWeight;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(200)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(200)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(200)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(200)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(200)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(200)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(200)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(200)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(200)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(200)")
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

    @Column(name = "ASSIGNED_BY", columnDefinition = "nvarchar(50)")
    private String assignedBy;

    @Column(name = "RESCHEDULE")
    private Date reschedule;

    @Column(name = "RESCHEDULE_REASON", columnDefinition = "nvarchar(50)")
    private String rescheduleReason;

    @Column(name = "NPR_ID", columnDefinition = "nvarchar(50)")
    private String nprId;

    @Column(name = "NPR_TEXT", columnDefinition = "nvarchar(50)")
    private String nprText;

    @Column(name = "SUB_PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String subProductId;

    @Column(name = "SUB_PRODUCT_NAME", columnDefinition = "nvarchar(50)")
    private String subProductName;

    @Column(name = "LINK_REF_NO", columnDefinition = "nvarchar(1000)")
    private String referenceNumber;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PICKUP_ENTITY_ID", referencedColumnName = "PICKUP_ENTITY_ID")
    private ReplicaPickupDestinationDetails destinationDetails = new ReplicaPickupDestinationDetails();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PICKUP_ENTITY_ID", referencedColumnName = "PICKUP_ENTITY_ID")
    private ReplicaPickupDetails pickupDetails = new ReplicaPickupDetails();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PICKUP_ENTITY_ID", referencedColumnName = "PICKUP_ENTITY_ID")
    private Set<ReplicaRiderAssignmentImageReference> imageReferenceList = new HashSet<>();

}
