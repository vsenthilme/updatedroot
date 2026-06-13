package com.courier.overc360.api.model.lastmile;


import lombok.Data;

import java.util.List;

@Data
public class FindReschedulePickup {

    List<String> languageId;
    List<String> companyId;
    List<String> pickupId;
}
