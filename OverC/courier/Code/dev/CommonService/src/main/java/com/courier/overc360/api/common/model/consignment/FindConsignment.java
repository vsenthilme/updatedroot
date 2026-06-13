package com.courier.overc360.api.common.model.consignment;

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
    private List<String> imageRefId;
    private List<String> statusId;
    private List<String> shipperId;
    private List<String> partnerHouseAirwayBill;
    private List<String> partnerMasterAirwayBill;
    private List<Long> consignmentId;
    private Date startDate;
    private Date endDate;

}