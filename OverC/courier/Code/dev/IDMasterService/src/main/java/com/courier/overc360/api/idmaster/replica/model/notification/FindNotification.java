package com.courier.overc360.api.idmaster.replica.model.notification;

import lombok.Data;

import java.util.List;

@Data
public class FindNotification {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> notificationId;

}
