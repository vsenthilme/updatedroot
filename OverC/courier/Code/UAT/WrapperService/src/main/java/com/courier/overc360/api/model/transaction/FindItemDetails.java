package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindItemDetails {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> masterAirwayBill;
    private List<String> partnerId;
    private List<String> houseAirwayBill;
    private List<String> pieceItemId;
    private List<String> pieceId;
    private List<String> imageRefId;
}
