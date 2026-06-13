package com.courier.overc360.api.idmaster.replica.model.timeslot;

import lombok.Data;

import java.util.List;


@Data
public class FindTimeSlot {


    List<String> languageId;
    List<String> companyId;
    List<String> timeSlotId;
}
