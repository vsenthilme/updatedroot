package com.courier.overc360.api.midmile.replica.model.inventorytable;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindInventoryTable {

    List<String> languageId;
    List<String> companyId;
    List<String> customerId;
    List<String> houseAirwayBill;
    List<String> hubCode;

}
