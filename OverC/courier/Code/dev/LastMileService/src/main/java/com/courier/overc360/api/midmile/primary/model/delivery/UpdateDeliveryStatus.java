package com.courier.overc360.api.midmile.primary.model.delivery;

import com.courier.overc360.api.midmile.primary.model.pickup.AddRiderImageReference;
import com.courier.overc360.api.midmile.primary.model.pickup.RiderAssignmentImageReference;
import com.courier.overc360.api.midmile.replica.model.delivery.PaymentCollected;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UpdateDeliveryStatus {

    private Long deliveryEntityId;
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
    private String hubCode;
    private String hubName;
    private String receiverPhone;
    private String etaDateTime;
    private String paymentCollected;
    private List<PaymentCollected> paymentCollectedList;
    private String signatureRef;
    private String ndrId;
    private String paymentMode;
    private Double invoiceAmount;
    private String invoiceName;
    private Double totalAmount;
    private String chequeNo;
    private Date deliveryTimeSlotStart;
    private Date deliveryTimeSlotEnd;
    private Set<AddRiderImageReference> imageReferenceList;
}
