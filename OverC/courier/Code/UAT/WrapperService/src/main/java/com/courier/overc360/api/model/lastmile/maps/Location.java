package com.courier.overc360.api.model.lastmile.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private Double latitude;
    private Double longitude;

    private Long pickupEntityId;

    private String consignmentBagId;

    private String pickupId;

    private String deliveryId;

    private String languageId;

    private String companyId;

    private String partnerId;

    private String statusId;

    private String partnerType;

    private String partnerName;

    private String statusDescription;

    private Date statusTimeStamp = new Date();

    private String paymentType;

    private Double totalShipmentWeight;

    private String productId;

    private String productName;

    private String serviceTypeId;

    private String serviceTypeText;

    private String houseAirwayBill;

    private String remarks;

    private String packageType;

    private String customerCode;

    private String customerReferenceNumber;

    private String consignmentType;

    private String movementType;

    private String loadType;

    private String description;

    private Double codAmount;

    private String codFavorOf;

    private String codCollectionMode;

    private String priority;

    private String pickupType;

    private String pickupOtp;

    private String rtoOtp;

    private String currency;

    private String etaDateTime;

    private String consignmentCreation;

    private Date customerPickupDate = new Date();

    private String pickupServiceTime;

    private String pickupTimeSlotStart;

    private String pickupTimeSlotEnd;

    private String courierType;

    private String courierId;

    private String assignedHubCode;

    private String vehicleRegNumber;

    private String routeId;

    private Long pickupAttemptCount;

    private String pickupFailedReason;

    private String invoiceAmount;

    private String invoiceUrl;

    private String productCode;

    private String paymentLink;

    private String sequenceNo;

    private Long actualSequenceNo;

    private String isCustomsDeclarable;

    private String pieceId;

    private String pieceCount;

    private String reverseReason;

    private Date pickupDateTime;

    private Date pickupAssignedDateTime;

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

    private Date reschedule1;

    private String rescheduleReason1;

    private Date reschedule2;

    private String rescheduleReason2;

    private Date reschedule3;

    private String rescheduleReason3;

    private String subProductId;

    private String subProductName;

    private Long deletionIndicator = 0L;

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();
}
