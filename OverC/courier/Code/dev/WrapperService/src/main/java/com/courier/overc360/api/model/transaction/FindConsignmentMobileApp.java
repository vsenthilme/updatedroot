package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindConsignmentMobileApp {

    private String languageId;
    private String companyId;
    //    private List<String> partnerId;
//    private List<String> masterAirwayBill;
//    private List<String> houseAirwayBill;
//    private List<String> pieceId;
//    private List<String> pieceItemId;
//    private List<String> imageRefId;
//    private List<String> statusId;
//    private List<String> shipperId;
//    private List<String> partnerHouseAirwayBill;
//    private List<String> partnerMasterAirwayBill;
    private String hawbTypeId;

    // for mobile App
    private String shippingLabelNo;

}
