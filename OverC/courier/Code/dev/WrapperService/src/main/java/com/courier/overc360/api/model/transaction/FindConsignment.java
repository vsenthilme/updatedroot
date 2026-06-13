package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindConsignment {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> masterAirwayBill;
    private List<String> houseAirwayBill;
    private List<String> pieceId;
    private List<String> pieceItemId;
    private List<String> statusId;
    private List<String> shipperId;
    private List<String> hubCode;
    private List<String> partnerHouseAirwayBill;
    private List<String> partnerMasterAirwayBill;

    private Date fromDate;
    private Date toDate;

    // for mobile App
    private List<String> shippingLabelNo;

}
