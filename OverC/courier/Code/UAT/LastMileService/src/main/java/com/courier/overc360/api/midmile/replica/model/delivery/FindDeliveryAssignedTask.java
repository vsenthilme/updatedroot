package com.courier.overc360.api.midmile.replica.model.delivery;


import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class FindDeliveryAssignedTask {

    private String houseAirwayBill;
    private String customerName;
    private String addressLine1;
    private String phone;
    private Date deliveryTimeSlotStart;
    private String pieceId;
    private String pieceCount;
    private String incoTerms;
    private String paymentType;
    private Double codAmount;
    private String receiverName;
    private String receiverRelationShip;
    private String receiverPhone;
    private String paymentCollected;
    private String deliveryId;
    private String statusId;
    private String paymentMode;
    private Double invoiceAmount;
    private Double totalAmount;
    private String invoiceUrl;
    private String invoiceName;
    private String paymentLink;
    private Double latitude;
    private Double longitude;
    private String createdBy;
    private Date createdOn;
}
