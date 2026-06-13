package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindClearanceCharges {

    List<Long> clearanceChargesId;
    List<String> partnerId;
    List<String> companyId;
    List<String> languageId;
    List<String> subCustomerId;
}
