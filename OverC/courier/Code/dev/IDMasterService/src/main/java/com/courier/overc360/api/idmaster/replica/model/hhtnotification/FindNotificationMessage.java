package com.courier.overc360.api.idmaster.replica.model.hhtnotification;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindNotificationMessage {

    private List<Long> notificationId;
    private List<String> companyId;
    private List<String> languageId;
    private List<String> houseAirwayBill;
    private List<String> clientUserId;
    private List<String> orderType;

    private Date startDate;
    private Date endDate;

}
