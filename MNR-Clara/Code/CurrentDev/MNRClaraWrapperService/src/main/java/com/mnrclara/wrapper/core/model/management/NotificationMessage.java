package com.mnrclara.wrapper.core.model.management;

import lombok.Data;
import java.util.Date;

@Data
public class NotificationMessage {
    private Long notificationId;
    private String classId;
    private String clientId;
    private String clientUserId;
    private String title;
    private String message;
    private Boolean menu;
    private Boolean tab;
    private String orderType;

    private Long documentId = 0L;
    private Long deletionIndicator;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn ;
}

