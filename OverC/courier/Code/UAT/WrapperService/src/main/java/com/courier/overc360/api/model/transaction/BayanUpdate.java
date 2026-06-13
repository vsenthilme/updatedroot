package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class BayanUpdate {
    
    private String languageId;

    private String companyId;
    
    private String partnerId;

    private String partnerHouseAirwayBill;

    private String partnerMasterAirwayBill;

    private Double bayanHv;
    
}