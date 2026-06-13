package com.mnrclara.api.management.model.hhtnotification;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblnotificationmessage")
public class NotificationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(name = "CLASS_ID", columnDefinition = "nvarchar(100)")
    private String classId;

    @Column(name = "CLIENT_ID", columnDefinition = "nvarchar(100)")
    private String clientId;

    @Column(name = "CLIENT_USR_ID", columnDefinition = "nvarchar(100)")
    private String clientUserId;

    @Column(name = "TITLE",columnDefinition = "nvarchar(50)")
    private String title;

    @Column(name = "MSG",columnDefinition = "nvarchar(200)")
    private String message;

    @Column(name = "MENU")
    private Boolean menu = false;

    @Column(name = "TAB")
    private Boolean tab = false;

    @Column(name = "ORDER_TYPE", columnDefinition = "nvarchar(50)")
    private String orderType;

    @Column(name = "DOC_ID")
    private Long documentId = 0L;

    @Column(name = "QUOTATION_NO")
    private String quotationNo;

    @Column(name = "PAYMENT_PLAN_NO")
    private String paymentPlanNo;

    @Column(name = "RECEIPT_NO")
    private String receiptNo;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator;

    @Column(name = "CTD_BY",columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();
}

