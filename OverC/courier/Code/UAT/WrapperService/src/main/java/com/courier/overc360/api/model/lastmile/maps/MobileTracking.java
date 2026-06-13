package com.courier.overc360.api.model.lastmile.maps;


import lombok.Data;

import java.util.Date;

@Data
public class MobileTracking {
    private String userId;
    private Double latitude;
    private Double longitude;
    private Date fromDate;
    private Date toDate;
    private String mobileNo;
}
