package com.tekclover.wms.api.idmaster.model.hhtnotification;

import lombok.Data;

<<<<<<< HEAD
import java.util.Date;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
import java.util.List;

@Data
public class FindHhtNotification {
<<<<<<< HEAD
    private List<String> companyCodeId;
    private List<String> plantId;
    private List<String> languageId;
    private List<String> warehouseId;
    private List<String> deviceId;
    private List<String> userId;
    private Boolean isLoggedIn;
    private Boolean portalUser;
    private Date startDate;
    private Date endDate;
}
=======
    private List<String> cityId;
    private String cityName;
    private String stateId;
    private String countryId;
    private List<String>languageId;
}
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
