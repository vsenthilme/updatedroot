package com.courier.overc360.api.model.lastmile;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindDrs {

    List<String> languageId;
    List<String> companyId;
    List<String> customerId;
    List<String> houseAirwayBill;
    List<String> deliveryId;
    List<String> pieceId;
}
