package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class ReadMessages {
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
