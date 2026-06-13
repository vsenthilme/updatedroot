package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindRiderAssignment {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> masterAirwayBill;
    private List<String> houseAirwayBill;
    private List<String> riderId;
    private List<String> pickupId;
    private List<String> serviceTypeId;

    private Date fromCreatedOn;
    private Date toCreatedOn;

}
