package com.courier.overc360.api.midmile.replica.model.debrief;

import lombok.Data;

import java.util.List;

@Data
public class FindDebrief {

    List<String> languageId;
    List<String> companyId;
    List<String> courierId;
}
