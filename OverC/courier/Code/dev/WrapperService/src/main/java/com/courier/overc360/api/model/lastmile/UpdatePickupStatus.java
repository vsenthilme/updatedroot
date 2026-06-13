package com.courier.overc360.api.model.lastmile;


import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UpdatePickupStatus {


    private Long pickupEntityId;
    private String languageId;
    private String companyId;
    private String pickupId;
    private String statusId;
    private String partnerId;
    private String houseAirwayBill;
    private String paymentType;
    private Double codAmount;
    private String pieceCount;
    private String pickupFailedReason;
    private String nprId;
    private Date reschedule;
    private String rescheduleReason;
    private String pickupTimeSlotStart;
    private String pickupTimeSlotEnd;
    private String referenceNumber;
    private Set<RiderAssignmentImageReference> imageReferenceList;

}
