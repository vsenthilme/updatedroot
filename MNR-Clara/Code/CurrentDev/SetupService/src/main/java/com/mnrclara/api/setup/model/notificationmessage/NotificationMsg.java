package com.mnrclara.api.setup.model.notificationmessage;


import lombok.Data;

import java.util.List;

@Data
public class NotificationMsg {

    private Long classId;
    private List<String> token;
    private String title;
    private String message;
    private String clientUserId;
}
