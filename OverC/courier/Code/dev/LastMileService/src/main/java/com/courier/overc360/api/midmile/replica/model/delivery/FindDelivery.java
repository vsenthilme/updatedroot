package com.courier.overc360.api.midmile.replica.model.delivery;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindDelivery {

    List<String> companyId;
    List<String> languageId;
    List<String> pieceId;
    List<String> houseAirwayBill;
    List<String> deliveryId;
    List<String> courierId;
    List<String> statusId;
    private Date fromDate;
    private Date toDate;
}
