package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UpdateDeliveryStatus {

    private String languageId;
    private String companyId;
    private String deliveryId;
    private String statusId;
    private String pieceId;
    private String houseAirwayBill;
    private String paymentType;
    private Double codAmount;
    private String pieceCount;
    private String receiverName;
    private String receiverRelationShip;
    private String receiverPhone;
    private String paymentCollected;
    private List<PaymentCollected> paymentCollectedList;
    private String signatureRef;
    private String ndrId;
    private String paymentMode;
    private Double invoiceAmount;
    private Double totalAmount;
    private String chequeNo;
    private Date deliveryTimeSlotStart;
    private Date deliveryTimeSlotEnd;
    private Set<RiderAssignmentImageReference> imageReferenceList;
}
