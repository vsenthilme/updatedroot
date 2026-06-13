package com.courier.overc360.api.midmile.replica.model.rescheduledelivery;

import lombok.Data;

import java.util.List;

@Data
public class FindRescheduleDelivery {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> deliveryId;
    private List<Long> rescheduleNo;
    private List<String> houseAirwayBill;
}
