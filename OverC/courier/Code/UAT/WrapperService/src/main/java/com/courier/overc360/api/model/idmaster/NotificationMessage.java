package com.courier.overc360.api.model.idmaster;


import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class NotificationMessage {

    private Long notificationId;

    private String consoleId;

    private String companyId;

    private String languageId;

    private String houseAirwayBill;

    private String title;

    private String message;

    private Boolean menu = false;

    private Boolean tab = false;

    private String orderType;

    private Long documentId = 0L;

    private Long deletionIndicator;

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();
}