package com.courier.overc360.api.midmile.primary.model.notificationmessage;


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
    @Column(name = "NOTIFICATION_ID")
    private Long notificationId;

    @Column(name = "CONSOLE_ID", columnDefinition = "nvarchar(50)")
    private String consoleId;

    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

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

