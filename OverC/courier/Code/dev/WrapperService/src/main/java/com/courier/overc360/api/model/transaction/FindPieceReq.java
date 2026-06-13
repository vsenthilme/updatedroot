package com.courier.overc360.api.model.transaction;


import lombok.Data;

import java.util.List;

@Data
public class FindPieceReq {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> statusId;
    private List<String> houseAirwayBill;
    private List<String> pickupId;

}
