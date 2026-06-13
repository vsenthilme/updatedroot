package com.courier.overc360.api.midmile.primary.model.pickup;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.Set;

@Data
public class PickupEntity  {

    private Long pickupEntityId;
    private String consignmentBagId;
    private String pickupId;
    private String languageId;
    private String languageDescription;
    private String companyId;
    private String companyName;
    private String partnerType;
    private String partnerId;
    private String pieceId;
    private String pieceCount;
    private String partnerName;
    private String statusId;
    private String statusDescription;
    private Date statusTimeStamp = new Date();
    private String paymentType;
    private Double totalShipmentWeight;
    private String productId;
    private String productName;
    private String subProductId;
    private String subProductName;
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
    private Date customerPickupDate;
    private String pickupServiceTime;
    private String pickupTimeSlotStart;
    private String pickupTimeSlotEnd;
    private String courierType;
    private String courierId;
    private String assignedHubCode;
    private String vehicleRegNumber;
    private String routeId;
    private String pickupAttemptCount;
    private String pickupFailedReason;
    private String invoiceAmount;
    private String invoiceUrl;
    private String productCode;
    private String paymentLink;
    private String sequenceNo;
    private String actualSequenceNo;
    private String isCustomsDeclarable;
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
    private PickupDestinationDetails destinationDetails ;
    private PickupDetails pickupDetails;
    private Set<RiderAssignmentImageReference> imageReferenceList;


}
