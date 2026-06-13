package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindCustomsClearanceInvoice {

    private List<String> languageId;

    private List<String> companyId;

    private List<String> partnerHouseAirwayBill;

    private List<String> houseAirwayBill;

    private List<String> invoiceNo;
}
