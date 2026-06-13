package com.courier.overc360.api.midmile.replica.model.consignment;

import lombok.Data;

import java.util.List;

@Data
public class FindConsignmentInvoice {

    private List<String> companyId;
    private List<String> houseAirwayBill;
    private List<String> partnerMasterAirwayBill;
    private List<String> partnerHouseAirwayBill;
}
