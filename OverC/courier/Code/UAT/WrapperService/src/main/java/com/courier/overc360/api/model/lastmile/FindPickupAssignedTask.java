package com.courier.overc360.api.model.lastmile;


import com.courier.overc360.api.model.transaction.AddImageReference;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class FindPickupAssignedTask {

    private Long pickupEntityId;

    private String houseAirwayBill;

    private String customerName;

    private String pickupAddress;

    private String phone;

    private String pickupTimeSlotStart;

    private String pieceId;

    private String pieceCount;

    private String paymentType;

    private Double codAmount;

    private String pickupFailedReason;

    private String pickupId;

    private String statusId;

    private String statusDescription;

    private String remarks;

    private String consignmentCreation;

    private String createdBy;

    private Date createdOn;

    private Double latitude;

    private Double longitude;
    private String destinationAddress;

    private Set<AddImageReference> imageReferenceList;





}
