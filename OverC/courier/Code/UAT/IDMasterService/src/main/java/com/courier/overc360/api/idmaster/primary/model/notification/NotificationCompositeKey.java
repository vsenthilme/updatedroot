package com.courier.overc360.api.idmaster.primary.model.notification;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * ' LANG_ID' , ' C_ID ' , ' NOTIFICATION_ID '
     */

    private String languageId;
    private String companyId;
    private String notificationId;
}

