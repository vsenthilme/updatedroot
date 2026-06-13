package com.courier.overc360.api.midmile.replica.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FindIConsignment {

    private List<Long> consignmentId;
    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> masterAirwayBill;
    private List<String> houseAirwayBill;
    private List<String> statusId;
    private List<String> shipperId;
    private List<String> partnerHouseAirwayBill;
    private List<String> partnerMasterAirwayBill;
}