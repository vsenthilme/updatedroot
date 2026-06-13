package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.List;

@Data
public class FindInventoryTable {

    List<String> languageId;
    List<String> companyId;
    List<String> customerId;
    List<String> houseAirwayBill;
}
