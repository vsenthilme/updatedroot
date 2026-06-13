package com.mnrclara.api.management.model.hhtnotification;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindNotificationMessage {

    private List<Long> notificationId;
    private List<String> classId;
    private List<String> clientId;
    private List<String> clientUserId;
    private List<String> orderType;

    private Date startDate;
    private Date endDate;

}
