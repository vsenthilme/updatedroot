package com.courier.overc360.api.midmile.replica.model.reschedulepickup;


import lombok.Data;

import java.util.List;

@Data
public class FindReschedulePickup {

    List<String> languageId;
    List<String> companyId;
    List<String> pickupId;
    List<Long> pickupEntityId;
}
