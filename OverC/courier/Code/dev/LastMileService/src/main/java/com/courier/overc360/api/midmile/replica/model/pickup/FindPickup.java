package com.courier.overc360.api.midmile.replica.model.pickup;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class FindPickup {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> houseAirwayBill;
    private List<String> pickupId;
    private List<String> statusId;
    private List<String> courierId;
    private List<String> assignedBy;
    private String consignmentCreation;
    private Date fromCreatedOn;
    private Date toCreatedOn;
    private List<String> createdBy;
    private List<String> partnerName;
    private List<Long> pickupEntityId;
}
