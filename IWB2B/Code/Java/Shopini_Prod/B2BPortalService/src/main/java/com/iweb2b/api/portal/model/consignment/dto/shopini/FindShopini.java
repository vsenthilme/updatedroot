package com.iweb2b.api.portal.model.consignment.dto.shopini;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindShopini {

    private List<String> referenceNumber;
    private List<String> trackingNo;
    private List<String> shipmentStatus;
    private List<String> itemActionName;
    private List<Long> lmdStatus;

    private Date startDate;
    private Date EndDate;
}
