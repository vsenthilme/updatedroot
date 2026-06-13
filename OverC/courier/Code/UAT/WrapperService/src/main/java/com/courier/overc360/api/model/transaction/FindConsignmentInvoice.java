package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindConsignmentInvoice {

    private List<String> companyId;
    private List<String> houseAirwayBill;
    private List<String> partnerMasterAirwayBill;
    private List<String> partnerHouseAirwayBill;
}
